package com.example.bitter.repository;

import com.example.bitter.entity.Hashtag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository <Hashtag, Integer>{
    Optional<Hashtag> findByLabel(String label);
}
