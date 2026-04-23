package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentController {

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimulationResultRepository resultRepository;

    @GetMapping("/{id}/tasks")
    public List<ScenarioAssignment> getTasksById(@PathVariable Long id) {
        User agent = userRepository.findById(id).orElseThrow();
        return assignmentRepository.findByAgentAndStatus(agent, "PENDING");
    }

    @GetMapping("/{id}/history")
    public List<SimulationResult> getMyHistory(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return resultRepository.findByTraineeId(user.getAgentCode());
    }

    @GetMapping("/assignment/{id}")
    public ScenarioAssignment getAssignment(@PathVariable Long id) {
        return assignmentRepository.findById(id).orElseThrow();
    }
}
