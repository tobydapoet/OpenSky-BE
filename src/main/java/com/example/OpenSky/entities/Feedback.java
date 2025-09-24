package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "Feedback")
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableType tableType;

    @Column( nullable = false)
    private String tableId;

    @Min(0)
    @Max(5)
    @Column(name = "Rate")
    private Integer rate = 0;

    private String description;
}
