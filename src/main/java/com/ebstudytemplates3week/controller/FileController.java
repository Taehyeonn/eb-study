package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.service.FileService;
import com.ebstudytemplates3week.vo.File;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{id}")
    public void fileDownload(@PathVariable(name = "id") String id,
                             HttpServletResponse response) throws IOException {

        File fileInfo = fileService.getFileInfoByFileId(id); //todo 파일이 없는 경우 예외처리
        log.info("fileInfo ={}", fileInfo);

        String uploadFolder = "/Users/th/Documents/study/eb-study-templates-3week/files/"; //todo 환경변수로 빼기, 폴더분류
        String originName = fileInfo.getOriginName();

        String fullPath = uploadFolder + originName;

        // 파일을 저장했던 위치에서 첨부파일을 읽어 byte[]형식으로 변환한다.
        byte[] fileByte = FileUtils.readFileToByteArray(new java.io.File(fullPath));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition",  "attachment; fileName=\""+ URLEncoder.encode(originName, StandardCharsets.UTF_8)+"\";");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
