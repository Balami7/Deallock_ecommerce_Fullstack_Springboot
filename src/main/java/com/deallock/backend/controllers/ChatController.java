package com.deallock.backend.controllers;

import com.deallock.backend.services.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        String reply = geminiService.getResponse(message);
        return ResponseEntity.ok(reply);
    }
}
