package com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponseItemsDto {

    @JsonProperty("FOOD_NM_KR")
    private String foodName;

    @JsonProperty("SERVING_SIZE")
    private String servingSize;

    @JsonProperty("AMT_NUM1")
    private String energy;

    @JsonProperty("AMT_NUM3")
    private String protein;

    @JsonProperty("AMT_NUM4")
    private String fat;

    @JsonProperty("AMT_NUM6")
    private String carbohydrate;

    @JsonProperty("AMT_NUM7")
    private String sugar;

    @JsonProperty("AMT_NUM12")
    private String potassium;

    @JsonProperty("AMT_NUM13")
    private String sodium;

    @JsonProperty("AMT_NUM23")
    private String cholesterol;

    @JsonProperty("AMT_NUM24")
    private String saturatedFat;

    @JsonProperty("AMT_NUM25")
    private String transFat;
}

