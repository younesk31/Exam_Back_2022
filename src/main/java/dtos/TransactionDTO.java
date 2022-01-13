package dtos;

import entities.Transaction;
import entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDTO {

    private String status;
    private int transactionAmount;
    private Date transactionDate;

    public static List<TransactionDTO> getDtos(List<Transaction> t){
        List<TransactionDTO> transactionDTOS = new ArrayList();
        t.forEach(dtotoentity -> transactionDTOS.add(new TransactionDTO(dtotoentity)));
        return transactionDTOS;
    }

    public TransactionDTO(Transaction transaction) {
        this.status = transaction.getStatus();
        this.transactionAmount = transaction.getTransactionAmount();
        this.transactionDate = transaction.getTransactionDate();

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
