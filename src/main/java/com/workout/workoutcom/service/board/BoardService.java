package com.workout.workoutcom.service.board;

import com.workout.workoutcom.dao.board.BoardMapper;
import com.workout.workoutcom.dto.board.Attachment;
import com.workout.workoutcom.dto.board.BoardDto;

import com.workout.workoutcom.dto.category.BoardCategoryDto;
import com.workout.workoutcom.exception.NotFoundException;
import com.workout.workoutcom.service.attachment.AttachService;
import com.workout.workoutcom.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    private final BoardMapper boardMapper;
    private final AttachService attachService;


    @Autowired
    public BoardService(BoardMapper boardMapper, AttachService attachService) {
        this.boardMapper = boardMapper;
        this.attachService = attachService;
    }

    //게시글 생성
    @Transactional
    public void postBoard (BoardDto boardDto){
       boardMapper.insertBoard(boardDto); // 첨부파일 제외한 필드 저장
       List<Attachment> attachList = new ArrayList<>(); // 첨부파일 리스트 (첨부파일 경로,MIME타입,파일명이 담긴 리스트)
       List<File> saveFileList = new ArrayList<>(); // 첨부파일의 경로 (롤백용)

        try{
            if(boardDto.getAttachmentFiles() != null && !boardDto.getAttachmentFiles().isEmpty()){
                for(MultipartFile file : boardDto.getAttachmentFiles()){
                    Attachment attach = attachService.saveFile(file,boardDto.getBoardId()); //로컬 디스크에 첨부파일 저장
                    attach.setBoardId(boardDto.getBoardId());
                    attachList.add(attach); 
                    saveFileList.add(new File(attach.getAttachmentPath())); // 저장된 파일 객체 따로 기록(롤백 시 삭제하기 위함)
                }
            }
            if(!attachList.isEmpty()){
                boardMapper.insertBoardAttach(attachList); //DB에 저장
            }
        }catch (Exception e){
            for(File file : saveFileList){ // 예외 발생 시 저장됐던 파일들 제거
                if(file.exists()){
                    file.delete();
                }
            }
            throw new RuntimeException("게시글 생성 중 오류 발생,트랜잭션 및 파일 롤백",e);
        }
    }

    public List<BoardCategoryDto> getBoardCreatableCategories(){
        return boardMapper.selectCreatableCategories();
    }

    //게시글 목록
    public List<BoardDto> getBoardsByCategoryId(int categoryId,int depth,int paginationIndex){
        boolean result;
        PaginationUtil paginationUtil = new PaginationUtil(paginationIndex,20);
        int offset = paginationUtil.calculateOffset();
        int pageSize = paginationUtil.getPageSize();

        if(depth == 0){
            //0뎁스 카테고리가 자식 카테고리를 가지는 카테고리인지 체크
            result = boardMapper.hasChildCategory(categoryId);
            if(result){
                return boardMapper.selectBoardsHasChildCategory(categoryId,pageSize,offset);
            }
            return  boardMapper.selectBoardsHasNoChildCategory(categoryId,pageSize,offset);
        }

        return boardMapper.selectChildCategoryBoards(categoryId,pageSize,offset);
    }


    //게시글 상세
    public BoardDto getBoardDetail(int boardId){
            BoardDto board = boardMapper.getBoardDetail(boardId);
            if(board == null) throw new NotFoundException("게시글을 찾을 수 없습니다.");
            return board;
    }
    //게시글 수정
    public void updateBoard (BoardDto board,List<Attachment> deleteAttachments){
        List<Attachment> attachList = new ArrayList<>(); // 첨부파일 리스트 (첨부파일 경로,MIME타입,파일명이 담긴 리스트)
        List<File> saveFileList = new ArrayList<>(); // 첨부파일의 경로 (롤백용)

        if(deleteAttachments != null && !deleteAttachments.isEmpty()){
                attachService.deleteFile(deleteAttachments); // 로컬 디스크에 저장된 파일 삭제 (기존의 첨부파일 삭제 시 삭제)
                boardMapper.deleteBoardAttach(deleteAttachments);
        }
        try{
            if(board.getAttachmentFiles() != null && !board.getAttachmentFiles().isEmpty()){
                for(MultipartFile file : board.getAttachmentFiles()){
                    Attachment attach = attachService.saveFile(file,board.getBoardId()); //로컬 디스크에 첨부파일 저장
                    attach.setBoardId(board.getBoardId());
                    attachList.add(attach);
                    saveFileList.add(new File(attach.getAttachmentPath())); // 저장된 파일 객체 따로 기록(롤백 시 삭제하기 위함)
                }
            }
            if(!attachList.isEmpty()){
                boardMapper.insertBoardAttach(attachList); //DB에 저장
            }
        }catch (Exception e){
            for(File file : saveFileList){ // 예외 발생 시 저장됐던 파일들 제거
                if(file.exists()){
                    file.delete();
                }
            }
            throw new RuntimeException("게시글 생성 중 오류 발생,트랜잭션 및 파일 롤백",e);
        }
        boardMapper.updateBoard(board);
    
    }
    //게시글 삭제
    public void deleteBoard (int boardId){
        attachService.deleteFile(boardId);
        int result = boardMapper.deleteBoard(boardId); //게시글 삭제
        if(result == 0) throw new  NotFoundException("삭제하려는 게시글이 존재하지 않습니다.");
    }

    //게시글 목록 불러오기 전 게시글 전체 개수 (페이지네이션 버튼 계산)
    public int getBoardTotalCount(){
        int count = boardMapper.selectBoardCount();
        return count;
    }

    //게시글 카테고리 불러오기
    public List<BoardCategoryDto> getBoardCategories(){
        List<BoardCategoryDto> boardCategories = boardMapper.selectBoardCategory();
        return boardCategories;
    }
}
