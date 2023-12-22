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
    List<File> getFileByBoardId(String id);

    /**
     * 파일 번호로 단일 조회
     * @param id 파일 번호
     * @return File(id boardId originName reName)
     */
    File getFileInfoByFileId(String id);

}
