package com.natwest.exam;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import service.TransformationService;

public class TransformationServiceTest {

    @Test
    public void testTransform() {
        TransformationService transformationService = new TransformationService();
        File inputFile = Mockito.mock(File.class);
        File referenceFile = Mockito.mock(File.class);
        File outputFile = Mockito.mock(File.class);

        assertDoesNotThrow(() -> transformationService.transform(inputFile, referenceFile, outputFile, List.of()));
    }
}