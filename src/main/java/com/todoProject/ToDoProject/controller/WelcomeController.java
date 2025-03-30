package com.todoProject.ToDoProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String getFirstPage(){
        return "Hello from first page in welcome controller ";
    }
}
