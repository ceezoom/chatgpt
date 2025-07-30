package com.example.nameapp.name;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/name")
public class NameController {

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody Map<String, String> body) {
        String surname = body.getOrDefault("surname", "张");
        List<NameCandidate> list = new ArrayList<>();
        list.add(new NameCandidate(surname + "安琪", 90));
        list.add(new NameCandidate(surname + "晨曦", 88));
        list.add(new NameCandidate(surname + "梓涵", 85));
        return ResponseEntity.ok(Map.of("candidates", list));
    }
}
