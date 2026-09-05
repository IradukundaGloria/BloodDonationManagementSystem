package com.blooddonation.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * BloodInventory entity representing blood stock levels stored per blood group.
 * NOTE: BloodInventory is a resource/stock entity, NOT a person.
 */
@Entity
@Table(name = "blood_inventory")
public class BloodInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @NotNull(message = "Blood group is required")
    @Column(name = "blood_group", nullable = false, unique = true, length = 10)
    private String bloodGroup;

    @NotNull(message = "Quantity available is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable; // in mL

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated", nullable = false)
    private Date lastUpdated;

    @NotNull(message = "Storage status is required")
    @Column(name = "storage_status", nullable = false, length = 50)
    private String storageStatus; // Normal, Low Stock, Critical

    public BloodInventory() {
        this.lastUpdated = new Date();
        this.storageStatus = "Normal";
    }

    public BloodInventory(String bloodGroup, Integer quantityAvailable, String storageStatus) {
        this.bloodGroup = bloodGroup;
        this.quantityAvailable = quantityAvailable;
        this.lastUpdated = new Date();
        this.storageStatus = storageStatus;
    }

    // Getters and Setters
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(String storageStatus) {
        this.storageStatus = storageStatus;
    }
}
