package com.example.drugapi.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "images")
public class Images {
    @Id
    private Integer id;
    private String url;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
