package com.blooddonation.service;

import com.blooddonation.dao.RecipientDAO;
import com.blooddonation.entity.Recipient;

import java.util.Arrays;
import java.util.List;

public class RecipientService {

    private final RecipientDAO recipientDAO;
    private static final List<String> VALID_BLOOD_GROUPS = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    private static final List<String> VALID_STATUSES = Arrays.asList("Active", "Pending Request", "Fulfilled", "Inactive");

    public RecipientService() {
        this.recipientDAO = new RecipientDAO();
    }

    public RecipientService(RecipientDAO recipientDAO) {
        this.recipientDAO = recipientDAO;
    }

    /**
     * Layer 2 Business Validation & Save / Update method
     */
    public void saveOrUpdateRecipient(Recipient recipient) throws Exception {
        validateRecipient(recipient);

        if (recipient.getRecipientId() == null) {
            recipientDAO.save(recipient);
        } else {
            recipientDAO.update(recipient);
        }
    }

    /**
     * Layer 2 Business Rules Verification
     */
    public void validateRecipient(Recipient recipient) throws Exception {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient record cannot be null.");
        }

        // Business Rule 1: Recipient Age Range Validation
        if (recipient.getAge() == null || recipient.getAge() < 0 || recipient.getAge() > 120) {
            throw new Exception("Business Validation Error: Recipient age must be a realistic value between 0 and 120.");
        }

        // Business Rule 2: Email Uniqueness Verification in Database
        if (recipient.getEmail() == null || recipient.getEmail().trim().isEmpty()) {
            throw new Exception("Business Validation Error: Recipient email address cannot be empty.");
        }

        if (recipientDAO.isEmailExists(recipient.getEmail(), recipient.getRecipientId())) {
            throw new Exception("Business Validation Error: The email address '" + recipient.getEmail() + "' is already registered to another recipient.");
        }

        // Business Rule 3: Valid Blood Group System Check
        if (recipient.getBloodGroup() == null || !VALID_BLOOD_GROUPS.contains(recipient.getBloodGroup().trim().toUpperCase())) {
            throw new Exception("Business Validation Error: Invalid blood group specified. Must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-.");
        }

        // Business Rule 4: Medical Need Verification
        if (recipient.getMedicalNeed() == null || recipient.getMedicalNeed().trim().isEmpty()) {
            throw new Exception("Business Validation Error: Medical need details are mandatory for registering a recipient.");
        }

        // Business Rule 5: Status Validation
        if (recipient.getStatus() == null || !VALID_STATUSES.contains(recipient.getStatus().trim())) {
            throw new Exception("Business Validation Error: Invalid recipient status. Allowed statuses: Active, Pending Request, Fulfilled, Inactive.");
        }
    }

    public List<Recipient> getAllRecipients() {
        return recipientDAO.findAll();
    }

    public Recipient getRecipientById(Long id) {
        return recipientDAO.findById(id);
    }

    public void deleteRecipient(Long id) throws Exception {
        Recipient recipient = recipientDAO.findById(id);
        if (recipient == null) {
            throw new Exception("Recipient with ID " + id + " does not exist.");
        }
        recipientDAO.delete(id);
    }

    public long getRecipientCount() {
        return recipientDAO.count();
    }

    public List<String> getValidBloodGroups() {
        return VALID_BLOOD_GROUPS;
    }

    public List<String> getValidStatuses() {
        return VALID_STATUSES;
    }
}
