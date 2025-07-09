package com.workout.workoutcom.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCategoryDto {
    private int categoryId;
    private String categoryName;
    private int parentCategoryId;
    private int depth;
}
