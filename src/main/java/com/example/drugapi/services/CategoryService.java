package com.example.drugapi.services;

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
        Categories categories = categoryRepository.findFirstBySlug(slug);
        if(categories.getLevel() == 1) {
            List<Categories> categoriesLv2 = categoryRepository.getChillCategoriesById(categories.getId());
            List<Categories> categoriesLv3 = categoryRepository.getChillCategoriesById(categoriesLv2.getId())
        }
    }
}
