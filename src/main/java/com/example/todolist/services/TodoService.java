package com.example.todolist.services;

import com.example.todolist.models.TodoItem;

import java.util.List;

public interface TodoService {
    List<TodoItem> findAll();

    TodoItem getItem(String uuid);

    TodoItem updateItem(TodoItem item);
}
