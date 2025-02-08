package api.moneyflow.transaction.repository;

import api.moneyflow.transaction.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    @Query(value = "SELECT * FROM tb_expense e WHERE e.user_id = :userId", nativeQuery = true)
    List<Transaction> findAllByUserId(@Param("userId") UUID userId, Specification<Transaction> filter);
}
