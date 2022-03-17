package com.example.drugapi.repositories;

import com.example.drugapi.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users findUserByEmail(String email);
    Users findUsersById(Long id);

}
