package service;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import model.TransformationRule;

@Service
public class TransformationService {

    public void transform(File inputFile, File referenceFile, File outputFile, List<TransformationRule> rules) throws IOException {
        try (
            Reader inputReader = new FileReader(inputFile);
            Reader refReader = new FileReader(referenceFile);
            Writer outputWriter = new FileWriter(outputFile);
            CSVParser inputParser = new CSVParser(inputReader, CSVFormat.DEFAULT.withHeader());
            CSVParser refParser = new CSVParser(refReader, CSVFormat.DEFAULT.withHeader());
            CSVPrinter outputPrinter = new CSVPrinter(outputWriter, CSVFormat.DEFAULT.withHeader("outfield1", "outfield2", "outfield3", "outfield4", "outfield5"));
        ) {
            for (CSVRecord inputRecord : inputParser) {
                // Lookup reference data
                CSVRecord refRecord = findRefRecord(refParser, inputRecord.get("refkey1"), inputRecord.get("refkey2"));
                // Apply transformation rules
                String outfield1 = inputRecord.get("field1") + inputRecord.get("field2");
                String outfield2 = refRecord.get("refdata1");
                String outfield3 = refRecord.get("refdata2") + refRecord.get("refdata3");
                double outfield4 = Double.parseDouble(inputRecord.get("field3")) * Math.max(Double.parseDouble(inputRecord.get("field5")), Double.parseDouble(refRecord.get("refdata4")));
                double outfield5 = Math.max(Double.parseDouble(inputRecord.get("field5")), Double.parseDouble(refRecord.get("refdata4")));
                
                outputPrinter.printRecord(outfield1, outfield2, outfield3, outfield4, outfield5);
            }
        }
    }

    private CSVRecord findRefRecord(CSVParser refParser, String refkey1, String refkey2) throws IOException {
        for (CSVRecord refRecord : refParser) {
            if (refRecord.get("refkey1").equals(refkey1) && refRecord.get("refkey2").equals(refkey2)) {
                return refRecord;
            }
        }
        throw new IOException("Reference data not found for keys: " + refkey1 + ", " + refkey2);
    }
}