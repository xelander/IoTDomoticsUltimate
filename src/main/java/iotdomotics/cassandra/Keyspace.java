package iotdomotics.cassandra;

import java.util.HashMap;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class Keyspace implements DBKeyspaceInterface{
	private String name;
	private Connector<Session> connect;
	private boolean unitFlag = false;

	public Keyspace(String name, Connector<Session> connect){
		this.name = name;
		this.connect = connect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param keyspace
	 * @param name
	 * @param primaryKey
	 * @param map
	 * @return 2 se create può aver avuto successo(manca controllo se la table esiste già)
	 */
	public int createTable(String name, String primaryKey, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		String create = null;
		create = "CREATE TABLE IF NOT EXISTS " + name + " (";
		for(String entry: map.keySet()){
			create += "" + entry + " " + map.get(entry) + ", ";
		}
		create += "PRIMARY KEY (" + primaryKey + "));";
		this.connect.getSession().execute(create);
		return 2;
	}

	/**
	 * 
	 * @param keyspace
	 * @param name
	 * @return 2 se drop può aver avuto successo(manca controllo se la table esiste già)
	 */
	public int dropTable(String name) {
		// TODO Auto-generated method stub
		String drop = null;
		drop = "DROP TABLE " + name;
		this.connect.getSession().execute(drop);
		return 2;
	}

	public ResultSet loadRecords(String tableName, int limit) {
		String query = "SELECT * FROM " + tableName;
		if(limit >= 0)
			query += " LIMIT " + limit;
		return this.connect.getSession().execute(query);
	}

	public ResultSet loadRecords(String tableName) {
		// TODO Auto-generated method stub
		return loadRecords(tableName, -1);
	}

	public void updateUnit(String id, String unit, String tableName) {
		// TODO Auto-generated method stub
		if(!unitFlag){
			String query = "SELECT unit FROM " + tableName;
			try{
				this.connect.getSession().execute(query);
			} catch(com.datastax.driver.core.exceptions.InvalidQueryException e){
				this.connect.getSession().execute("ALTER TABLE " + tableName + " ADD unit text");
			} finally {
				String update = "UPDATE " + tableName + " SET unit = '" + unit + "' WHERE id = '" + id + "'"; 
				this.connect.getSession().execute(update);
			}
		} else {
			String update = "UPDATE " + tableName + " SET unit = '" + unit + "' WHERE id = '" + id + "'"; 
			this.connect.getSession().execute(update);
		}
	}
}
