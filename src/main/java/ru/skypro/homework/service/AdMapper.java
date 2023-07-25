package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdMapper {
    public AdDTO adToAdDTO(Ad ad) {
        return new AdDTO(ad.getAuthor().getId(),
                ad.getImage(),
                ad.getPk(),
                ad.getPrice(),
                ad.getTitle());
    }

    public List<AdDTO> adListToAdDTOList(List<Ad> adList) {
        List<AdDTO> adDTOList = new ArrayList<>();
        for (Ad ad : adList) {
            adDTOList.add(adToAdDTO(ad));
        }
        return adDTOList;
    }

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
