package api.moneyflow.transaction.converters;

import api.moneyflow.transaction.Transaction;
import api.moneyflow.transaction.payload.TransactionRequest;
import api.moneyflow.transaction.payload.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    public TransactionResponse toDTO(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getUser().getId(),
                transaction.getCategory().getId(),
                transaction.getType().toString(),
                transaction.getDate()
        );
    }

    public Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setType(request.type());
        return transaction;
    }

    public Transaction updateFromExpense(Transaction transaction, TransactionRequest request) {
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setType(request.type());
        return transaction;
    }
}