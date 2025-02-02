package api.moneyflow.expense.repository;

import api.moneyflow.expense.Expense;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID>, JpaSpecificationExecutor<Expense> {
    @Query(value = "SELECT * FROM tb_expense e WHERE e.user_id = :userId", nativeQuery = true)
    List<Expense> findAllByUserId(@Param("userId") UUID userId, Specification<Expense> filter);
}
