package service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import model.TransformationRule;

@Service
public class ReportGenerationService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TransformationService transformationService;

    // Inject configurable transformation rules
    private List<TransformationRule> transformationRules;

    @Scheduled(cron = "${report.generation.cron}")
    public void generateScheduledReports() {
        // Implement logic to get the list of files and generate reports
    }

    public void generateReport(File inputFile, File referenceFile, File outputFile) throws IOException {
        transformationService.transform(inputFile, referenceFile, outputFile, transformationRules);
    }
}
