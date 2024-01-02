package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.service.FileService;
import com.ebstudytemplates3week.vo.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    /**
     * 파일 목록 조회
     * @param boardId 게시글 번호
     * @return ResponseEntity<List<File>>
     */
    @GetMapping("/files/{boardId}")
    public ResponseEntity<List<File>> getFiles(
            @PathVariable(name = "boardId") String boardId) {

        //파일 목록 조회
        List<File> files = fileService.getFilesByBoardId(boardId);

        return ResponseEntity.ok(files);
    }

    /**
     * 파일 다운로드
     * @param fileId 파일 아이디
     * @return byte[]
     * @throws IOException 예외
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> fileDownload(@PathVariable(name = "fileId") String fileId) throws IOException {
        File fileInfo = fileService.getFileInfoByFileId(fileId);

        String uploadPath = "/Users/th/Documents/study/db/eb/" + fileInfo.getBoardId() + "/";
        String originName = fileInfo.getOriginName();
        String fullPath = uploadPath + fileInfo.getStoredName();

        byte[] fileBytes = FileUtils.readFileToByteArray(new java.io.File(fullPath));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originName, StandardCharsets.UTF_8) + "\"")
                .body(fileBytes);
    }
}
