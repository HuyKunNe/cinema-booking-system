package com.cinema.common.outbox.entity;


import com.cinema.common.outbox.enums.AggregateType;
import com.cinema.common.outbox.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AggregateType aggregateType;


    @Column(nullable = false)
    private Long aggregateId;


    @Column(nullable = false)
    private String eventType;


    @Lob
    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String payload;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status;


    private LocalDateTime processedAt;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    @PrePersist
    public void prePersist(){

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();

    }


    @PreUpdate
    public void preUpdate(){

        updatedAt = LocalDateTime.now();

    }

}