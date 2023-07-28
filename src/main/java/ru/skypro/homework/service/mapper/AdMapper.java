package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для преобразования сущности Ad в различные DTO
 */
@Service
public class AdMapper {
    /**
     * Преобразование Ad -> AdDTO
     * @param ad - Ad
     * @return
     */
    public AdDTO adToAdDTO(Ad ad) {
        return new AdDTO(ad.getAuthor().getId(),
                ad.getImage(),
                ad.getPk(),
                ad.getPrice(),
                ad.getTitle());
    }

    /**
     * Преобразование коллекции с Ad в коллекцию с AdDTO
     * @param adList
     * @return
     */
    public List<AdDTO> adListToAdDTOList(List<Ad> adList) {
        List<AdDTO> adDTOList = new ArrayList<>();
        for (Ad ad : adList) {
            adDTOList.add(adToAdDTO(ad));
        }
        return adDTOList;
    }

    /**
     * Преобразование Ad -> ExtendedAdDTO
     * @param ad
     * @return
     */
    public ExtendedAdDTO adToExtendedAdDTO(Ad ad) {
        return new ExtendedAdDTO(ad.getPk(),
                ad.getAuthor().getFirstname(),
                ad.getAuthor().getLastname(),
                ad.getDescription(),
                ad.getAuthor().getUsername(),
                ad.getImage(),
                ad.getAuthor().getPhone(),
                ad.getPrice(),
                ad.getTitle());
    }
}
