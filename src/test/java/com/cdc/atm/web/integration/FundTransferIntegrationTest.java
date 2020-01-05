package com.cdc.atm.web.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cdc.atm.web.model.FundTransfer;
import com.cdc.atm.web.model.entity.Transaction;
import com.cdc.atm.web.service.TransactionService;
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

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "test" })
public class FundTransferIntegrationTest {

    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    private static final String ACCOUNT_202020  = "202020";
    private static final String ACCOUNT_222222  = "222222";
    private static final String ACCOUNT_212121  = "212121";
    private static final String ACCOUNT_000000  = "000000";
    private static final String ACCOUNT_232323  = "232323";
    private static final String ACCOUNT_242424  = "242424";
    private static final String ACCOUNT_252525  = "252525";
    private static final String ACCOUNT_262626  = "262626";
    private static final String ACCOUNT_272727  = "272727";
    private static final String ACCOUNT_282828  = "282828";
    private static final String ACCOUNT_292929  = "292929";
    private static final String ACCOUNT_303030  = "303030";

    @Autowired
    private MockMvc             mockMvc;

    @Autowired
    private AccountComponent    accountComponent;

    @Autowired
    private AccountRepository   accountRepository;

    @Autowired
    private TransactionService  transactionService;

    /**
     * When creating a transfer transaction from account1 to account2, it's expected
     * to have account1's balance reduced by the amount of the transaction and have
     * account2's balance increased by the amount of the transaction
     * 
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenFundTransferToValidAccount_thenFundTransferred() throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_222222);
        BigDecimal origAccount1Balance = account1.getBalance();
        Account account2 = accountRepository.findByAccountNumber(ACCOUNT_202020);
        BigDecimal origAccount2Balance = account2.getBalance();
        String transferAmount = "10";

        // Given
        accountComponent.setAccountNumber(ACCOUNT_222222);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("accountNumber", ACCOUNT_202020)
                .param("transferAmount", transferAmount).param("referenceNumber", "222222"))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));

        account1 = accountRepository.findByAccountNumber(ACCOUNT_222222);
        account2 = accountRepository.findByAccountNumber(ACCOUNT_202020);

        // Then
        assertThat(account1.getBalance()).isEqualTo(origAccount1Balance.subtract(new BigDecimal(transferAmount)));
        assertThat(account2.getBalance()).isEqualTo(origAccount2Balance.add(new BigDecimal(transferAmount)));
    }

    /**
     * When creating a transfer transaction from account1 to account2, and account1
     * does not have enough funds, operation should throw an error
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithNotEnoughFunds_whenFundTransferToValidAccount_thenReceiveInsufficientBalanceError()
            throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_202020);
        BigDecimal origAccount1Balance = account1.getBalance();
        Account account2 = accountRepository.findByAccountNumber(ACCOUNT_222222);
        BigDecimal origAccount2Balance = account2.getBalance();
        String transferAmount = "100";

        // Given
        accountComponent.setAccountNumber(ACCOUNT_202020);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_2.toString())
                        .param("option", FundTransfer.Option.CONFIRM_TRX.toString())
                        .param("accountNumber", ACCOUNT_222222).param("transferAmount", transferAmount))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransfer2"))
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", Arrays.asList("Insufficient balance $100")));

        account1 = accountRepository.findByAccountNumber(ACCOUNT_202020);
        account2 = accountRepository.findByAccountNumber(ACCOUNT_222222);

        // Then
        assertThat(account1.getBalance()).isEqualTo(origAccount1Balance);
        assertThat(account2.getBalance()).isEqualTo(origAccount2Balance);
    }

    /**
     * When creating a transfer transaction from account1 to account2, and account2
     * does not exist, then operation should fail
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenFundTransferToInvalidAccount_thenReceiveInvalidAccountError()
            throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_212121);
        BigDecimal origAccount1Balance = account1.getBalance();

        // Given
        accountComponent.setAccountNumber(ACCOUNT_212121);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_1.toString())
                        .param("option", FundTransfer.Option.CONFIRM_TRX.toString())
                        .param("accountNumber", ACCOUNT_000000))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransfer1"))
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", Arrays.asList("Invalid account")));

        account1 = accountRepository.findByAccountNumber(ACCOUNT_212121);

        // Then
        assertThat(account1.getBalance()).isEqualTo(origAccount1Balance);
    }

    /**
     * When creating a successful transaction, it's expected to have this
     * transaction when getting list of last 10 transactions
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenFundTransfer_thenTrxIsInLastTenTransactions() throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_232323);
        BigDecimal origAccount1Balance = account1.getBalance();
        Account account2 = accountRepository.findByAccountNumber(ACCOUNT_242424);
        BigDecimal origAccount2Balance = account2.getBalance();
        String transferAmount = "15";

        // Given
        accountComponent.setAccountNumber(ACCOUNT_232323);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("transferAmount", transferAmount)
                .param("accountNumber", ACCOUNT_242424).param("referenceNumber", "232323"))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));

        List<Transaction> last10TrxList = transactionService.getLastTenTransaction(ACCOUNT_232323);
        Transaction trx = null;
        for (Transaction t : last10TrxList) {
            if (t.getAccountNumber().equals(ACCOUNT_232323) && t.getDestinationAccountNumber().equals(ACCOUNT_242424)
                    && Transaction.TrxType.FUND_TRANSFER.toString().equals(t.getTrxType())
                    && t.getAmount().compareTo(new BigDecimal(transferAmount)) == 0) {
                trx = t;
                break;
            }
        }
        account1 = accountRepository.findByAccountNumber(ACCOUNT_232323);
        account2 = accountRepository.findByAccountNumber(ACCOUNT_242424);

        // Then
        assertThat(account1.getBalance()).isEqualTo(origAccount1Balance.subtract(new BigDecimal(transferAmount)));
        assertThat(account2.getBalance()).isEqualTo(origAccount2Balance.add(new BigDecimal(transferAmount)));
        assertThat(last10TrxList).isNotEmpty();
        assertThat(trx).isNotNull();
    }

    /**
     * When creating a bunch of successful transactions, it's expected to have this
     * list of transactions when getting list of last 10 transactions
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenMultipleFundTransfer_thenTrxAreInLastTenTransactions()
            throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_252525);
        BigDecimal origAccount1Balance = account1.getBalance();
        Account account2 = accountRepository.findByAccountNumber(ACCOUNT_262626);
        BigDecimal origAccount2Balance = account2.getBalance();
        Account account3 = accountRepository.findByAccountNumber(ACCOUNT_272727);
        BigDecimal origAccount3Balance = account3.getBalance();
        Account account4 = accountRepository.findByAccountNumber(ACCOUNT_282828);
        BigDecimal origAccount4Balance = account4.getBalance();
        String transferAmount = "10";

        // Given
        accountComponent.setAccountNumber(ACCOUNT_252525);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("transferAmount", transferAmount)
                .param("accountNumber", ACCOUNT_262626).param("referenceNumber", "111111"))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("transferAmount", transferAmount)
                .param("accountNumber", ACCOUNT_272727).param("referenceNumber", "222222"))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("transferAmount", transferAmount)
                .param("accountNumber", ACCOUNT_282828).param("referenceNumber", "333333"))

                // Then
                .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));

        List<Transaction> last10TrxList = transactionService.getLastTenTransaction(ACCOUNT_252525);
        List<Transaction> trxList = new ArrayList<>();
        for (Transaction t : last10TrxList) {
            if (t.getAccountNumber().equals(ACCOUNT_252525)
                    && (t.getDestinationAccountNumber().equals(ACCOUNT_262626)
                            || t.getDestinationAccountNumber().equals(ACCOUNT_272727)
                            || t.getDestinationAccountNumber().equals(ACCOUNT_282828))
                    && Transaction.TrxType.FUND_TRANSFER.toString().equals(t.getTrxType())
                    && t.getAmount().compareTo(new BigDecimal(transferAmount)) == 0) {
                trxList.add(t);
            }
        }
        account1 = accountRepository.findByAccountNumber(ACCOUNT_252525);
        account2 = accountRepository.findByAccountNumber(ACCOUNT_262626);
        account3 = accountRepository.findByAccountNumber(ACCOUNT_272727);
        account4 = accountRepository.findByAccountNumber(ACCOUNT_282828);

        // Then
        assertThat(account1.getBalance())
                .isEqualTo(origAccount1Balance.subtract(new BigDecimal(transferAmount).multiply(new BigDecimal(3))));
        assertThat(account2.getBalance()).isEqualTo(origAccount2Balance.add(new BigDecimal(transferAmount)));
        assertThat(account3.getBalance()).isEqualTo(origAccount3Balance.add(new BigDecimal(transferAmount)));
        assertThat(account4.getBalance()).isEqualTo(origAccount4Balance.add(new BigDecimal(transferAmount)));
        assertThat(last10TrxList).isNotEmpty();
        assertThat(last10TrxList).containsAll(trxList);
    }

    /**
     * When creating more than 10 transactions, it's expected to have the 10 most
     * recent transactions when getting list of last 10 transactions
     *
     * @throws Exception
     */
    @Test
    public void givenAccountWithEnoughFunds_whenWithdraw_thenAccountBalanceReduced() throws Exception {
        Account account1 = accountRepository.findByAccountNumber(ACCOUNT_292929);
        BigDecimal origAccount1Balance = account1.getBalance();
        Account account2 = accountRepository.findByAccountNumber(ACCOUNT_303030);
        BigDecimal origAccount2Balance = account2.getBalance();
        String transferAmount = "10";

        // Given
        accountComponent.setAccountNumber(ACCOUNT_292929);
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        // When
        BigDecimal totalAmount = new BigDecimal(0);
        for (int i = 0; i < 15; i++) {
            totalAmount = totalAmount.add(new BigDecimal(transferAmount));
            this.mockMvc.perform(MockMvcRequestBuilders.post("/fundTransfer").sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken())
                    .param("page", FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString())
                    .param("option", FundTransfer.Option.CONFIRM_TRX.toString()).param("transferAmount", transferAmount)
                    .param("accountNumber", ACCOUNT_303030).param("referenceNumber", "292929"))

                    // Then
                    .andExpect(status().isOk()).andExpect(view().name("fundTransferSummary"));
        }

        List<Transaction> last10TrxList = transactionService.getLastTenTransaction(ACCOUNT_292929);
        List<Transaction> trxList = new ArrayList<>();
        for (Transaction t : last10TrxList) {
            if (t.getAccountNumber().equals(ACCOUNT_292929) && t.getDestinationAccountNumber().equals(ACCOUNT_303030)
                    && Transaction.TrxType.FUND_TRANSFER.toString().equals(t.getTrxType())
                    && t.getAmount().compareTo(new BigDecimal(transferAmount)) == 0
                    && t.getReferenceNumber().equals("292929")) {
                trxList.add(t);
            }
        }

        account1 = accountRepository.findByAccountNumber(ACCOUNT_292929);
        account2 = accountRepository.findByAccountNumber(ACCOUNT_303030);

        // Then
        assertThat(account1.getBalance()).isEqualTo(origAccount1Balance.subtract(totalAmount));
        assertThat(account2.getBalance()).isEqualTo(origAccount2Balance.add(totalAmount));
        assertThat(last10TrxList).isNotEmpty();
        assertThat(last10TrxList).containsAll(trxList);
    }

}
