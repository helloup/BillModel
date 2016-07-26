package org.homjie.bill.core;

public class Priority<T> {

	/**
	 * @Description 优先级
	 */
	int priority;

	/**
	 * @Description 关联对象
	 */
	T associate;

	Priority(int priority, T associate) {
		this.priority = priority;
		this.associate = associate;
	}

}
