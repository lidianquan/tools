package com.example.tool.database.constant;

/**
 * Created by if on 2017/12/1.
 */
public class Constant {
    public static final String GENERATE_SUFFIX_ENTITY = "Entity.java";
    public static final String GENERATE_SUFFIX_SERVICE = "Service.java";
    public static final String GENERATE_SUFFIX_JPA = "Repository.java";
    public static final String GENERATE_SUFFIX_CONTROLLER = "Controller.java";

    // #
    public static final String GENERATE_DATABASENAME = "analysis";
    //副本目录
    public static final String GENERATE_CARBON = "/carbon";
    //项目别名
    public static final String GENERATE_PROJECTNAME = "com.example.tool";
    //项目别名
    public static final String GENERATE_PACKAGENAME = GENERATE_PROJECTNAME + ".modules";

    public static final String EXCEL_PATH = "G:\\项目\\数据分析\\数据库文件\\表结构信息";



    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://192.168.1.40:6808/"+GENERATE_DATABASENAME+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String JDBC_USERNAME = "root";
    public static final String JDBC_PASSWORD = "123456";





}
