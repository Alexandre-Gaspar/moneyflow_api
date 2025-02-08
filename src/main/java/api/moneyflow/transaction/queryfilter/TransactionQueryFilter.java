package api.moneyflow.transaction.queryfilter;

import api.moneyflow.transaction.Transaction;
import api.moneyflow.transaction.enums.TransactionType;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static api.moneyflow.transaction.specification.TransactionSpecs.*;

public class TransactionQueryFilter {

    @Parameter(description = "Filter transaction by description")
    private String description;

    @Parameter(description = "Filter transaction by category")
    private String category;

    @Parameter(description = "Filter transaction by minimum amount")
    private BigDecimal minAmount;

    @Parameter(description = "Filter transaction by maximum amount")
    private BigDecimal maxAmount;

    @Parameter(description = "Filter transaction by transaction type")
    private TransactionType type;

    public Specification<Transaction> toSpecification() {
        return filterByDescription(description)
                .and(filterByAmountGreaterThenOrEqualTo(minAmount))
                .and(filterByAmountLessThenOrEqualTo(maxAmount))
                .and(filterByCategory(category))
                .and(filterByType(type));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
