package com.example.library_IAU.repository;

import com.example.library_IAU.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {
}
