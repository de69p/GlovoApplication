package com.example.glovoapplication.repository;

import com.example.glovoapplication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User getUserByUsername(String username);
}

