package com.example.todolist;

import com.example.todolist.models.TodoItem;
import com.example.todolist.repositories.TodoItemRepository;
import com.example.todolist.services.TodoService;
import com.example.todolist.services.impl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodoServiceTests {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private final TodoService todoService = new TodoServiceImpl();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTodoItems() {
        TodoItem Todo1 = new TodoItem(UUID.randomUUID(), "Do the dishes", "", false);
        TodoItem Todo2 = new TodoItem(UUID.randomUUID(), "Mow the lawn", "", false);
        TodoItem Todo3 = new TodoItem(UUID.randomUUID(), "Hide the beers", "", true);
        List<TodoItem> items = new ArrayList<>(Arrays.asList(Todo1, Todo2, Todo3));
        when(todoItemRepository.findAll()).thenReturn(items);
        assertEquals(items, todoService.findAll());
    }

    @Test
    void testGetOneItem() {
        TodoItem toGet = new TodoItem(UUID.randomUUID(), "To do", "", true);
        String uuid = toGet.getUuid().toString();
        when(todoItemRepository.getItem(uuid)).thenReturn(toGet);
        assertEquals(toGet, todoService.getItem(uuid));
    }

    @Test
    void testUpdateOneItem() {
        TodoItem toUpdate = new TodoItem(UUID.randomUUID(), "To do", "", true);
        when(todoItemRepository.updateItem(Mockito.any(TodoItem.class))).thenReturn(toUpdate);
        TodoItem updated = todoItemRepository.updateItem(toUpdate);
        verify(todoItemRepository, times(1)).updateItem(toUpdate);
        assertNotNull(updated);
        assertEquals(updated.getUuid(), toUpdate.getUuid());
        assertEquals(updated.getTitle(), toUpdate.getTitle());
        assertTrue(updated.isDone());
    }

    @Test
    void testSaveOneItem() {
        TodoItem toSave = new TodoItem(UUID.randomUUID(), "To do", "", false);

        when(todoItemRepository.saveItem(Mockito.any(TodoItem.class))).thenReturn(toSave);
        TodoItem saved = todoItemRepository.saveItem(toSave);

        verify(todoItemRepository, times(1)).saveItem(toSave);
        assertNotNull(saved);
        assertEquals(saved.getUuid(), toSave.getUuid());
        assertEquals(saved.getTitle(), toSave.getTitle());
        assertFalse(saved.isDone());
    }

}