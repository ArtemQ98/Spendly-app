package ru.artteam.spendly_app.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    Long id;
    String name;
}
