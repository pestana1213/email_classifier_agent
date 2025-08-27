package com.langchain.agent.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Classifications {

    @Id
    private UUID id;

    private String classification;

    private String sender;

    private LocalDateTime createdAt;
}
