package com.example.demo.controller;

import com.example.demo.model.SimulationResult;
import com.example.demo.model.User;
import com.example.demo.repository.ScenarioAssignmentRepository;
import com.example.demo.repository.SimulationResultRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    @Autowired
    private SimulationResultRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;

    @PostMapping("/save")
    public SimulationResult saveResult(@RequestBody SimulationResult result, @RequestParam Long assignmentId) {
        // מציאת המשימה ששויכה לנציג
        return assignmentRepository.findById(assignmentId).map(assignment -> {

            // קישור התוצאה לתרחיש האמיתי מתוך המשימה
            result.setScenario(assignment.getScenario());

            // הגדרת מזהה המתאמן מקוד הנציג
            if (result.getTraineeId() == null) {
                result.setTraineeId(assignment.getAgent().getAgentCode());
            }

            // שמירת התוצאה ב-DB
            SimulationResult saved = repository.save(result);

            // עדכון סטטוס המשימה ל"בוצע"
            assignment.setStatus("COMPLETED");
            assignmentRepository.save(assignment);

            // לוגיקת קידום דרגה (Rank) במידה והציון מעל 90
            if (saved.getFinalScore() != null && saved.getFinalScore() >= 90) {
                User user = assignment.getAgent();
                user.setRank(user.getRank() + 1);
                userRepository.save(user);
            }

            return saved;
        }).orElseThrow(() -> new RuntimeException("Assignment not found: " + assignmentId));
    }
}