package api.moneyflow.expense.converters;

import api.moneyflow.expense.Expense;
import api.moneyflow.expense.payload.ExpenseRequest;
import api.moneyflow.expense.payload.ExpenseResponse;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter {

    public ExpenseResponse toDTO(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getUser().getId(),
                expense.getCategory().getId(),
                expense.getDate()
        );
    }

    public Expense toEntity(ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        return expense;
    }

    public Expense updateFromExpense(Expense expense, ExpenseRequest request) {
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        return expense;
    }
}