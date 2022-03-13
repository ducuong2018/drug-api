package com.example.drugapi.repositories;

import com.example.drugapi.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findCategoriesBySlug(String slug);
    List<Categories> findCategoriesById(Integer id);
    @Query("SELECT DISTINCT c FROM ProductCategory pc inner join Products p on pc.productId = p.id inner join Categories c on c.id = pc.categoryId where p.id =  ?1")
    List<Categories> getCategoryByProductId(Integer id);
}
