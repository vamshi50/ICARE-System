package com.icare.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ModificationHistory {
    private @Id @GeneratedValue Long id;
    private Date dateOfModification;
    private String description;

    public ModificationHistory() {
    }

    public ModificationHistory(Date dateOfModification, String description) {
        this.dateOfModification = dateOfModification;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfModification() {
        return this.dateOfModification;
    }

    public void setDateOfModification(Date dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ModificationHistory(" +
            " id='" + getId() + "'" +
            ", dateOfModification='" + getDateOfModification() + "'" +
            ", description='" + getDescription() + "'" +
            ")";
    }
}
