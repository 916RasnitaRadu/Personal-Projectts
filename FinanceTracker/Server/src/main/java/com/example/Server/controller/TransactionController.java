package com.example.Server.controller;

import com.example.Server.model.Transaction;
import com.example.Server.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        System.out.println("GET ALL TRANSACTIONS");
        try {
            return transactionRepository.findAll();
        }
        catch (Exception e) {
            System.out.println("GET: Error at retrieving transactions");
        }
        return null;
    }

    @PostMapping("/transactions")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        System.out.println("POST TRANSACTION \n" + transaction);
        try {
            return transactionRepository.save(Transaction.builder()
                    .category(transaction.getCategory())
                    .amount(transaction.getAmount())
                    .title(transaction.getTitle())
                    .timestamp(transaction.getTimestamp())
                    .type(transaction.getType())
                    .build());
        }
        catch (Exception e) {
            System.out.println("POST: Error at posting transaction");
        }
        return null;

    }

    @DeleteMapping("/transactions/{id}")
    public Transaction deleteTransaction(@PathVariable long id) {
        System.out.println("DELETE TRANSACTION");
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);

        if (transactionOptional.isEmpty()) {
            System.out.println("DELETE: Error at deleting, transaction not found");
            return null;
        }

        transactionRepository.deleteById(id);
        return transactionOptional.get();
    }

    @PutMapping("/transactions/{id}")
    public Transaction updateTransaction(@PathVariable long id, @RequestBody Transaction transaction) {
        System.out.println("PUT TRANSACTION");
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);

        if (transactionOptional.isEmpty()) {
            System.out.println("UPDATE: Error at updating, transaction not found");
            return null;
        }

        return transactionRepository.save(transaction);
    }
}
