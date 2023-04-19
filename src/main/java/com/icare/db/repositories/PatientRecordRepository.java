package com.icare.db.repositories;

import com.icare.db.entities.PatientRecord;
import com.icare.db.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {
    Iterable<PatientRecord> findByWorker(Worker worker);

    @Query("SELECT pr FROM PatientRecord pr WHERE pr.worker IS NULL")
    Iterable<PatientRecord> findByNoWorker();
}
