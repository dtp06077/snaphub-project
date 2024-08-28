package project.server.service.Implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.server.service.FileService;

import java.io.File;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    @Override
    public String upload(MultipartFile file) {

        if(file.isEmpty()) return null;

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "." + ext;
        String saveFilePath = filePath + saveFileName;

        try {
            file.transferTo(new File(saveFilePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String url = fileUrl + saveFileName;
        return url;
    }

    @Override
    public Resource getImage(String filename) {

        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resource;
    }
}
