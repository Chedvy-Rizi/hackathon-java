package com.example.demo.controller;

import com.example.demo.model.Scenario;
import com.example.demo.model.ScenarioAssignment;
import com.example.demo.model.User;
import com.example.demo.repository.ScenarioAssignmentRepository;
import com.example.demo.repository.ScenarioRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.SimulationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ManagerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;

    @PostMapping("/add-agent")
    public User addAgent(@RequestBody User agent) {
        agent.setRole("AGENT");
        return userRepository.save(agent);
    }

    @GetMapping("/all-agents")
    public List<User> getAllAgents() {
        return userRepository.findAll().stream()
                .filter(u -> "AGENT".equals(u.getRole())).toList();
    }

    @PostMapping("/add-scenario")
    public Scenario addScenario(@RequestBody Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    @GetMapping("/scenarios")
    public List<Scenario> getScenarios() {
        return scenarioRepository.findAll();
    }

    @PostMapping("/assign-to-agent")
    public ScenarioAssignment assignToAgent(@RequestParam Long agentId, @RequestParam Long scenarioId, @RequestParam String difficulty) {
        User agent = userRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));
        Scenario scenario = scenarioRepository.findById(scenarioId).orElseThrow(() -> new RuntimeException("Scenario not found"));

        ScenarioAssignment assignment = new ScenarioAssignment();
        assignment.setAgent(agent);
        assignment.setScenario(scenario);
        assignment.setDifficulty(difficulty);
        assignment.setStatus("PENDING");
        return assignmentRepository.save(assignment);
    }
}
