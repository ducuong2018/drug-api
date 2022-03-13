package com.example.drugapi.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "products")
public class Products {
    @Id
    private Integer id;
    private String name;
    private Double price;
    @Column(name = "sale_price")
    private Double salePrice;
    private String description;
    @Column(name = "long_description")
    private String longDescription;
    private String slug;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;}
