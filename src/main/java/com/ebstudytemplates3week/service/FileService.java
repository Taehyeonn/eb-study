package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.mapper.FileMapper;
import com.ebstudytemplates3week.vo.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    /**
     * 게시글 번호에 맞춰 파일 추가
     * @param file 첨부파일
     * @param boardId 게시글 번호
     * @throws IOException 입출력시 발생되는 오류//todo 예외처리하기
     */
    public void addFile(MultipartFile file, String boardId) throws IOException {

        String uploadPath = "/Users/th/Documents/study/db/eb/"+boardId+"/"; //todo 환경변수로 빼기

        java.io.File uploadFolder = new java.io.File(uploadPath);

        //폴더가 존재하지 않으면 생성
        if(!uploadFolder.exists()) {
            boolean mkdirs = uploadFolder.mkdirs();
        }

        String originName = file.getOriginalFilename();
        log.info("file.getOriginalFilename = {}", originName);  //todo 확장자 분리

        // 저장을 위해 새로운 이름 생성(현시간+난수+확장자)
        long currentTime = System.currentTimeMillis();
        int randomNum = (int)(Math.random()*1000000);
        String storedName = "" + currentTime + randomNum + "." + StringUtils.getFilenameExtension(originName);

        String fullPath = uploadPath + storedName;
        file.transferTo(new java.io.File(fullPath));

        File fileParams = new File(boardId, originName, storedName);
        log.info("fileParams = {}", fileParams);

        fileMapper.addFile(fileParams);
    }

    /**
     * 게시글 번호로 모두 조회
     * @param id 게시글 번호
     * @return List<File>
     */
    public List<File> getFileByBoardId(String id) {
        return fileMapper.getFileByBoardId(id);
    }

    /**
     * 파일 번호로 세부 조회
     * @param id 파일 번호
     * @return File
     */
    public File getFileInfoByFileId(String id) {
        return fileMapper.getFileInfoByFileId(id);
    }
}
