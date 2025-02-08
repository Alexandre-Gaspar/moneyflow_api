package api.moneyflow.transaction.service.impl;

import api.moneyflow.category.converters.CategoryConverter;
import api.moneyflow.category.service.CategoryService;
import api.moneyflow.transaction.Transaction;
import api.moneyflow.transaction.converters.TransactionConverter;
import api.moneyflow.transaction.payload.TransactionRequest;
import api.moneyflow.transaction.payload.TransactionResponse;
import api.moneyflow.transaction.queryfilter.TransactionQueryFilter;
import api.moneyflow.transaction.repository.TransactionRepository;
import api.moneyflow.transaction.service.TransactionService;
import api.moneyflow.user.controller.converters.UserConverter;
import api.moneyflow.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final TransactionConverter transactionConverter;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionServiceImpl(TransactionRepository repository, TransactionConverter transactionConverter, UserService userService, CategoryService categoryService) {
        this.repository = repository;
        this.transactionConverter = transactionConverter;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public TransactionResponse create(TransactionRequest request, UUID userId) {
        var transaction = transactionConverter.toEntity(request);

        transaction.setUser(UserConverter.fromResponseToEntity(userService.getById(userId)));
        transaction.setCategory(CategoryConverter.fromResponseToEntity(categoryService.getById(request.categoryId())));

        transaction = repository.saveAndFlush(transaction);
        return transactionConverter.toDTO(transaction);
    }

    @Override
    public TransactionResponse getById(UUID transactionId) {
        var transaction = repository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return transactionConverter.toDTO(transaction);
    }

    @Override
    public List<TransactionResponse> getAllByUserId(UUID userId, TransactionQueryFilter filter) {
        Specification<Transaction> filterByUserId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);

        var combinedSpecs = filterByUserId.and(filter.toSpecification());

        return repository.findAll(combinedSpecs).stream()
                .map(transactionConverter::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public TransactionResponse update(UUID transactionId, TransactionRequest request, UUID userId) {
        return repository.findById(transactionId)
                .map(transaction -> {
                    var transactionRaw = transactionConverter.updateFromExpense(transaction, request);

                    transactionRaw.setUser(UserConverter.fromResponseToEntity(userService.getById(userId)));
                    transactionRaw.setCategory(CategoryConverter.fromResponseToEntity(categoryService.getById(request.categoryId())));

                    var updatedExpense = repository.save(transactionRaw);
                    return transactionConverter.toDTO(updatedExpense);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    @Transactional
    public void delete(UUID transactionId, UUID userId) {
        repository.deleteById(transactionId);
    }
}