package ir.maktab.model.author;

import ir.maktab.model.book.Book;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Nader on 2/1/2018.
 */

public class Author implements Serializable{


    private Integer id;

    private Long authorCode;
    private String name;
    private Integer age;

    private Set<Book> books;

    public Author(long author_code, String name, Integer age) {
        this.authorCode = author_code;
        this.name = name;
        this.age = age;
    }

    public Author() {
    }

    public Author(Long authorCode) {
        this.authorCode = authorCode;
    }

    public Author(long authorCode, String name) {
        this.authorCode = authorCode;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(Long authorCode) {
        this.authorCode = authorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorCode=" + authorCode +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
