package com.workout.workoutcom.dao.board;


import com.workout.workoutcom.dto.board.BoardDto;
import com.workout.workoutcom.dto.board.CreateBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insertBoard(CreateBoardDto createBoardDto); //게시글 생성
    List<BoardDto> getBoards(); // 게시글 목록 조회
    BoardDto getBoardDetail(int boardId); // 게시글 상세
    int updateBoard(BoardDto board); //
    int deleteBoard(int boardId);// 게시글 삭제
}
