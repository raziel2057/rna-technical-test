package com.rna.test_ms_client;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rna.test_ms_client.controller.ClientController;
import com.rna.test_ms_client.model.Client;
import com.rna.test_ms_client.repository.ClientRepository;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientController clientController;

    private Client client;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        client = new Client("Raúl Naranjo", "Masculino", 33, "Dirección Falsa", "123-456-7890", "P@assword", true);
    }

    @Test
    void testGetAllClients() throws Exception {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Raúl Naranjo"));
    }

    @Test
    void testGetClientById() throws Exception {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Raúl Naranjo"));
    }

    @Test
    void testCreateClient() throws Exception {
        when(clientRepository.save(client)).thenReturn(client);

        String clientJson = objectMapper.writeValueAsString(client); // Convert client to JSON

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson)) // Use JSON content
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateClient() throws Exception {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        client.setAge(31); // Update the client object as needed
        String clientJson = objectMapper.writeValueAsString(client); // Convert updated client to JSON

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clients/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson)) // Use JSON content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(31));
    }

    @Test
    void testDeleteClient() throws Exception {
        doNothing().when(clientRepository).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAllClients() throws Exception {
        doNothing().when(clientRepository).deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
