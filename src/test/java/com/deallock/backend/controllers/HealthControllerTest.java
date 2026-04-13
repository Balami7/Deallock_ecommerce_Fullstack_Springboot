package com.deallock.backend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class HealthControllerTest {

    @Autowired
    private HealthController healthController;

    @Test
    void returnsHealthyStatus() {
        ResponseEntity<Map<String, String>> response = healthController.health();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("ok", response.getBody().get("status"));
    }
}
