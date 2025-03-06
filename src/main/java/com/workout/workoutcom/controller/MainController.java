package com.workout.workoutcom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")  // Vue 개발 서버 포트
public class MainController {

    @GetMapping("/getPage")
    public ResponseEntity<String> getPage() {
        return ResponseEntity.ok("Hello");
    }
}
