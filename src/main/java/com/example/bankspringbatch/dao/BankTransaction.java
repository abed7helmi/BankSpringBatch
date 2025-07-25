package com.example.bankspringbatch.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankTransaction {
    @Id
    private Long id;
    private long accountID;
    private Date transactionDate ;
    @Transient
    private String strTransactionDate;
    private String transactionType;
    private double amount;

}
