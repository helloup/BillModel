package org.homjie.bill.core;

/**
 * @Class Monitor
 * @Description 资金流向监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:40:32
 */
public abstract class AbstractMonitor {

	private Object monitor;

	public AbstractMonitor(Object monitor) {
		this.monitor = monitor;
	}

	@SuppressWarnings("unchecked")
	public <T> T get() {
		return (T) monitor;
	}

}
