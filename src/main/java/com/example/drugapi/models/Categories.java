package com.example.drugapi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Categories")
public class Categories {
    @Id
    private Integer id;
    private String name;
    private String image;
    private Integer level;
    private String slug;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
