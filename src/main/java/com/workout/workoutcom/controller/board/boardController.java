package com.workout.workoutcom.controller.board;

import com.workout.workoutcom.dto.board.BoardDto;
import com.workout.workoutcom.dto.board.CreateBoardDto;
import com.workout.workoutcom.dto.ApiResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "boardController", description = "게시글에 관련한 API")
public class boardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/createBoard")
    @Operation(summary = "게시글 생성", description = "게시글을 생성할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "게시글 생성에 성공했습니다.",content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value= "{ \"status\": 201, \"message\": \"게시글 생성에 성공했습니다.\"}"))),
            @ApiResponse(responseCode = "500", description = "게시글 생성 중 서버 에러 발생", content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value= "{ \"status\": 500, \"message\": \"서버에서 예기치 못한 오류가 발생했습니다.\" }")))
    })
    public ResponseEntity<ApiResponseDto> createBoard(@RequestBody CreateBoardDto createBoardDto){
        boardService.postBoard(createBoardDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED.value(),"게시글이 성공적으로 생성되었습니다.",null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getBoards")
    @Operation(summary = "게시글 리스트", description = "게시글을 리스트를 가져올 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "게시글 목록로딩에 성공했습니다.", content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value = "{\"status\": 200, \"message\": \"게시글 목록 로딩에 성공했습니다.\", \"data\": [{\"boardId\": 1, \"title\": \"첫 번째 게시글\", \"content\": \"첫 번째 게시글 내용\",\"createdAt\" :\"2025-03-12 01:04:28\" , \"updatedAt\":\"2025-03-12 01:04:28\"}, {\"boardId\": 2, \"title\": \"두 번째 게시글\", \"content\": \"두 번째 게시글 내용\",\"createdAt\" :\"2025-03-12 01:04:28\" , \"updatedAt\":\"2025-03-12 01:04:28\"}]}"))),
            @ApiResponse(responseCode = "404", description = "가져올 게시글 목록이 없습니다.", content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples =  @ExampleObject(value = "{ \"status\": 404, \"message\": \"가져올 게시글 목록이 없습니다.\" }") )),
            @ApiResponse(responseCode = "500", description = "게시글 목록 로딩 중 서버 에러 발생",content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples =  @ExampleObject(value = "{ \"status\": 500, \"message\": \"서버에서 예기치 못한 오류가 발생했습니다.\" }"))),
            })
    public ResponseEntity<ApiResponseDto<List<BoardDto>>> getBoards(){
        List<BoardDto> boards = boardService.getBoards();
        ApiResponseDto<List<BoardDto>> response = new ApiResponseDto<>(HttpStatus.OK.value(), "게시글 목록 로딩에 성공했습니다.",boards);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getBoardDetail/{boardId}")
    @Operation(summary = "게시글 상세", description = "게시글 상세내용을 가져올 때 사용하는 API")
    @Parameter(name = "boardId", description = "게시글 고유번호", example = "5")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 상세 정보 로딩에 성공했습니다.", content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value = "{\"status\" : 200 , \"message\" : \"게시글 상세 정보 로딩에 성공했습니다.\" , \"data\" : {\"boardId\": 1,\"title\":\"첫 번째 게시글\",\"content\":\"첫 번째 게시글 내용\",\"createdAt\" :\"2025-03-12 01:04:28\" , \"updatedAt\":\"2025-03-12 01:04:28\"}}"))),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않습니다.",content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value = "{ \"status\": 404, \"message\": \"게시글을 찾을 수 없습니다.\" }"))),
            @ApiResponse(responseCode = "500",description = "게시글 조회 중 서버 에러 발생",content = @Content(mediaType ="application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples =  @ExampleObject(value = "{ \"status\": 500, \"message\": \"서버에서 예기치 못한 오류가 발생했습니다.\" }")))
    })
    public ResponseEntity<ApiResponseDto<BoardDto>> getBoardDetail(@PathVariable int boardId){
        BoardDto board = boardService.getBoardDetail(boardId);
        ApiResponseDto<BoardDto> response = new ApiResponseDto<BoardDto>(HttpStatus.OK.value(), "게시글 상세 정보 로딩에 성공했습니다.",board);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateBoard")
    @Operation(summary = "게시글 수정", description = "게시글을 수정할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정이 완료되었습니다.",content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = ApiResponseDto.class),
                         examples = @ExampleObject(value="{\"status\":200,\"message\":\"게시글 수정이 완료되었습니다.\"}"))),
            @ApiResponse(responseCode = "404", description = "수정하려는 게시글이 존재하지 않습니다.",content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponseDto.class),
                        examples = @ExampleObject(value="{\"status\":404,\"message\":\"수정하려는 게시글이 존재하지 않습니다.\"}"))),
            @ApiResponse(responseCode = "500", description = "게시글  수정중 서버 에러 발생",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value="{\"status\":500,\"message\":\"서버에서 예기치 못한 오류가 발생했습니다.\"}")))
    })
    public ResponseEntity<ApiResponseDto> updateBoard(@RequestBody BoardDto board){
        boardService.updateBoard(board);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "게시글 수정이 완료되었습니다.",null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/deleteBoard/{boardId}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제할 때 사용하는 API")
    @Parameter(name = "boardId", description = "게시글 고유번호", example = "5")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정이 완료되었습니다.",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value="{\"status\":200,\"message\":\"게시글 삭제가 완료되었습니다.\"}"))),
            @ApiResponse(responseCode = "404", description = "삭제하려는 게시글이 존재하지 않습니다.",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value="{\"status\":404,\"message\":\"삭제하려는 게시글이 존재하지 않습니다.\"}"))),
            @ApiResponse(responseCode = "500", description = "게시글 삭제 중 서버 에러 발생",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value="{\"status\":500,\"message\":\"서버에서 예기치 못한 오류가 발생했습니다.\"}")))
    })
    public ResponseEntity<ApiResponseDto> deleteBoard(@PathVariable int boardId){
        boardService.deleteBoard(boardId);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "게시글 삭제가 완료되었습니다.",null);
        return ResponseEntity.ok(response);

    }


}
