package com.example.todolist.repositories;

import com.example.todolist.models.TodoItem;

import java.util.List;

public interface TodoItemRepository {
    List<TodoItem> findAll();

    TodoItem updateItem(TodoItem item);
}
