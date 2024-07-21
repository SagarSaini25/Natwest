package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	@Value("${file.upload-dir}")
    private String uploadDir;
	
	public String getUploadDir(){
		return uploadDir;
	}

    public void storeFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);
    }
}