package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.DAO.AdDAO;
import ru.skypro.homework.service.mapper.AdMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {
    private final UserService userService;
    private final AdMapper adMapper;
    private final AdDAO adDAO;

    public AdService(UserService userService, AdMapper adMapper, AdDAO adDAO) {
        this.userService = userService;
        this.adMapper = adMapper;
        this.adDAO = adDAO;
    }

    public AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image) throws UserNotAuthorizedException {
        User user = userService.getAuthorizedUser();
        Ad newAd = new Ad();
        newAd.setDescription(properties.getDescription());
        newAd.setTitle(properties.getTitle());
        newAd.setPrice(properties.getPrice());
        newAd.setAuthor(user);
        newAd.setImage(image.getOriginalFilename());

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

    public void deleteAd(int id) {
        Ad ad = adDAO.getAdById(id);
        adDAO.removeAd(ad);
    }

    public AdDTO updateAd(int id, CreateOrUpdateAdDTO adDTO) {
        Ad ad = adDAO.getAdById(id);
        ad.setDescription(adDTO.getDescription());
        ad.setPrice(adDTO.getPrice());
        ad.setTitle(ad.getTitle());

        adDAO.updateAd(ad);

        return adMapper.adToAdDTO(ad);
    }
}
