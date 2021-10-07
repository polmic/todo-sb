package com.example.todolist.controllers;

import com.example.todolist.models.TodoItem;
import com.example.todolist.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @CrossOrigin
    @GetMapping("/todo")
    public ResponseEntity<List<TodoItem>> findAll() {
        List<TodoItem> items = todoService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
