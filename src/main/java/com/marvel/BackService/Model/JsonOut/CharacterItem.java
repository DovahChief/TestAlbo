package com.marvel.BackService.Model.JsonOut;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "character",
        "Comics"
})
public class CharacterItem {

    @JsonProperty("character")
    private String character;
    @JsonProperty("Comics")
    private List<String> Comics;

    @JsonProperty("character")
    public String getCharacter() {
        return character;
    }

    @JsonProperty("character")
    public void setCharacter(String character) {
        this.character = character;
    }

    @JsonProperty("Comics")
    public List<String> getComics() {
        return Comics;
    }

    @JsonProperty("Comics")
    public void setComics(List<String> comics) {
        Comics = comics;
    }
}
