package api.moneyflow.expense.controller;

import api.moneyflow.expense.payload.ExpenseRequest;
import api.moneyflow.expense.payload.ExpenseResponse;
import api.moneyflow.expense.queryfilter.ExpenseQueryFilter;
import api.moneyflow.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid ExpenseRequest request,
                                                         JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(request, userId));
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable UUID expenseId) {
        return ResponseEntity.ok(expenseService.getById(expenseId));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpensesByUser(ExpenseQueryFilter filter, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        return ResponseEntity.ok(expenseService.getAllByUserId(userId, filter));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable UUID expenseId,
                                                         @RequestBody @Valid ExpenseRequest request,
                                                         JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        ExpenseResponse updatedExpense = expenseService.update(expenseId, request, userId);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId, JwtAuthenticationToken token) {
        expenseService.delete(expenseId, UUID.fromString(token.getName()));
        return ResponseEntity.noContent().build();
    }
}
