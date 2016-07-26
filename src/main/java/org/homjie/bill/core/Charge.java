package org.homjie.bill.core;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Charge
 * @Description 收费
 * @Author JieHong
 * @Date 2016年7月26日 下午4:58:11
 */
public class Charge {

	Priority<Charge> priority;

	List<Item> items = Lists.newArrayList();

	List<Charge> charges = Lists.newArrayList();
	
	int deep;

	public Charge priority(int priority) {
		this.priority = new Priority<Charge>(priority, this);
		return this;
	}

	/**
	 * @Title validate
	 * @Description 校验结构是否合法
	 * @Author JieHong
	 * @Date 2016年7月26日 下午5:16:27
	 */
	private void validate() {
		boolean items_empty = items.isEmpty();
		boolean charges_empty = charges.isEmpty();
		if (items_empty && charges_empty)
			throw new NullPointerException("Items and Chargs, both can not be empty!");
		if (!items_empty && !charges_empty)
			throw new IllegalStateException("Items or Chargs should be empty!");
	}
}
