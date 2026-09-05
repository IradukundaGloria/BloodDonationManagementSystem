package com.blooddonation.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blood_requests")
public class BloodRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    @NotNull(message = "Blood group is required")
    @Column(name = "blood_group", nullable = false, length = 10)
    private String bloodGroup;

    @NotNull(message = "Requested quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    @Column(name = "quantity_requested", nullable = false)
    private Integer quantityRequested; // in mL

    @Temporal(TemporalType.DATE)
    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @NotNull(message = "Priority is required")
    @Column(name = "priority", nullable = false, length = 20)
    private String priority; // High, Normal, Urgent

    @NotNull(message = "Request status is required")
    @Column(name = "request_status", nullable = false, length = 50)
    private String requestStatus; // Pending, Approved, Fulfilled, Rejected

    public BloodRequest() {
        this.requestDate = new Date();
        this.priority = "Normal";
        this.requestStatus = "Pending";
    }

    public BloodRequest(Recipient recipient, String bloodGroup, Integer quantityRequested, String priority, String requestStatus) {
        this.recipient = recipient;
        this.bloodGroup = bloodGroup;
        this.quantityRequested = quantityRequested;
        this.requestDate = new Date();
        this.priority = priority;
        this.requestStatus = requestStatus;
    }

    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getQuantityRequested() {
        return quantityRequested;
    }

    public void setQuantityRequested(Integer quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
