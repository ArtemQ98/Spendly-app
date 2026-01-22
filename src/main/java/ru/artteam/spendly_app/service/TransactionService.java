package ru.artteam.spendly_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artteam.spendly_app.domain.CategoryEntity;
import ru.artteam.spendly_app.domain.TransactionEntity;
import ru.artteam.spendly_app.domain.UserEntity;
import ru.artteam.spendly_app.domain.enums.TransactionType;
import ru.artteam.spendly_app.dto.req.TransactionRequestDto;
import ru.artteam.spendly_app.dto.req.TransactionUpdateDto;
import ru.artteam.spendly_app.dto.res.TransactionResponseDto;
import ru.artteam.spendly_app.dto.res.UserBalanceDto;
import ru.artteam.spendly_app.exceptions.AccessDeniedException;
import ru.artteam.spendly_app.exceptions.ResourceNotFoundException;
import ru.artteam.spendly_app.repository.CategoryRepository;
import ru.artteam.spendly_app.repository.TransactionRepository;
import ru.artteam.spendly_app.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto dto){
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setTransactionType(dto.getType());

        TransactionEntity savedTransaction = transactionRepository.save(transaction);
        return mapToResponseDto(savedTransaction);
    }

    public List<TransactionResponseDto> getTransactionByUserId(Long userId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found");
        }
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional
    public UserBalanceDto getUserBalanceDto(Long userId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found");
        }

        BigDecimal income = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.INCOME);
        income = (income != null) ? income : BigDecimal.ZERO;

        BigDecimal expense = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.EXPENSE);
        expense = (expense != null) ? expense : BigDecimal.ZERO;

        BigDecimal balance = income.subtract(expense);

        return new UserBalanceDto(income, expense, balance);
    }

    @Transactional
    public void deleteTransaction(Long transactionId, Long userId){
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction not found"));
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found");
        }

        if (!transaction.getUser().getId().equals(userId)){
            throw new AccessDeniedException("Access denied");
        }
        transactionRepository.delete(transaction);
    }

    @Transactional
    public TransactionResponseDto updateTransaction(Long transactionId, Long userId, TransactionUpdateDto dto){
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new RuntimeException("Transaction not found"));
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        if (!transaction.getUser().getId().equals(userId)){
            throw new RuntimeException("Access denied: transaction");
        }
        if (dto.getCategoryId() != null){
            CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            if (!category.getUser().getId().equals(userId)){
                throw new RuntimeException("Access denied: category");
            }
            transaction.setCategory(category);
        }
        if (dto.getAmount() != null) transaction.setAmount(dto.getAmount());
        if (dto.getDescription() != null) transaction.setDescription(dto.getDescription());
        if (dto.getTransactionType() != null) transaction.setTransactionType(dto.getTransactionType());

        return mapToResponseDto(transaction);
    }


    private TransactionResponseDto mapToResponseDto(TransactionEntity transaction){
        TransactionResponseDto res = new TransactionResponseDto();
        res.setId(transaction.getId());
        res.setAmount(transaction.getAmount());
        res.setDescription(transaction.getDescription());
        res.setUsername(transaction.getUser().getUsername());
        res.setCategoryName(transaction.getCategory().getName());
        res.setType(transaction.getTransactionType());
        res.setCreatedAt(transaction.getCreatedAt().toString());
        return res;
    }

}
