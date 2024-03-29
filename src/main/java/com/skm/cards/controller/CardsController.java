package com.skm.cards.controller;

import com.skm.cards.constants.CardsConstants;
import com.skm.cards.dto.CardsContactInfoDto;
import com.skm.cards.dto.CardsDto;
import com.skm.cards.dto.ResponseDto;
import com.skm.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@EnableConfigurationProperties(value = CardsContactInfoDto.class)
public class CardsController {
    private ICardsService cardsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    Environment environment;

    @Autowired
    CardsContactInfoDto cardsContactInfoDto;

    public CardsController(ICardsService cardsService) {
        this.cardsService = cardsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Number should contain 10 digits")
            @RequestParam String mobileNumber) {
        cardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Number should contain 10 digits")
            @RequestParam String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(
            @Valid
            @RequestBody CardsDto cardsDto) {
        if (cardsService.updateCard(cardsDto)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Number should contain 10 digits")
            @RequestParam String mobileNumber) {
        if (cardsService.deleteCard(mobileNumber)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/cards-info")
    public ResponseEntity<CardsContactInfoDto> getCardsInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
