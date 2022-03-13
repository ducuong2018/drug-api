package com.example.drugapi.controllers;

import com.example.drugapi.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping(path = "/products")
    public ResponseEntity<?> getListProducts (@RequestParam (required = false) String slug, Pageable pageable) throws Exception {
        return  productService.getProducts(slug,pageable);
    }
}
