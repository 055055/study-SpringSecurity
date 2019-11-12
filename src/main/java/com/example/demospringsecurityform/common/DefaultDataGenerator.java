package com.example.demospringsecurityform.common;

import com.example.demospringsecurityform.account.Account;
import com.example.demospringsecurityform.account.AccountService;
import com.example.demospringsecurityform.book.Book;
import com.example.demospringsecurityform.book.BookReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {
    @Autowired
    AccountService accountService;

    @Autowired
    BookReposiroty bookReposiroty;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //055055 -spring
        //8220 - hibernate
        Account a1 = createAccount("055055");
        System.out.println(a1.toString());
        Account a2 = createAccount("8220");
        System.out.println(a2.toString());

        Book book1 = createBook("spring",a1);
        Book book2 = createBook("hibernate",a2);


    }

    private Book createBook(String bookName,Account account) {
        Book book = new Book();
        book.setAuthor(account);
        book.setTitle(bookName);
        bookReposiroty.save(book);
        return book;
    }

    private Account createAccount(String userName) {
        Account account = new Account();
        account.setUsername(userName);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
