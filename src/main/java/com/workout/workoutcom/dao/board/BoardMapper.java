package com.workout.workoutcom.dao.board;


import com.workout.workoutcom.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insertBoard(Board board);
    List<Board> getBoards();
    Board getBoardDetail(int boardId);
}
