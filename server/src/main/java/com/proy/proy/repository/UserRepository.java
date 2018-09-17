package com.proy.proy.repository;

import com.proy.proy.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail (String email);
}
