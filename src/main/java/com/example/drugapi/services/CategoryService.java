package com.example.drugapi.services;

import com.example.drugapi.Dtos.CategoryDto;
import com.example.drugapi.models.Categories;
import com.example.drugapi.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<CategoryDto> getCategories(String slug) throws IOException {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Categories> categoriesLv3;
        List<Categories> categoriesLv2;
        List<Categories> categoriesLv1;
        List<Categories> categories;
        if(slug != null) {
            categoriesLv3 = categoryRepository.findCategoriesBySlug(slug);
            int parentId = 0;
            if(categoriesLv3!= null) {
                for(Categories category : categoriesLv3) {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(category.getId());
                    categoryDtos.add((categoryDto));
                    parentId = category.getParentId();
                }
            }

            categoriesLv2 = categoryRepository.findCategoriesById(parentId);
            if(categoriesLv2 != null) {
                for(Categories category : categoriesLv2) {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(category.getId());
                    categoryDtos.add((categoryDto));
                    parentId = category.getParentId();
                }
            }
            categoriesLv1 = categoryRepository.findCategoriesById(parentId);
            if(categoriesLv1 != null) {
                for(Categories category : categoriesLv1) {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(category.getId());
                    categoryDtos.add((categoryDto));
                }
            }

           return categoryDtos;
        }
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
        return  categoryDtos;
    }
}
