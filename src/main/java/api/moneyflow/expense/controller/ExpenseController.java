package api.moneyflow.expense.controller;

import api.moneyflow.commom.config.SpringDocConfig;
import api.moneyflow.expense.payload.ExpenseRequest;
import api.moneyflow.expense.payload.ExpenseResponse;
import api.moneyflow.expense.queryfilter.ExpenseQueryFilter;
import api.moneyflow.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@Tag(name = "expense", description = "Endpoints for expenses management")
@SecurityRequirement(name = SpringDocConfig.AUTHORIZATION)
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create a new expense", description = "Creates a new expense for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid ExpenseRequest request,
                                                         JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(request, userId));
    }

    @Operation(summary = "Get an expense by ID", description = "Retrieves an expense by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense found"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable UUID expenseId) {
        return ResponseEntity.ok(expenseService.getById(expenseId));
    }

    @Operation(summary = "Get all expenses", description = "Retrieves all expenses for the authenticated user, with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No expenses found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpensesByUser(@ParameterObject ExpenseQueryFilter filter, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        return ResponseEntity.ok(expenseService.getAllByUserId(userId, filter));
    }

    @Operation(summary = "Update an expense", description = "Updates an existing expense identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable UUID expenseId,
                                                         @RequestBody @Valid ExpenseRequest request,
                                                         JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        ExpenseResponse updatedExpense = expenseService.update(expenseId, request, userId);
        return ResponseEntity.ok(updatedExpense);
    }

    @Operation(summary = "Delete an expense", description = "Deletes an expense by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId, JwtAuthenticationToken token) {
        expenseService.delete(expenseId, UUID.fromString(token.getName()));
        return ResponseEntity.noContent().build();
    }
}
