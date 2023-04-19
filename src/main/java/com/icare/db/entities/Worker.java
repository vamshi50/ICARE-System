package com.icare.db.entities;

import javax.persistence.Entity;


@Entity
public class Worker extends User {
    private String profession;

    public Worker() {
        super();
    }

    public Worker(String profession) {
        this.profession = profession;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "Worker(" +
            " profession='" + getProfession() + "'" +
            ")";
    }
}







