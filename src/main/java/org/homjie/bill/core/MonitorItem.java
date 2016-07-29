package org.homjie.bill.core;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class MonitorItem
 * @Description 明细项监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:48:40
 */
public class MonitorItem extends AbstractMonitor {

	public MonitorItem(Object monitor) {
		super(monitor);
	}

	private List<Item> items = Lists.newArrayList();

	void link(Item item) {
		items.add(item);
	}

	public List<Item> getItems() {
		return items;
	}

}
