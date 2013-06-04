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
	
	private static String SQL_MAP_DB_TYPE_ORACLE = "SQL_MAP_ORACLE";
	private static String SQL_MAP_DB_TYPE_MYSQL = "SQL_MAP_MYSQL";
	
	public static String SQL_MAP_DB_TYPE = SQL_MAP_DB_TYPE_ORACLE;

	
	private static Map sqlmapContainer ; 

	static {
		
		String resource = "oracle-sqlmap-config.xml";
		
		try {
			sqlmapContainer = new HashMap();
			SQL_MAP_DB_TYPE = ResourceBundle.getBundle("sapphire_db").getString("db.type");
			if( SQL_MAP_DB_TYPE.equals( SQL_MAP_DB_TYPE_MYSQL ) ) {
				resource = "mysql/mysql-sqlmap-config.xml";
			} else {
				resource = "oracle-sqlmap-config.xml";
			}
			System.out.println("\n\n\n############# System Uses [[" + SQL_MAP_DB_TYPE + "]] ###############\n\n\n");
			
		} catch (Exception e) {
			System.out.println("\n\n\n----------------------------- Info : Cannot Find sapphire.db.type properties!! -----------------------------" +
					"\n############# System uses Default Oracle Database ###############\n\n\n");
		} finally {
			registerSQLMapClient(resource);
		}
		
		
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