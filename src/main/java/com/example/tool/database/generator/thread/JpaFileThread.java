package com.example.tool.database.generator.thread;

import com.example.tool.database.generator.model.Table;
import com.example.tool.database.helper.DbHelper;
import com.example.tool.database.helper.VelocityHelper;
import com.example.tool.database.constant.Constant;

/**
 * Created by lidianquan on 2018/7/12.
 */
public class JpaFileThread implements Runnable {

    private DbHelper helper;
    private String tableName;

    public JpaFileThread(DbHelper helper, String tableName) {
        this.helper = helper;
        this.tableName = tableName;
    }

    @Override
    public void run() {
        String sql = "select * from (select COLUMN_NAME as ColumnName," +
                " DATA_TYPE as Type,IS_NULLABLE as IsNullAble, " +
                " CHARACTER_MAXIMUM_LENGTH as Length,COLUMN_COMMENT as Description ," +
                " COLUMN_KEY as ColumnKey ," +
                " table_schema as DbName ," +
                " table_name as TableName " +
                "from information_schema.columns  where table_schema = '" +
                Constant.GENERATE_DATABASENAME + "' and table_name = '" + tableName +
                "')k";

        Table table = helper.getTableDes(sql, null);
        int count = table.getRowCount();
        System.out.println("在表名 " + tableName + " 中发现 " + count + " 个字段……");

        //自动生成实体类
        new VelocityHelper(tableName, table,
                Constant.GENERATE_CARBON + "/" + Constant.GENERATE_PACKAGENAME + "/domain",
                Constant.GENERATE_PROJECTNAME).GenerateEntity();
        //自动生成jpa接口文件
        new VelocityHelper(tableName, table,
                Constant.GENERATE_CARBON + "/" + Constant.GENERATE_PACKAGENAME + "/repository",
                Constant.GENERATE_PROJECTNAME).GenerateJpa();
        //自动生成Service服务类文件
        new VelocityHelper(tableName, table,
                Constant.GENERATE_CARBON + "/" + Constant.GENERATE_PACKAGENAME + "/service",
                Constant.GENERATE_PROJECTNAME).GenerateService();
        //自动生成Controller控制类文件
        new VelocityHelper(tableName, table,
                Constant.GENERATE_CARBON + "/" + Constant.GENERATE_PACKAGENAME + "/controller",
                Constant.GENERATE_PROJECTNAME).GenerateController();

    }

}
