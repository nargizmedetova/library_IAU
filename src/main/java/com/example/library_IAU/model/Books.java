package com.example.library_IAU.model;

import jakarta.persistence.*;
import org.jsoup.nodes.Element;

import java.util.Objects;

@Entity
@Table(name = "books_table")
public class Books {
    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return Objects.equals(id, books.id) && Objects.equals(name, books.name) && Objects.equals(userId, books.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId);
    }

    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Books(){}

    String name;
    @Column(name = "userId", table = "users_table")
    Long userId;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
