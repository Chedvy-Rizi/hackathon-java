package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal_notes")
@Data
public class PersonalNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // הקישור הייחודי לנציג - ככה אף אחד לא רואה פתקים של אחרים!
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User agent;

    @Column(columnDefinition = "TEXT")
    private String content;

    // --- שדות חדשים שהוספנו במיוחד עבור ה-React ---
    private Double xPosition;
    private Double yPosition;
    private String color;
    private Boolean isDraft;

    private LocalDateTime createdAt = LocalDateTime.now();
}