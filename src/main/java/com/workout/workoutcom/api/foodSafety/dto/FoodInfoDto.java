package com.workout.workoutcom.api.foodSafety.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodInfoDto {
    private int foodRecordId;
    private String foodName;
    private String recorderAccount;
    private String foodKcal;
    private String foodCarbo;
    private String foodProtein;
    private String foodFat;
    private String foodRecordedDate;
    private String dietCategory;
    private String foodWeight;
}
