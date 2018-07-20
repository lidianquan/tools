package com.example.tool.database.generator.model;

import java.io.Serializable;

public class Column implements Serializable{
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = -6622967944036145346L;

	/**
	 * 数据库名带下划线
	 */
	private String underLineDatabaseName = "";

	/**
	 * 表名带下划线
	 */
	private String underLineTableName= "";

	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 列名带下划线
	 */
	private String underLineColumnName= "";

	/**
	 * 列名首字符大写名称
	 */
	private String pascalColumnName;

	/**
	 * 主键名带下划线
	 */
	private String underLineIdName = "";

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 长度
	 */
	private int length;

	/**
	 * 是否可空（1:可为空;0:不可为空）
	 */
	private int isNullAble;

	/**
	 * 注释
	 */
	private String comment;

	/**
	 * 是否主键（1:是;0:否）
	 */
	private int isPrimaryKey;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUnderLineDatabaseName() {
		return underLineDatabaseName;
	}

	public void setUnderLineDatabaseName(String underLineDatabaseName) {
		this.underLineDatabaseName = underLineDatabaseName;
	}

	public String getUnderLineTableName() {
		return underLineTableName;
	}

	public void setUnderLineTableName(String underLineTableName) {
		this.underLineTableName = underLineTableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getUnderLineColumnName() {
		return underLineColumnName;
	}

	public void setUnderLineColumnName(String underLineColumnName) {
		this.underLineColumnName = underLineColumnName;
	}

	public String getPascalColumnName() {
		return pascalColumnName;
	}

	public void setPascalColumnName(String pascalColumnName) {
		this.pascalColumnName = pascalColumnName;
	}

	public String getUnderLineIdName() {
		return underLineIdName;
	}

	public void setUnderLineIdName(String underLineIdName) {
		this.underLineIdName = underLineIdName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getIsNullAble() {
		return isNullAble;
	}

	public void setIsNullAble(int isNullAble) {
		this.isNullAble = isNullAble;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(int isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
}
