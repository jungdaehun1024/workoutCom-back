package com.workout.workoutcom.service.board;

import com.workout.workoutcom.dao.board.BoardMapper;
import com.workout.workoutcom.dto.board.Attachment;
import com.workout.workoutcom.dto.board.BoardDto;

import com.workout.workoutcom.exception.NotFoundException;
import com.workout.workoutcom.service.attachment.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

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
