package com.blooddonation.managedbean;

import com.blooddonation.dao.GenericDAO;
import com.blooddonation.entity.BloodInventory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "inventoryBean")
@ViewScoped
public class InventoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private BloodInventory inventory;
    private List<BloodInventory> inventoryList;
    private final GenericDAO<BloodInventory, Long> inventoryDAO;
    private static final List<String> VALID_BLOOD_GROUPS = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

    public InventoryBean() {
        this.inventoryDAO = new GenericDAO<>(BloodInventory.class);
        this.inventory = new BloodInventory();
    }

    @PostConstruct
    public void init() {
        loadInventory();
    }

    public void loadInventory() {
        try {
            this.inventoryList = inventoryDAO.findAll();
        } catch (Exception e) {
            addErrorMessage("Failed to load blood inventory stock: " + e.getMessage());
        }
    }

    public String prepareNewStock() {
        if (!isStaffAuthenticated()) {
            return redirectToLogin();
        }
        this.inventory = new BloodInventory();
        return "inventory-form.xhtml?faces-redirect=true";
    }

    public String saveStock() {
        if (!isStaffAuthenticated()) {
            return redirectToLogin();
        }
        try {
            validateInventory(this.inventory);

            // Calculate storage status dynamically based on available volume
            if (this.inventory.getQuantityAvailable() < 3000) {
                this.inventory.setStorageStatus("Critical");
            } else if (this.inventory.getQuantityAvailable() < 6000) {
                this.inventory.setStorageStatus("Low Stock");
            } else {
                this.inventory.setStorageStatus("Normal");
            }
            this.inventory.setLastUpdated(new Date());

            if (inventory.getInventoryId() == null) {
                inventoryDAO.save(inventory);
                addSuccessMessage("Blood inventory stock successfully registered for " + inventory.getBloodGroup());
            } else {
                inventoryDAO.update(inventory);
                addSuccessMessage("Blood inventory stock updated for " + inventory.getBloodGroup());
            }
            this.inventory = new BloodInventory();
            loadInventory();
            return "inventory-list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            return null;
        }
    }

    public String prepareEditStock(Long inventoryId) {
        if (!isStaffAuthenticated()) {
            return redirectToLogin();
        }
        try {
            this.inventory = inventoryDAO.findById(inventoryId);
            if (this.inventory == null) {
                addErrorMessage("Selected inventory record not found.");
                return "inventory-list.xhtml";
            }
            return "inventory-form.xhtml";
        } catch (Exception e) {
            addErrorMessage("Error loading stock for edit: " + e.getMessage());
            return null;
        }
    }

    public String deleteStock(Long inventoryId) {
        if (!isStaffAuthenticated()) {
            return redirectToLogin();
        }
        try {
            inventoryDAO.delete(inventoryId);
            addSuccessMessage("Blood inventory record successfully removed.");
            loadInventory();
        } catch (Exception e) {
            addErrorMessage("Error deleting inventory item: " + e.getMessage());
        }
        return "inventory-list.xhtml?faces-redirect=true";
    }

    private void validateInventory(BloodInventory inv) throws Exception {
        if (inv == null) {
            throw new IllegalArgumentException("Inventory item cannot be null.");
        }
        if (inv.getBloodGroup() == null || !VALID_BLOOD_GROUPS.contains(inv.getBloodGroup().trim().toUpperCase())) {
            throw new Exception("Business Validation Error: Must select a valid blood group (A+, A-, B+, B-, AB+, AB-, O+, O-).");
        }
        if (inv.getQuantityAvailable() == null || inv.getQuantityAvailable() < 0) {
            throw new Exception("Business Validation Error: Quantity available cannot be negative.");
        }
    }

    private boolean isStaffAuthenticated() {
        FacesContext context = FacesContext.getCurrentInstance();
        AuthBean authBean = context.getApplication().evaluateExpressionGet(context, "#{authBean}", AuthBean.class);
        return authBean != null && authBean.isLoggedIn();
    }

    private String redirectToLogin() {
        addErrorMessage("Access Restricted: Admin / Doctor login required for adding, editing, or deleting blood stock.");
        return "staff-login.xhtml?faces-redirect=true";
    }

    private void addSuccessMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS: ", message));
    }

    private void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: ", message));
    }

    // Getters and Setters
    public BloodInventory getInventory() {
        return inventory;
    }

    public void setInventory(BloodInventory inventory) {
        this.inventory = inventory;
    }

    public List<BloodInventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<BloodInventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public List<String> getSupportedBloodGroups() {
        return VALID_BLOOD_GROUPS;
    }
}
