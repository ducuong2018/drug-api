package com.example.drugapi.services;

import com.example.drugapi.Dtos.CategoryDto;
import com.example.drugapi.Dtos.ImageDto;
import com.example.drugapi.Dtos.ProductDto;
import com.example.drugapi.exceptions.BadRequestException;
import com.example.drugapi.exceptions.Message;
import com.example.drugapi.models.Categories;
import com.example.drugapi.models.Images;
import com.example.drugapi.models.Products;
import com.example.drugapi.repositories.CategoryRepository;
import com.example.drugapi.repositories.ImageRepository;
import com.example.drugapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public ResponseEntity<?> getProducts(String slug,Pageable pageable) throws Exception{
        if(slug != null) {
            ProductDto productDto = new ProductDto();
            Products products = productRepository.findFirstBySlug(slug);
            if(products == null) {
                throw new BadRequestException(Message.NOT_FOUND);
            }
            List<Images> images = imageRepository.findAllByProductId(products.getId());
            List<ImageDto> imageDtos = new ArrayList<>();
            images.stream().forEach(item -> {
                ImageDto imageDto = new ImageDto();
                imageDto.setId(item.getId());
                imageDto.setUrl(item.getUrl());
                imageDtos.add(imageDto);
            });
            List<Categories> categories = categoryRepository.getCategoryByProductId(products.getId());
            List<CategoryDto> categoryDtos = new ArrayList<>();
            categories.stream().forEach(category -> {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(category.getId());
                categoryDto.setParentId(category.getParentId());
                categoryDto.setLevel(category.getLevel());
                categoryDto.setName(category.getName());
                categoryDto.setImage(category.getImage());
                categoryDto.setSlug(category.getSlug());
                categoryDtos.add(categoryDto);
            });
            productDto.setId(products.getId());
            productDto.setName(products.getName());
            productDto.setPrice(products.getPrice());
            productDto.setSalePrice(products.getSalePrice());
            productDto.setDescription(products.getDescription());
            productDto.setLongDescription(products.getLongDescription());
            productDto.setSlug(products.getSlug());
            productDto.setImages(imageDtos);
            productDto.setCategory(categoryDtos);
            return ResponseEntity.ok(productDto);
        }
        List<ProductDto> productDtos = new ArrayList<>();
        Page<Products> products = productRepository.findAll(pageable);
        products.stream().forEach(value -> {
            List<ImageDto> imageDtos = new ArrayList<>();
            ProductDto productDto = new ProductDto();
            List<Images> images = imageRepository.findAllByProductId(value.getId());
            images.stream().forEach(item -> {
                ImageDto imageDto = new ImageDto();
                imageDto.setId(item.getId());
                imageDto.setUrl(item.getUrl());
                imageDtos.add(imageDto);
            });
            productDto.setId(value.getId());
            productDto.setName(value.getName());
            productDto.setPrice(value.getPrice());
            productDto.setSalePrice(value.getSalePrice());
            productDto.setDescription(value.getDescription());
            productDto.setLongDescription(value.getLongDescription());
            productDto.setSlug(value.getSlug());
            productDto.setImages(imageDtos);
            productDtos.add(productDto);
        });
        return ResponseEntity.ok(productDtos);
        }
        public  ResponseEntity<?> getProductInCategory(String slug) throws IOException {
            List<ProductDto> productDtos = new ArrayList<>();
            Categories categories = categoryRepository.findCategoriesBySlug(slug);
            List<Products> products = productRepository.findProductsByIdCategory(categories.getId());
            products.stream().forEach(value -> {
                List<ImageDto> imageDtos = new ArrayList<>();
                ProductDto productDto = new ProductDto();
                List<Images> images = imageRepository.findAllByProductId(value.getId());
                images.stream().forEach(item -> {
                    ImageDto imageDto = new ImageDto();
                    imageDto.setId(item.getId());
                    imageDto.setUrl(item.getUrl());
                    imageDtos.add(imageDto);
                });
                productDto.setId(value.getId());
                productDto.setName(value.getName());
                productDto.setPrice(value.getPrice());
                productDto.setSalePrice(value.getSalePrice());
                productDto.setDescription(value.getDescription());
                productDto.setLongDescription(value.getLongDescription());
                productDto.setSlug(value.getSlug());
                productDto.setImages(imageDtos);
                productDtos.add(productDto);
            });
            return ResponseEntity.ok(productDtos);
        }

}

