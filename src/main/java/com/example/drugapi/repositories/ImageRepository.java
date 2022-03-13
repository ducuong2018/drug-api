package com.example.drugapi.repositories;

import com.example.drugapi.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Images,Integer> {
    List<Images> findAllByProductId(Integer id);
}
