package com.example.tool.database.helper;




import com.example.tool.database.generator.model.Column;
import com.example.tool.database.generator.model.Table;
import com.example.tool.database.constant.Constant;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sunyifu
 * @ClassName: VelocityHelper
 * @Description: 生成器助手类
 * @date 2017年4月7日 下午5:55:02
 */
public class VelocityHelper {
    private String rootPath = VelocityHelper.class.getClassLoader()
            .getResource("").getFile()
            + "../../src/main";
    private String rootPath_java = rootPath + "/java";
    private String rootPath_webapp = rootPath + "/webapp/WEB-INF";

    //表名
    private String tableName;
    //去掉第一个下划线之前的
    private String tableNameI;
    //驼峰首字母大写
    private String tableNameII;
    //驼峰首字母小写
    private String tableNameIII;
    //生成文件名
    private String fileName;
    //生成文件路径
    private String filePath;
    //业务包路径
    private String projectName;
    //所在包路径
    private String packageName;
    private Table table;

    private VelocityEngine velocityEngine = null;
    private VelocityContext velocityContext = null;

    private static final String columnlist = "columnlist";

    public VelocityHelper() {
    }

    public VelocityHelper(String tableName, Table table,
                          String relativePath, String projectName) {
        this.tableName = tableName;
        //去掉t_user  t_
        this.tableNameI = StringHelper.toRequestMappingName(tableName);
        //首字母大写
        this.tableNameII = StringHelper.toEntityClassName(tableName);
        //首字母小写
        this.tableNameIII = StringHelper.underlineToCamel(tableName);
        this.filePath = rootPath_java + relativePath + "/";
        this.fileName = filePath + tableNameII;
        this.projectName = projectName;
        this.packageName = projectName + ".core";
        this.table = table;

        this.init();
    }

    /**
     * @return void
     * @throws
     * @Title: generateEntity
     * @Description: 生成实体类Java文件
     */
    public void GenerateEntity() {
        List<Column> columnlist = table.ConvertDataTableToList(table);
        for (Column data : columnlist) {
            if (!data.getUnderLineIdName().equals("")) {
                //因为主键名称不统一 所以动态传入
                velocityContext.put("id_name", data.getUnderLineIdName());
                if (!data.getUnderLineIdName().equals(""))
                    velocityContext.put("table_name", data.getUnderLineTableName());
                if (!data.getUnderLineIdName().equals(""))
                    velocityContext.put("db_name", data.getUnderLineDatabaseName());
            }
        }
        velocityContext.put("columnlist", columnlist);
        GenerateFile(Constant.GENERATE_SUFFIX_ENTITY, "/vm/entity.vm");
    }

    /**
     * 生成文件
     *
     * @param suffix       生成文件后缀 比如".java"
     * @param templatePath 模板路径
     */
    public void GenerateFile(String suffix, String templatePath) {
        Template t = velocityEngine.getTemplate(templatePath, "UTF-8");
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        PrintWriter pw = null;
        try {
            System.out.println(fileName + suffix);
            pw = new PrintWriter(fileName + suffix);
            t.merge(velocityContext, pw);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }

    }

    /**
     * 参数初始化
     */
    public void init() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        velocityContext = new VelocityContext();
        velocityContext.put("table_name", tableName);
        velocityContext.put("name", tableNameI);
        velocityContext.put("tableName", tableNameIII);
        velocityContext.put("TableName", tableNameII);
        velocityContext.put("projectName", projectName);
        velocityContext.put("packageName", packageName);
    }


    /**
     *   
     *
     * @Title: GenerateToService
     * @Description: 自动生成Service服务类文件
     */
    public void GenerateService() {
        velocityContext.put(columnlist, table.ConvertDataTableToList(table));
        GenerateFile(Constant.GENERATE_SUFFIX_SERVICE, "/vm/service.vm");
    }

    /**
     * @Title: GenerateToController
     * @Description: 自动生成Controller控制类文件
     */
    public void GenerateController() {
        velocityContext.put(columnlist, table.ConvertDataTableToList(table));
        GenerateFile(Constant.GENERATE_SUFFIX_CONTROLLER, "/vm/controller.vm");

    }

    /**
     * @Title: GenerateToController
     * @Description: 自动生成Controller控制类文件
     */
    public void GenerateJpa() {
        velocityContext.put(columnlist, table.ConvertDataTableToList(table));
        GenerateFile(Constant.GENERATE_SUFFIX_JPA, "/vm/jpa.vm");
    }

}
