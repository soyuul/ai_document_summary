package com.soyuul.documentsummary.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    public static String saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {

        // 1. 파일 이름 추출
        /*
        * getOriginalFilename(): 업로드된 파일의 원래 이름을 문자열 형태로 반환
        * */
        String originalFilename = file.getOriginalFilename();


        // 2. UUID 붙이기
        /*
        * UUID : UUID에 대한 사용
        * randomUUID() : 랜덤으로 id를 붙여준다 (고유 아이디 만들기)
        * toString() : 읽을 수 있는 언어로 반환
        *
        * originalFilename + uuid : 기본 파일 이름에 uuid 삽입
        * => 고유한 파일명을 만들기 위해 uuid를 만들어서 새로운 파일 명 만들기
        *
        * 이 방식이면 확장자가 없어질 수 있으며 파일 이름에 한글이나 공백, 특수문자가 포함되었을 경우 운영체제에서 문제를 일으킬 수 있다.
        * -> 확장자가 없으면 나중에 파일 접근이 어려워진다 + 문제도
        * -> uuid + 확장자 형태 권장
        *  */
//        String uuid = UUID.randomUUID().toString();
//        String replaceFilename = originalFilename + uuid;

        /*
        * 고유한 파일명을 만들기 위해 UUID를 생성하고, 기존 파일 확장자를 유지하며 저장
        * substring(): 문자열 자르기
        * extension: 파일 확장자
        * => originalFilename에서 마지막 인덱스에 "." 추가
        * => 조합한 파일명을 만들때 UUID 랜덤으로 붙이기
        * */
//        확장자가 없는 경우 substring()에서 exception이 발생 가능, 그렇기에 조건 체크
        String replaceFilename = "";
        if(originalFilename != null && originalFilename.contains(".")){
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            replaceFilename = UUID.randomUUID().toString() + extension;
        }else{
//            확장자가 없는 경우 로그 남기기
            throw new IOException("파일 이름이 유효하지 않거나 확장자가 없습니다.");
        }


        // 3. 디렉터리 없으면 생성
        /*
        * Path : 파일의 주소를 나타내는 생성자
        * uploadPath : 내가 지정한 변수명
        * Paths.get() : 내가 지정한 패스 저장하기
        * uploadDir : 업로드한 파일
        * => uploadPath라는 파일을 저장하는 변수를 만들고 Paths.get()로 지정된 파일 저장하기
        * */
        Path uploadPath = Paths.get(uploadDir);

        /*
        * Files : 파일이 존재하는지, 디렉토리가 존재하는지, 사이즈가 몇인지 알려주는 클래스
        * exists() : 파일이 존재하는지 확인 (반환결과 boolean)
        * createDirectories() : 디렉토리가 없을 경우 생성
        * => 만약 해당 파일의 경로를 못찾을 경우 해당 파일의 경로 생성
        * */
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        log.info("파일 업로드 경로: {}", uploadPath.toAbsolutePath());


        // 4. 저장
        /*
        * 간단하고 직관적인 코드라 짧고 명확해서 사용하기 쉬움
        * Spring이 추상화해준 편의 메서드라 빠르게 파일을 저장할 수 있다.
        * 하지만,
        * MultipartFile 내부 구현체에 따라 제한이 있음
        * (ex. CommonsMultipartFile 이나 StandardMultipartHttpServletRequest 등 구현체에 따라 transferTo() 가 임시 파일 기반으로 동작할 수 있다.)
        * 종류가 제한적
        * */
//        Path filePath = uploadPath.resolve(replaceFilename);
//        Files.transferTo(filePath.toFile());

        /*
         * Java NIO 표준 방식 => Spring에 종속되지 않고 유연하고 확장성이 있다.
         * InputStream 기반으로 다양한 입출력 처리나 버퍼 제어 가능
         * 예외 처리를 더 세분화해 로그나 사용자 응답으로 활용 가능
         * 하지만,
         * 코드가 길고 복잡해 보일 수 있다.
         * 실수하면 스트림을 닫지 않아 리소스 누수가 발생할 수 있다. (이때, try-with-resources로 방지 가능)
         *
         * => 간단한 파일 업로드는 transferTo()로 충분하다
         * 하지만, 복잡한 처리, 로깅, 보안, 확장성이 필요한 실무에서는 Files.copy() 방식이 더 안정적
         *
         * file.getInputStream() : 사용자가 업로드한 MultipartFile 객체로부터 파일 내용을 읽을 수 있는 InputStream을 가지고옴 (메모리에 있는 파일 데이터를 Stream을 꺼낸다.)
         * try(...){...} : Java의 try-with-resources 문법으로 InputStream은 IO 지원이기 때문에 try 블록이 끝나면 자동으로 닫힌다. (close()를 다로 호출하지 않아도 메모리 누수 없이 처리)
         * resolve() : 기존 경로에 새 파일 이름을 붙여서 전체 경로를 만들어주는 메서드
         * Files.copy(...) : 파일 복사 메서드
         * - inputStream: 원본 (업로드된 파일 내용)
         * - filePath: 복사할 대상 경로 (실제로 저장할 위치)
         * StandardCopyOption.REPLACE_EXISTING: 동일한 이름의 파일이 있으면 덮어쓰기
         *
         * => 업로드된 파일을 inputStream으로 읽어서 지정된 디렉토리 경로에 고유 파일명으로 저장하고 이미 있으면 덮어쓴다.
         * */

        try(InputStream inputStream = file.getInputStream()){
            Path filePath = uploadPath.resolve(replaceFilename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            throw new IOException("파일 저장 실패: " + fileName, ex);
        }


        // 5. 저장된 파일명 or 경로 리턴
        return replaceFilename;

    }
}
