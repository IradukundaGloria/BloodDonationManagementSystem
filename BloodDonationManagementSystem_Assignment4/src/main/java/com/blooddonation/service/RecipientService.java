package com.blooddonation.service;

import com.blooddonation.entity.Recipient;
import com.blooddonation.exception.ResourceNotFoundException;
import com.blooddonation.repository.RecipientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public Recipient create(Recipient recipient) {
        recipient.setRecipientId(null);
        if (recipient.getRegistrationDate() == null) {
            recipient.setRegistrationDate(LocalDate.now());
        }
        return recipientRepository.save(recipient);
    }

    public List<Recipient> getAll() {
        return recipientRepository.findAll();
    }

    public Recipient getById(Long id) {
        return recipientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id: " + id));
    }

    public Recipient update(Long id, Recipient updatedRecipient) {
        Recipient existing = getById(id);
        existing.setFirstName(updatedRecipient.getFirstName());
        existing.setLastName(updatedRecipient.getLastName());
        existing.setAge(updatedRecipient.getAge());
        existing.setGender(updatedRecipient.getGender());
        existing.setBloodGroup(updatedRecipient.getBloodGroup());
        existing.setPhone(updatedRecipient.getPhone());
        existing.setEmail(updatedRecipient.getEmail());
        existing.setAddress(updatedRecipient.getAddress());
        existing.setMedicalNeed(updatedRecipient.getMedicalNeed());
        existing.setRegistrationDate(
                updatedRecipient.getRegistrationDate() != null
                        ? updatedRecipient.getRegistrationDate()
                        : existing.getRegistrationDate()
        );
        existing.setStatus(updatedRecipient.getStatus());
        return recipientRepository.save(existing);
    }

    public void delete(Long id) {
        Recipient existing = getById(id);
        recipientRepository.delete(existing);
    }
}
