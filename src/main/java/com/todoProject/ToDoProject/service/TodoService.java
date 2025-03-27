package com.todoProject.ToDoProject.service;

import com.todoProject.ToDoProject.model.Todo;
import com.todoProject.ToDoProject.repository.TodoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
