package iotdomotics.cassandra;

import java.util.HashMap;

import com.datastax.driver.core.ResultSet;

public interface DBKeyspaceInterface {
	int createTable(String name, String primaryKey, HashMap<String, String> map);
	int dropTable(String name);
	ResultSet loadRecords(String table, int limit);
	ResultSet loadRecords(String table);
	void updateUnit(String id, String unit, String tableName);
}
