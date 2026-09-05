package com.blooddonation.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "donors")
public class Donor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long donorId;

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Donor must be at least 18 years old")
    @Max(value = 75, message = "Donor age cannot exceed 75 years")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull(message = "Gender is required")
    @Column(name = "gender", nullable = false, length = 20)
    private String gender;

    @NotNull(message = "Blood group is required")
    @Column(name = "blood_group", nullable = false, length = 10)
    private String bloodGroup;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be valid (7-15 digits)")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address format")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "Address is required")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @NotNull(message = "Eligibility status is required")
    @Column(name = "eligibility_status", nullable = false, length = 50)
    private String eligibilityStatus;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BloodDonation> donations = new ArrayList<>();

    public Donor() {
        this.registrationDate = new Date();
        this.eligibilityStatus = "Eligible";
    }

    public Donor(String firstName, String lastName, Integer age, String gender, String bloodGroup,
                 String phone, String email, String address, String eligibilityStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registrationDate = new Date();
        this.eligibilityStatus = eligibilityStatus != null ? eligibilityStatus : "Eligible";
    }

    // Getters and Setters
    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(String eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public List<BloodDonation> getDonations() {
        return donations;
    }

    public void setDonations(List<BloodDonation> donations) {
        this.donations = donations;
    }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}
