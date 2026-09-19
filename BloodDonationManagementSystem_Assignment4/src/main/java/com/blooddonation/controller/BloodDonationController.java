package com.blooddonation.controller;

import com.blooddonation.entity.BloodDonation;
import com.blooddonation.service.BloodDonationService;
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
@RequestMapping("/api/donations")
public class BloodDonationController {

    private final BloodDonationService bloodDonationService;

    public BloodDonationController(BloodDonationService bloodDonationService) {
        this.bloodDonationService = bloodDonationService;
    }

    @PostMapping
    public ResponseEntity<BloodDonation> create(@Valid @RequestBody BloodDonation donation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloodDonationService.create(donation));
    }

    @GetMapping
    public ResponseEntity<List<BloodDonation>> getAll() {
        return ResponseEntity.ok(bloodDonationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodDonation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodDonationService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodDonation> update(@PathVariable Long id, @Valid @RequestBody BloodDonation donation) {
        return ResponseEntity.ok(bloodDonationService.update(id, donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bloodDonationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
