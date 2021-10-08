package com.example.todolist;

import com.example.todolist.controllers.TodoController;
import com.example.todolist.models.TodoItem;
import com.example.todolist.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    TodoService todoService;

    TodoItem Todo1 = new TodoItem(UUID.randomUUID(), "Do the dishes", "", false);
    TodoItem Todo2 = new TodoItem(UUID.randomUUID(), "Mow the lawn", "", false);
    TodoItem Todo3 = new TodoItem(UUID.randomUUID(), "Hide the beers", "", true);

    @Test
    public void getAllItems() throws Exception {
        List<TodoItem> items = new ArrayList<>(Arrays.asList(Todo1, Todo2, Todo3));
        Mockito.when(todoService.findAll()).thenReturn(items);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].title", is("Mow the lawn")));
    }

    @Test
    public void updateItem_success() throws Exception {
        TodoItem Todo1Updated = new TodoItem(Todo1.getUuid(), "Do the dishes", "", true);
        Mockito.when(todoService.updateItem(Todo1Updated)).thenReturn(Todo1Updated);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/todo/item/" + Todo1.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(Todo1Updated));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateItemBadURI_notFound() throws Exception {
        TodoItem Todo1Updated = new TodoItem(Todo1.getUuid(), "Do the dishes", "", true);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/todo/item/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(Todo1Updated));
        mockMvc.perform(mockRequest
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}