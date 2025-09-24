package com.example.OpenSky.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue
    private String id;

    @Column( nullable = false)
    private String message = "";

    @CreationTimestamp
    @Column( nullable = false)
    private LocalDateTime createdAt;

    @Column( nullable = false)
    private boolean isReaded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User senderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private User receiverUser;

}
