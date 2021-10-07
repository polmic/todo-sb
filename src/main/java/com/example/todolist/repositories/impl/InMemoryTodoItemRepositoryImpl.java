package com.example.todolist.repositories.impl;

import com.example.todolist.models.TodoItem;
import com.example.todolist.repositories.TodoItemRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryTodoItemRepositoryImpl implements TodoItemRepository {

    private final List<TodoItem> todoList;

    public InMemoryTodoItemRepositoryImpl() {
        this.todoList = new ArrayList<>();
        todoList.add(new TodoItem(UUID.randomUUID(), "Do the dishes", false));
        todoList.add(new TodoItem(UUID.randomUUID(), "Mow the lawn", false));
        todoList.add(new TodoItem(UUID.randomUUID(), "Hide the beers", false));
    }

    @Override
    public List<TodoItem> findAll() {
        return todoList;
    }

}
