package com.icare.db.entities;

import com.icare.db.TreatmentRecordDrugId;

import javax.persistence.*;

@Entity
public class TreatmentRecordDrug {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "treatmentId")
    private TreatmentRecord treatmentRecord;

    @ManyToOne
    @JoinColumn(name = "drugId")
    private DrugsDictionary drug;

    public TreatmentRecordDrug() {
    }

    public TreatmentRecordDrug(TreatmentRecord treatmentRecord, DrugsDictionary drug) {
        this.treatmentRecord = treatmentRecord;
        this.drug = drug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentRecord getTreatmentRecord() {
        return treatmentRecord;
    }

    public void setTreatmentRecord(TreatmentRecord treatmentRecord) {
        this.treatmentRecord = treatmentRecord;
    }

    public DrugsDictionary getDrug() {
        return drug;
    }

    public void setDrug(DrugsDictionary drug) {
        this.drug = drug;
    }

    @Override
    public String toString() {
        return "TreatmentRecordDrug{" +
                "treatmentRecord=" + treatmentRecord +
                ", drug=" + drug +
                '}';
    }
}
