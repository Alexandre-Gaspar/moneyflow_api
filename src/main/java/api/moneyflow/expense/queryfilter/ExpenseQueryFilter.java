package api.moneyflow.expense.queryfilter;

import api.moneyflow.expense.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static api.moneyflow.expense.specification.ExpenseSpecs.*;

public class ExpenseQueryFilter {

   // @Parameter(description = "Filter expense by description")
    private String description;

    //@Parameter(description = "Filter expense by category")
    private String category;

    //@Parameter(description = "Filter expense by minimum amount")
    private BigDecimal minAmount;

    //@Parameter(description = "Filter expense by maximum amount")
    private BigDecimal maxAmount;

    public Specification<Expense> toSpecification() {
        return filterByDescription(description)
                .and(filterByAmountGreaterThenOrEqualTo(minAmount))
                .and(filterByAmountLessThenOrEqualTo(maxAmount))
                .and(filterByCategory(category));
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
}
