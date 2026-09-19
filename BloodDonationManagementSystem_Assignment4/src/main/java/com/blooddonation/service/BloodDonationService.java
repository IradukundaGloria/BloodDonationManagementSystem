package com.blooddonation.service;

import com.blooddonation.entity.BloodDonation;
import com.blooddonation.entity.Donor;
import com.blooddonation.exception.ResourceNotFoundException;
import com.blooddonation.repository.BloodDonationRepository;
import com.blooddonation.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodDonationService {

    private final BloodDonationRepository bloodDonationRepository;
    private final DonorRepository donorRepository;

    public BloodDonationService(BloodDonationRepository bloodDonationRepository, DonorRepository donorRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
        this.donorRepository = donorRepository;
    }

    public BloodDonation create(BloodDonation donation) {
        donation.setDonationId(null);
        donation.setDonor(resolveExistingDonor(donation));
        return bloodDonationRepository.save(donation);
    }

    public List<BloodDonation> getAll() {
        return bloodDonationRepository.findAll();
    }

    public BloodDonation getById(Long id) {
        return bloodDonationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood donation not found with id: " + id));
    }

    public BloodDonation update(Long id, BloodDonation updatedDonation) {
        BloodDonation existing = getById(id);
        existing.setDonor(resolveExistingDonor(updatedDonation));
        existing.setBloodGroup(updatedDonation.getBloodGroup());
        existing.setDonationDate(updatedDonation.getDonationDate());
        existing.setQuantity(updatedDonation.getQuantity());
        existing.setDonationStatus(updatedDonation.getDonationStatus());
        existing.setMedicalApproval(updatedDonation.getMedicalApproval());
        return bloodDonationRepository.save(existing);
    }

    public void delete(Long id) {
        BloodDonation existing = getById(id);
        bloodDonationRepository.delete(existing);
    }

    private Donor resolveExistingDonor(BloodDonation donation) {
        if (donation.getDonor() == null || donation.getDonor().getDonorId() == null) {
            throw new ResourceNotFoundException("Donor not found with id: null");
        }
        Long donorId = donation.getDonor().getDonorId();
        return donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + donorId));
    }
}
