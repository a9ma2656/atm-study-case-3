package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = { "test" })
public class AccountServiceTest {

    @Autowired
    private TransactionService service;

    @Autowired
    private AccountRepository  repository;

    /**
     * When creating a withdraw transaction to an account, this account is expected
     * to have its balance reduced by the amount indicated in the withdraw
     */
    @Test
    public void givenNewAccount_whenWithdrawAccountBalance_thenBalanceReduced() {
        Account account = repository.findByAccountNumber("333333");
        if (account != null) {
            repository.delete(account);
        }

        // Given
        Account testAccount = new Account();
        testAccount.setAccountNumber("333333");
        testAccount.setBalance(new BigDecimal(100));
        testAccount.setName("Account 333333");
        testAccount.setPin("123456");
        repository.save(testAccount);

        // When
        account = service.fundWithdraw("333333", new BigDecimal(90));
        account = repository.findByAccountNumber("333333");

        // Then
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo("333333");
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(10));
        assertThat(account.getName()).isEqualTo("Account 333333");
        assertThat(account.getPin()).isEqualTo("123456");

    }
}
