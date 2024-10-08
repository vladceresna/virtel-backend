package com.vladceresna.virtel_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;
    private String name;
    private String description;
    private String code;
    private String authorEmail;//user
    private LocalDateTime createdTime;
    private int likes;
    @Transient
    private int tempSearchLevel;

}
