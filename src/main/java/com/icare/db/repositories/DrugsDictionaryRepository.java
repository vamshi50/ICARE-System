package com.icare.db.repositories;

import com.icare.db.entities.DrugsDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugsDictionaryRepository extends JpaRepository<DrugsDictionary, Long> {
}
