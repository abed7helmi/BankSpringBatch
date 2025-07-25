package com.example.bankspringbatch;

import com.example.bankspringbatch.dao.BankTransaction;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

//@Component
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor <BankTransaction, BankTransaction> {

    @Getter private double totalDebit;
    @Getter double totalCredit;

    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        // on va prendre le format date en string et le changer vers Date
        // c un exemple simple , mais normalement , on prent les données de l'objet A , on les traite et on les stoche dans un Objet B

        //bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));
        if(bankTransaction.getTransactionType().equals("D"))
            totalDebit += bankTransaction.getAmount();
        else if (bankTransaction.getTransactionType().equals("C")) {
            totalCredit += bankTransaction.getAmount();
        }

        return bankTransaction;
}
}
