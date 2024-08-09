package com.khoi.unilibrary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Date yearPublished;
    private Long volumeUp;
    private String genre;
    private String category;
    private String keyword;
    private boolean borrowed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User borrowedBy;

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowedBy(User user) {
    }

    public void setBorrowed(boolean borrowed) {
    }
}
