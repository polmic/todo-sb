package com.example.todolist.controllers;

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
    @PutMapping("/todo/item/{uuid}")
    public @ResponseBody
    ResponseEntity<TodoItem> updateItem(
            @PathVariable
                    String uuid,
            @RequestBody
                    TodoItem item) {
        if (uuid == null || item.getUuid() == null) {
            throw new TodoItemNotFoundException(Constants.ErrorMessages.INVALID_ID);
        }
        todoService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
