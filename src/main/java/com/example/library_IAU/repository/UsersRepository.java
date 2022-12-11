package com.example.library_IAU.repository;

import com.example.library_IAU.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel, Integer> {

    Optional<UsersModel> findByEmailAndPassword(String email, String password);
}
