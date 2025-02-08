package api.moneyflow.category;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, unique = true)
    private String name;

    @Column(name = "spending_limit", precision = 10, scale = 2)
    private BigDecimal spendingLimit;

    public Category() {}

    public Category(Long id, String name, BigDecimal spendingLimit) {
        this.id = id;
        this.name = name;
        this.spendingLimit = spendingLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit;
    }
}
