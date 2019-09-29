package org.a3.mandarin.common.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false, unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Set<BookDescription> bookDescriptionSet=new HashSet<>();

    public Category(){}

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
