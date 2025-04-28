package com.workout.workoutcom.controller.board;

import com.workout.workoutcom.configuration.auth.PrincipalDetails;
import com.workout.workoutcom.dto.board.BoardDto;
import com.workout.workoutcom.dto.board.CreateBoardDto;
import com.workout.workoutcom.dto.ApiResponseDto;
import com.workout.workoutcom.service.attachment.AttachService;
import com.workout.workoutcom.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Tag(name = "boardController", description = "게시글에 관련한 API")
public class boardController {

    private final BoardService boardService;

    private final AttachService attachService;

    @Autowired
    public boardController(BoardService boardService, AttachService attachService) {
        this.boardService = boardService;
        this.attachService = attachService;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<ApiResponseDto> createBoard(@ModelAttribute BoardDto boardDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boardDto.setWriterAccount(principalDetails.getUsername());
        boardService.postBoard(boardDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED.value(),"게시글이 성공적으로 생성되었습니다.",null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getBoards")
    public ResponseEntity<ApiResponseDto<List<BoardDto>>> getBoards(){
        List<BoardDto> boards = boardService.getBoards();
        ApiResponseDto<List<BoardDto>> response = new ApiResponseDto<>(HttpStatus.OK.value(), "게시글 목록 로딩에 성공했습니다.",boards);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getBoardDetail/{boardId}")
    public ResponseEntity<ApiResponseDto<BoardDto>> getBoardDetail(@PathVariable int boardId){
        BoardDto board = boardService.getBoardDetail(boardId);
        ApiResponseDto<BoardDto> response = new ApiResponseDto<BoardDto>(HttpStatus.OK.value(), "게시글 상세 정보 로딩에 성공했습니다.",board);
        return ResponseEntity.ok(response);
    }

    //파일 다운로드
    @GetMapping("/files/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String attachmentPath, @RequestParam String attachmentName) throws MalformedURLException, FileNotFoundException {
        try{
            Resource resource = attachService.downloadFile(attachmentPath);
            String encodedName = URLEncoder.encode(attachmentName, StandardCharsets.UTF_8)
                    .replace("+", "%20"); //공백처리
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename*=UTF-8''"+encodedName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/updateBoard")
    public ResponseEntity<ApiResponseDto> updateBoard(@RequestBody BoardDto board){
        boardService.updateBoard(board);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "게시글 수정이 완료되었습니다.",null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/deleteBoard/{boardId}")
    public ResponseEntity<ApiResponseDto> deleteBoard(@PathVariable int boardId){
        boardService.deleteBoard(boardId);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "게시글 삭제가 완료되었습니다.",null);
        return ResponseEntity.ok(response);

    }


}
