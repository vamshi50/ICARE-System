package com.icare.db.repositories;

import com.icare.db.entities.PatientRecord;
import com.icare.db.entities.TreatmentRecord;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRecordRepository extends CrudRepository<TreatmentRecord, Long> {
    Iterable<TreatmentRecord> findByPatientRecord(PatientRecord patientRecord);
}
