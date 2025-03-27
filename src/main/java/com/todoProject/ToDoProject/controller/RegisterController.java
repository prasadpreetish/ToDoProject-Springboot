package com.todoProject.ToDoProject.controller;

import com.todoProject.ToDoProject.model.User;
import com.todoProject.ToDoProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    @Autowired
    private  UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser){
        try{
            User savedUser = userRepository.save(newUser);

            if(savedUser.getUser_id()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with username: "+savedUser.getUsername());
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not registered");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
    }
}
