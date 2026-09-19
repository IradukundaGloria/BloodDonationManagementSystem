package com.blooddonation.controller;

import com.blooddonation.entity.Recipient;
import com.blooddonation.service.RecipientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @PostMapping
    public ResponseEntity<Recipient> create(@Valid @RequestBody Recipient recipient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipientService.create(recipient));
    }

    @GetMapping
    public ResponseEntity<List<Recipient>> getAll() {
        return ResponseEntity.ok(recipientService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipient> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recipientService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipient> update(@PathVariable Long id, @Valid @RequestBody Recipient recipient) {
        return ResponseEntity.ok(recipientService.update(id, recipient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
