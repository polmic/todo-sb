package com.example.todolist;

import com.example.todolist.models.TodoItem;
import com.example.todolist.services.TodoService;
import com.example.todolist.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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

    // @formatter:off
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
    public void getItem_success() throws Exception {
        Mockito.when(todoService.getItem(Todo1.getUuid().toString())).thenReturn(Todo1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/todo/item/" + Todo1.getUuid());

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.uuid", is(Todo1.getUuid().toString())));
    }

    @Test
    public void getItemBadUUID_notFound() throws Exception {
        Mockito.when(todoService.getItem(Todo1.getUuid().toString())).thenReturn(Todo1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/todo/item/aaa");

        MvcResult result = mockMvc.perform(mockRequest
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains(Constants.ErrorMessages.INVALID_ID));
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
    public void updateItemNullUUID_notFound() throws Exception {
        TodoItem Todo1Updated = new TodoItem(Todo1.getUuid(), "Do the dishes", "", true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/todo/item/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(Todo1Updated));

        mockMvc.perform(mockRequest
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateItemBadUUID_notFound() throws Exception {
        TodoItem Todo1Updated = new TodoItem(Todo1.getUuid(), "Do the dishes", "", true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/todo/item/aaa")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(Todo1Updated));

        MvcResult result = mockMvc.perform(mockRequest
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains(Constants.ErrorMessages.INVALID_ID));
    }

    @Test
    public void createItem() throws Exception {
        TodoItem toCreate = new TodoItem();
        toCreate.setTitle("To do");
        TodoItem created = new TodoItem(Todo1.getUuid(), "To do", "", true);

        Mockito.when(todoService.saveItem(toCreate)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/todo/")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(toCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}