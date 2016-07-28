package org.homjie.bill.core;

public interface Convertible<T> {

	public Charge marshall(T obj);

	public T unmarshall(Charge charge);

}
