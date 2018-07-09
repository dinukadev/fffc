package com.octo.fffc.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * This class will hold the metadata information.
 *
 * @author dinuka
 */
public final class Metadata {

    private List<ColumnInfo> metaDataInfo = new LinkedList<>();

    public List<ColumnInfo> getMetaDataInfo() {
        return metaDataInfo;
    }

    public void addMetadata(ColumnInfo columnInfo) {
        this.metaDataInfo.add(columnInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metadata metadata = (Metadata) o;

        return metaDataInfo.equals(metadata.metaDataInfo);
    }

    @Override
    public int hashCode() {
        return metaDataInfo.hashCode();
    }
}
