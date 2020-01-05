package com.cdc.atm.web.integration;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.Summary;
import com.cdc.atm.web.model.Withdraw;
import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "test" })
public class WithdrawIntegrationTest {

    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    private static final String ACCOUNT_101010  = "101010";
    private static final String ACCOUNT_111111  = "111111";

    @Autowired
    private MockMvc             mockMvc;

    @Autowired
    private AccountComponent    accountComponent;

    @Autowired
    private AccountRepository   accountRepository;

    /**
     * When creating a withdraw transaction to an account, and the account does not
     * have enough funds, it's expected to receive an error
     * 
     * @throws Exception
     */
    @Test
    public void givenAccountWithNotEnoughFunds_whenWithdraw_thenReceiveInsufficientBalanceError() throws Exception {
        Account account = accountRepository.findByAccountNumber(ACCOUNT_101010);
        BigDecimal origBalance = account.getBalance();

        // Given
        accountComponent.setAccountNumber(ACCOUNT_101010);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/withdraw").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .param("option", Withdraw.Option.DEDUCT_HUNDRED_DOLLARS.toString()))
                // Then
                .andExpect(status().isOk()).andExpect(view().name("withdraw"))
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", Arrays.asList("Insufficient balance $100")));
        account = accountRepository.findByAccountNumber(ACCOUNT_101010);
        assertThat(account.getBalance()).isEqualTo(origBalance);
    }

    /**
     * When creating a withdraw transaction to an account, this account is expected
     * to have its balance reduced by the amount indicated in the withdraw
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenWithdraw_thenAccountBalanceReduced() throws Exception {
        Account account = accountRepository.findByAccountNumber(ACCOUNT_111111);
        BigDecimal origBalance = account.getBalance();

        // Given
        accountComponent.setAccountNumber(ACCOUNT_111111);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/withdraw").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .param("option", Withdraw.Option.DEDUCT_TEN_DOLLARS.toString()))
                // Then
                .andExpect(status().isOk()).andExpect(view().name("summary"))
                .andExpect(model().attributeExists(Summary.Metadata.MODEL));
        account = accountRepository.findByAccountNumber(ACCOUNT_111111);
        assertThat(account.getBalance()).isEqualTo(origBalance.subtract(new BigDecimal(10)));
    }

}
