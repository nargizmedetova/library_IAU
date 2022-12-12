package com.example.library_IAU.service;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServicelmpl implements BooksService{

    private BooksRepository booksRepository;
    public BooksServicelmpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    @Override
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public Books saveBook(Books books) {
        return booksRepository.save(books);
    }

    @Override
    public Books getBookById(Long id) {
        return booksRepository.findById(id).get();
    }

    @Override
    public Books updateBook(Books books) {
        return booksRepository.save(books);
    }

    @Override
    public void deleteBookById(Long id) {
        booksRepository.deleteById(id);
    }
}
