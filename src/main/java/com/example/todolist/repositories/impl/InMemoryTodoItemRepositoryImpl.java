package com.example.todolist.repositories.impl;

import com.example.todolist.exceptions.TodoItemNotFoundException;
import com.example.todolist.models.TodoItem;
import com.example.todolist.repositories.TodoItemRepository;
import com.example.todolist.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryTodoItemRepositoryImpl implements TodoItemRepository {

    private final List<TodoItem> todoList;

    public InMemoryTodoItemRepositoryImpl() {
        this.todoList = new ArrayList<>();
        todoList.add(new TodoItem(UUID.randomUUID(), "Do the dishes", "Needs to be done before lunch", false));
        todoList.add(new TodoItem(UUID.randomUUID(), "Mow the lawn", "It's a jungle out there", false));
        todoList.add(new TodoItem(UUID.randomUUID(), "Hide the beers", "Don't forget to get more", true));
    }

    @Override
    public List<TodoItem> findAll() {
        return todoList;
    }

    @Override
    public TodoItem getItem(String uuid) {
        TodoItem item = todoList.stream().filter(i -> i.getUuid().toString().equals(uuid)).findFirst().orElse(null);
        if (item == null) {
            throw new TodoItemNotFoundException(Constants.ErrorMessages.ITEM_NOT_FOUND);
        }
        return item;
    }

    @Override
    public TodoItem updateItem(TodoItem toUpdate) {
        TodoItem listItem = todoList.stream().filter(i -> i.getUuid().equals(toUpdate.getUuid())).findFirst().orElse(null);
        if (listItem == null) {
            throw new TodoItemNotFoundException(Constants.ErrorMessages.ITEM_NOT_FOUND);
        }
        listItem.setDone(toUpdate.isDone());
        return toUpdate;
    }

    @Override
    public TodoItem saveItem(TodoItem item) {
        todoList.add(0, item);
        return item;
    }

}
