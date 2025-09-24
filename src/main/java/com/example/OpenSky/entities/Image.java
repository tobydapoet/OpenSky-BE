package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableTypeImage tableType;

    @Column(nullable = false)
    private String typeId;

    private String url;
}
