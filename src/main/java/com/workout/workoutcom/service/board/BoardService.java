package com.workout.workoutcom.service.board;

import com.workout.workoutcom.dao.board.BoardMapper;
import com.workout.workoutcom.dto.board.BoardDto;
import com.workout.workoutcom.dto.board.CreateBoardDto;
import com.workout.workoutcom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    //게시글 생성
    public void postBoard (CreateBoardDto createBoardDto){
            boardMapper.insertBoard(createBoardDto);
    }

    //게시글 목록
    public List<BoardDto> getBoards(){
            List<BoardDto> boards = boardMapper.getBoards();
            if(boards == null || boards.isEmpty()) throw new NotFoundException("게시글을 찾을 수 없습니다.");
            return boards;
    }

    //게시글 상세
    public BoardDto getBoardDetail(int boardId){
            BoardDto board = boardMapper.getBoardDetail(boardId);
            if(board == null) throw new NotFoundException("게시글을 찾을 수 없습니다.");
            return board;

    }

    //게시글 수정
    public void updateBoard (BoardDto board){
            int result = boardMapper.updateBoard(board);
            if(result == 0)throw new NotFoundException("수정하려는 게시글이 존재하지 않습니다.");
    }


    //게시글 삭제
    public void deleteBoard (int boardId){
        int result = boardMapper.deleteBoard(boardId);
        if(result == 0) throw new  NotFoundException("삭제하려는 게시글이 존재하지 않습니다.");
    }
}
