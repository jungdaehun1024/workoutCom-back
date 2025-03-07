package com.workout.workoutcom.controller.board;

import com.workout.workoutcom.dto.Board;
import com.workout.workoutcom.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
            @ApiResponse(responseCode ="201", description = "게시글 생성에 성공했습니다.",content = @Content(mediaType = "application/json"))
    })

    public ResponseEntity<String> createBoard(@RequestBody Board board) {
        boardService.postBoard(board);
        return ResponseEntity.ok("Board created successfully");
    }

    @GetMapping("/getBoards")
    @Operation(summary = "게시글 리스트", description = "게시글을 리스트를 가져올 때 사용하는 API")

    public ResponseEntity<List<Board>> getBoards() {
        System.out.println("게시글 목록 가져오는중..");
        return ResponseEntity.ok(boardService.getBoards());
    }

    @GetMapping("/getBoardDetail/{boardId}")
    @Operation(summary = "게시글 상세", description = "게시글 상세내용을 가져올 때 사용하는 API")
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 고유번호", example = "5 r")
    })
    public ResponseEntity<Board> getBoardDetail(@PathVariable int boardId) {
        System.out.println("게시글 상세 메소드"+boardId);
        Board boardDetail = boardService.getBoardDeatil(boardId);
        System.out.println((boardDetail));

        return ResponseEntity.ok(boardDetail);

    }

}
