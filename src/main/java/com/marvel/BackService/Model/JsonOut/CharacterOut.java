package com.marvel.BackService.Model.JsonOut;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "last_sync",
        "characters"
})
public class CharacterOut {
    @JsonProperty("last_sync")
    private String last_sync;
    @JsonProperty("characters")
    private List<CharacterItem> characters;

    @JsonProperty("last_sync")
    public String getLast_sync() {
        return last_sync;
    }
    @JsonProperty("last_sync")
    public void setLast_sync(String last_sync) {
        this.last_sync = last_sync;
    }
    @JsonProperty("characters")
    public List<CharacterItem> getCharacters() {
        return characters;
    }
    @JsonProperty("characters")
    public void setCharacters(List<CharacterItem> characters) {
        this.characters = characters;
    }
}
