package com.blooddonation.managedbean;

import com.blooddonation.entity.Donor;
import com.blooddonation.service.DonorService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "donorBean")
@ViewScoped
public class DonorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Donor donor;
    private List<Donor> donorList;
    private final DonorService donorService;

    public DonorBean() {
        this.donorService = new DonorService();
        this.donor = new Donor();
    }

    @PostConstruct
    public void init() {
        loadDonors();
    }

    public void loadDonors() {
        try {
            this.donorList = donorService.getAllDonors();
        } catch (Exception e) {
            addErrorMessage("Failed to load donor records: " + e.getMessage());
        }
    }

    public String prepareNewDonor() {
        this.donor = new Donor();
        return "donor-form.xhtml?faces-redirect=true";
    }

    public String saveDonor() {
        try {
            donorService.saveOrUpdateDonor(this.donor);
            addSuccessMessage("Donor record successfully " + (donor.getDonorId() != null ? "updated." : "registered."));
            this.donor = new Donor();
            loadDonors();
            return "donor-list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            return null; // Stay on form page to show error message
        }
    }

    public String prepareEditDonor(Long donorId) {
        try {
            this.donor = donorService.getDonorById(donorId);
            if (this.donor == null) {
                addErrorMessage("Selected donor record could not be found.");
                return "donor-list.xhtml";
            }
            return "donor-form.xhtml";
        } catch (Exception e) {
            addErrorMessage("Error loading donor for edit: " + e.getMessage());
            return null;
        }
    }

    public String deleteDonor(Long donorId) {
        try {
            donorService.deleteDonor(donorId);
            addSuccessMessage("Donor record successfully deleted.");
            loadDonors();
        } catch (Exception e) {
            addErrorMessage("Error deleting donor: " + e.getMessage());
        }
        return "donor-list.xhtml?faces-redirect=true";
    }

    public List<String> getSupportedBloodGroups() {
        return donorService.getValidBloodGroups();
    }

    public List<String> getSupportedEligibilityStatuses() {
        return donorService.getValidEligibilityStatuses();
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
    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public List<Donor> getDonorList() {
        return donorList;
    }

    public void setDonorList(List<Donor> donorList) {
        this.donorList = donorList;
    }
}
