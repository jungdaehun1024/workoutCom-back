package com.workout.workoutcom.service.board;

import com.workout.workoutcom.dao.board.BoardMapper;
import com.workout.workoutcom.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    //게시글 생성
    public void postBoard (Board board){
        boardMapper.insertBoard(board);
    }

    //게시글 목록
    public List<Board> getBoards(){
        List<Board> boards = boardMapper.getBoards();

        return boards;
    }

    //게시글 상세
    public Board getBoardDeatil(int boardId){
        System.out.println("게시글 상세 서비스 실행");
        Board board = boardMapper.getBoardDetail(boardId);


        return board;
    }
}
