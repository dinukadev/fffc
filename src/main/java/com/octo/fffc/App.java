package com.octo.fffc;

import com.octo.fffc.domain.Metadata;
import com.octo.fffc.service.MetadataService;
import com.octo.fffc.service.ProcessDataFileService;
import com.octo.fffc.service.impl.MetadataServiceImpl;
import com.octo.fffc.service.impl.ProcessDataFileServiceImpl;

import java.io.File;

/**
 * This is the main class of the application.
 *
 * @author dinuka
 */
public class App {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage : fixed-file-format-converter-1.0-SNAPSHOT <meta data file path> <data file path> <csv file path>");
            System.exit(1);
        }
        MetadataService metadataService = new MetadataServiceImpl();
        Metadata metadata = metadataService.retrieveMetadata(new File(args[0]));
        ProcessDataFileService processDataFileService = new ProcessDataFileServiceImpl();
        processDataFileService.processDatafile(metadata, args[2], new File(args[1]));
    }
}
