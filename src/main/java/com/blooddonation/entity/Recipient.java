package com.blooddonation.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipients")
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_id")
    private Long recipientId;

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be a positive integer")
    @Max(value = 120, message = "Age cannot exceed 120 years")
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

    @NotNull(message = "Medical need is required")
    @Size(min = 3, max = 200, message = "Medical need details must be provided")
    @Column(name = "medical_need", nullable = false, length = 200)
    private String medicalNeed;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @NotNull(message = "Status is required")
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BloodRequest> bloodRequests = new ArrayList<>();

    public Recipient() {
        this.registrationDate = new Date();
        this.status = "Active";
    }

    public Recipient(String firstName, String lastName, Integer age, String gender, String bloodGroup,
                     String phone, String email, String address, String medicalNeed, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.medicalNeed = medicalNeed;
        this.registrationDate = new Date();
        this.status = status != null ? status : "Active";
    }

    // Getters and Setters
    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
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

    public String getMedicalNeed() {
        return medicalNeed;
    }

    public void setMedicalNeed(String medicalNeed) {
        this.medicalNeed = medicalNeed;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BloodRequest> getBloodRequests() {
        return bloodRequests;
    }

    public void setBloodRequests(List<BloodRequest> bloodRequests) {
        this.bloodRequests = bloodRequests;
    }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}
