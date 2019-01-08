package com.marvel.BackService.Controller;


import com.marvel.BackService.ApiCallService.ExternalApiRequest;
import com.marvel.BackService.Model.ApiREsponses.comics.ApiResponse;
import com.marvel.BackService.Model.ApiREsponses.comics.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);
    private ExternalApiRequest apiRequest = new ExternalApiRequest();
    private final String baseUrl = "https://gateway.marvel.com:443/v1/public/";

    @GetMapping("/{name}")
    public ResponseEntity<List<ApiResult>>  getCharacters(@PathVariable String name){
        List<ApiResult> results = null;
        try {
            results = this.apiRequest.GetRequest(baseUrl +"characters", ApiResponse.class, name).getData().getResults();
            log.info("Succesfully retrieved Data");
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
