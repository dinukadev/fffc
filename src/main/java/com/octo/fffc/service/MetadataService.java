package com.octo.fffc.service;

import com.octo.fffc.domain.Metadata;

import java.io.File;

/**
 * This interface defines the functionality exposed for processing the metadata information.
 *
 * @author dinuka
 */
public interface MetadataService {

    /**
     * This method will process the metadata file and construct the ${@link Metadata} domain which will be used within the application.
     *
     * @param metadataFile the metadata file
     * @return an instance of {@link Metadata}
     */
    Metadata retrieveMetadata(File metadataFile);
}
