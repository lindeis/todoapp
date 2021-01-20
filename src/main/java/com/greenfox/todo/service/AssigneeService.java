package com.greenfox.todo.service;

import com.greenfox.todo.model.Assignee;
import com.greenfox.todo.repository.AssigneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssigneeService {
    private AssigneeRepository assigneeRepository;

    @Autowired
    public AssigneeService(AssigneeRepository assigneeRepository) {
        this.assigneeRepository = assigneeRepository;
    }

    public Assignee getAssigneeById(Long id) {
        return assigneeRepository.findById(id).orElse(null);
    }

    public void editAssignee(Long id, Assignee updatedAssignee) {
        Optional<Assignee> optionalAssignee = assigneeRepository.findById(id);
        if (!optionalAssignee.isPresent()) {
            throw new IllegalArgumentException();
        } else {
            Assignee assignee = optionalAssignee.get();
            assignee.setName(updatedAssignee.getName());
            assignee.setEmail(updatedAssignee.getEmail());
            assigneeRepository.save(assignee);
        }
    }
}
