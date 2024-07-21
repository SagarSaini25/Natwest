package Controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import service.FileStorageService;
import service.ReportGenerationService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ReportGenerationService reportGenerationService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("inputFile") MultipartFile inputFile,
                                             @RequestParam("referenceFile") MultipartFile referenceFile) throws IOException {
        fileStorageService.storeFile(inputFile);
        fileStorageService.storeFile(referenceFile);
        return ResponseEntity.ok("Files uploaded successfully");
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateReport(@RequestParam("inputFile") String inputFileName,
                                                 @RequestParam("referenceFile") String referenceFileName,
                                                 @RequestParam("outputFile") String outputFileName) throws IOException {
        File inputFile = new File(fileStorageService.getUploadDir() + File.separator + inputFileName);
        File referenceFile = new File(fileStorageService.getUploadDir() + File.separator + referenceFileName);
        File outputFile = new File(fileStorageService.getUploadDir() + File.separator + outputFileName);
        reportGenerationService.generateReport(inputFile, referenceFile, outputFile);
        return ResponseEntity.ok("Report generated successfully");
    }
}