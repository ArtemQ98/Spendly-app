package ru.artteam.spendly_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artteam.spendly_app.domain.CategoryEntity;
import ru.artteam.spendly_app.domain.UserEntity;
import ru.artteam.spendly_app.dto.res.CategoryResponseDto;
import ru.artteam.spendly_app.repository.CategoryRepository;
import ru.artteam.spendly_app.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategoryByUserId(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        return categoryRepository.findAllByUserId(userId).stream().map(this::mapToResponseDto).toList();
    }

    @Transactional
    public CategoryResponseDto createCategory(String name, Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        if (categoryRepository.existsByNameAndUserId(name, userId)){
            throw new RuntimeException("Category already exists for this user");
        }
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setUser(user);

        CategoryEntity saved = categoryRepository.save(category);
        return mapToResponseDto(saved);

    }

    private CategoryResponseDto mapToResponseDto(CategoryEntity category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
