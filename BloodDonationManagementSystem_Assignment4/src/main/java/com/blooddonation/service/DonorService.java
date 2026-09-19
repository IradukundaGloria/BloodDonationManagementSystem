package com.blooddonation.service;

import com.blooddonation.entity.Donor;
import com.blooddonation.exception.ResourceNotFoundException;
import com.blooddonation.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor create(Donor donor) {
        donor.setDonorId(null);
        if (donor.getRegistrationDate() == null) {
            donor.setRegistrationDate(LocalDate.now());
        }
        return donorRepository.save(donor);
    }

    public List<Donor> getAll() {
        return donorRepository.findAll();
    }

    public Donor getById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    public Donor update(Long id, Donor updatedDonor) {
        Donor existing = getById(id);
        existing.setFirstName(updatedDonor.getFirstName());
        existing.setLastName(updatedDonor.getLastName());
        existing.setAge(updatedDonor.getAge());
        existing.setGender(updatedDonor.getGender());
        existing.setBloodGroup(updatedDonor.getBloodGroup());
        existing.setPhone(updatedDonor.getPhone());
        existing.setEmail(updatedDonor.getEmail());
        existing.setAddress(updatedDonor.getAddress());
        existing.setRegistrationDate(
                updatedDonor.getRegistrationDate() != null
                        ? updatedDonor.getRegistrationDate()
                        : existing.getRegistrationDate()
        );
        existing.setEligibilityStatus(updatedDonor.getEligibilityStatus());
        return donorRepository.save(existing);
    }

    public void delete(Long id) {
        Donor existing = getById(id);
        donorRepository.delete(existing);
    }
}
