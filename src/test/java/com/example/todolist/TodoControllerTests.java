package com.example.todolist;

import com.example.todolist.models.TodoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllInitTodoItems() throws Exception {
        URI uri = new URI("http://localhost:" + port + "/todo");
        ResponseEntity<TodoItem[]> response = restTemplate.getForEntity(uri, TodoItem[].class);
        TodoItem[] allItems = response.getBody();
        assertAll("allItems",
                () -> assertEquals(allItems[0].getTitle(), "Do the dishes"),
                () -> assertEquals(allItems[1].getTitle(), "Mow the lawn"),
                () -> assertEquals(allItems[2].getTitle(), "Hide the beers")
        );
    }

}

