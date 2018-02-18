package ir.maktab.model.book;

import ir.maktab.model.author.Author;

import java.io.Serializable;

/**
 * Created by Nader on 2/1/2018.
 */

public class Book implements Serializable{

    private Integer id;

    private Long isbnNumber;

    private String title;

    private Integer pageNumber;

    private Integer Reference;

    private Author author;

    public Book() {
    }

    public Book(Long isbnNumber, String title, Integer pageNumber, Integer Reference, Author author) {
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.pageNumber = pageNumber;
        this.Reference = Reference;
        this.author = author;
    }

    public Book(Long isbnNumber, String title, Integer pageNumber, Integer Reference) {
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.pageNumber = pageNumber;
        this.Reference = Reference;
    }

    public Book(long isbn) {
        isbnNumber = isbn;
    }

    public Book(long isbnNumber, String title, Integer Reference, Author author) {
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.Reference = Reference;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(Long isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getReference() {
        return Reference;
    }

    public void setReference(Integer reference) {
        Reference = reference;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbnNumber=" + isbnNumber +
                ", title='" + title + '\'' +
                ", pageNumber=" + pageNumber +
                ", Reference=" + Reference +
                ", author=" + author +
                '}';
    }
}
