package com.cdc.atm.web.repository;

import com.cdc.atm.web.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select a from Transaction a where a.accountNumber=:accountNumber and a.trxDate>=:trxDateFrom and a.trxDate <=:trxDateTo")
    List<Transaction> findTransactionByDate(@Param("accountNumber") String accountNumber,
            @Param("trxDateFrom") Date trxDateFrom, @Param("trxDateTo") Date trxDateTo);

    @Query(nativeQuery = true,
            value = "select * from Transaction a where a.account_number=:accountNumber order by a.trx_date desc limit :maxResult")
    List<Transaction> findLastTransaction(@Param("accountNumber") String accountNumber,
            @Param("maxResult") int maxResult);

    List<Transaction> findTop10ByAccountNumberOrderByTrxDateDesc(String accountNumber);
}
