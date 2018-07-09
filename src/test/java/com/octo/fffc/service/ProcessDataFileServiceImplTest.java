package com.octo.fffc.service;

import com.octo.fffc.domain.Metadata;
import com.octo.fffc.exception.FixedFileFormatConverterException;
import com.octo.fffc.service.impl.MetadataServiceImpl;
import com.octo.fffc.service.impl.ProcessDataFileServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessDataFileServiceImplTest {

    private String csvPath;

    private ProcessDataFileService processDataFileService;

    private MetadataService metadataService;

    private Metadata metadata;

    @Before
    public void setUp() {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("sample_data.txt").getFile();
        csvPath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + "data_file.txt";
        processDataFileService = new ProcessDataFileServiceImpl();
        metadataService = new MetadataServiceImpl();
        metadata = getMetadata();
    }

    @After
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(csvPath));
    }


    @Test
    public void shouldCreateOutputFile() {
        File dataFile = new File(Thread.currentThread().getContextClassLoader().getResource("sample_data.txt").getFile());
        processDataFileService.processDatafile(metadata, csvPath, dataFile);
    }

    private Metadata getMetadata() {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("sample_metadata.txt").getFile());
        return metadataService.retrieveMetadata(file);
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfInvalidNumericDataType() throws Exception {
        File dataFile = new File(Thread.currentThread().getContextClassLoader().getResource("invalid_numeric_data.txt").getFile());
        processDataFileService.processDatafile(metadata, csvPath, dataFile);
    }
}