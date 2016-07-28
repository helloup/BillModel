package org.homjie.bill.core;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class MonitorCharge
 * @Description 监控收费
 * @Author JieHong
 * @Date 2016年7月28日 下午1:41:38
 */
public class MonitorCharge extends AbstractMonitor {

	public MonitorCharge(Object monitor) {
		super(monitor);
	}

	private List<Charge> charges = Lists.newArrayList();

	void link(Charge charge) {
		this.charges.add(charge);
	}

}
