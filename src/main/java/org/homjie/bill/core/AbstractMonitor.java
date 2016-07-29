package org.homjie.bill.core;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class AbstractMonitor
 * @Description 监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:40:32
 */
public abstract class AbstractMonitor {

	private Object monitor;

	protected List<MonitorResult> results = Lists.newArrayList();

	public AbstractMonitor(Object monitor) {
		this.monitor = monitor;
	}

	@SuppressWarnings("unchecked")
	public <T> T get() {
		return (T) monitor;
	}

	/**
	 * @Title history
	 * @Description 通过版本号获取结果
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:56:01
	 * @param version
	 * @return
	 */
	public MonitorResult history(int version) {
		if (version >= 0 && results.size() > version)
			return results.get(version);
		throw new NullPointerException("Version does not exist!");
	}
}
