package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.NoResultsException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.status(HttpStatus.OK).body(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart CreateOrUpdateAdDTO properties, @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adService.addAd(properties, image));
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAds(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adService.getAdInformation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeAd(@PathVariable int id) {
        try {
            adService.deleteAd(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAdDTO updateAd) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adService.updateAd(id, updateAd));
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adService.getAdsMe());
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping(value = "/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> updateImage(@PathVariable int id, @RequestBody MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adService.updateAdImage(id, image));
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<AdsDTO> searchAdsByTitle(@RequestParam String query) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adService.getAdsWithTitleLike(query));
        } catch (NoResultsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
