package com.greenfox.todo.model;

import javax.persistence.*;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean urgent = false;
    private Boolean done = false;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    public Todo() {
    }

    public Todo(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public Boolean getDone() {
        return done;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
