package com.example.todolist.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TodoItem {

    private UUID uuid;
    private String title;
    private boolean isDone;

    public TodoItem() { }

    public TodoItem(UUID uuid, String title, boolean isDone) {
        this.uuid = uuid;
        this.title = title;
        this.isDone = isDone;
    }

}
