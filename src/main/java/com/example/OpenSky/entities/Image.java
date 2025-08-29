package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TableTypeImage getTableType() {
        return tableType;
    }

    public void setTableType(TableTypeImage tableType) {
        this.tableType = tableType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
