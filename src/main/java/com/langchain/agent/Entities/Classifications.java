package com.langchain.agent.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classifications")
public class Classifications {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String classification;

    private String sender;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
