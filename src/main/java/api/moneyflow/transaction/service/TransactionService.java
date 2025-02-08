package api.moneyflow.transaction.service;

import api.moneyflow.transaction.payload.TransactionRequest;
import api.moneyflow.transaction.payload.TransactionResponse;
import api.moneyflow.transaction.queryfilter.TransactionQueryFilter;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request, UUID userId);
    TransactionResponse getById(UUID transactionId);
    List<TransactionResponse> getAllByUserId(UUID request, TransactionQueryFilter filter);
    TransactionResponse update(UUID transactionId, TransactionRequest request, UUID userId);
    void delete(UUID transactionId, UUID userId);
}
