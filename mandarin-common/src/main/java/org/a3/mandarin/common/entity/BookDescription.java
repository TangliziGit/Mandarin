package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Double price;

    @Column
    private String location;

    @Column
    private Integer publishYear;

    @Column
    private String publisher;

    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    @OneToMany(mappedBy = "bookDescription", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Book> books=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    public BookDescription() {}

    public BookDescription(String ISBN, String title, String author, Double price,
                           String location, Integer publishYear, String publisher, String summary,
                           Category category) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.price = price;
        this.location = location;
        this.publishYear = publishYear;
        this.publisher = publisher;
        this.summary = summary;
        this.category = category;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
