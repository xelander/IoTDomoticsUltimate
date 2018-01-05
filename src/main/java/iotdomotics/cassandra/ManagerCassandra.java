package iotdomotics.cassandra;

import java.util.Vector;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class ManagerCassandra implements DBCassandraManagerInterface {
	private Vector<String> keyspaces = new Vector<String>();
	private Connector<Session> connect = null;
	
	public ManagerCassandra(String host, int port){
		this.connect = new CassandraConnector(host, port);
		ResultSet set = this.connect.getSession().execute("select * from system_schema.keyspaces;");
		
		for(Row row: set)
			keyspaces.addElement(row.getString("keyspace_name"));
	}
	
	public ManagerCassandra(){
		this.connect = new CassandraConnector("127.0.0.1", 9047);
		ResultSet set = this.connect.getSession().execute("select * from system_schema.keyspaces;");
		
		for(Row row: set)
			keyspaces.addElement(row.getString("keyspace_name"));
	}
	
	/**
	 * 
	 * @param name
	 * @param strategy
	 * @param replicationFactor
	 * @return false if keyspace already exists, true otherwise
	 */
	public boolean createKeyspace(String keyspace, String strategy, int replicationFactor){
		if(keyspaces.contains(keyspace.toLowerCase()))
			return false;
		else{
			String query = "CREATE KEYSPACE " + keyspace 
					+ " WITH replication = {'class':'" + strategy 
					+ "', 'replication_factor':" + replicationFactor
					+ "};";
			this.connect.getSession().execute(query);
			keyspaces.add(keyspace);
			return true;
		}
	}
	
	public Keyspace useKeyspace(String keyspace){
		if(!keyspaces.contains(keyspace))
			return null;
		else{
			String query = "USE " + keyspace;
			this.connect.getSession().execute(query); 
			return new Keyspace(keyspace, this.connect);
		}
	}

	public Vector<String> getKeyspaces() {
		// TODO Auto-generated method stub
		return this.keyspaces;
	}

	
}
