package com.workout.workoutcom.dao.board;


import com.workout.workoutcom.dto.board.Attachment;
import com.workout.workoutcom.dto.board.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insertBoard(BoardDto boardDto); //게시글 생성
    int insertBoardAttach(List<Attachment> attachment); // 게시글 첨부파일 저장
    List<BoardDto> getBoards(int offset,int pageSize); // 게시글 목록 조회
    BoardDto getBoardDetail(int boardId); // 게시글 상세
    int deleteBoardAttach (List<Attachment> attachments); // 첨부파일 삭제
    int updateBoard(BoardDto board); //
    int deleteBoard(int boardId);// 게시글 삭제
    int selectBoardCount();
    
}
