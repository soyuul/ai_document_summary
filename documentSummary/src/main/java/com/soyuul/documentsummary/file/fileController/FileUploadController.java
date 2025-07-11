package com.soyuul.documentsummary.file.fileController;

import com.soyuul.documentsummary.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    //  yml에 설정한 저장 경로
    @Value("${file.upload.path.summary-files}")
    private String uploadFile;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        try{
            //  저장된 파일 이름 가지고오기
            //  유틸 클래스에 실제 저장
            String savedFileName = FileUploadUtils.saveFile(uploadFile, file.getOriginalFilename(), file);

            return ResponseEntity.ok(savedFileName);
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }
    }
}
