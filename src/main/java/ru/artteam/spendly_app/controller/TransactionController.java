package ru.artteam.spendly_app.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artteam.spendly_app.dto.req.TransactionRequestDto;
import ru.artteam.spendly_app.dto.req.TransactionUpdateDto;
import ru.artteam.spendly_app.dto.res.TransactionResponseDto;
import ru.artteam.spendly_app.dto.res.UserBalanceDto;
import ru.artteam.spendly_app.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    @JsonIgnore
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @Valid @RequestBody TransactionRequestDto dto
    ) {
        TransactionResponseDto responseDto = transactionService.createTransaction(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponseDto>> getAllByUserId(
            @PathVariable("userId") Long userId
    ){
        List<TransactionResponseDto> trasactions = transactionService.getTransactionByUserId(userId);
        return ResponseEntity.ok(trasactions);
    }

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<UserBalanceDto> getUserBalance(
            @PathVariable("userId") Long userId
    ){
        UserBalanceDto userBalance = transactionService.getUserBalanceDto(userId);
        return ResponseEntity.ok(userBalance);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable("transactionId") Long transactionId,
            @RequestParam Long userId
    ){
        transactionService.deleteTransaction(transactionId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> updateTransaction(
            @PathVariable("id") Long id,
            @RequestParam Long userId,
            @RequestBody TransactionUpdateDto dto
            ){
        return ResponseEntity.ok(transactionService.updateTransaction(id,userId,dto));
    }

}
