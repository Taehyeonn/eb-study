package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.service.FileService;
import com.ebstudytemplates3week.vo.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @GetMapping("/files/{boardId}")
    public ResponseEntity<List<File>> getFiles(
            @PathVariable(name = "boardId") String boardId) {

        //파일 목록 조회
        List<File> files = fileService.getFilesByBoardId(boardId);

        return ResponseEntity.ok(files);
    }
}
