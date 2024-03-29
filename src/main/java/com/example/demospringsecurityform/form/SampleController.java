package com.example.demospringsecurityform.form;

import com.example.demospringsecurityform.account.AccountContext;
import com.example.demospringsecurityform.account.AccountReposiroty;
import com.example.demospringsecurityform.account.UserAccount;
import com.example.demospringsecurityform.book.BookReposiroty;
import com.example.demospringsecurityform.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {
    @Autowired SampleService sampleService;
    @Autowired
    AccountReposiroty accountReposiroty;
    @Autowired
    BookReposiroty bookReposiroty;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserAccount userAccount){
        if(userAccount == null){
            model.addAttribute("message","Hello Spring Security");

        }else{
            model.addAttribute("message","Hello "+userAccount.getUsername());

        }
        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message","Info");
        return "info";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message","Hello "+principal.getName());
        AccountContext.setAccount(accountReposiroty.findByUsername(principal.getName()));
       sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/admin")
    public String admin(Model model,Principal principal){
        model.addAttribute("message","Hello Admin "+principal.getName());

        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model,Principal principal){
        model.addAttribute("message","Hello User "+principal.getName());
        model.addAttribute("books",bookReposiroty.findCurrentUserBooks());

        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler(){
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService(){
        SecurityLogger.log("MVC before async service");
       sampleService.asyncService();
        SecurityLogger.log("MVC after async service");
        return "Async Service";
    }

}


