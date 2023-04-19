package com.icare.controllers;

import com.icare.db.entities.User;
import com.icare.db.entities.UserPassword;
import com.icare.db.entities.Worker;
import com.icare.db.repositories.UserPasswordRepository;
import com.icare.db.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class AuthenticationController implements ErrorController {
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @GetMapping(path = {"", "home"})
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) auth.getAuthorities();
            List<String> authorities = new ArrayList<>();
            grantedAuthorities.forEach(grantedAuthority -> {
                authorities.add(grantedAuthority.getAuthority());
            });
            System.out.println("authorities" + authorities);
            if (authorities.contains("admin")) {
                return "admin/home";
            } else if (authorities.contains("worker")) {
                return "worker/home";
            }
        }

        return "redirect:/login";
    }

    @GetMapping(path = "login")
    public String login(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
        UserPassword account = new UserPassword();
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        model.addAttribute("account", account);
        return "login";
    }

    @PostMapping(path = "login")
    public String login(@ModelAttribute UserPassword account, Model model) {
        return "redirect:/";
    }

    @GetMapping(path = "register")
    public String register(Model model) {
        UserPassword userPassword = new UserPassword();
        Worker worker = new Worker();
        model.addAttribute("userPassword", userPassword);
        model.addAttribute("worker", worker);
        return "register";
    }

    @PostMapping(path="register/worker")
    public String create(Worker worker, UserPassword userPassword) {
        userPassword.encryptPassword();
        userPassword.setPasswordExpiryTime(300);
        workerRepository.save(worker);
        userPassword.setUser(worker);
        userPasswordRepository.save(userPassword);
        return "redirect:/login";
    }
}
