package com.example.drugapi.repositories;

import com.example.drugapi.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    Categories findFirstBySlug(String slug);
    @Query("SELECT DISTINCT c FROM ProductCategory pc inner join Products p on pc.productId = p.id inner join Categories c on c.id = pc.categoryId where p.id =  ?1")
    List<Categories> getCategoryByProductId(Integer id);
    @Query("SELECT b FROM Categories a inner join Categories b on a.id = b.parentId  where a.id = ?1")
    List<Categories> getChillCategoriesById(Integer id);
    @Query("SELECT b FROM Categories a inner join Categories b on a.parentId = b.id  where a.id = ?1")
    Categories getParentCategoriesById(Integer id);
}
