package ru.artteam.spendly_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artteam.spendly_app.dto.req.CategoryRequestDto;
import ru.artteam.spendly_app.dto.res.CategoryResponseDto;
import ru.artteam.spendly_app.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategoryByUserId(
            @PathVariable("userId") Long userId
    ){
        List<CategoryResponseDto> categories = categoryService.getAllCategoryByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto dto
    ){
        CategoryResponseDto responseDto = categoryService.createCategory(dto.getName(), dto.getUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
