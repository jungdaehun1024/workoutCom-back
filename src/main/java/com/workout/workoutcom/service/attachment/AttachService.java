package com.workout.workoutcom.service.attachment;

import com.workout.workoutcom.dto.board.Attachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AttachService {

    private final String baseFilePath;

    public AttachService(@Value("${BOARD_ATTACH_PATH}") String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    //파일 명 추출 후 중복 방지를 위한 이름 부여
    public String extractFileName(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID()+"_"+fileName;
        return uniqueFilename;
    }

    //파일 MIME타입 추출
    public String extractFileMIME(MultipartFile file){
        String fileMIME = file.getContentType();
        return fileMIME;
    }


    //파일 저장 후 저장경로 반환(절대경로)
    public Attachment saveFile(MultipartFile file,int boardId){
        String filePath = baseFilePath +boardId+"/" +extractFileName(file); //  파일이 저장될 경로
        Attachment attachment = new Attachment();
        File destination = new File(filePath); 
        try{
            File parentDir = destination.getParentFile();
            if(!parentDir.exists()){
                parentDir.mkdirs(); // 디렉토리 없으면 생성
            }
            file.transferTo(destination);
            attachment.setAttachmentPath(destination.getAbsolutePath());
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentMIME(extractFileMIME(file));

            return attachment;
        }catch(IOException e){
            //예외 시 저장된 파일 삭제
            if(destination.exists()){
                destination.delete();
            }
            e.printStackTrace();
            return null;
        }
    }


    //게시글 다운로드
    public Resource downloadFile (String attachmentPath) throws MalformedURLException, FileNotFoundException {
        //파일 경로 정규화
        Path filePath = Paths.get(attachmentPath).normalize();

        //파일을 리소스로 로딩
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists() && resource.isReadable()){
            return resource; // 파일 리소스(스트림)를 반환
        }else {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
}
