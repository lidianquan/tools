package com.example.tool.database.generator.thread;

import com.example.tool.database.generator.model.ColumnDesc;
import com.example.tool.database.helper.DbHelper;
import com.example.tool.database.generator.utils.PoiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lidianquan on 2018/7/12.
 */
public class DataDictThread implements Runnable{

    private String tableName;
    private DbHelper helper;
    PoiUtil poiUtil;

    private final List<String> header = new ArrayList<String>(){
        {
            add("列名");
            add("数据类型");
            add("是否为空");
            add("主键");
            add("备注");
        }
    };

    public DataDictThread(String tableName, DbHelper helper, PoiUtil poiUtil) {
        this.tableName = tableName;
        this.helper = helper;
        this.poiUtil = poiUtil;
    }

    @Override
    public void run() {
        System.out.println("写入【" + tableName + "】表信息");
        List<List<String>> rows = new ArrayList<>();
        List<ColumnDesc> columnDescs = helper.getTableDesc(tableName);
        rows.add(header);
        for(ColumnDesc columnDesc : columnDescs) {
            List<String> row = new ArrayList<>();
            row.add(columnDesc.getName());
            row.add(columnDesc.getDataType());
            row.add(columnDesc.getIsNull());
            row.add(columnDesc.getPrimaryKey());
            row.add(columnDesc.getComment());
            rows.add(row);
        }
        poiUtil.writeExcel(rows, tableName);
    }


}
