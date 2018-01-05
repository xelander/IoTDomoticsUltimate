package iotdomotics.cassandra;

import java.util.Vector;

public interface DBCassandraManagerInterface {
	Vector<String> getKeyspaces();
	boolean createKeyspace(String keyspace, String strategy, int replicationFactor);
	Keyspace useKeyspace(String name);
}
