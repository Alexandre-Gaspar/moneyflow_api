package api.moneyflow.expense.service.impl;

import api.moneyflow.category.converters.CategoryConverter;
import api.moneyflow.category.service.CategoryService;
import api.moneyflow.expense.Expense;
import api.moneyflow.expense.converters.ExpenseConverter;
import api.moneyflow.expense.payload.ExpenseRequest;
import api.moneyflow.expense.payload.ExpenseResponse;
import api.moneyflow.expense.queryfilter.ExpenseQueryFilter;
import api.moneyflow.expense.repository.ExpenseRepository;
import api.moneyflow.expense.service.ExpenseService;
import api.moneyflow.user.controller.converters.UserConverter;
import api.moneyflow.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;
    private final ExpenseConverter expenseConverter;
    private final UserService userService;
    private final CategoryService categoryService;

    public ExpenseServiceImpl(ExpenseRepository repository, ExpenseConverter expenseConverter, UserService userService, CategoryService categoryService) {
        this.repository = repository;
        this.expenseConverter = expenseConverter;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public ExpenseResponse create(ExpenseRequest request, UUID userId) {
        var expense = expenseConverter.toEntity(request);

        expense.setUser(UserConverter.fromResponseToEntity(userService.getById(userId)));
        expense.setCategory(CategoryConverter.fromResponseToEntity(categoryService.getById(request.categoryId())));

        expense = repository.saveAndFlush(expense);
        return expenseConverter.toDTO(expense);
    }

    @Override
    public ExpenseResponse getById(UUID expenseId) {
        var expense = repository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return expenseConverter.toDTO(expense);
    }

    @Override
    public List<ExpenseResponse> getAllByUserId(UUID userId, ExpenseQueryFilter filter) {
        Specification<Expense> filterByUserId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);

        var combinedSpecs = filterByUserId.and(filter.toSpecification());

        return repository.findAll(combinedSpecs).stream()
                .map(expenseConverter::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ExpenseResponse update(UUID expenseId, ExpenseRequest request, UUID userId) {
        return repository.findById(expenseId)
                .map(expense -> {
                    var expenseRaw = expenseConverter.updateFromExpense(expense, request);

                    expenseRaw.setUser(UserConverter.fromResponseToEntity(userService.getById(userId)));
                    expenseRaw.setCategory(CategoryConverter.fromResponseToEntity(categoryService.getById(request.categoryId())));

                    var updatedExpense = repository.save(expenseRaw);
                    return expenseConverter.toDTO(updatedExpense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    @Override
    @Transactional
    public void delete(UUID expenseId, UUID userId) {
        repository.deleteById(expenseId);
    }
}