package com.octo.fffc.com.octo.fffc.service;

import com.octo.fffc.com.octo.fffc.service.impl.MetadataServiceImpl;
import com.octo.fffc.domain.ColumnInfo;
import com.octo.fffc.domain.Metadata;
import com.octo.fffc.exception.FixedFileFormatConverterException;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MetadataServiceImplTest {

    private MetadataService metadataService;

    @Before
    public void setUp() {
        metadataService = new MetadataServiceImpl();
    }

    @Test
    public void shouldGenerateMetadata() {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("sample_metadata.txt").getFile());
        Metadata actual = metadataService.retrieveMetadata(file);
        Metadata expected = getExpectedMetadata();
        assertThat(actual, is(expected));
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfLengthIsNotThree() {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("invalid_metadata_length.txt").getFile());
        metadataService.retrieveMetadata(file);
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfColumnLengthIsNotNumber(){
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("invalid_column_length.txt").getFile());
        metadataService.retrieveMetadata(file);
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfColumnNameIsEmpty(){
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("column_name_empty.txt").getFile());
        metadataService.retrieveMetadata(file);
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfColumnLengthIsEmpty(){
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("column_length_empty.txt").getFile());
        metadataService.retrieveMetadata(file);
    }

    @Test(expected = FixedFileFormatConverterException.class)
    public void shouldFailIfColumnTypeIsEmpty(){
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("column_type_empty.txt").getFile());
        metadataService.retrieveMetadata(file);
    }

    private Metadata getExpectedMetadata() {
        ColumnInfo birthData = new ColumnInfo("Birth date", 10, "date");
        ColumnInfo firstName = new ColumnInfo("First name", 15, "string");
        ColumnInfo lastName = new ColumnInfo("Last name", 15, "string");
        ColumnInfo weight = new ColumnInfo("Weight", 5, "numeric");
        Metadata metadata = new Metadata();
        metadata.addMetadata(birthData);
        metadata.addMetadata(firstName);
        metadata.addMetadata(lastName);
        metadata.addMetadata(weight);
        return metadata;
    }
}