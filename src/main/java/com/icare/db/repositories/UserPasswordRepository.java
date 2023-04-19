package com.icare.db.repositories;

import com.icare.db.entities.UserPassword;
import org.springframework.data.repository.CrudRepository;

public interface UserPasswordRepository extends CrudRepository<UserPassword, Long> {
    UserPassword findByUserName(String username);
}
