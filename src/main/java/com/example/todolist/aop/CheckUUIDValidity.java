package com.example.todolist.aop;

import com.example.todolist.exceptions.TodoItemNotFoundException;
import com.example.todolist.utils.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class CheckUUIDValidity {

    @Before("@annotation(com.example.todolist.aop.CheckUUID)")
    public void beforeCheckUUIDAnnotations(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object uuid = args[0];
        try {
            UUID.fromString(uuid.toString());
        } catch (Exception e) {
            throw new TodoItemNotFoundException(Constants.ErrorMessages.INVALID_ID);
        }
    }

}


