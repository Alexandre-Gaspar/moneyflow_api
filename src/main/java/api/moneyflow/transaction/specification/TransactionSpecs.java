package api.moneyflow.transaction.specification;

import api.moneyflow.transaction.Transaction;
import api.moneyflow.transaction.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

public class TransactionSpecs {

    private TransactionSpecs() {}

    public static Specification<Transaction> filterByDescription(String description) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(description)
                        ? null
                        : criteriaBuilder.like(root.get("description"), "%" + description + "%"));
    }

    public static Specification<Transaction> filterByAmountGreaterThenOrEqualTo(BigDecimal minAmount) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(minAmount)
                        ? null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
    }

    public static Specification<Transaction> filterByAmountLessThenOrEqualTo(BigDecimal maxAmount) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(maxAmount)
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
    }

    public static Specification<Transaction> filterByCategory(String category) {
        return ((root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(category)
                        ? null
                        : criteriaBuilder.like(root.get("category").get("name"), "%" + category + "%"));
    }

    public static Specification<Transaction> filterByType(TransactionType type) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(type)
                        ? null
                        : criteriaBuilder.equal(root.get("type"), type);
    }

}
