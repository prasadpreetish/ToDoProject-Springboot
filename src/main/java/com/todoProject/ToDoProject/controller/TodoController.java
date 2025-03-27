package com.todoProject.ToDoProject.controller;

import com.todoProject.ToDoProject.model.Todo;
import com.todoProject.ToDoProject.service.TodoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> displayAllTodo(){
        return todoService.displayAllTodo();
    }

}
