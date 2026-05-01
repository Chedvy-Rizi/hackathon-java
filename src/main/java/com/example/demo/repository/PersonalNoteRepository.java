package com.example.demo.repository;

import com.example.demo.model.PersonalNote;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface PersonalNoteRepository extends JpaRepository<PersonalNote, Long> {
    List<PersonalNote> findByAgent(User agent);

    // --- הפונקציה שהוספנו למחיקת פתקיות לפי נציג ---
    @Modifying
    @Transactional
    void deleteByAgentId(Long agentId);
}