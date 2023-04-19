package com.icare.db.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DocumentMetadata {
    private  @Id @GeneratedValue Long docId;
    private String docName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateOfCreation;

    @ManyToOne
    @JoinColumn(name = "treatmentId")
    private TreatmentRecord treatmentRecord;

    public DocumentMetadata() {
    }

    public DocumentMetadata(Long docId, String docName, Date dateOfCreation, TreatmentRecord treatmentRecord) {
        this.docId = docId;
        this.docName = docName;
        this.dateOfCreation = dateOfCreation;
        this.treatmentRecord = treatmentRecord;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public TreatmentRecord getTreatmentRecord() {
        return treatmentRecord;
    }

    public void setTreatmentRecord(TreatmentRecord treatmentRecord) {
        this.treatmentRecord = treatmentRecord;
    }

    public String getJoinedName() {
        return treatmentRecord.getTreatmentId() + "_" + docId;
    }

    @Override
    public String toString() {
        return "DocumentMetadata(docId='" + docId + "' docName=" + docName + "')";
    }
}
