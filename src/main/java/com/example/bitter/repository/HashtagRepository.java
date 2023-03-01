package com.example.bitter.repository;

import com.example.bitter.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository <Hashtag, Long>{
    Optional<Hashtag> findByLabel(String label);
}
