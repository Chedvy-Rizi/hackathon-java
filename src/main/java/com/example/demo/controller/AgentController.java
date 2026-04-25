package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getMyHistory(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        User user = userOpt.get();
        return ResponseEntity.ok(resultRepository.findByTraineeId(user.getAgentCode()));
    }

    @GetMapping("/assignment/{id}")
    public ScenarioAssignment getAssignment(@PathVariable Long id) {
        return assignmentRepository.findById(id).orElseThrow();
    }


    @GetMapping("/{id}/tasks")
    public List<ScenarioAssignment> getTasksById(@PathVariable Long id) {
        User agent = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("נציג לא נמצא"));
        // עכשיו זה שולף את כל המשימות - גם PENDING וגם COMPLETED!
        return assignmentRepository.findByAgent(agent);
    }
}
