package org.a3.mandarin.common.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class BookDescription {
    @Id
    @Column(columnDefinition = "VARCHAR(30)")
    private String ISBN;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private Integer price;

    @Column
    private String location;

    @OneToMany(mappedBy = "ISBN")
    private Set<Book> books=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    public BookDescription() {}

    public BookDescription(String ISBN, String title, String author, Integer price, String location) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.price = price;
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getISBN() {
        return ISBN;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
