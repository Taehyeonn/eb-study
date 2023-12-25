package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.mapper.FileMapper;
import com.ebstudytemplates3week.vo.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    /**
     * 파일 추가
     * @param multipartFile 입력받은 파일
     * @param boardId 게시글 번호
     * @throws IOException 입출력 예외
     */
    public void addFiles(List<MultipartFile> multipartFile, String boardId) throws IOException {

        String uploadPath = "/Users/th/Documents/study/db/eb/"+boardId+"/"; //todo 환경변수로 빼기
        java.io.File uploadFolder = new java.io.File(uploadPath);

        //폴더가 존재하지 않으면 생성
        if(!uploadFolder.exists()) {
            if (uploadFolder.mkdirs()) {
                log.info("폴더 생성 완료");
            }
        }

        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                log.info("================");
                log.info("업로드 파일명" + file.getOriginalFilename());
                log.info("업로드 파일크기" + file.getSize());

                String originName = file.getOriginalFilename();

                // 저장을 위해 새로운 이름 생성(현시간+난수+확장자)
                long currentTime = System.currentTimeMillis();
                String storedName = "" + currentTime + (int)(Math.random()*1000000) + "." + StringUtils.getFilenameExtension(originName);

                String fullPath = uploadPath + storedName;
                file.transferTo(new java.io.File(fullPath));

                File fileParams = new File(boardId, originName, storedName, file.getSize());
                log.info("fileParams = {}", fileParams);

                fileMapper.addFile(fileParams);

            }
        }


    }

    /**
     * 게시글 번호로 모두 조회
     * @param id 게시글 번호
     * @return List<File>
     */
    public List<File> getFilesByBoardId(String id) {
        return fileMapper.getFilesByBoardId(id);
    }

    /**
     * 파일 번호로 세부 조회
     * @param id 파일 번호
     * @return File
     */
    public File getFileInfoByFileId(String id) {
        return fileMapper.getFileInfoByFileId(id);
    }

    /**
     * 게시글 번호에 해당하는 파일 id 모두 조회
     * @param id 게시글 번호
     * @return file.id
     */
    public List<String> getFileIdsByBoardId(String id) {
        return fileMapper.getFileIdsByBoardId(id);
    }

    /**
     * 저장된 파일 번호 리스트에서 받아온 파일 번호 리스트를 빼서 삭제된 리스트를 구한다
     * @param fileIds 뷰에서 받아온 파일 번호 리스트
     * @param storedFileIds 디비에 저장된 파일 번호 리스트
     * @return 삭제된 파일 번호 리스트
     */
    public List<String> getDeletedFileIds(List<String> fileIds, List<String> storedFileIds) {

        //입력값이 null이면
        if (fileIds == null) {
            return storedFileIds;
        }

        List<String> deletedFileIds = new ArrayList<>(storedFileIds);
        deletedFileIds.removeAll(fileIds);

        return deletedFileIds;
    }

    /**
     * 실제 파일 삭제 후 디비 삭제
     * @param file 파일 정보
     */
    @Transactional
    public void deleteFile(File file) {

        String uploadPath = "/Users/th/Documents/study/db/eb/"+file.getBoardId()+"/"; //todo 환경변수로 빼기
        String fullPath = uploadPath + file.getStoredName();

        java.io.File fileToDelete = new java.io.File(fullPath);

        // 파일이 존재하면 삭제
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                fileMapper.deleteFile(String.valueOf(file.getId()));
                log.info("파일 삭제 완료 file.id={}", file.getId());
            }
        }
    }
}
