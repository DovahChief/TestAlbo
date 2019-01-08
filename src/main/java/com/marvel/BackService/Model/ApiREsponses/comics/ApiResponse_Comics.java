package com.marvel.BackService.Model.ApiREsponses.comics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse_Comics extends ApiResponse {
    @JsonProperty("data")
    private Data_comics data_c;

    @JsonProperty("data")
    public Data_comics getData() {
        return data_c;
    }

    @JsonProperty("data")
    public void setData(Data_comics data) {
        this.data_c = data;
    }
}
