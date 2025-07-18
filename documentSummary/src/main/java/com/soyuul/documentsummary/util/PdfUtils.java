package com.soyuul.documentsummary.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class PdfUtils {

    /*PDF 파일 경로로부터 텍스트 추출*/
    public static String extractTextFromPdf(Path path) throws IOException{
        /*
        * PDDocument.load(...) : PDF 파일을 열어서 문서 객체로 읽어들임
        * new File(path.toString()) : Path를 File로 바꿔서 PDFBox가 이해할 수 있게 한다.
        *
        * PDFTextStripper stripper = new PDFTextStripper()
        * : PDF 파일에서 텍스트만 추출해주는 객체 생성
        * => PDF의 레이아웃, 폰트 같은 건 무시하고 글자만 뽑기
        * */
        try (PDDocument document = PDDocument.load(new File(path.toString()))){
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}


