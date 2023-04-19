package com.icare.db.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PatientRecord {
    private @Id @GeneratedValue Long id;
    private String name;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private double height;
    private double weight;
    private String bloodGroup;
    private String bedId;
    private String treatmentArea;
    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public PatientRecord() {
    }

    public PatientRecord(String name, String address, Date dateOfBirth, double height, double weight, String bloodGroup, String bedId, String treatmentArea, Worker worker) {
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.bloodGroup = bloodGroup;
        this.bedId = bedId;
        this.treatmentArea = treatmentArea;
        this.worker = worker;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBloodGroup() {
        return this.bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBedId() {
        return this.bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getTreatmentArea() {
        return this.treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    @Override
    public String toString() {
        return "PatientRecord(id=" + this.getId() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", dateOfBirth=" + this.getDateOfBirth() + ", height=" + this.getHeight() + ", weight=" + this.getWeight() + ", bloodGroup=" + this.getBloodGroup() + ", bedId=" + this.getBedId() + ", treatmentArea=" + this.getTreatmentArea() + ")";
    }
}
