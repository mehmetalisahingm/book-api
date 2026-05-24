package com.mehmet.bookapi.service;

import com.mehmet.bookapi.dto.BookRequestDTO;
import com.mehmet.bookapi.model.Book;
import com.mehmet.bookapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // public Book createBook(Book book) {
    //     return bookRepository.save(book);
    // }
    public Book createBook (BookRequestDTO dto){
        
        Book book = new Book();
        
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());

        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(Long id, Book updatedBook) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPrice(updatedBook.getPrice());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}