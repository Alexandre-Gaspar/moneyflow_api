package api.moneyflow.expense.service;

import api.moneyflow.expense.payload.ExpenseRequest;
import api.moneyflow.expense.payload.ExpenseResponse;
import api.moneyflow.expense.queryfilter.ExpenseQueryFilter;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponse create(ExpenseRequest request, UUID userId);
    ExpenseResponse getById(UUID expenseId);
    List<ExpenseResponse> getAllByUserId(UUID request, ExpenseQueryFilter filter);
    ExpenseResponse update(UUID expenseId, ExpenseRequest request, UUID userId);
    void delete(UUID expenseId, UUID userId);
}
