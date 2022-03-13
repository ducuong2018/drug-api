package com.example.drugapi.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
