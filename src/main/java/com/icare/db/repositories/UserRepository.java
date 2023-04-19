package com.icare.db.repositories;

import org.springframework.data.repository.CrudRepository;
import com.icare.db.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
