package com.example.drugapi.Dtos;

import lombok.Data;

@Data
public class CategoryDto {
    private Integer id;
    private String name;
    private String image;
    private Integer level;
    private Integer parentId;
    private String slug;
}
