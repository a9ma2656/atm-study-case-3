package com.cdc.atm.web.repository;

import com.cdc.atm.web.model.entity.Account;
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
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    /**
     * When adding an account, this account is expected to be there when getting
     * user by number account
     */
    @Test
    public void givenNewAccount_whenGettingByAccountNumber_thenNewAccountFound() {
        Account account = repository.findByAccountNumber("111111");
        if (account != null) {
            repository.delete(account);
        }

        // Given
        Account testAccount = new Account();
        testAccount.setAccountNumber("111111");
        testAccount.setBalance(new BigDecimal(100));
        testAccount.setName("Account 111111");
        testAccount.setPin("123456");
        repository.save(testAccount);

        // When
        account = repository.findByAccountNumber("111111");

        // Then
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo("111111");
        assertThat(account.getPin()).isEqualTo("123456");
        assertThat(account.getName()).isEqualTo("Account 111111");
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(100));
    }

    /**
     * When adding an account, this account is expected to be there when getting
     * list of accounts
     */
    @Test
    public void givenNewAccount_whenGettingListAccounts_thenNewAccountFound() {
        Account account = repository.findByAccountNumber("222222");
        if (account != null) {
            repository.delete(account);
        }

        // Given
        Account testAccount = new Account();
        testAccount.setAccountNumber("222222");
        testAccount.setBalance(new BigDecimal(100));
        testAccount.setName("Account 222222");
        testAccount.setPin("123456");
        repository.save(testAccount);

        // When
        Iterable<Account> accounts = repository.findAll();
        Account newAccount = null;
        for (Account acc : accounts) {
            if ("222222".equals(acc.getAccountNumber())) {
                newAccount = acc;
                break;
            }
        }

        // Then
        assertThat(accounts).isNotEmpty();
        assertThat(newAccount).isNotNull();
        assertThat(newAccount.getAccountNumber()).isEqualTo("222222");
        assertThat(newAccount.getPin()).isEqualTo("123456");
        assertThat(newAccount.getName()).isEqualTo("Account 222222");
        assertThat(newAccount.getBalance()).isEqualTo(new BigDecimal(100));
    }
}
