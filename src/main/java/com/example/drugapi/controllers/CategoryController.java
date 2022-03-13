package com.example.drugapi.controllers;

import com.example.drugapi.Dtos.CategoryDto;
import com.example.drugapi.services.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping(path = "/category")
    public List<CategoryDto> getCategories (@RequestParam (required = false) String slug) throws IOException {
        return categoryService.getCategories(slug);
    }
}
