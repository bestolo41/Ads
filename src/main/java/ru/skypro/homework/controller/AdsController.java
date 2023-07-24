package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        AdsDTO allAds = new AdsDTO();
//        AdDTO ad = new AdDTO();
//        ad.setAuthor(1);
//        ad.setPk(3);
//        ad.setImage("fdfbdfbdf");
//        ad.setTitle("phone");
//        ad.setPrice(1000);
//        List<AdDTO> list = new ArrayList<>();
//        list.add(ad);
//        allAds.setResults(list);
        return ResponseEntity.ok().body(allAds);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart CreateOrUpdateAdDTO properties, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok().body(new AdDTO());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении")
    @ApiResponse(responseCode = "200",
            description = "Операция успешна")
    @ApiResponse(responseCode = "401",
            description = "Ошибка авторизации")
    public ResponseEntity<ExtendedAdDTO> getAds(@PathVariable Integer id) {
        ExtendedAdDTO ad = new ExtendedAdDTO();
        return ResponseEntity.ok().body(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<AdDTO> addAd(@PathVariable int id, @RequestBody MultipartFile image) {
        return ResponseEntity.ok().body(new AdDTO());
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe() {
        AdsDTO allAds = new AdsDTO();
        return ResponseEntity.ok().body(allAds);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateImage(@PathVariable int id, @RequestBody CreateOrUpdateAdDTO updateAd) {
        return ResponseEntity.ok().body(new AdDTO());
    }
}
