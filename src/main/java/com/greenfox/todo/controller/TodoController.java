package com.greenfox.todo.controller;

import com.greenfox.todo.model.Assignee;
import com.greenfox.todo.model.Todo;
import com.greenfox.todo.repository.AssigneeRepository;
import com.greenfox.todo.repository.TodoRepository;
import com.greenfox.todo.service.AssigneeService;
import com.greenfox.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {
    private final TodoRepository todoRepository;
    private final AssigneeRepository assigneeRepository;
    private final AssigneeService assigneeService;
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoRepository todoRepository, AssigneeRepository assigneeRepository, AssigneeService assigneeService, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.assigneeRepository = assigneeRepository;
        this.assigneeService = assigneeService;
        this.todoService = todoService;
    }

    @GetMapping(value = {"/", "/list"})
    public String list(Model model, @RequestParam(required = false) Boolean isDone) {
        List<Todo> todos = (List<Todo>) todoRepository.findAll();
        if (isDone == null) {
            model.addAttribute("todos", todoRepository.findAll());
        } else {
            model.addAttribute("todos", todos.stream().filter(x -> x.getDone() == !isDone).collect(Collectors.toList()));
        }
        return "todolist";
    }

    @GetMapping("/todo")
    public String todo() {
        return "redirect:/";
    }

    @GetMapping("/newtodo")
    public String getNewTodo() {
        return "newtodo";
    }

    @PostMapping("/newtodo")
    public String postNewTodo(@RequestParam String title) {
        todoRepository.save(new Todo(title));
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam String search) {
        List<Todo> todos = (List<Todo>) todoRepository.findAll();
        List<Todo> foundTodos;
        foundTodos = todos.stream().filter(x -> x.getTitle().contains(search)).collect(Collectors.toList());
        model.addAttribute("todos", foundTodos);
        return "todolist";
    }

    @GetMapping("/listofassignees")
    public String listOfAssignees(Model model) {
        List<Assignee> assignees = (List<Assignee>) assigneeRepository.findAll();
        model.addAttribute("assignees", assignees);
        return "listofassignees";
    }

    @GetMapping("/newassignee")
    public String getNewAssignee() {
        return "newassignee";
    }

    @PostMapping("/newassignee")
    public String postNewAssignee(@RequestParam String name, @RequestParam String email) {
        assigneeRepository.save(new Assignee(name, email));
        return "redirect:/listofassignees";
    }

    @GetMapping("/deletetodo/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/deleteassignee/{id}")
    public String deleteAssignee(@PathVariable Long id) {
        assigneeRepository.deleteById(id);
        return "redirect:/listofassignees";
    }

    @GetMapping("/editassignee/{id}")
    public String getEditAssignee(@PathVariable Long id, Model model) {
        model.addAttribute("assignee", assigneeService.getAssigneeById(id));
        return "editassignee";
    }

    @PostMapping("/editassignee/{id}")
    public String postEditAssignee(@PathVariable Long id, @ModelAttribute Assignee assignee) {
        assigneeService.editAssignee(id, assignee);
        return "redirect:/listofassignees";
    }

    @GetMapping("/edittodo/{id}")
    public String getEditTodo(@PathVariable Long id, Model model) {
        model.addAttribute("todo", todoService.getTodoById(id));
        return "edittodo";
    }

    @PostMapping("/edittodo/{id}")
    public String postEditTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
        todoService.editTodo(id, todo);
        return "redirect:/";
    }
}
