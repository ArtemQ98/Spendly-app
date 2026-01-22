package ru.artteam.spendly_app.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.artteam.spendly_app.domain.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "User ID is required")
    private Long userId;
}
