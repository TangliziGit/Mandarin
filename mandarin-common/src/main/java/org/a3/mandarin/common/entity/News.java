package org.a3.mandarin.common.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(300)", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column
    private Instant date;

    public News() {
    }

    public News(String title, String content, User user, Instant date) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
