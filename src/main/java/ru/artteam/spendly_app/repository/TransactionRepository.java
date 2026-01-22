package ru.artteam.spendly_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.artteam.spendly_app.domain.TransactionEntity;
import ru.artteam.spendly_app.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByUserId(Long userId);

    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.user.id = :userId AND t.transactionType = :type")
    BigDecimal sumAmountByUserIdAndType(Long userId, TransactionType type);
}
