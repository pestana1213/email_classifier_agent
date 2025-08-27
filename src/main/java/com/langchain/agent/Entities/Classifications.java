package com.langchain.agent.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classifications")
@Builder
public class Classifications {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String messageId;

    private String classification;

    private String sender;

    private String subject;

    private String receivedAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
