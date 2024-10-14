package project.server.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    //파일 업로드하기
    String upload(MultipartFile file);

    //파일 불러오기
    Resource getImage(String filename);
}
