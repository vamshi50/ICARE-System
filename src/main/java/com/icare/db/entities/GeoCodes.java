package com.icare.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GeoCodes {
    private @Id @GeneratedValue Long id;
    private String description;

    public GeoCodes() {
    }

    public GeoCodes(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GeoCodes(" +
            " id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ")";
    }
}
