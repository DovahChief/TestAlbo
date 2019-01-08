package com.marvel.BackService.Model.JsonOut;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "last_sync",
        "editors",
        "writers",
        "colorists"
})
public class ColaboratorsOut {
    @JsonProperty("last_sync")
    private String lastSync;
    @JsonProperty("editors")
    private List<String> editors = null;
    @JsonProperty("writers")
    private List<String> writers = null;
    @JsonProperty("colorists")
    private List<String> colorists = null;

    @JsonProperty("last_sync")
    public String getLastSync() {
        return lastSync;
    }

    @JsonProperty("last_sync")
    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    @JsonProperty("editors")
    public List<String> getEditors() {
        return editors;
    }

    @JsonProperty("editors")
    public void setEditors(List<String> editors) {
        this.editors = editors;
    }

    @JsonProperty("writers")
    public List<String> getWriters() {
        return writers;
    }

    @JsonProperty("writers")
    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    @JsonProperty("colorists")
    public List<String> getColorists() {
        return colorists;
    }

    @JsonProperty("colorists")
    public void setColorists(List<String> colorists) {
        this.colorists = colorists;
    }

}

