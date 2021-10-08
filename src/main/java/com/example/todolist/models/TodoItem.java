package com.example.todolist.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TodoItem {

    private UUID uuid;
    private String title;
    private String description;
    private boolean isDone;

    public TodoItem() { }

    public TodoItem(UUID uuid, String title, String description, boolean isDone) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }

}
