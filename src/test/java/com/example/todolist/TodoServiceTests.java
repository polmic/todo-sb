package com.example.todolist;

import com.example.todolist.repositories.TodoItemRepository;
import com.example.todolist.services.TodoService;
import com.example.todolist.services.impl.TodoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodoServiceTests {

	@Mock
	private TodoItemRepository todoItemRepository;

	@InjectMocks
	private TodoService todoService = new TodoServiceImpl();

	@DisplayName("Test todoService.findAllTodoItems")
	@Test
	void testGetAllTodoItems() {
		when(todoItemRepository.findAll()).thenReturn(new ArrayList<>());
		assertEquals(new ArrayList<>(), todoService.findAll());
	}

}

