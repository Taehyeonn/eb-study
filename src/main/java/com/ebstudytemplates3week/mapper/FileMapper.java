package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.vo.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * 게시글 추가
     * @param fileParams File(boardId, originName, reName);
     */
    void addFile(File fileParams);

    /**
     * 게시글 번호로 모두 조회
     * @param id 게시글 번호
     * @return List<File>(id boardId originName reName);
     */
    List<File> getFilesByBoardId(String id);

    /**
     * 파일 번호로 단일 조회
     * @param id 파일 번호
     * @return File(id boardId originName reName)
     */
    File getFileInfoByFileId(String id);

    /**
     * 게시글 번호에 해당하는 파일 id 모두 조회
     * @param id 게시글 번호
     * @return file.id
     */
    List<String> getFileIdsByBoardId(String id);

    /**
     * 파일 번호에 해당하는 파일 삭제
     * @param id 파일 번호
     */
    void deleteFile(String id);

}
