package com.icare.db.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class TreatmentRecord {
    private @Id @GeneratedValue Long treatmentId;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date treatmentDate;
    @ManyToOne
    @JoinColumn(name = "id")
    private PatientRecord patientRecord;

    @OneToMany(mappedBy = "treatmentRecord", cascade = CascadeType.ALL)
    private List<TreatmentRecordDrug> drugs;

    @OneToMany(mappedBy = "treatmentRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentMetadata> documents;

    public TreatmentRecord() {
    }

    public TreatmentRecord(Long treatmentId, String description, Date treatmentDate, PatientRecord patientRecord, List<DocumentMetadata> documents, List<TreatmentRecordDrug> drugs) {
        this.treatmentId = treatmentId;
        this.description = description;
        this.treatmentDate = treatmentDate;
        this.patientRecord = patientRecord;
        this.documents = documents;
        this.drugs = drugs;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public PatientRecord getPatientRecord() {
        return patientRecord;
    }

    public void setPatientRecord(PatientRecord patientRecord) {
        this.patientRecord = patientRecord;
    }

    public List<DocumentMetadata> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentMetadata> documents) {
        this.documents = documents;
    }

    public List<TreatmentRecordDrug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<TreatmentRecordDrug> drugs) {
        this.drugs = drugs;
    }

    @Override
    public String toString() {
        return "TreatmentRecord(treatmentId='" + treatmentId + "')";
    }

    @Override
    public int hashCode() {
        int result = treatmentId != null ? treatmentId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (treatmentDate != null ? treatmentDate.hashCode() : 0);
        result = 31 * result + (patientRecord != null ? patientRecord.hashCode() : 0);
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        result = 31 * result + (drugs != null ? drugs.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TreatmentRecord other = (TreatmentRecord) obj;
        if (treatmentId == null) {
            if (other.treatmentId != null) {
                return false;
            }
        } else if (!treatmentId.equals(other.treatmentId)) {
            return false;
        }
        return true;
    }
}
