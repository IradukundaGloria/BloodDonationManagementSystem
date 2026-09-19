package com.blooddonation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BloodDonationApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void donorRecipientAndDonationCrudWithValidation() throws Exception {
        String donorBody = """
                {
                  "firstName": "John",
                  "lastName": "Doe",
                  "age": 25,
                  "gender": "Male",
                  "bloodGroup": "O+",
                  "phone": "0780000000",
                  "email": "john.doe@example.com",
                  "address": "Kigali",
                  "registrationDate": "2026-09-19",
                  "eligibilityStatus": "Eligible"
                }
                """;

        MvcResult createDonor = mockMvc.perform(post("/api/donors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donorBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.donorId").isNumber())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andReturn();

        Long donorId = objectMapper.readTree(createDonor.getResponse().getContentAsString()).get("donorId").asLong();

        mockMvc.perform(get("/api/donors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        mockMvc.perform(get("/api/donors/{id}", donorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bloodGroup").value("O+"));

        String updatedDonor = donorBody.replace("Eligible", "Temporarily Deferred");
        mockMvc.perform(put("/api/donors/{id}", donorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDonor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eligibilityStatus").value("Temporarily Deferred"));

        String recipientBody = """
                {
                  "firstName": "Jane",
                  "lastName": "Doe",
                  "age": 30,
                  "gender": "Female",
                  "bloodGroup": "A+",
                  "phone": "0790000000",
                  "email": "jane.doe@example.com",
                  "address": "Kigali",
                  "medicalNeed": "Blood transfusion",
                  "registrationDate": "2026-09-19",
                  "status": "Pending"
                }
                """;

        MvcResult createRecipient = mockMvc.perform(post("/api/recipients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipientBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recipientId").isNumber())
                .andReturn();

        Long recipientId = objectMapper.readTree(createRecipient.getResponse().getContentAsString())
                .get("recipientId").asLong();

        mockMvc.perform(get("/api/recipients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/recipients/{id}", recipientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicalNeed").value("Blood transfusion"));

        String updatedRecipient = recipientBody.replace("Pending", "Approved");
        mockMvc.perform(put("/api/recipients/{id}", recipientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRecipient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Approved"));

        String donationBody = """
                {
                  "donor": {
                    "donorId": %d
                  },
                  "bloodGroup": "O+",
                  "donationDate": "2026-09-19",
                  "quantity": 450,
                  "donationStatus": "Completed",
                  "medicalApproval": true
                }
                """.formatted(donorId);

        MvcResult createDonation = mockMvc.perform(post("/api/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donationBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.donationId").isNumber())
                .andExpect(jsonPath("$.donor.donorId").value(donorId))
                .andExpect(jsonPath("$.donor.donations").doesNotExist())
                .andReturn();

        Long donationId = objectMapper.readTree(createDonation.getResponse().getContentAsString())
                .get("donationId").asLong();

        mockMvc.perform(get("/api/donations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(450));

        mockMvc.perform(get("/api/donations/{id}", donationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donationStatus").value("Completed"));

        mockMvc.perform(get("/api/donors/{id}", donorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donations", hasSize(1)))
                .andExpect(jsonPath("$.donations[0].donor").doesNotExist());

        String updatedDonation = donationBody.replace("Completed", "Stored");
        mockMvc.perform(put("/api/donations/{id}", donationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDonation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donationStatus").value("Stored"));

        mockMvc.perform(post("/api/donors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donorBody.replace("john.doe@example.com", "not-an-email")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.email", containsString("valid")));

        mockMvc.perform(post("/api/donors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donorBody.replace("\"firstName\": \"John\"", "\"firstName\": \"\"")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.firstName").exists());

        mockMvc.perform(post("/api/donors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donorBody.replace("\"age\": 25", "\"age\": 16")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.age").exists());

        mockMvc.perform(post("/api/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donationBody.replace("\"quantity\": 450", "\"quantity\": 0")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.quantity").exists());

        mockMvc.perform(post("/api/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donationBody.replace("\"donorId\": " + donorId, "\"donorId\": 99999")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Donor not found with id: 99999")));

        mockMvc.perform(get("/api/donors/{id}", 99999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));

        mockMvc.perform(delete("/api/donations/{id}", donationId))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/recipients/{id}", recipientId))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/donors/{id}", donorId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/donors/{id}", donorId))
                .andExpect(status().isNotFound());
    }

    @Test
    void malformedJsonReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/donors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request"));
    }
}
