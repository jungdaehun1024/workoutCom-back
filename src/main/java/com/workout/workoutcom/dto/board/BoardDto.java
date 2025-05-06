package com.workout.workoutcom.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Schema(description = "게시글 정보를 담는 DTO 클래스")
public class BoardDto {

    @Schema(description = "게시글 ID",example = "15")
    private int boardId;

    @Schema(description = "게시글 제목", example = "SpringBoot Swagger 예제")
    private String title;

    @Schema(description = "게시글 내용", example = "이것은 게시글 내용입니다.")
    private String content;


    @Schema(description = "게시글 삭제 여부")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // 요청에서 제외, 응답에서는 포함
    private String deleteYn;

    @Schema(description = "게시글 생성일자")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // 요청에서 제외, 응답에서는 포함
    private String createdAt;

    @Schema(description = "게시글 수정일자")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // 요청에서 제외, 응답에서는 포함
    private String updatedAt;

    private String writerAccount;

    private List<MultipartFile> attachmentFiles; // 첨부파일을 클라이언트로 부터 받는 필드

    private List<Attachment> attachments; // 첨부파일 정보를 담는 필드 (Filename,Path,MIME ...)


}
