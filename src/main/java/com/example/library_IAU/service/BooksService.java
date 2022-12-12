package com.example.library_IAU.service;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BooksService {

    List<Books> getAllBooks();

    Books saveBook(Books books);

    Books getBookById(Long id);

    Books updateBook(Books books);

    void deleteBookById(Long id);
}
