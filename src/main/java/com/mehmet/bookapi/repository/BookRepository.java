package com.mehmet.bookapi.repository;

import com.mehmet.bookapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorIgnoreCase(String author);

    @Query("SELECT b FROM Book b WHERE b.price > :price")
    List<Book> findBooksMoreExpensiveThan(@Param("price") Double price);
}