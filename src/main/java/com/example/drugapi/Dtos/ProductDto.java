package com.example.drugapi.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private Double price;
    private Double salePrice;
    private String description;
    private String longDescription;
    private String slug;
    private List<ImageDto> images;
    private List<CategoryDto> category;
}