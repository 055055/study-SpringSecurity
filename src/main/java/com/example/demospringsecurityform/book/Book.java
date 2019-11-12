package com.example.demospringsecurityform.book;

import com.example.demospringsecurityform.account.Account;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Book {
    @Id @GeneratedValue
    private Integer id;

    private String title;

    @ManyToOne
    private Account author;

    public Integer getId(){
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public Account getAuthor() {
        return author;
    }
}
