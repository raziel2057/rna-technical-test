package com.rna.test_ms_client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rna.test_ms_client.model.Client;
import com.rna.test_ms_client.repository.ClientRepository;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientControllerIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper(); 
    }

    @Test
    void testGetAllClients() throws Exception {
        // Arrange
        Client client = new Client("Jose Lema", "Masculino", 30, "Otavalo sn y principal", "098254785", "1234", true);
        client = clientRepository.save(client);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jose Lema"));
        //Delete client
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/{id}", client.getIdentification()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetClientById() throws Exception {
        // Arrange
    	Client client = new Client("Jose Lema", "Masculino", 30, "Otavalo sn y principal", "098254785", "1234", true);
        client = clientRepository.save(client);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/{id}", client.getIdentification())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jose Lema"));
        //Delete client
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/{id}", client.getIdentification()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testCreateClient() throws Exception {
        // Arrange
        Client client = new Client("Jose Lema", "Masculino", 30, "Otavalo sn y principal", "098254785", "1234", true);
        String clientJson = objectMapper.writeValueAsString(client); // Convert client to JSON

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson)) // Use JSON content
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn(); // Capture the result to extract the response

        // Extract the response body
        String responseBody = result.getResponse().getContentAsString();

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jose Lema"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].identification").exists()); // Check if identification exists

        // Assuming that client.getIdentification() returns the actual ID
        // Extract the identification from the response and assert it
        Long clientId = objectMapper.readTree(responseBody).get("identification").asLong();
        assertNotNull(clientId); // Check if the ID is not null

        // Delete client
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/{id}", clientId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateClient() throws Exception {
        // Arrange
    	Client client = new Client("Jose Lema", "Masculino", 30, "Otavalo sn y principal", "098254785", "1234", true);
        client = clientRepository.save(client);
        client.setAge(55);
        String clientJson = objectMapper.writeValueAsString(client); // Convert updated client to JSON

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/clients/{id}", client.getIdentification())
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson)) // Use JSON content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(55));
        
        //Delete client
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/{id}", client.getIdentification()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
