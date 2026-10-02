package com.library.model;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private int quantity;
    private int availableQuantity;

    public Book() {
    }

    public Book(String title, String author, String publisher,
                String category, int quantity, int availableQuantity) {

        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}