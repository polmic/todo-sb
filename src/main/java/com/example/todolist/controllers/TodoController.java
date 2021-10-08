package com.example.todolist.controllers;

import com.example.todolist.aop.CheckUUID;
import com.example.todolist.exceptions.TodoItemNotFoundException;
import com.example.todolist.models.TodoItem;
import com.example.todolist.services.TodoService;
import com.example.todolist.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody
    ResponseEntity<List<TodoItem>> findAll() {
        List<TodoItem> items = todoService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/todo/item/{uuid}")
    @CheckUUID
    public @ResponseBody
    ResponseEntity<TodoItem> getItem(
            @PathVariable
                    String uuid) {
        TodoItem item = todoService.getItem(uuid);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/todo/item/{uuid}")
    @CheckUUID
    public @ResponseBody
    ResponseEntity<TodoItem> updateItem(
            @PathVariable
                    String uuid,
            @RequestBody
                    TodoItem item) {
        todoService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
