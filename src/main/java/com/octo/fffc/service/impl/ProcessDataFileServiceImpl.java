package com.octo.fffc.service.impl;

import com.octo.fffc.domain.ColumnInfo;
import com.octo.fffc.domain.Metadata;
import com.octo.fffc.exception.FixedFileFormatConverterException;
import com.octo.fffc.service.ProcessDataFileService;
import com.octo.fffc.util.CommonUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * This class implements {@link ProcessDataFileService}
 *
 * @author dinuka
 */
public class ProcessDataFileServiceImpl implements ProcessDataFileService {

    @Override
    public void processDatafile(Metadata metadata, String csvFilePath, File dataFile) {
        writeColumnHeaders(metadata,csvFilePath);

        try (Stream<String> dataFileStream = Files.lines(Paths.get(dataFile.getAbsolutePath()), StandardCharsets.UTF_8)) {
            dataFileStream.forEach(dataFileLine -> {
                int startLength = 0;
                StringJoiner csvData = new StringJoiner(",");
                for (ColumnInfo columnInfo : metadata.getMetaDataInfo()) {
                    String data;
                    try {
                        data = constructDataByType(columnInfo, StringUtils.trim(dataFileLine.substring(startLength,
                                startLength + columnInfo.getLength())));
                        csvData.add(data);
                    } catch (FixedFileFormatConverterException e) {
                        try {
                            //If an exception occur, we should delete the file created so that it can be started
                            //from scratch again
                            Files.deleteIfExists(Paths.get(csvFilePath));
                        } catch (IOException io) {
                            throw new FixedFileFormatConverterException(io);
                        }
                        String exceptionMessage = String.format("Exception occurred at line %s with exception %s",
                                dataFileLine, e);
                        throw new FixedFileFormatConverterException(exceptionMessage);
                    }
                    startLength = startLength + columnInfo.getLength();
                }

                CommonUtil.writeToFile(csvFilePath, csvData.toString());

            });
        } catch (IOException e) {
            throw new FixedFileFormatConverterException(e);
        }
    }

    private String constructDataByType(ColumnInfo columnInfo, String data) {
        String constructedData;
        switch (columnInfo.getDataType()) {
            case "date":
                constructedData = CommonUtil.constructDate(data);
                break;
            case "string":
                constructedData = data;
                break;
            case "numeric":
                CommonUtil.isValidNumber(data);
                constructedData = data;
                break;
            default:
                throw new FixedFileFormatConverterException("Unsupported data type");
        }
        return StringEscapeUtils.escapeCsv(constructedData);
    }

    private void writeColumnHeaders(Metadata metadata, String csvPath) {
        StringJoiner columnHeaders = new StringJoiner(",");
        for (ColumnInfo columnInfo : metadata.getMetaDataInfo()) {
            columnHeaders.add(columnInfo.getName());
        }
        CommonUtil.writeToFile(csvPath, columnHeaders.toString());
    }


}
