package com.octo.fffc.service.impl;

import com.octo.fffc.service.MetadataService;
import com.octo.fffc.domain.ColumnInfo;
import com.octo.fffc.domain.Metadata;
import com.octo.fffc.exception.FixedFileFormatConverterException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class implements ${@link MetadataService}
 *
 * @author dinuka
 */
public class MetadataServiceImpl implements MetadataService {

    @Override
    public Metadata retrieveMetadata(File metadataFile) {
        Metadata metadata = new Metadata();
        try (Stream<String> fileStream = Files.lines(Paths.get(metadataFile.getAbsolutePath()), StandardCharsets.UTF_8)) {
            fileStream.forEach(metaDataLine -> {
                String[] metadataInfo = metaDataLine.split(",");
                metadata.addMetadata(validateAndReturnColumnInfo(metadataInfo));
            });
        } catch (IOException e) {
            throw new FixedFileFormatConverterException(e);
        }
        return metadata;
    }

    private ColumnInfo validateAndReturnColumnInfo(String[]metadataInfo){
        if(metadataInfo.length!=3){
            throw new FixedFileFormatConverterException("The length of the metadata information is incorrect");
        }
        String columnName = metadataInfo[0];
        if(StringUtils.isBlank(columnName)){
            throw new FixedFileFormatConverterException("The column name on the metadata file cannot be empty");
        }
        String columnLength = metadataInfo[1];
        if(StringUtils.isBlank(columnLength)){
            throw new FixedFileFormatConverterException("The column length cannot be empty");
        }
        Integer colLength;

        try{
            colLength = Integer.valueOf(columnLength);
        }catch(NumberFormatException e){
            throw new FixedFileFormatConverterException("The column length provided is not a number");
        }

        String columnType = metadataInfo[2];
        if(StringUtils.isBlank(columnType)){
            throw new FixedFileFormatConverterException("The column type cannot be empty");
        }
        validateDataType(columnType);
        return new ColumnInfo(columnName,colLength,columnType);
    }

    private void validateDataType(String dataType){
        if(!dataType.matches("date|numeric|string")){
            throw new FixedFileFormatConverterException("Unsupported data type");
        }
    }
}
