package com.wdfall.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wdfall.utils.Day;

@SuppressWarnings("unchecked")
public class DaoHelper {

	private static final Logger logger = Logger.getLogger(DaoHelper.class);


	@SuppressWarnings("unused")
	private boolean isTransactionStarted = false;




	/**
	 * 기본적인 데이터의 입력을 담당한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵 또는 any type object
	 * @return int 데이터가 제대로 입력 되었다면 입력된 키값을
	 * (mysql 의 경우 auto increament 또는 oracle 일경우에는 next sequence)
	 * 반드시 query 설정 파일에 selectKey 프로퍼티 설정이 되어 있어야 한다. 그렇지 않으면
	 * 해당 메서드에서 nullPointException 을 발생하고 transaction 은 rollback 처리 된다.
	 *
	 * 반환값이 없는 insert 구문을 사용하려면 createTrivia 메서드를 사용한다.
	 *
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, Object paramMap) throws SQLException {

		if (logger.isInfoEnabled()) {
			logger.info("create(String, Map) - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("create(String, Map) - start - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - start transaction ");
			}
			Integer resultObj = (Integer)sqlMap.insert(queryKeyName, paramMap); 
			result = resultObj==null?-1:resultObj.intValue();
			
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - commit transaction the result is = " + result);
			}
		} catch(SQLException e) {
			logger.fatal("create(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		} finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - end transaction create result is = " + result);
			}
		}

		if(result < 0) {
			logger.debug("create(String, Map) - create result value " + result);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("create(String, Map) - end - return value  or primary key =" + result);
		}
		return result;

	}

	/**
	 * 반환값 없이, 기본적인 데이터의 입력을 담당한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵 또는 any type object
	 * @return 반환값이 없다.
	 *
	 * (mysql 의 경우 auto increament 또는 oracle 일경우에는 next sequence)
	 * 반드시 query 설정 파일에 selectKey 프로퍼티 설정이 되어 있어야 한다. 그렇지 않으면
	 * 해당 메서드에서 nullPointException 을 발생하고 transaction 은 rollback 처리 된다.
	 *
	 * @throws SQLException
	 */
	public static void createTrivia(String dbName, String queryKeyName, Object paramMap) throws SQLException {

		if (logger.isInfoEnabled()) {
			logger.info("create(String, Map) - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("create(String, Map) - start - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - start transaction ");
			}
			sqlMap.insert(queryKeyName, paramMap);

			sqlMap.commitTransaction();

		} catch(SQLException e) {
			logger.fatal("create(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		} finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - end transaction create ");
			}
		}

	}

	/**
	 * 기본적인 데이터의 입력을 담당한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵 또는 any type object
	 * @return int 데이터가 제대로 입력 되었다면 입력된 키값을
	 * (mysql 의 경우 auto increament 또는 oracle 일경우에는 next sequence)
	 * 반드시 query 설정 파일에 selectKey 프로퍼티 설정이 되어 있어야 한다. 그렇지 않으면
	 * 해당 메서드에서 nullPointException 을 발생하고 transaction 은 rollback 처리 된다.
	 *
	 * 반환값이 없는 insert 구문을 사용하려면 createTrivia 메서드를 사용한다.
	 *
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, Object paramMap, boolean autoTransaction) throws SQLException {

		if (logger.isInfoEnabled()) {
			logger.info("create(String, Map) - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("create(String, Map) - start - queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}
		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			if(autoTransaction) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - start transaction ");
			}
			Integer resultObj = (Integer)sqlMap.insert(queryKeyName, paramMap); 
			result = resultObj==null?-1:resultObj.intValue();

			if(autoTransaction) {
				sqlMap.commitTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - commit transaction the result is = " + result);
			}
		} catch(SQLException e) {
			logger.fatal("create(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		} finally {
			if(autoTransaction) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("create(String, Map) - end transaction create result is = " + result);
			}
		}

		if(result < 0) {
			logger.debug("create(String, Map) - create result value " + result);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("create(String, Map) - end - return value  or primary key =" + result);
		}
		return result;

	}

	/**
	 *  여러개의 데이터 삽입
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, Object[] paramMaps) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramMaps);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			sqlMap.startBatch();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start batch ");
			}
			for(int i = 0; i < paramMaps.length; i++) {

				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramMaps[i]+" ***** " );
				}
				sqlMap.insert(queryKeyName, paramMaps[i]);
			}
			result = sqlMap.executeBatch();
			if(result > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** created row count is "+result+" batch script excuete successfully without any Exceptions or Errors ***** ");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - commit transaction the result is = " + result);
			}

		}catch(SQLException e) {
			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		return result;
	}



	/**
	 *  여러개의 데이터 삽입
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, Object[] paramMaps, boolean autoTransaction) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramMaps);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			if(autoTransaction) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			sqlMap.startBatch();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start batch ");
			}
			for(int i = 0; i < paramMaps.length; i++) {

				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramMaps[i]+" ***** " );
				}
				sqlMap.insert(queryKeyName, paramMaps[i]);
			}
			result = sqlMap.executeBatch();
			if(result > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** created row count is "+result+" batch script excuete successfully without any Exceptions or Errors ***** ");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			if(autoTransaction) {
				sqlMap.commitTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - commit transaction the result is = " + result);
			}

		}catch(SQLException e) {
			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			if(autoTransaction) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		return result;
	}


	/**
	 *  여러개의 데이터 삽입
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, List paramObj) throws SQLException {

		
		long maxMillis = 1500L;
				
		long startTime = System.currentTimeMillis();
		
		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramObj);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			sqlMap.startBatch();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start batch ");
			}
			for(int i = 0; i < paramObj.size(); i++) {
				
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramObj.get(i)+" ***** " );
				}
				sqlMap.insert(queryKeyName, paramObj.get(i));
			}
			result = sqlMap.executeBatch();
			if(result > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** created row count is "+result+" batch script excuete successfully without any Exceptions or Errors ***** ");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - commit transaction the result is = " + result);
			}

		}catch(SQLException e) {
			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		
		long endTime = System.currentTimeMillis();
		
		long gap = endTime - startTime;
		
		if( gap > maxMillis ) {
			String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
			String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
			String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
						" \n start time : " + startTimestamp + " end time : " + endTimestamp + 
						" startmillis " + startTime + " endmillis " + endTime + " data size : " + paramObj.size();
			logger.warn( msg );
		}
		
		return result;
	}
	
	
	/**
	 *  여러개의 데이터 삽입
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, List paramObj, int maxCursorCount, boolean autoTransaction) throws SQLException {

		
		long maxMillis = 1500L;
		
		long startTime = System.currentTimeMillis();
		
		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramObj);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			
			if( autoTransaction ) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			
			
			sqlMap.startBatch();			
			int cursor = 0;
			int dataSize = paramObj.size();
			
			for(int i = 0; i < paramObj.size(); i++) {
				
				cursor++;
				
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramObj.get(i)+" ***** " );
				}
				
				boolean overed = maxCursorCount - 2 < cursor; 
				
				sqlMap.insert(queryKeyName, paramObj.get(i));
				
				if( overed ) {
					
					result = sqlMap.executeBatch();
					
					if( dataSize > cursor ) { //데이터가 더 있다면... 다시 배치 시작
						sqlMap.startBatch();
					}
					logger.info("data size is over than " + maxCursorCount + " data size is " + paramObj.size() + " current cursor : " + cursor );
					cursor = 0;
					
				}
				
			}
			
			boolean existBatch = cursor > 0 ; 
			if( existBatch ){
				result = sqlMap.executeBatch();				
			}

			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			
			long endTime = System.currentTimeMillis();
			
			long gap = endTime - startTime;
			
			if( gap > maxMillis ) {
				String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
				String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
				String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
							" \n start time : " + startTimestamp + " end time : " + endTimestamp+ 
							" startmillis " + startTime + " endmillis " + endTime + " data size : " + paramObj.size(); 
							
//				System.out.println(msg);
				logger.warn( msg );
			}
			
			if( autoTransaction ) {
				sqlMap.commitTransaction();
			}
			

		}catch(SQLException e) {			

			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			
			if( autoTransaction ) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		return result;
	}
	
	/**
	 *  여러개의 데이터 삽입
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String dbName, String queryKeyName, List paramObj, int maxCursorCount) throws SQLException {

		
		long maxMillis = 1500L;
		
		long startTime = System.currentTimeMillis();
		
		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramObj);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			
			
			sqlMap.startBatch();
			int cursor = 0;
			int dataSize = paramObj.size();
			
			for(int i = 0; i < paramObj.size(); i++) {
				
				cursor++;
				
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramObj.get(i)+" ***** " );
				}
				
				boolean overed = maxCursorCount - 2 < cursor; 
				
				sqlMap.insert(queryKeyName, paramObj.get(i));
				
				if( overed ) {
					
					result = sqlMap.executeBatch();
					
					if( dataSize > cursor ) { //데이터가 더 있다면... 다시 배치 시작
						sqlMap.startBatch();
					}
					logger.info("data size is over than " + maxCursorCount + " data size is " + paramObj.size() + " current cursor : " + cursor );
					cursor = 0;
					
				}
				
			}
			
			boolean existBatch = cursor > 0 ; 
			if( existBatch ){
				result = sqlMap.executeBatch();				
			}

			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			
			long endTime = System.currentTimeMillis();
			
			long gap = endTime - startTime;
			
			if( gap > maxMillis ) {
				String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
				String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
				String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
							" \n start time : " + startTimestamp + " end time : " + endTimestamp+ 
							" startmillis " + startTime + " endmillis " + endTime + " data size : " + paramObj.size(); 
							
//				System.out.println(msg);
				logger.warn( msg );
			}
			
			sqlMap.commitTransaction();
			

		}catch(SQLException e) {			

			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		return result;
	}
	
	
	
	/**
	 *  여러개의 데이터 삽입
	 *  Transaction 이 필요 없는 상태의 인서트
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static int insertWithNoTrx(String dbName, String queryKeyName, List paramObj, int maxCursorCount) throws SQLException {

		
		long maxMillis = 1500L;
		long startTime = System.currentTimeMillis();
		
		logger.debug("createMultiple(String, String[]) - start");
		logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramObj);

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			
			logger.info("createMultiple(String, Map) - start transaction ");
			sqlMap.startBatch();			
			int cursor = 0;
			int dataSize = paramObj.size();
			
			for(int i = 0; i < paramObj.size(); i++) {
				
				cursor++;
				
				logger.debug(" ***** batch script excuete [" + i + "] statement and " +
						"the paramMap is "+paramObj.get(i)+" ***** " );
				
				boolean overed = maxCursorCount - 2 < cursor; 
				
				sqlMap.insert(queryKeyName, paramObj.get(i));
				
				if( overed ) {
					
					result = sqlMap.executeBatch();
					
					if( dataSize > cursor ) { //데이터가 더 있다면... 다시 배치 시작
						sqlMap.startBatch();
					}
					logger.info("data size is over than " + maxCursorCount + " data size is " + paramObj.size() + " current cursor : " + cursor );
					cursor = 0;
					
				}
				
			}
			
			boolean existBatch = cursor > 0 ; 
			if( existBatch ){
				result = sqlMap.executeBatch();				
			}

			logger.info("createMultiple(String, Map) - end batch ");
			long endTime = System.currentTimeMillis();
			long gap = endTime - startTime;
			
			if( gap > maxMillis ) {
				String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
				String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
				String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
							" \n start time : " + startTimestamp + " end time : " + endTimestamp+ 
							" startmillis " + startTime + " endmillis " + endTime + " data size : " + paramObj.size(); 
							
//				System.out.println(msg);
				logger.warn( msg );
			}

		}catch(SQLException e) {			
			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
		}

		logger.debug("createMultiple(String, Map) - end");
		return result;
	}
	
	
	/**
	 *  
	 *  여러개의 데이터 삽입
	 *  
	 *  update 된 seq 값을 리스트로 받아오기 때문에
	 *  단건씩 커밋 하는 로직을 사용한다.
	 *  
	 *  Mysql 에서는 SELECT LAST_INSERT_ID() 을 사용해서 seq 를 받아오기 때문에..
	 *  startBatch 로 batch update 할 경우 seq 값을 제대로 못 받아옴
	 *  
	 *  대용량의 데이터 처리로는 부적합하다....
	 *  
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 입력 되어지는 파리미터들의 Map 배열
	 * @return
	 * @throws SQLException
	 */
	public static List inserts(String dbName, String queryKeyName, List paramObj) throws SQLException {

		
		long maxMillis = 1500L;
		
		long startTime = System.currentTimeMillis();
		List resultList = new ArrayList(); 
		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("createMultiple(String, Map) - queryKeyName=" + queryKeyName + ", paramMaps=" + paramObj);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbName);
			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - start transaction ");
			}
			
			int cursor = 0;
			int dataSize = paramObj.size();
			
			for(int i = 0; i < paramObj.size(); i++) {
				
				cursor++;
				
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the paramMap is "+paramObj.get(i)+" ***** " );
				}
				
				sqlMap.startTransaction();
				int updatedSeq = (Integer)sqlMap.insert(queryKeyName, paramObj.get(i));
				sqlMap.commitTransaction();
				sqlMap.endTransaction();
				
				Map resultMap = new HashMap();
				resultMap.put("param", paramObj.get(i));
				resultMap.put("seq", updatedSeq);
				resultList.add(resultMap);
				
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple(String, Map) - end batch ");
			}
			
			long endTime = System.currentTimeMillis();
			
			long gap = endTime - startTime;
			
			if( gap > maxMillis ) {
				String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
				String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
				String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
							" \n start time : " + startTimestamp + " end time : " + endTimestamp+ 
							" startmillis " + startTime + " endmillis " + endTime + " data size : " + paramObj.size(); 
							
//				System.out.println(msg);
				logger.warn( msg );
			}
			
			//sqlMap.commitTransaction();
			

		}catch(SQLException e) {			

			logger.fatal("createMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createMultiple(String, Map) - end");
		}
		return resultList;
	}
	
	/**
	 * 트랜잭션을 수동으로 설정하고 시작한다.
	 * insert 메서드를 사용할때 autoTransaction 을 false 로 설정하고
	 * 사용해야 한다.
	 *
	 * @param dbName
	 */
	public static void startTransaction(String dbName) {

		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbName);

		try {
			sqlMap.startTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 트랜잭션을 수동으로 설정하고 시작한 후에
	 * insert 메서드 autoTransaction 을 false 로 설정하고
	 * 하고 이 메서드를 사용한다.
	 *
	 * @param dbName
	 */
	public static void endTransaction(String dbName) {

		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbName);

		try {
			sqlMap.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 트랜잭션을 수동으로 설정하고 시작한 후에
	 * insert 메서드 autoTransaction 을 false 로 설정하고
	 * 하고 이 메서드를 사용한다.
	 *
	 * @param dbName
	 */
	public static void commitTransaction(String dbName) {

		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbName);

		try {
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 데이터를 삭제한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int 데이터의 삭제가 정상적으로 처리  되었다면 데이터를 삭제한 row count 그렇지 않다면 -1 을 리턴한다.
	 *
	 * @throws SQLException
	 */
	public static int delete(String dbname, String queryKeyName, Object paramMap) throws SQLException {

		if (logger.isInfoEnabled()) {
			logger.info("delete(String, Map) - queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteSingle(String, Map) - start");
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - start transaction ");
			}
			result = sqlMap.delete(queryKeyName, paramMap);
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("delete(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("delete(String, Map) - end");
		}
		return result;
	}
	
	
	public static int delete(String dbname, String queryKeyName) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("deleteSingle(String, Map) - start");
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - start transaction ");
			}
			result = sqlMap.delete(queryKeyName);
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("delete(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("delete(String, Map) - end");
		}
		return result;
	}
	
	
	public static int delete(String dbname, String queryKeyName, Object paramMap, boolean autoTransaction) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("deleteSingle(String, Map) - start");
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			if( autoTransaction ) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - start transaction ");
			}
			result = sqlMap.delete(queryKeyName, paramMap);
			
			if( autoTransaction ) {
				sqlMap.commitTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("delete(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			if( autoTransaction ) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("delete(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("delete(String, Map) - end");
		}
		return result;
	}

	/**
	 * 다중 항목의 삭제를 처리한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 지워지는 데이터의 식별자 배열
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int delete(String dbname, String queryKeyName, Object[] keyArray) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("deleteMultiple(String, String[]) - start");
		}

		if (logger.isInfoEnabled()) {
			logger.info("deleteMultiple(String, Map) - queryKeyName=" + queryKeyName + ", keyArray=" + keyArray);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - start transaction ");
			}
			sqlMap.startBatch();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - start batch ");
			}
			for(int i = 0; i < keyArray.length; i++) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the primary key is "+keyArray[i]+" ***** " );
				}
				sqlMap.delete(queryKeyName, keyArray[i]);
			}
			result = sqlMap.executeBatch();
			if(result > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** deleted row count is "+result+" batch script excuete successfully without any Exceptions or Errors ***** ");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end batch ");
			}
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - commit transaction the result is = " + result);
			}

		}catch(SQLException e) {
			logger.fatal("deleteMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("deleteMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteMultiple(String, Map) - end");
		}
		return result;
	}




	/**
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return List 결과물이 없다면 size가 0 인 List 를 반환한다.
	 * @throws SQLException
	 */
	public static List getList(String dbname, String queryKeyName, Object paramMap) throws SQLException {

		long maxMillis = 1500L;
		
		long startTime = System.currentTimeMillis();

		if (logger.isDebugEnabled()) {
			logger.debug("getList(String, Map) - start queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}
		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbname);

//		List list = null;
//		list = sqlMap.queryForList(queryKeyName, paramMap);		
		HugeListRowHandler resultHandler = new HugeListRowHandler() ;		
		sqlMap.queryWithRowHandler(queryKeyName, paramMap, resultHandler);
		List list = resultHandler.getResultList() ;
		
		
		
		if(list == null) {
			list = new ArrayList();
			logger.debug("getList(String, Map) - the list is null for query " + queryKeyName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getList(String, Map) - end - return value=");
		}
		
		long endTime = System.currentTimeMillis();
		
		long gap = endTime - startTime;
		
		if( gap > maxMillis ) {
			String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
			String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
			String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
						" \n start time : " + startTimestamp + " end time : " + endTimestamp + 
						" startmillis " + startTime + " endmillis " + endTime;
//			System.out.println(msg);
			logger.warn( msg );
		}
		
		return list;
	}


	/**
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return List 결과물이 없다면 size가 0 인 List 를 반환한다.
	 * @throws SQLException
	 */
	public static List getList(String dbname, String queryKeyName) throws SQLException {
		
		long maxMillis = 1500L;
		
		long startTime = System.currentTimeMillis();
		
		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbname);
//		List list = null;
//		list = sqlMap.queryForList(queryKeyName);
		HugeListRowHandler resultHandler = new HugeListRowHandler() ;
		sqlMap.queryWithRowHandler(queryKeyName, resultHandler);
		List list = resultHandler.getResultList() ;

		
		if(list == null) {
			list = new ArrayList();
			logger.debug("getList(String, Map) - the list is null for query " + queryKeyName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getList(String, Map) - end - return value=");
		}
		
		long endTime = System.currentTimeMillis();
		
		long gap = endTime - startTime;
		
		if( gap > maxMillis ) {
			String startTimestamp = Day.toStringAsyyyyMMddHHmmss( startTime );
			String endTimestamp = Day.toStringAsyyyyMMddHHmmss( endTime );
			String msg = " ************************ duration is over than " + maxMillis + " \n QUERY KEY : " + queryKeyName + " gap " + gap + 
						" \n start time : " + startTimestamp + " end time : " + endTimestamp  + 
						" startmillis " + startTime + " endmillis " + endTime;
			//System.out.println(msg);
			logger.warn( msg );
		}
		
		
		return list;
	}



	/**
	 *
	 * 데이터 맵을 반환한다. Map 의 키값은 해당 query result 의 Field 의 이름이다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return Map 결과물이 없다면 size가 0 인 Map 를 반환한다.
	 * @throws SQLException
	 */
	public static Map getMap(String dbname, String queryKeyName, Object param) throws SQLException  {

		if (logger.isDebugEnabled()) {
			logger.debug("getMap(String, Map) - start queryKeyName=" + queryKeyName +
					", paramMap=" + param);
		}

		SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance(dbname);
		Map map = null;
		map = (Map)sqlMap.queryForObject(queryKeyName, param);
		if(map == null) {
			map = new HashMap();
			logger.debug("getMap(String, Map) - queryKeyName=" + queryKeyName + " the result map is null ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMap(String, Map) - end - return value=" + map);
		}
		return map;
	}



	/**
	 *
	 * Object를 반환한다. Object 의 요소는  query statement 의 resultClass 이다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param param 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 String
	 * @return Object 결과물이 없다면 null을 반환한다.
	 * @throws SQLException
	 */
	public static Object getObject(String dbname, String queryKeyName, Object param) throws SQLException  {


		if (logger.isDebugEnabled()) {
			logger.debug("getObject(String, String) - start queryKeyName=" + queryKeyName +
					", param=" + param);
		}
		SqlMapClient sqlMap = null;
		Object obj = null;
		try{
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			obj = sqlMap.queryForObject(queryKeyName, param);
			if(obj == null) {
				logger.debug("getObject(String, String) - queryKeyName=" + queryKeyName +
				" the result map is null ");
			}
		}catch(SQLException e) {
			logger.error("getObject(String, String) - exception ignored", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getObject(String, String) - end - return class is " + obj);
		}
		return obj;
	}


	/**
	 *
	 * 단일 항목의 데이터를 수정한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int update(String dbname, String queryKeyName, Object paramMap) throws SQLException {


		if (logger.isInfoEnabled()) {
			logger.info("modify(String, Map) - queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - start queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - start transaction ");
			}
			result = sqlMap.update(queryKeyName, paramMap);
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("modify(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - end result=" + result);
		}
		return result;
	}

	/**
	 *
	 * 단일 항목의 데이터를 수정한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int update(String dbname, String queryKeyName) throws SQLException {


		if (logger.isInfoEnabled()) {
			logger.info("modify(String, Map) - queryKeyName=" + queryKeyName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - start queryKeyName=" + queryKeyName);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - start transaction ");
			}
			result = sqlMap.update(queryKeyName);
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("modify(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - end result=" + result);
		}
		return result;
	}

	/**
	 *
	 * 단일 항목의 데이터를 수정한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int update(String dbname, String queryKeyName, Object paramMap, boolean autoTransaction ) throws SQLException {


		if (logger.isInfoEnabled()) {
			logger.info("modify(String, Map) - queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - start queryKeyName=" + queryKeyName + ", paramMap=" + paramMap);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			if(autoTransaction) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - start transaction ");
			}
			result = sqlMap.update(queryKeyName, paramMap);
			if(autoTransaction) {
				sqlMap.commitTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("modify(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			if(autoTransaction) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - end result=" + result);
		}
		return result;
	}

	
	/**
	 *
	 * 단일 항목의 데이터를 수정한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int update(String dbname, String queryKeyName, boolean autoTransaction ) throws SQLException {


		if (logger.isInfoEnabled()) {
			logger.info("modify(String, Map) - queryKeyName=" + queryKeyName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - start queryKeyName=" + queryKeyName);
		}

		int result = -1;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			if(autoTransaction) {
				sqlMap.startTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - start transaction ");
			}
			result = sqlMap.update(queryKeyName);
			if(autoTransaction) {
				sqlMap.commitTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - commit transaction the result is = " + result);
			}
		}catch(SQLException e) {
			logger.fatal("modify(String, Map) trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			if(autoTransaction) {
				sqlMap.endTransaction();
			}
			if (logger.isInfoEnabled()) {
				logger.info("modify(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modify(String, Map) - end result=" + result);
		}
		return result;
	}

	/**
	 * 다중 항목의 수정를 처리한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * @param keyArray 지워지는 데이터의 식별자 배열
	 * @return int update 된 row의 count를 리턴한다. Exception 발생 시 -1 을 리턴한다.
	 * @throws SQLException
	 */
	public static int update(String dbname, String queryKeyName, Object[] keyArray) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("modifyMultiple(String, String[]) - start");
			logger.info("modifyMultiple(String, Map) - queryKeyName=" + queryKeyName + ", keyArray=" + keyArray);
		}


		int result = -1;
		SqlMapClient sqlMap = null;
		try {

			sqlMap = SqlMapManager.getSqlMapInstance(dbname);
			sqlMap.startTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modifyMultiple(String, Map) - start transaction ");
			}
			sqlMap.startBatch();

			logger.debug("modifyMultiple(String, Map) - start batch ");

			for(int i = 0; i < keyArray.length; i++) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** batch script excuete [" + i + "] statement and " +
							"the primary key is "+keyArray[i]+" ***** " );
				}
				sqlMap.update(queryKeyName, keyArray[i]);
			}
			result = sqlMap.executeBatch();
			if(result > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug(" ***** updated row count is "+result+" batch script excuete successfully without any Exceptions or Errors ***** ");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("modifyMultiple(String, Map) - end batch ");
			}
			sqlMap.commitTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modifyMultiple(String, Map) - commit transaction the result is = " + result);
			}

		}catch(SQLException e) {
			logger.fatal("modifyMultiple(String, Map) break the batch function with exception : " + e +
					"trying to rollback in finally statement - e=" + e, e);
			throw e;
		}finally {
			sqlMap.endTransaction();
			if (logger.isInfoEnabled()) {
				logger.info("modifyMultiple(String, Map) - end transaction delete result is = " + result);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modifyMultiple(String, Map) - end");
		}
		return result;
	}






	/**
	 *  단일 항목 int result 를 반환한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * ex) TEST.selectTests <- queries/test/TEST.xml 파일 참조 -> 반드시 int 를 resultClass 에 명시해 줘야 한다.
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int 결과물이 없다면 0 인 int 를 반환한다.
	 * @throws SQLException
	 */
	public static int getIntResult(String dbname, String queryKeyName, Object paramMap) throws SQLException{

		int result = -1;

		if (logger.isDebugEnabled()) {
			logger.debug("getIntResult(String, Map) - start queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}

		try {
			SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance( dbname );

			Object obj = sqlMap.queryForObject( queryKeyName , paramMap  );
			if(obj != null) {
				result = ((Integer)obj).intValue();
			}

		} catch (SQLException e) {
			logger.debug("getIntResult(String, Map) - queryKeyName=" + queryKeyName +
						", paramMap=" + paramMap + ", e=" + e, e);
			e.printStackTrace();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getIntResult(String, Map) - end - return value=" + result);
		}


		return result;

	}
	
	
	/**
	 *  단일 항목 int result 를 반환한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * ex) TEST.selectTests <- queries/test/TEST.xml 파일 참조 -> 반드시 int 를 resultClass 에 명시해 줘야 한다.
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int 결과물이 없다면 0 인 int 를 반환한다.
	 * @throws SQLException
	 */
	public static int getIntResult(String dbname, String queryKeyName) throws SQLException{

		int result = -1;

		if (logger.isDebugEnabled()) {
			logger.debug("getIntResult(String, Map) - start queryKeyName=" + queryKeyName);
		}

		try {
			SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance( dbname );

			Object obj = sqlMap.queryForObject( queryKeyName );
			if(obj != null) {
				result = ((Integer)obj).intValue();
			}

		} catch (SQLException e) {
			logger.debug("getIntResult(String, Map) - queryKeyName=" + queryKeyName +
						", e=" + e, e);
			e.printStackTrace();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getIntResult(String, Map) - end - return value=" + result);
		}


		return result;

	}
	
	/**
	 *  단일 항목 long result 를 반환한다.
	 *
	 * @param queryKeyName ibatis query configuration(xml) 파일의 namespace + query key
	 * ex) TEST.selectTests <- queries/test/TEST.xml 파일 참조 -> 반드시 int 를 resultClass 에 명시해 줘야 한다.
	 * @param paramMap 해당 쿼리에서 맵핑 되어야할 요소들을 보유하고 있는 맵
	 * @return int 결과물이 없다면 0 인 int 를 반환한다.
	 * @throws SQLException
	 */
	public static long getLongResult(String dbname, String queryKeyName, Object paramMap) throws SQLException{

		long result = -1;

		if (logger.isDebugEnabled()) {
			logger.debug("getLongResult(String, Map) - start queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}

		try {
			SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance( dbname );

			Object obj = sqlMap.queryForObject( queryKeyName , paramMap  );
			if(obj != null) {
				result = ((Long)obj).longValue();
			}

		} catch (SQLException e) {
			logger.debug("getLongResult(String, Map) - queryKeyName=" + queryKeyName +
						", paramMap=" + paramMap + ", e=" + e, e);
			e.printStackTrace();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getLongResult(String, Map) - end - return value=" + result);
		}


		return result;

	}

	public static float getFloatResult(String dbname, String queryKeyName, Object paramMap) throws SQLException {

		float result = -1;

		if (logger.isDebugEnabled()) {
			logger.debug("getFloatResult(String, Map) - start queryKeyName=" + queryKeyName +
					", paramMap=" + paramMap);
		}

		try {
			SqlMapClient sqlMap = SqlMapManager.getSqlMapInstance( dbname );

			Object obj = sqlMap.queryForObject( queryKeyName , paramMap  );
			if(obj != null) {
				result = ((Float)obj).floatValue();
			}

		} catch (SQLException e) {
			logger.debug("getFloatResult(String, Map) - queryKeyName=" + queryKeyName +
						", paramMap=" + paramMap + ", e=" + e, e);
			e.printStackTrace();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFloatResult(String, Map) - end - return value=" + result);
		}


		return result;

	}

}
