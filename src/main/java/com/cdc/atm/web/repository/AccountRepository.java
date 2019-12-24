package com.cdc.atm.web.repository;

import com.cdc.atm.web.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("select a from Account a where a.accountNumber=:accountNumber and a.pin=:pin")
    Account validateAccount(@Param("accountNumber") String accountNumber, @Param("pin") String pin);
}