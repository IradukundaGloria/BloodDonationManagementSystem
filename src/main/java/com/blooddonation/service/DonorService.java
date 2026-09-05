package com.blooddonation.service;

import com.blooddonation.dao.DonorDAO;
import com.blooddonation.entity.Donor;

import java.util.Arrays;
import java.util.List;

public class DonorService {

    private final DonorDAO donorDAO;
    private static final List<String> VALID_BLOOD_GROUPS = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    private static final List<String> VALID_ELIGIBILITY_STATUSES = Arrays.asList("Eligible", "Temporarily Ineligible", "Permanently Ineligible", "Pending Review");

    public DonorService() {
        this.donorDAO = new DonorDAO();
    }

    public DonorService(DonorDAO donorDAO) {
        this.donorDAO = donorDAO;
    }

    /**
     * Layer 2 Business Validation & Save / Update method
     */
    public void saveOrUpdateDonor(Donor donor) throws Exception {
        validateDonor(donor);

        if (donor.getDonorId() == null) {
            donorDAO.save(donor);
        } else {
            donorDAO.update(donor);
        }
    }

    /**
     * Layer 2 Business Rules Verification
     */
    public void validateDonor(Donor donor) throws Exception {
        if (donor == null) {
            throw new IllegalArgumentException("Donor record cannot be null.");
        }

        // Business Rule 1: Donor Minimum & Maximum Age Validation
        if (donor.getAge() == null || donor.getAge() < 18 || donor.getAge() > 75) {
            throw new Exception("Business Validation Error: Donor must be between 18 and 75 years of age to participate in blood donation.");
        }

        // Business Rule 2: Email Uniqueness Verification in Database
        if (donor.getEmail() == null || donor.getEmail().trim().isEmpty()) {
            throw new Exception("Business Validation Error: Donor email address cannot be empty.");
        }

        if (donorDAO.isEmailExists(donor.getEmail(), donor.getDonorId())) {
            throw new Exception("Business Validation Error: The email address '" + donor.getEmail() + "' is already registered to another donor.");
        }

        // Business Rule 3: Valid Blood Group System Check
        if (donor.getBloodGroup() == null || !VALID_BLOOD_GROUPS.contains(donor.getBloodGroup().trim().toUpperCase())) {
            throw new Exception("Business Validation Error: Invalid blood group specified. Must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-.");
        }

        // Business Rule 4: Eligibility Status Validation
        if (donor.getEligibilityStatus() == null || !VALID_ELIGIBILITY_STATUSES.contains(donor.getEligibilityStatus().trim())) {
            throw new Exception("Business Validation Error: Invalid eligibility status. Allowed statuses: Eligible, Temporarily Ineligible, Permanently Ineligible, Pending Review.");
        }
    }

    public List<Donor> getAllDonors() {
        return donorDAO.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorDAO.findById(id);
    }

    public void deleteDonor(Long id) throws Exception {
        Donor donor = donorDAO.findById(id);
        if (donor == null) {
            throw new Exception("Donor with ID " + id + " does not exist.");
        }
        donorDAO.delete(id);
    }

    public long getDonorCount() {
        return donorDAO.count();
    }

    public List<String> getValidBloodGroups() {
        return VALID_BLOOD_GROUPS;
    }

    public List<String> getValidEligibilityStatuses() {
        return VALID_ELIGIBILITY_STATUSES;
    }
}
