package com.example.tool.generator.model;


import com.example.tool.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class Table {
    List<String> headers;
    List<Row> rows;


    public int rowCount;
    public int columnCount;

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Table() {
    }

    public Table(List<Row> rows) {

        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public static void PrintTable(Table dt) {
        for (Row r : dt.getRows()) {
            for (KeyAndValue c : r.getKeyAndValues()) {
                System.out.print(c.getKey() + ":" + c.getValue() + "  ");
            }
            System.out.println("");
        }
    }

    public List<Column> ConvertDataTableToList(Table dt) {
        List<Column> list = null;
        if (dt.rowCount > 0) {

        }
        return list;
    }

    private void setTableInfo(Table table) {
        List<Column> list = new ArrayList<Column>();
        for (Row r : table.getRows()) {
            Column column = new Column();
            for (KeyAndValue keyAndValue : r.getKeyAndValues()) {
                String key = keyAndValue.getKey();
                setColumnInfo(key, keyAndValue, column);
            }

            list.add(column);
        }
    }

    private void setColumnInfo(String key, KeyAndValue keyAndValue, Column column) {
        switch (key) {
            case "DbName":
                setDatabaseName(keyAndValue, column);
                return;
            case "TableName":
                setTableName(keyAndValue, column);
                return;
            case "ColumnName":
                setColumnName(keyAndValue, column);
                return;
            case "Type":
                setColumnType(keyAndValue, column);
                return;
            case "Length":
                setColumnLength(keyAndValue, column);
                return;
            case "IsNullAble":
                setIsNullable(keyAndValue, column);
                return;
            case "Description":
                setComment(key, keyAndValue, column);
                return;
            case "ColumnKey":
                setPrimaryKey(keyAndValue, column);
                return;
            default:
                return;
        }
    }

    private void setDatabaseName(KeyAndValue keyAndValue, Column column) {
        if (keyAndValue.getValue() != null) {
            column.setUnderLineDatabaseName(keyAndValue.getValue().toString());
        }
    }

    private void setTableName(KeyAndValue keyAndValue, Column column) {
        if (keyAndValue.getValue() != null) {
            column.setUnderLineTableName(keyAndValue.getValue().toString());
        }
    }

    private void setColumnName(KeyAndValue keyAndValue, Column column) {
        column.setColumnName(StringHelper.underlineToCamel(keyAndValue.getValue().toString()));
        //转换首字符大写
        column.setUnderLineColumnName(StringHelper.toFirstCharUpperCase(StringHelper.underlineToCamel(keyAndValue.getValue().toString())));
        column.setPascalColumnName(keyAndValue.getValue().toString());
    }

    private void setColumnType(KeyAndValue keyAndValue, Column column) {
        //类型
        String type = keyAndValue.getValue().toString();
        if ("int".equals(type) || "smallint".equals(type) || "tinyint".equals(type)) {
            column.setType("Integer");
        } else if ("varchar".equals(type) || "text".equals(type) || "nvarchar".equals(type)) {
            column.setType("String");
        } else if ("datetime".equals(type) || "datetime2".equals(type) || "date".equals(type)) {
                            /*row.setType("Date");*/
            column.setType("String");
        } else if ("decimal".equals(type) || "float".equals(type) || "numeric".equals(type)) {
            column.setType("Double");
        } else if ("bigint".equals(type)) {
            column.setType("Long");
        }
    }

    private void setColumnLength(KeyAndValue keyAndValue, Column column) {
        try {
            //长度
            column.setLength(Integer.parseInt(keyAndValue.getValue().toString()));
        } catch (Exception e) {
            column.setLength(0);
        }
    }

    private void setIsNullable(KeyAndValue keyAndValue, Column column) {
        try {
            //可否为空( 0、不允许; 1、允许)
            column.setIsNullAble(Integer.parseInt(keyAndValue.getValue().toString()));
        } catch (Exception e) {
            column.setIsNullAble(1);
        }
    }

    private void setComment(String key, KeyAndValue keyAndValue, Column column) {
        //字段说明
        if (keyAndValue.getValue() != null) {
            column.setComment("" + keyAndValue.getValue().toString());
        } else {
            column.setComment("");
        }
    }

    private void setPrimaryKey(KeyAndValue keyAndValue, Column column) {
        //字段说明

        if (keyAndValue.getValue().equals("PRI")) {
            column.setIsPrimaryKey(1);
            column.setUnderLineIdName(column.getUnderLineColumnName());

        } else {
            column.setIsPrimaryKey(0);
        }
    }

}
