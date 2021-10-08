package com.example.todolist.services.impl;

import com.example.todolist.repositories.TodoItemRepository;
import com.example.todolist.models.TodoItem;
import com.example.todolist.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Override
    public List<TodoItem> findAll() {
        return todoItemRepository.findAll();
    }

    @Override
    public TodoItem updateItem(TodoItem item) {
        return todoItemRepository.updateItem(item);
    }

    @Override
    public TodoItem getItem(String uuid) {
        return todoItemRepository.getItem(uuid);
    }

}
