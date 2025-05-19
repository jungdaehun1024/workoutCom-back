package com.workout.workoutcom.api.foodSafety;

import com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.FoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Component
public class FoodSafetyApiClient {

    private final RestTemplate restTemplate;

    @Autowired
    public FoodSafetyApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${foodsafety.service-key}")
    private String serviceKey;

    public FoodResponseDto getFoodInfo(String foodName){
        String baseUrl = "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02/getFoodNtrCpntDbInq02";
        String encodeServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
        String encodeFoodName = URLEncoder.encode(foodName, StandardCharsets.UTF_8);
        URI uriWithParams = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey",encodeServiceKey)
                .queryParam("type","json")
                .queryParam("FOOD_NM_KR",encodeFoodName)
                .build(true)
                .toUri();

        FoodResponseDto response = restTemplate.getForObject(uriWithParams, FoodResponseDto.class);



        return  response;
    }

}
