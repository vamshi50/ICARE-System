package com.icare.db.repositories;

import com.icare.db.entities.User;
import com.icare.db.entities.Worker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT w.id FROM Worker w)")
    List<Object> findByUserId(Long id);
}
