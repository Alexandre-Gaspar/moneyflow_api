package api.moneyflow.expense.specification;

import api.moneyflow.expense.Expense;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseSpecs {

    public static Specification<Expense> filterByDescription(String description) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(description)
                        ? null
                        : criteriaBuilder.like(root.get("description"), "%" + description + "%"));
    }

    public static Specification<Expense> filterByAmountGreaterThenOrEqualTo(BigDecimal minAmount) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(minAmount)
                        ? null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
    }

    public static Specification<Expense> filterByAmountLessThenOrEqualTo(BigDecimal maxAmount) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(maxAmount)
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
    }

    public static Specification<Expense> filterByCategory(String category) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(category)
                        ? null
                        : criteriaBuilder.like(root.get("category").get("name"), "%" + category + "%"));
    }
}
