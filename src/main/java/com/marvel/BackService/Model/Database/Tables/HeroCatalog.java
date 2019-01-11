package com.marvel.BackService.Model.Database.Tables;

import javax.persistence.*;
import java.util.List;

@Entity
public class HeroCatalog {

    @Id
    @Column(name = "idHeroCatalog")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idHeroCatalog;

    @Column(name = "HeroName")
    private String heroName;


    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="hero")
    private List<Creator> creators;

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        heroName = heroName;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    public void setCreators(List<Creator> creators) {
        this.creators = creators;
    }

    public Long getIdHeroCatalog() {
        return idHeroCatalog;
    }
}
