package com.example.drugapi.services;

import com.example.drugapi.Dtos.CategoriesDetailDto;
import com.example.drugapi.Dtos.CategoryDto;
import com.example.drugapi.exceptions.BadRequestException;
import com.example.drugapi.exceptions.Message;
import com.example.drugapi.models.Categories;
import com.example.drugapi.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public ResponseEntity<?> getCategories(String slug) throws IOException {
        if(slug != null) {
            Categories categories  = categoryRepository.findFirstBySlug(slug);
            if(Objects.isNull(categories)) {
                throw new BadRequestException(Message.NOT_FOUND);
            }
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(categories.getId());
                categoryDto.setImage(categories.getImage());
                categoryDto.setName(categories.getName());
                categoryDto.setParentId(categories.getParentId());
                categoryDto.setLevel(categories.getLevel());
                categoryDto.setSlug(categories.getSlug());
            return  ResponseEntity.ok(categoryDto);
        }
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Categories> categories;
        categories  = categoryRepository.findAll();
        for(Categories category : categories){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setImage(category.getImage());
            categoryDto.setName(category.getName());
            categoryDto.setParentId(category.getParentId());
            categoryDto.setLevel(category.getLevel());
            categoryDto.setSlug(category.getSlug());
            categoryDtos.add((categoryDto));
        }
        return  ResponseEntity.ok(categoryDtos);
    }
    public ResponseEntity<?> getCategoryFullLv(String slug) throws IOException {
        CategoriesDetailDto categoriesDetailDto = new CategoriesDetailDto();
        Categories categories = categoryRepository.findFirstBySlug(slug);
        CategoryDto categoryDtosLv1 = new CategoryDto();
        if(categories.getLevel() == 1) {
            categoryDtosLv1.setId(categories.getId());
            categoryDtosLv1.setSlug(categories.getSlug());
            categoryDtosLv1.setLevel(categories.getLevel());
            categoryDtosLv1.setParentId(categories.getParentId());
            categoryDtosLv1.setImage(categories.getImage());
            categoryDtosLv1.setName(categories.getName());

        }
        if((categories.getLevel() == 2)) {
            Categories categoriesLv1 = categoryRepository.getParentCategoriesById(categories.getId());
            categoryDtosLv1.setId(categoriesLv1.getId());
            categoryDtosLv1.setSlug(categoriesLv1.getSlug());
            categoryDtosLv1.setLevel(categoriesLv1.getLevel());
            categoryDtosLv1.setParentId(categoriesLv1.getParentId());
            categoryDtosLv1.setImage(categoriesLv1.getImage());
            categoryDtosLv1.setName(categoriesLv1.getName());
        }
        if(categories.getLevel() == 3) {
        Categories categoryLv2 = categoryRepository.getParentCategoriesById(categories.getId());
        Categories categoryLv1 = categoryRepository.getParentCategoriesById(categoryLv2.getId());
        categoryDtosLv1.setId(categoryLv1.getId());
        categoryDtosLv1.setSlug(categoryLv1.getSlug());
        categoryDtosLv1.setLevel(categoryLv1.getLevel());
        categoryDtosLv1.setParentId(categoryLv1.getParentId());
        categoryDtosLv1.setImage(categoryLv1.getImage());
        categoryDtosLv1.setName(categoryLv1.getName());
        }
        List<Categories> categoriesLv2 = categoryRepository.getChillCategoriesById(categoryDtosLv1.getId());
        log.info(String.valueOf(categoriesLv2));
        List<CategoryDto> categoryDtosLv3 = new ArrayList<>();
        List<CategoryDto> categoryDtosLv2 = new ArrayList<>();
        categoriesLv2.stream().forEach(value -> {
            CategoryDto categoryDtoLv2 = new CategoryDto();
            categoryDtoLv2.setId(value.getId());
            categoryDtoLv2.setSlug(value.getSlug());
            categoryDtoLv2.setLevel(value.getLevel());
            categoryDtoLv2.setParentId(value.getParentId());
            categoryDtoLv2.setImage(value.getImage());
            categoryDtoLv2.setName(value.getName());
            categoryDtosLv2.add(categoryDtoLv2);
            //
            List<Categories> categoryLv3 = categoryRepository.getChillCategoriesById(value.getId());
            categoryLv3.stream().forEach(lv3 -> {
                CategoryDto categoryDtoLv3 = new CategoryDto();
                categoryDtoLv3.setId(lv3.getId());
                categoryDtoLv3.setSlug(lv3.getSlug());
                categoryDtoLv3.setLevel(lv3.getLevel());
                categoryDtoLv3.setParentId(lv3.getParentId());
                categoryDtoLv3.setImage(lv3.getImage());
                categoryDtoLv3.setName(lv3.getName());
                categoryDtosLv3.add(categoryDtoLv3);
            });
        });
        categoriesDetailDto.setCategoryLv1(categoryDtosLv1);
        categoriesDetailDto.setCategoryLv2(categoryDtosLv2);
        categoriesDetailDto.setCategoryLv3(categoryDtosLv3);
        return ResponseEntity.ok(categoriesDetailDto);
    }
}
