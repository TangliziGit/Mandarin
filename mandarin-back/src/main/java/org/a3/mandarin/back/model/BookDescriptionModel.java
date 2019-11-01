package org.a3.mandarin.back.model;

import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BookDescription;
import org.a3.mandarin.common.entity.Category;
import org.a3.mandarin.common.util.ComponentUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDescriptionModel {
    private String ISBN;
    private String title;
    private String author;
    private Double price;
    private String coverUrl;
    private String location;
    private Integer publishYear;
    private String publisher;
    private String summary;
    private Category category;
    private Integer copyNumber;
    private List<Integer> bookIdList;
    private Integer canReserveNumber;

    public BookDescriptionModel() {}

    public BookDescriptionModel(BookDescription bookDescription, Integer copyNumber) {
        this.ISBN = bookDescription.getISBN();
        this.title = bookDescription.getTitle();
        this.author = bookDescription.getAuthor();
        this.price = bookDescription.getPrice();
        this.coverUrl = bookDescription.getCoverUrl();
        this.location = bookDescription.getLocation();
        this.publishYear = bookDescription.getPublishYear();
        this.publisher = bookDescription.getPublisher();
        this.summary = bookDescription.getSummary();
        this.category = bookDescription.getCategory();
        this.copyNumber=copyNumber;

        this.bookIdList=new ArrayList<>();
        this.canReserveNumber = this.copyNumber;
        for (Book book: bookDescription.getBooks()){
            Integer id = book.getBookId();
            this.bookIdList.add(id);

            if (ComponentUtil.bookRepository.isOnReserving(id)
                || ComponentUtil.bookRepository.isOnBorrowing(id)
                || ComponentUtil.bookRepository.isDeleted(id))
                this.canReserveNumber--;
        }
    }

    public Integer getCanReserveNumber() {
        return canReserveNumber;
    }

    public void setCanReserveNumber(Integer canReserveNumber) {
        this.canReserveNumber = canReserveNumber;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<Integer> getBookIdList() {
        return bookIdList;
    }

    public void setBookIdList(List<Integer> bookIdList) {
        this.bookIdList = bookIdList;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public Integer getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(Integer copyNumber) {
        this.copyNumber = copyNumber;
    }
}
