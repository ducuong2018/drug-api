package com.example.drugapi.repositories;

import com.example.drugapi.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    Page<Products> findAll(Pageable pageable);
    Products findFirstBySlug(String slug);
    @Query("SELECT p FROM Products p inner join ProductCategory pc on pc.productId = p.id inner join Categories c on c.id = pc.categoryId where c.id = ?1")
    List<Products> findProductsByIdCategory(Integer id);
}
