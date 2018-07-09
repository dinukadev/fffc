package com.octo.fffc.domain;

/**
 * This class will hold the column information presented in the metadata file.
 *
 * @author dinuka
 */
public final class ColumnInfo {

    private String name;

    private Integer length;

    private String dataType;

    public ColumnInfo(String name, Integer length, String dataType) {
        this.name = name;
        this.length = length;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public String getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnInfo that = (ColumnInfo) o;

        if (!name.equals(that.name)) return false;
        if (!length.equals(that.length)) return false;
        return dataType.equals(that.dataType);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + length.hashCode();
        result = 31 * result + dataType.hashCode();
        return result;
    }
}
