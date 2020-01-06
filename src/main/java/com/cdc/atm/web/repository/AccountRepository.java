package com.cdc.atm.web.repository;

import com.cdc.atm.web.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select a from Account a where a.accountNumber=:accountNumber and a.pin=:pin")
    Account validateAccount(@Param("accountNumber") String accountNumber, @Param("pin") String pin);

    @Query("select a from Account a where a.accountNumber=:accountNumber")
    Account findByAccountNumber(@Param("accountNumber") String accountNumber);
}
