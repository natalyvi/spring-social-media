package com.example.bitter.repository;

import com.example.bitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByCredentials_Username(String username);

    Boolean existsByCredentials_Username(String username);

}
