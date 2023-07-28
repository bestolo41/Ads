package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.NoResultsException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.dao.AdDAO;
import ru.skypro.homework.service.mapper.AdMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {
    private final UserService userService;
    private final AdMapper adMapper;
    private final AdDAO adDAO;
    private final ImageService imageService;

    public AdService(UserService userService, AdMapper adMapper, AdDAO adDAO, ImageService imageService) {
        this.userService = userService;
        this.adMapper = adMapper;
        this.adDAO = adDAO;
        this.imageService = imageService;
    }

    public AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image) throws UserNotAuthorizedException, IOException {
        User user = userService.getAuthorizedUser();
        Ad newAd = new Ad();
        newAd.setDescription(properties.getDescription());
        newAd.setTitle(properties.getTitle());
        newAd.setPrice(properties.getPrice());
        newAd.setAuthor(user);
        newAd.setImage(imageService.uploadAdImage(properties.getTitle(), image));

        adDAO.addAd(newAd);

        return adMapper.adToAdDTO(newAd);
    }

    public AdsDTO getAdsMe() throws UserNotAuthorizedException {
        User user = userService.getAuthorizedUser();

        List<Ad> myAds = adDAO.getAllAds().stream()
                .filter(ad -> ad.getAuthor().getId() == user.getId())
                .collect(Collectors.toList());

        return new AdsDTO(myAds.size(),
                adMapper.adListToAdDTOList(myAds));
    }

    public AdsDTO getAllAds() {
        List<Ad> allAds = adDAO.getAllAds();

        return new AdsDTO(allAds.size(), adMapper.adListToAdDTOList(allAds));
    }

    public ExtendedAdDTO getAdInformation(int id) {
        return adMapper.adToExtendedAdDTO(adDAO.getAdById(id));
    }

    public void deleteAd(int id) throws UserNotAuthorizedException, NoAccessException {
        User user = userService.getAuthorizedUser();
        Ad ad = adDAO.getAdById(id);

        if (ad.getAuthor().getId() == user.getId() || user.getRole().equals(Role.ADMIN)) {
            adDAO.removeAd(ad);
        } else {
            throw new NoAccessException("No access");
        }
    }

    public AdDTO updateAd(int id, CreateOrUpdateAdDTO adDTO) throws UserNotAuthorizedException, NoAccessException {
        User user = userService.getAuthorizedUser();
        Ad ad = adDAO.getAdById(id);

        if (ad.getAuthor().getId() == user.getId() || user.getRole().equals(Role.ADMIN)) {
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());
            ad.setTitle(ad.getTitle());
            adDAO.updateAd(ad);
            return adMapper.adToAdDTO(ad);
        } else {
            throw new NoAccessException("No access");
        }

    }

    public byte[] updateAdImage(int adId, MultipartFile image) throws UserNotAuthorizedException, IOException {
        Ad ad = adDAO.getAdById(adId);
        ad.setImage(imageService.uploadAdImage(ad.getTitle(), image));
        adDAO.updateAd(ad);
        return image.getBytes();
    }

    public AdsDTO getAdsWithTitleLike(String query) throws NoResultsException {
        List<AdDTO> list = adDAO.getAllAds().stream()
                .filter(ad -> ad.getTitle().contains(query))
                .map(adMapper::adToAdDTO)
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            return new AdsDTO(list.size(), list);
        } else {
            throw new NoResultsException("Результатов по запросу нет");
        }
    }
}
