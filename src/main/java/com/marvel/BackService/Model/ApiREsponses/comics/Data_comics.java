package com.marvel.BackService.Model.ApiREsponses.comics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Data_comics extends Data {

    @JsonProperty("results")
    private List<Comic> result = null;

    @JsonProperty("results")
    public List<Comic> getResult() {
        return result;
    }

    @JsonProperty("results")
    public void setResult(List<Comic> results) {
        this.result = results;
    }

}
