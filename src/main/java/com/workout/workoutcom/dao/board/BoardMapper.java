package com.workout.workoutcom.dao.board;


import com.workout.workoutcom.dto.board.Attachment;
import com.workout.workoutcom.dto.board.BoardDto;
import com.workout.workoutcom.dto.category.BoardCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insertBoard(BoardDto boardDto); //게시글 생성
    int insertBoardAttach(List<Attachment> attachment); // 게시글 첨부파일 저장
    List<BoardCategoryDto>selectCreatableCategories();


    boolean hasChildCategory(int categoryId);//0뎁스 카테고리가 자식 카테고리를 가지는지 판단(T F)
    List<BoardDto> selectBoardsHasNoChildCategory(@Param("categoryId") int categoryId,@Param("pageSize") int pageSize , @Param("offset") int offset); // 자식 카테고리를 안가지지 않는 0뎁스 카테고리 게시글 조회
    List<BoardDto> selectBoardsHasChildCategory(@Param("categoryId") int categoryId,@Param("pageSize") int pageSize , @Param("offset") int offset);// 자식 카테고리를 가지는 0뎁스 카테고리 게시글 조회
    List<BoardDto> selectChildCategoryBoards(@Param("categoryId") int categoryId,@Param("pageSize") int pageSize , @Param("offset") int offset); // 1뎁스 카테고리 게시글 조회


    BoardDto getBoardDetail(int boardId); // 게시글 상세
    int deleteBoardAttach (List<Attachment> attachments); // 첨부파일 삭제
    int updateBoard(BoardDto board); //
    int deleteBoard(int boardId);// 게시글 삭제
    int selectBoardCount();
    List<BoardCategoryDto> selectBoardCategory();
    
}
