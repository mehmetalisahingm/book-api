package com.mehmet.bookapi.service;

import com.mehmet.bookapi.dto.BookRequestDTO;
import com.mehmet.bookapi.dto.BookResponseDTO;
import com.mehmet.bookapi.exception.BookNotFoundException;
import com.mehmet.bookapi.model.Book;
import com.mehmet.bookapi.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponseDTO> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        List<BookResponseDTO> responseList = new ArrayList<>();

        for (Book book : books) {
            BookResponseDTO dto = convertToResponseDTO(book);
            responseList.add(dto);
        }

        return responseList;
    }

    public Page<BookResponseDTO> getBooksPage(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        Page<Book> booksPage = bookRepository.findAll(pageable);

        return booksPage.map(this::convertToResponseDTO);
    }

    public List<BookResponseDTO> searchBooksByTitle(String title) {

        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);

        List<BookResponseDTO> responseList = new ArrayList<>();

        for (Book book : books) {
            BookResponseDTO dto = convertToResponseDTO(book);
            responseList.add(dto);
        }

        return responseList;
    }

    public List<BookResponseDTO> searchBooksByAuthor(String author) {

        List<Book> books = bookRepository.findByAuthorIgnoreCase(author);

        List<BookResponseDTO> responseList = new ArrayList<>();

        for (Book book : books) {
            BookResponseDTO dto = convertToResponseDTO(book);
            responseList.add(dto);
        }

        return responseList;
    }

    public BookResponseDTO createBook(BookRequestDTO dto) {

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());

        Book savedBook = bookRepository.save(book);

        return convertToResponseDTO(savedBook);
    }

    public BookResponseDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return convertToResponseDTO(book);
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());

        Book updatedBook = bookRepository.save(book);

        return convertToResponseDTO(updatedBook);
    }

    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        bookRepository.deleteById(id);
    }

    private BookResponseDTO convertToResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice()
        );
    }
    public List<BookResponseDTO> findExpensiveBooks(Double price) {

    List<Book> books = bookRepository.findBooksMoreExpensiveThan(price);

    List<BookResponseDTO> responseList = new ArrayList<>();

    for (Book book : books) {
        BookResponseDTO dto = convertToResponseDTO(book);
        responseList.add(dto);
    }

    return responseList;
}
}