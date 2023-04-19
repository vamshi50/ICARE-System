package com.icare.controllers;

import com.icare.db.entities.UserPassword;
import com.icare.db.entities.Worker;
import com.icare.db.repositories.UserPasswordRepository;
import com.icare.db.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path="/workers")
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @GetMapping(path="")
    public String list(Model model) {
        Iterable<Worker> workers = workerRepository.findAll();
        model.addAttribute("workers", workers);
        return "worker/list";
    }

    @GetMapping(path="create")
    public String create(Model model) {
        Worker worker = new Worker();
        UserPassword userPassword = new UserPassword();
        model.addAttribute("worker", worker);
        model.addAttribute("userPassword", userPassword);
        return "worker/add";
    }

    @PostMapping(path="")
    public String create(Worker worker, UserPassword userPassword) {
        userPassword.encryptPassword();
        userPassword.setPasswordExpiryTime(300);
        workerRepository.save(worker);
        userPassword.setUser(worker);
        userPasswordRepository.save(userPassword);
        return "redirect:/workers";
    }

    @GetMapping(path="edit/{workerId}")
    public String update(Model model, @PathVariable Long workerId) {
        Worker worker = workerRepository.findById(workerId).get();
        model.addAttribute("worker", worker);
        return "workers/edit";
    }

    @PostMapping(path="{workerId}")
    public String update(Worker worker, @PathVariable Long workerId) {
        worker.setId(workerId);
        workerRepository.save(worker);
        return "redirect:/workers/list";
    }
}
