package com.todoProject.ToDoProject.service;

import com.todoProject.ToDoProject.model.Todo;
import com.todoProject.ToDoProject.repository.TodoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@NoArgsConstructor
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = new Todo(todo.getTitle(), todo.getDescription());
        todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    public ResponseEntity<List<Todo>> displayAllTodo(){
        List<Todo> todoList = todoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }
}
