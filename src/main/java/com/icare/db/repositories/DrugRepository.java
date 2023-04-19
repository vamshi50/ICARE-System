package com.icare.db.repositories;

import com.icare.db.entities.DrugsDictionary;
import org.springframework.data.repository.CrudRepository;

public interface DrugRepository extends CrudRepository<DrugsDictionary, Long> {
}
