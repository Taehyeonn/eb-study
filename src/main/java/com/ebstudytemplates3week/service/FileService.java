package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.mapper.FileMapper;
import com.ebstudytemplates3week.vo.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    //파일 저장
    public void addFile(MultipartFile file, String boardId) throws IOException {

        String uploadFolder = "/Users/th/Documents/study/eb-study-templates-3week/files/"; //todo 환경변수로 빼기

        String originName = file.getOriginalFilename();
        log.info("file.getOriginalFilename = {}", originName); //백업용.png //todo 확장자 분리

        //rename 로직
        String reName = originName;

        String fullPath = uploadFolder + originName;
        file.transferTo(new java.io.File(fullPath));

        File fileParams = new File(boardId, originName, reName);
        log.info("fileParams = {}", fileParams);

        fileMapper.addFile(fileParams);
    }

    //ㅇㅇ??
    public List<File> getFileByBoardId(String id) {
        return fileMapper.getFileByBoardId(id);
    }

    public File getFileInfoByFileId(String id) {
        return fileMapper.getFileInfoByFileId(id);
    }
}
