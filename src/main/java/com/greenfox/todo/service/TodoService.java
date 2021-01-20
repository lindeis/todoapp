package com.greenfox.todo.service;

import com.greenfox.todo.model.Todo;
import com.greenfox.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public void editTodo(Long id, Todo updatedTodo) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (!optionalTodo.isPresent()) {
            throw new IllegalArgumentException();
        } else {
            Todo todo = optionalTodo.get();
            todo.setTitle(updatedTodo.getTitle());
            todo.setUrgent(updatedTodo.getUrgent());
            todo.setDone(updatedTodo.getDone());
            todoRepository.save(todo);
        }
    }
}
