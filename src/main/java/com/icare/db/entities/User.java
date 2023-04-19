package com.icare.db.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
    private @Id @GeneratedValue Long id;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        if (this instanceof Worker) {
            return "worker";
        } else if (this instanceof Admin) {
            return "admin";
        } else {
            return "unknown";
        }
    }

    @Override
    public String toString() {
        return "(" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ")";
    }
}