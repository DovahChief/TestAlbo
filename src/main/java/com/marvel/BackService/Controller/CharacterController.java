package com.marvel.BackService.Controller;


import com.marvel.BackService.ApiCallService.ExternalApiRequest;
import com.marvel.BackService.Model.ApiREsponses.comics.*;
import com.marvel.BackService.Model.JsonOut.CharacterItem;
import com.marvel.BackService.Model.JsonOut.CharacterOut;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);
    private ExternalApiRequest apiRequest = new ExternalApiRequest();
    private final String baseUrl = "https://gateway.marvel.com:443/v1/public/";

    @GetMapping("/heroinfo/{name}")
    public ResponseEntity<List<ApiResult>>  getCharacters(@PathVariable String name){
        try {
            List<ApiResult> results = this.apiRequest.GetRequest(baseUrl +"characters", ApiResponse.class, name).getData().getResults();
            log.info("Succesfully retrieved Data");
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<CharacterOut>  getRelatedCharacters(@PathVariable String name){
        try {
            List<ApiResult> results = this.apiRequest.GetRequest(baseUrl +"characters", ApiResponse.class, name).getData().getResults();
            String comicsUrl = results.get(0).getComics().getCollectionURI();
            ApiResponse_Comics comics = this.apiRequest.GetRequest(comicsUrl, ApiResponse_Comics.class);
            log.info("Succesfully retrieved Data");
            CharacterOut response = formatOutput(extractCharacters(comics.getData().getResult()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    private List<Item> extractCharacters(List<Comic> comics) {
        return comics.stream()
                .map(e -> {
                    e.getCharacters().setCollectionURI(e.getTitle());
                    return e.getCharacters();
                })
                .map(e -> {
                    e.getItems().forEach(e_ -> e_.setRole(e.getCollectionURI()));
                    return e.getItems();
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private CharacterOut formatOutput(List<Item> characters){
        CharacterOut response = new CharacterOut();
        response.setCharacters(new ArrayList<>());
        for (Item character: characters){
            if (!findCharacter(response.getCharacters(), character.getName())) {
                CharacterItem c = new CharacterItem();
                c.setCharacter(character.getName());
                c.setComics( characters.stream()
                        .filter(e -> e.getName().equals(character.getName()))
                        .map(Item::getRole)
                        .collect(Collectors.toList())
                );
                response.getCharacters().add(c);
            }
        }
        return response;
    }

    private boolean findCharacter( List<CharacterItem> items, String  character) {
        for(CharacterItem i : items) {
            if(i.getCharacter().equals(character)) {
                return true;
            }
        }
        return false;
    }
}
