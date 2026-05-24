package com.mehmet.bookapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookRequestDTO {

    @NotBlank(message = "Title bos olamaz")
    private String title;

    @NotBlank(message = "Author bos olamaz")
    private String author;

    @NotNull(message = "Price bos olamaz")
    @Positive(message = "Price 0'dan buyuk olmalidir")
    private Double price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}