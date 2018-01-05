package iotdomotics.cassandra;

public interface Connector<E> {
	E getSession();
}
