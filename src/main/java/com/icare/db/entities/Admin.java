package com.icare.db.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Admin extends User {
    private String adminEmail;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateHired;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateFinished;

    @Override
    public String toString() {
        return "Admin(adminEmail='" + adminEmail + "')";
    }
}
