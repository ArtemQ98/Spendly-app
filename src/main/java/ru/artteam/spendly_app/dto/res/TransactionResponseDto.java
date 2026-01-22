package ru.artteam.spendly_app.dto.res;

import lombok.Data;
import ru.artteam.spendly_app.domain.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionResponseDto{
    private Long id;
    private BigDecimal amount;
    private String description;
    private String categoryName;
    private String username;
    private String createdAt;
    private TransactionType type;
}
