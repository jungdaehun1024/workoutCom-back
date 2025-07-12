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
public class BoardDto {
    private int boardId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String writerAccount;
    private String categoryName;
    private String categoryId;
    private int totalCount;
    private List<MultipartFile> attachmentFiles; // 첨부파일을 클라이언트로 부터 받는 필드

    private List<Attachment> attachments; // 첨부파일 정보를 담는 필드 (Filename,Path,MIME ...)


}
