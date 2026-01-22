package ru.artteam.spendly_app.dto.req;

import lombok.Data;
import ru.artteam.spendly_app.domain.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionUpdateDto {
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private Long categoryId;
}
