package com.example.tool.helper;

import com.example.tool.generator.model.KeyAndValue;
import com.example.tool.generator.model.Row;
import com.example.tool.generator.model.Table;
import com.example.tool.generator.model.ColumnDesc;
import com.example.tool.constant.Constant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    /**
     * 数据库连接对象
     */
    private Connection connection = null;


    /**
     * 获取数据库连接对象
     *
     * @param username 数据库账号
     * @param password  数据库密码
     * @return
     */
    private void initConnection(String username, String password) {
        if (connection != null) {
            return;
        }
        try {
            Class.forName(Constant.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constant.JDBC_URL, username, password);
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }

    public void initConnection() {
        initConnection(Constant.JDBC_USERNAME, Constant.JDBC_PASSWORD);
    }


    /**
     * 获取表信息
     *
     * @param sql
     * @param objParams
     * @return
     */
    public Table getTableDes(String sql, Object... objParams) {
        Table table = null;
        String key;
        Object value;
        KeyAndValue keyAndValue = null;
        List<KeyAndValue> keyAndValues;
        List<Row> rows = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<String> metaDataNames = getMetaDataNames(rs);
            int rowCount = 0;
            while(rs.next()) {
                rowCount++;
                keyAndValues = new ArrayList<>();
                for(int i = 1; i <= metaDataNames.size(); i++) {
                    key = metaDataNames.get(i - 1);
                    value = rs.getObject(i);
                    keyAndValue = new KeyAndValue(key, value);
                    keyAndValues.add(keyAndValue);
                }
                Row row = new Row(keyAndValues);
                rows.add(row);
            }

            table = new Table(rows);
            table.setColumnCount(metaDataNames.size());
            table.setRowCount(rowCount);
            closeResource(rs, ps);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 获取表字段描述信息
     *
     * @param tableName
     * @return
     */
    public List<ColumnDesc> getTableDesc(String tableName) {
        String sql = "select COLUMN_NAME,COLUMN_TYPE,IS_NULLABLE,COLUMN_KEY,COLUMN_COMMENT " +
                "from information_schema.columns " +
                "where TABLE_SCHEMA='analysis' and TABLE_NAME='" + tableName + "'";
        List<ColumnDesc> columnDescs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ColumnDesc columnDesc = new ColumnDesc();
                columnDesc.setName(resultSet.getString(1));
                columnDesc.setDataType(resultSet.getString(2));
                columnDesc.setIsNull(resultSet.getString(3));
                columnDesc.setPrimaryKey(resultSet.getString(4));
                columnDesc.setComment(resultSet.getString(5));
                columnDescs.add(columnDesc);
            }
            closeResource(resultSet, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnDescs;
    }

    private List<String> getMetaDataNames(ResultSet rs) throws SQLException{

        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> metaDataNames = new ArrayList<>();
        for(int i = 1; i < rsmd.getColumnCount(); i++) {
            metaDataNames.add(rsmd.getColumnName(i));
        }
        return metaDataNames;
    }


    public List<String> getTableNames() {
        //查询数据库信息语句
        String sql = "SHOW TABLES";
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> tableList = null;
        try {
            statement = connection.createStatement();
            //结果集
            resultSet = statement.executeQuery(sql);
            //数据表名列表
            tableList = new ArrayList<String>();
            while (resultSet.next()) {
                //将数据表名添加到数据表名列表中
                tableList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //关闭连接
        closeResource(resultSet, statement);
        //返回结果
        return tableList;
    }



    /**
     * 设置SQL执行参数
     *
     * @param ps
     * @param objParams
     * @throws SQLException
     */
    private void setQueryParam(PreparedStatement ps, Object... objParams) throws SQLException{
        if (objParams != null) {
            for (int i = 0; i < objParams.length; i++) {
                ps.setObject(i + 1, objParams[i]);
            }
        }
    }



    private void closeResource(ResultSet rs, Statement stmt) {
        try {
            if(null != rs) {
                rs.close();
            }
            if(null != stmt) {
                stmt.close();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResource(ResultSet rs, PreparedStatement ps) {
        try {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if(null != connection || !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
