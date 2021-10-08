package com.example.todolist.repositories;

import com.example.todolist.models.TodoItem;

import java.util.List;

public interface TodoItemRepository {
    List<TodoItem> findAll();

    TodoItem getItem(String uuid);

    TodoItem updateItem(TodoItem item);

    TodoItem saveItem(TodoItem item);
}
