package com.icare.db.repositories;

import com.icare.db.entities.DocumentMetadata;
import com.icare.db.entities.PatientRecord;
import com.icare.db.entities.TreatmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {
    @Query("SELECT d FROM DocumentMetadata d WHERE d.treatmentRecord = ?1")
    List<DocumentMetadata> findByTreatmentRecord(TreatmentRecord patientRecord);
}
