package com.workout.workoutcom.controller.board;

import com.workout.workoutcom.dto.Board;
import com.workout.workoutcom.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class boardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/createBoard")
    public ResponseEntity<String> createBoard(@RequestBody Board board) {
        boardService.postBoard(board);
        return ResponseEntity.ok("Board created successfully");
    }

    @GetMapping("/getBoards")
    public ResponseEntity<List<Board>> getBoards() {
        System.out.println("게시글 목록 가져오는중..");
        return ResponseEntity.ok(boardService.getBoards());
    }

    @GetMapping("/getBoardDetail/{boardId}")
    public ResponseEntity<Board> getBoardDetail(@PathVariable int boardId) {
        System.out.println("게시글 상세 메소드"+boardId);
        Board boardDetail = boardService.getBoardDeatil(boardId);
        System.out.println((boardDetail));

        return ResponseEntity.ok(boardDetail);


    }

}
