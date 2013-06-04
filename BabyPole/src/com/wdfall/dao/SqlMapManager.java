package com.wdfall.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

@SuppressWarnings("unchecked")
public class SqlMapManager {
	
	private static String SQL_MAP_DB_TYPE_MYSQL = "SQL_MAP_MYSQL";
	
	public static String SQL_MAP_DB_TYPE = SQL_MAP_DB_TYPE_MYSQL;

	
	private static Map sqlmapContainer ; 

	static {
		
		String resource = "mysql-sqlmap-config.xml";
		sqlmapContainer = new HashMap();
		registerSQLMapClient(resource);
	}
	
	public static void registerSQLMapClient(String resource) {
		try {
			Reader reader = Resources.getResourceAsReader (resource);
			sqlmapContainer.put( SQL_MAP_DB_TYPE, SqlMapClientBuilder.buildSqlMapClient(reader));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static SqlMapClient getSqlMapInstance(String mapName) {		
		return (SqlMapClient ) sqlmapContainer.get(mapName ) ;
	}
	
	
}