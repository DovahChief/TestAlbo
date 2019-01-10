package com.marvel.BackService.Model.Database.Tables;

import javax.persistence.*;

@Entity
public class Creator {

    @Id
    @Column(name = "idcreators")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idcreators;

    @Column(name = "creator_name")
    private String creator_name;

    @Column(name = "creator_role")
    private String creator_role;

    @Column(name = "hero")
    private long hero;

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_role() {
        return creator_role;
    }

    public void setCreator_role(String creator_role) {
        this.creator_role = creator_role;
    }
}
