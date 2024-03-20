package com.example.Server.converter;

import com.example.Server.dto.TransactionDTO;
import com.example.Server.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransactionConverter {
    public Set<Long> convertModelsToIDs(Set<Transaction> models) {
        return models.stream()
                .map(Transaction::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> convertDTOsToIDs(Set<TransactionDTO> dtos) {
        return dtos.stream()
                .map(TransactionDTO::getId)
                .collect(Collectors.toSet());
    }

    public Set<TransactionDTO> convertModelsToDtos(Collection<Transaction> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toSet());
    }

    public Set<Transaction> convertDtosToModels(Collection<TransactionDTO> dtos) {
        return dtos.stream()
                .map(this::convertDtoToModel)
                .collect(Collectors.toSet());
    }

    public Transaction convertDtoToModel(TransactionDTO transactionDTO) {
        Transaction story = new Transaction(transactionDTO.getId(), transactionDTO.getTitle(), transactionDTO.getType(), transactionDTO.getCategory(), transactionDTO.getAmount(), transactionDTO.getTimestamp());

        return story;
    }

    public TransactionDTO convertModelToDto(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO(transaction.getId(), transaction.getTitle(), transaction.getType(), transaction.getCategory(), transaction.getAmount(), transaction.getTimestamp());

        return dto;
    }
}
