package com.blooddonation.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blood_donations")
public class BloodDonation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long donationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @NotNull(message = "Blood group is required")
    @Column(name = "blood_group", nullable = false, length = 10)
    private String bloodGroup;

    @Temporal(TemporalType.DATE)
    @Column(name = "donation_date", nullable = false)
    private Date donationDate;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    @Column(name = "quantity", nullable = false)
    private Integer quantity; // in mL

    @NotNull(message = "Donation status is required")
    @Column(name = "donation_status", nullable = false, length = 50)
    private String donationStatus;

    @Column(name = "medical_approval")
    private Boolean medicalApproval;

    public BloodDonation() {
        this.donationDate = new Date();
        this.donationStatus = "Completed";
        this.medicalApproval = true;
    }

    public BloodDonation(Donor donor, String bloodGroup, Integer quantity, String donationStatus, Boolean medicalApproval) {
        this.donor = donor;
        this.bloodGroup = bloodGroup;
        this.donationDate = new Date();
        this.quantity = quantity;
        this.donationStatus = donationStatus;
        this.medicalApproval = medicalApproval;
    }

    // Getters and Setters
    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDonationStatus() {
        return donationStatus;
    }

    public void setDonationStatus(String donationStatus) {
        this.donationStatus = donationStatus;
    }

    public Boolean getMedicalApproval() {
        return medicalApproval;
    }

    public void setMedicalApproval(Boolean medicalApproval) {
        this.medicalApproval = medicalApproval;
    }
}
