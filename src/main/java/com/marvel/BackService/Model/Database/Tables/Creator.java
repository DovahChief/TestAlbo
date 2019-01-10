package com.marvel.BackService.Model.Database.Tables;

import javax.persistence.*;

@Entity
public class Creator {

    @Id
    @Column(name = "idcreators")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idcreators;

    @Column(name = "CreatorName")
    private String creatorName;

    @Column(name = "CreatorRole")
    private String CreatorRole;

    @Column(name = "hero")
    private long hero;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.creatorName = CreatorName;
    }

    public String getCreatorRole() {
        return CreatorRole;
    }

    public void setCreatorRole(String CreatorRole) {
        this.CreatorRole = CreatorRole;
    }

    public long getHero() {
        return hero;
    }

    public void setHero(long hero) {
        this.hero = hero;
    }
}
