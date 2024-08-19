package com.rna.test_ms_account;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rna.test_ms_account.controller.AccountController;
import com.rna.test_ms_account.model.Account;
import com.rna.test_ms_account.repository.AccountRepository;
import com.rna.test_ms_account.repository.MovementRepository;
import com.rna.test_ms_account.repository.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

	@Autowired(required=true)
    private MockMvc mockMvc;
	
	@MockBean
    private AccountRepository accountRepository;

	@MockBean
    private PersonRepository personRepository;
	
	@MockBean
    private MovementRepository movementRepository;

    @InjectMocks
    private AccountController accountController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }
    
    @Test
    void testGetAllAccounts() throws Exception {
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(new Account(1L, "Savings", BigDecimal.valueOf(1000), true, 1L));
        when(accountRepository.findAll()).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].accountNumber").value(1))
               .andExpect(jsonPath("$[0].accountType").value("Savings"));
    }

    @Test
    void testGetAllAccountsWithName() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, "Savings", BigDecimal.valueOf(1000), true, 1L));
        when(personRepository.findIdentificationByBame("John")).thenReturn(1L);
        when(accountRepository.findAccountsByIdentification(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts").param("name", "John"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].accountNumber").value(1))
               .andExpect(jsonPath("$[0].accountType").value("Savings"));
    }

    @Test
    void testGetAllAccountsNoContent() throws Exception {
        when(personRepository.findIdentificationByBame("Nonexistent")).thenReturn(null);

        mockMvc.perform(get("/api/accounts").param("name", "Nonexistent"))
               .andExpect(status().isNoContent());
    }

}
