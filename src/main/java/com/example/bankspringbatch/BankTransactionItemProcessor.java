package com.example.bankspringbatch;

import com.example.bankspringbatch.dao.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BankTransactionItemProcessor implements ItemProcessor <BankTransaction, BankTransaction> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");


    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        // on va prendre le format date en string et le changer vers Date
        // c un exemple simple , mais normalement , on prent les données de l'objet A , on les traite et on les stoche dans un Objet B

        bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));

        return bankTransaction;
}
}
