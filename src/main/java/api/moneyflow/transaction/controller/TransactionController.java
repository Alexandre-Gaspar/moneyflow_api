package api.moneyflow.transaction.controller;

import api.moneyflow.commom.config.SpringDocConfig;
import api.moneyflow.transaction.payload.TransactionRequest;
import api.moneyflow.transaction.payload.TransactionResponse;
import api.moneyflow.transaction.queryfilter.TransactionQueryFilter;
import api.moneyflow.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@Tag(name = "transaction", description = "Endpoints for transactions management")
@SecurityRequirement(name = SpringDocConfig.AUTHORIZATION)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create a new transaction", description = "Creates a new transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(request));
    }

    @Operation(summary = "Get a transaction by ID", description = "Retrieves a transaction by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID transactionId) {
        return ResponseEntity.ok(transactionService.getById(transactionId));
    }

    @Operation(summary = "Get all transactions", description = "Retrieves all transactions with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No transactions found")
    })
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactionByUser(@ParameterObject TransactionQueryFilter filter) {
        return ResponseEntity.ok(transactionService.getAllByUserId(filter));
    }

    @Operation(summary = "Update a transaction", description = "Updates an existing transaction identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID transactionId,
                                                                 @RequestBody @Valid TransactionRequest request) {
        var updatedTransaction = transactionService.update(transactionId, request);
        return ResponseEntity.ok(updatedTransaction);
    }

    @Operation(summary = "Delete a transaction", description = "Deletes a transaction by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        transactionService.delete(transactionId);
        return ResponseEntity.noContent().build();
    }
}



//package api.moneyflow.transaction.controller;
//
//import api.moneyflow.commom.config.SpringDocConfig;
//import api.moneyflow.transaction.payload.TransactionRequest;
//import api.moneyflow.transaction.payload.TransactionResponse;
//import api.moneyflow.transaction.queryfilter.TransactionQueryFilter;
//import api.moneyflow.transaction.service.TransactionService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springdoc.core.annotations.ParameterObject;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/transactions")
//@Tag(name = "transaction", description = "Endpoints for transactions management")
//@SecurityRequirement(name = SpringDocConfig.AUTHORIZATION)
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    @Operation(summary = "Create a new transaction", description = "Creates a new transaction for the authenticated user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid request data"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//    @PostMapping
//    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request,
//                                                             JwtAuthenticationToken token) {
//        var userId = UUID.fromString(token.getName());
//        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(request, userId));
//    }
//
//    @Operation(summary = "Get an transaction by ID", description = "Retrieves an transaction by its unique identifier.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Transaction found"),
//            @ApiResponse(responseCode = "404", description = "Transaction not found"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//    @GetMapping("/{transactionId}")
//    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID transactionId) {
//        return ResponseEntity.ok(transactionService.getById(transactionId));
//    }
//
//    @Operation(summary = "Get all transactions", description = "Retrieves all transactions for the authenticated user, with optional filters.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
//            @ApiResponse(responseCode = "404", description = "No transactions found"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//    @GetMapping
//    public ResponseEntity<List<TransactionResponse>> getAllTransactionByUser(@ParameterObject TransactionQueryFilter filter, JwtAuthenticationToken token) {
//        var userId = UUID.fromString(token.getName());
//        return ResponseEntity.ok(transactionService.getAllByUserId(userId, filter));
//    }
//
//    @Operation(summary = "Update an transaction", description = "Updates an existing transaction identified by its ID.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
//            @ApiResponse(responseCode = "404", description = "Transaction not found"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//    @PutMapping("/{transactionId}")
//    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID transactionId,
//                                                             @RequestBody @Valid TransactionRequest request,
//                                                             JwtAuthenticationToken token) {
//        var userId = UUID.fromString(token.getName());
//        var updatedTransaction = transactionService.update(transactionId, request, userId);
//        return ResponseEntity.ok(updatedTransaction);
//    }
//
//    @Operation(summary = "Delete a transaction", description = "Deletes a transaction by its ID.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
//            @ApiResponse(responseCode = "404", description = "Transaction not found"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
//    @DeleteMapping("/{transactionId}")
//    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId, JwtAuthenticationToken token) {
//        transactionService.delete(transactionId, UUID.fromString(token.getName()));
//        return ResponseEntity.noContent().build();
//    }
//}
