package com.blooddonation.managedbean;

import com.blooddonation.entity.Recipient;
import com.blooddonation.service.RecipientService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "recipientBean")
@ViewScoped
public class RecipientBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Recipient recipient;
    private List<Recipient> recipientList;
    private final RecipientService recipientService;

    public RecipientBean() {
        this.recipientService = new RecipientService();
        this.recipient = new Recipient();
    }

    @PostConstruct
    public void init() {
        loadRecipients();
    }

    public void loadRecipients() {
        try {
            this.recipientList = recipientService.getAllRecipients();
        } catch (Exception e) {
            addErrorMessage("Failed to load recipient records: " + e.getMessage());
        }
    }

    public String prepareNewRecipient() {
        this.recipient = new Recipient();
        return "recipient-form.xhtml?faces-redirect=true";
    }

    public String saveRecipient() {
        try {
            recipientService.saveOrUpdateRecipient(this.recipient);
            addSuccessMessage("Recipient record successfully " + (recipient.getRecipientId() != null ? "updated." : "registered."));
            this.recipient = new Recipient();
            loadRecipients();
            return "recipient-list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            return null; // Stay on form page to display error message
        }
    }

    public String prepareEditRecipient(Long recipientId) {
        try {
            this.recipient = recipientService.getRecipientById(recipientId);
            if (this.recipient == null) {
                addErrorMessage("Selected recipient record could not be found.");
                return "recipient-list.xhtml";
            }
            return "recipient-form.xhtml";
        } catch (Exception e) {
            addErrorMessage("Error loading recipient for edit: " + e.getMessage());
            return null;
        }
    }

    public String deleteRecipient(Long recipientId) {
        try {
            recipientService.deleteRecipient(recipientId);
            addSuccessMessage("Recipient record successfully deleted.");
            loadRecipients();
        } catch (Exception e) {
            addErrorMessage("Error deleting recipient: " + e.getMessage());
        }
        return "recipient-list.xhtml?faces-redirect=true";
    }

    public List<String> getSupportedBloodGroups() {
        return recipientService.getValidBloodGroups();
    }

    public List<String> getSupportedStatuses() {
        return recipientService.getValidStatuses();
    }

    // Flash/Context message helpers
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
    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public List<Recipient> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }
}
