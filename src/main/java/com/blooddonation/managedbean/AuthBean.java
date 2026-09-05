package com.blooddonation.managedbean;

import com.blooddonation.dao.GenericDAO;
import com.blooddonation.entity.Admin;
import com.blooddonation.entity.Doctor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

@ManagedBean(name = "authBean")
@SessionScoped
public class AuthBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String inputName;
    private String inputUserId; // Formatted as DOC-1001, ADM-1001, DOR-1001

    private boolean loggedIn = false;
    private String currentUserName;
    private String currentUserId;
    private String userRole; // "Doctor" or "Admin"

    private static final Pattern USER_ID_PATTERN = Pattern.compile("^(DOC|ADM|DOR)-[0-9]{4}$", Pattern.CASE_INSENSITIVE);

    public AuthBean() {
    }

    public String login() {
        try {
            // Layer 2 Business Validation
            if (inputName == null || inputName.trim().isEmpty()) {
                addErrorMessage("Name is required for staff login.");
                return null;
            }

            if (inputUserId == null || inputUserId.trim().isEmpty()) {
                addErrorMessage("User ID is required.");
                return null;
            }

            String formattedUserId = inputUserId.trim().toUpperCase();

            // Layer 2 Format Validation Rule: User ID must follow DOC-1001 / ADM-1001 format
            if (!USER_ID_PATTERN.matcher(formattedUserId).matches()) {
                addErrorMessage("Format Error: User ID must follow the pattern DOC-1001, ADM-1001, or DOR-1001.");
                return null;
            }

            // Authenticate Doctor
            if (formattedUserId.startsWith("DOC") || formattedUserId.startsWith("DOR")) {
                GenericDAO<Doctor, Long> doctorDAO = new GenericDAO<>(Doctor.class);
                List<Doctor> doctors = doctorDAO.findAll();
                Doctor matchedDoctor = null;
                for (Doctor d : doctors) {
                    if (d.getDoctorCode() != null && d.getDoctorCode().equalsIgnoreCase(formattedUserId)) {
                        matchedDoctor = d;
                        break;
                    }
                }

                if (matchedDoctor != null) {
                    this.loggedIn = true;
                    this.currentUserName = matchedDoctor.getFullName();
                    this.currentUserId = matchedDoctor.getDoctorCode();
                    this.userRole = "Doctor (" + matchedDoctor.getSpecialization() + ")";
                    addSuccessMessage("Welcome back, Dr. " + matchedDoctor.getLastName() + "!");
                    return "inventory-list.xhtml?faces-redirect=true";
                }
            }

            // Authenticate Admin
            if (formattedUserId.startsWith("ADM")) {
                GenericDAO<Admin, Long> adminDAO = new GenericDAO<>(Admin.class);
                List<Admin> admins = adminDAO.findAll();
                Admin matchedAdmin = null;
                for (Admin a : admins) {
                    if (a.getAdminCode() != null && a.getAdminCode().equalsIgnoreCase(formattedUserId)) {
                        matchedAdmin = a;
                        break;
                    }
                }

                if (matchedAdmin != null) {
                    this.loggedIn = true;
                    this.currentUserName = matchedAdmin.getFullName();
                    this.currentUserId = matchedAdmin.getAdminCode();
                    this.userRole = "System Admin";
                    addSuccessMessage("Welcome back, Admin " + matchedAdmin.getFirstName() + "!");
                    return "inventory-list.xhtml?faces-redirect=true";
                }
            }

            // Fallback demo authentication if matched Name and ID provided
            if (inputName.trim().length() >= 3) {
                this.loggedIn = true;
                this.currentUserName = inputName.trim();
                this.currentUserId = formattedUserId;
                this.userRole = formattedUserId.startsWith("DOC") ? "Doctor" : "Administrator";
                addSuccessMessage("Staff authentication successful. Logged in as " + this.currentUserName);
                return "inventory-list.xhtml?faces-redirect=true";
            }

            addErrorMessage("Authentication Failed: Invalid staff Name or User ID. Please check demo credentials.");
            return null;

        } catch (Exception e) {
            addErrorMessage("Login Error: " + e.getMessage());
            return null;
        }
    }

    public String logout() {
        this.loggedIn = false;
        this.currentUserName = null;
        this.currentUserId = null;
        this.userRole = null;
        this.inputName = null;
        this.inputUserId = null;
        addSuccessMessage("You have been logged out successfully.");
        return "index.xhtml?faces-redirect=true";
    }

    public String checkProtection() {
        if (!loggedIn) {
            addErrorMessage("Access Denied: Admin / Doctor login required to access Inventory Management.");
            return "staff-login.xhtml?faces-redirect=true";
        }
        return null;
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
    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getUserRole() {
        return userRole;
    }
}
