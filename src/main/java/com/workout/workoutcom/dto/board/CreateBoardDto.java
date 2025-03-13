package com.workout.workoutcom.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "게시글 생성 정보를 담는 DTO 클래스")
public class CreateBoardDto {

    @Schema(description = "게시글 제목", example = "SpringBoot 게시글 생성 예제")
    private String title;

    @Schema(description = "게시글 내용", example = "이것은 게시글 내용입니다.")
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // 요청에서 제외, 응답에서는 포함
    @Schema(description = "게시글 ID(생성시에는 필요 없음)",example = "1", nullable = true)
    private int boardId;
}
