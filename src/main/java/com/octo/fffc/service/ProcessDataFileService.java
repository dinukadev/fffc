package com.octo.fffc.service;

import com.octo.fffc.domain.Metadata;

import java.io.File;

/**
 * This interface defines functionality to process the CSV data file.
 *
 * @author dinuka
 */
public interface ProcessDataFileService {

    /**
     * This method will process the data file using the metadata information passed in.
     *
     * @param metadata    an instance of {@link Metadata} which contains the metadata information.
     * @param csvFilePath the path to write the csv file to.
     * @param dataFile    the data file
     */
    void processDatafile(Metadata metadata, String csvFilePath, File dataFile);
}
