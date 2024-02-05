package com.kodilla.Flashcard.repository;

import com.kodilla.Flashcard.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>{
}
