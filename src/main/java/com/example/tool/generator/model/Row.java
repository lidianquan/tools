package com.example.tool.generator.model;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

public class Row {
    private List<KeyAndValue> keyAndValues;

    public Row(List<KeyAndValue> keyAndValues) {
        this.keyAndValues = keyAndValues;
    }

    public List<KeyAndValue> getKeyAndValues() {
        return keyAndValues;
    }

    public void setKeyAndValues(List<KeyAndValue> keyAndValues) {
        this.keyAndValues = keyAndValues;
    }

    public KeyAndValue getColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return keyAndValue;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return null;
    }

    public int getIntColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return Integer.parseInt(keyAndValue.getValue().toString());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return 0;
    }

    public String getStringColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return keyAndValue.getValue().toString();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return "";
    }

    public Date getDateColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return Date.valueOf(keyAndValue.getValue().toString());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return null;
    }

    public Blob getBlobColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return (Blob) keyAndValue.getValue();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return null;
    }

    public float getFloatColumn(String columnName) {
        for (KeyAndValue keyAndValue : keyAndValues) {
            if (keyAndValue.getKey().toUpperCase().equals(columnName.toUpperCase())) {
                try {
                    return Float.parseFloat(keyAndValue.getValue().toString());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return 0;
    }

}
