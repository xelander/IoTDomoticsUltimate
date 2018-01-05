package iotdomotics.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnector implements Connector<Session>{
	private Cluster cluster = null;
	private Session session = null;
	private String host;
	@SuppressWarnings("unused")
	private int port;
	
	public CassandraConnector(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public Session getSession(){
		if(session != null)
			return session;
		else{
			this.cluster = Cluster.builder().addContactPoint(this.host).build();
			this.session = this.cluster.connect();
			return session;
		}
	}
}
