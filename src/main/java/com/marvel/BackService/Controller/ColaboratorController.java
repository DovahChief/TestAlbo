package com.marvel.BackService.Controller;


import com.marvel.BackService.ApiCallService.ExternalApiRequest;
import com.marvel.BackService.Model.ApiREsponses.comics.ApiResponse;
import com.marvel.BackService.Model.ApiREsponses.comics.ApiResponse_Comics;
import com.marvel.BackService.Model.ApiREsponses.comics.ApiResult;
import com.marvel.BackService.Model.ApiREsponses.comics.Item;
import com.marvel.BackService.Model.ApiREsponses.comics.Characters;
import com.marvel.BackService.Model.ApiREsponses.comics.Comic;
import com.marvel.BackService.Model.Database.Repository.CreatorsRepository;
import com.marvel.BackService.Model.Database.Repository.HeroCatalogRepository;
import com.marvel.BackService.Model.Database.Tables.Creator;
import com.marvel.BackService.Model.JsonOut.ColaboratorsOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/colaborators")
public class ColaboratorController {

    private static final Logger log = LoggerFactory.getLogger(ColaboratorController.class);
    private ExternalApiRequest apiRequest = new ExternalApiRequest();
    private final String baseUrl = "https://gateway.marvel.com:443/v1/public/";

    @Autowired
    private CharacterController characterController;

    @Autowired
    private CreatorsRepository creatorsRepository;

    @Autowired
    private HeroCatalogRepository heroCatalogRepository;

    @GetMapping("/byID/{id}")
    public ResponseEntity<List<ApiResult>> getColaboratos(@PathVariable Long id){
        List<ApiResult> results = null;
        try {
            results = this.apiRequest.GetRequest(baseUrl +"creators/" + id, ApiResponse.class).getData().getResults();
            log.info("Succesfully retrieved Data");
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{hero}")
    public ResponseEntity<ColaboratorsOut> getCreatorsFromHero(@PathVariable String hero) {
        try {
            Integer heroId = characterController.getCharacters(hero).getBody().get(0).getId();
            ApiResponse_Comics comics = this.apiRequest.GetRequest(baseUrl + "characters/" + heroId + "/comics", ApiResponse_Comics.class);
            List<Item> creators = this.extractCreators(comics.getData().getResult());
            saveColaborator(creators, hero);
            ColaboratorsOut output = this.formatOutput(creators);
            output.setLastSync(comics.getData().getResult().get(0).getModified());
            return  new ResponseEntity<>(output, HttpStatus.OK);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | IndexOutOfBoundsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    private List<Item> extractCreators(List<Comic> comics) {
        return comics.stream()
                .map(Comic::getCreators)
                .map(Characters::getItems)
                .flatMap(List::stream)
                .filter(e -> e.getRole().contains("editor") || e.getRole().contains("writer") || e.getRole().contains("colorist"))
                .collect(Collectors.toList());
    }

    private ColaboratorsOut formatOutput(List<Item> colaborators){
        ColaboratorsOut output = new ColaboratorsOut();
        output.setColorists(new ArrayList<>());
        output.setEditors(new ArrayList<>());
        output.setWriters(new ArrayList<>());

        colaborators.forEach(e -> {
            if(e.getRole().contains("writer") && !output.getWriters().contains(e.getName()))
                output.getWriters().add(e.getName());
            if(e.getRole().contains("colorist") && !output.getColorists().contains(e.getName()))
                output.getColorists().add(e.getName());
            if(e.getRole().contains("editor") && !output.getEditors().contains(e.getName()))
                output.getEditors().add(e.getName());
        });
        return output;
    }

    private void saveColaborator(List<Item> colaborators, String heroName){
        if (heroCatalogRepository.findByHeroName(heroName).isPresent()){
            colaborators.forEach(e -> {
                if(!creatorsRepository.findByCreatorName(e.getName()).isPresent()){
                    Creator c = new Creator();
                    c.setCreatorName(e.getName());
                    c.setCreatorRole(e.getRole());
                    c.setHero(heroCatalogRepository.findByHeroName(heroName).get().getIdHeroCatalog());
                    creatorsRepository.save(c);
                }
            });
        }
    }

}
