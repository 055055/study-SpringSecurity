package com.example.demospringsecurityform.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReposiroty extends JpaRepository<Book,Integer> {
    @Query("select b from Book b where b.author.id=?#{principal.account.id}")
    //query anotation안에서 Principal을 사용할 수 있게 해준다.
    List<Book> findCurrentUserBooks();
}
