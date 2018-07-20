package com.example.tool;


import com.example.tool.generator.thread.JpaFileThread;
import com.example.tool.helper.DbHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Generator {
    private static DbHelper helper = new DbHelper();
    /**
     * @param @param args   
     * @return void
     * @throws
     * @Title: main
     * @Description: 生成基础项目源码文件执行函数
     */
    public static void main(String[] args) throws Exception{


        //查询数据库中所有表名称
        helper.initConnection();
        List<String> tables = helper.getTableNames();
        int tableSize = tables.size();
        ExecutorService executor = Executors.newFixedThreadPool(tableSize);
        for(int i = 0; i < tableSize; i++) {
//            executor.submit(new DataDictThread(tables.get(i), helper, new PoiUtil(Constant.EXCEL_PATH)));
            executor.submit(new JpaFileThread(helper, tables.get(i)));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
        helper.closeConnection();

    }


}
