package com.example.drugapi.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesDetailDto {
    private CategoryDto categoryLv1;
    private List<CategoryDto> categoryLv2;
    private List<CategoryDto> categoryLv3;
}
