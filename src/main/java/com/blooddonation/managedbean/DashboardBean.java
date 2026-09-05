package com.blooddonation.managedbean;

import com.blooddonation.dao.GenericDAO;
import com.blooddonation.entity.*;
import com.blooddonation.service.DonorService;
import com.blooddonation.service.RecipientService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private long totalDonors;
    private long totalRecipients;
    private long totalDonations;
    private long totalRequests;
    private List<BloodInventory> inventoryList;

    private final DonorService donorService;
    private final RecipientService recipientService;
    private final GenericDAO<BloodInventory, Long> inventoryDAO;
    private final GenericDAO<BloodDonation, Long> donationDAO;
    private final GenericDAO<BloodRequest, Long> requestDAO;
    private final GenericDAO<Doctor, Long> doctorDAO;
    private final GenericDAO<Admin, Long> adminDAO;

    public DashboardBean() {
        this.donorService = new DonorService();
        this.recipientService = new RecipientService();
        this.inventoryDAO = new GenericDAO<>(BloodInventory.class);
        this.donationDAO = new GenericDAO<>(BloodDonation.class);
        this.requestDAO = new GenericDAO<>(BloodRequest.class);
        this.doctorDAO = new GenericDAO<>(Doctor.class);
        this.adminDAO = new GenericDAO<>(Admin.class);
    }

    @PostConstruct
    public void init() {
        seedInitialDataIfEmpty();
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        try {
            this.totalDonors = donorService.getDonorCount();
            this.totalRecipients = recipientService.getRecipientCount();
            this.totalDonations = donationDAO.count();
            this.totalRequests = requestDAO.count();
            this.inventoryList = inventoryDAO.findAll();
        } catch (Exception e) {
            this.inventoryList = new ArrayList<>();
        }
    }

    private void seedInitialDataIfEmpty() {
        try {
            // Seed Doctors if empty
            if (doctorDAO.count() == 0) {
                Doctor doc1 = new Doctor("DOC-1001", "Sarah", "Jenkins", "Hematology Specialist", "+1-555-0100", "dr.jenkins@hospital-example.org", "LIC-DOC-88392");
                Doctor doc2 = new Doctor("DOC-1002", "Robert", "Vance", "Transfusion Medicine", "+1-555-0101", "dr.vance@hospital-example.org", "LIC-DOC-99201");
                doctorDAO.save(doc1);
                doctorDAO.save(doc2);
            }

            // Seed Admins if empty
            if (adminDAO.count() == 0) {
                Admin adm1 = new Admin("ADM-1001", "Marcus", "Vance", "marcus.admin", "admin.marcus@hospital-example.org", "System Administrator");
                adminDAO.save(adm1);
            }

            if (donorService.getDonorCount() == 0) {
                // Seed Donors
                Donor d1 = new Donor("Alexander", "Wright", 29, "Male", "O+", "+1-555-0192", "alex.wright@univ-example.edu", "142 University Way, Campus North", "Eligible");
                Donor d2 = new Donor("Sophia", "Martinez", 34, "Female", "A-", "+1-555-0384", "sophia.m@univ-example.edu", "88 Healthcare Blvd, Suite 4B", "Eligible");
                Donor d3 = new Donor("Marcus", "Chen", 41, "Male", "B+", "+1-555-0721", "marcus.chen@univ-example.edu", "205 Oakridge Drive, East Ward", "Pending Review");
                donorService.saveOrUpdateDonor(d1);
                donorService.saveOrUpdateDonor(d2);
                donorService.saveOrUpdateDonor(d3);

                // Seed Recipients
                Recipient r1 = new Recipient("Emily", "Taylor", 24, "Female", "O+", "+1-555-9012", "emily.t@univ-example.edu", "12 General Hospital Plaza", "Post-Surgical Blood Transfusion Required", "Active");
                Recipient r2 = new Recipient("David", "Kim", 52, "Male", "AB+", "+1-555-8823", "david.kim@univ-example.edu", "543 St. Jude Avenue, West End", "Chronic Anemia Management", "Active");
                recipientService.saveOrUpdateRecipient(r1);
                recipientService.saveOrUpdateRecipient(r2);

                // Seed Blood Inventory
                inventoryDAO.save(new BloodInventory("A+", 14500, "Normal"));
                inventoryDAO.save(new BloodInventory("A-", 4200, "Low Stock"));
                inventoryDAO.save(new BloodInventory("B+", 11800, "Normal"));
                inventoryDAO.save(new BloodInventory("B-", 2100, "Critical"));
                inventoryDAO.save(new BloodInventory("AB+", 6500, "Normal"));
                inventoryDAO.save(new BloodInventory("AB-", 1800, "Critical"));
                inventoryDAO.save(new BloodInventory("O+", 22000, "Normal"));
                inventoryDAO.save(new BloodInventory("O-", 3100, "Low Stock"));
            }
        } catch (Exception ignored) {
            // Seeding exception caught silently if already present
        }
    }

    // Getters
    public long getTotalDonors() {
        return totalDonors;
    }

    public long getTotalRecipients() {
        return totalRecipients;
    }

    public long getTotalDonations() {
        return totalDonations;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public List<BloodInventory> getInventoryList() {
        return inventoryList;
    }
}
