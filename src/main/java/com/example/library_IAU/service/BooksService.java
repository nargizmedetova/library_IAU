package com.example.library_IAU.service;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

}
