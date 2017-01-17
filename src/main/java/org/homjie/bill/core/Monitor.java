package org.homjie.bill.core;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Monitor
 * @Description 监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:40:32
 */
public abstract class Monitor implements Serializable {

	private static final long serialVersionUID = 1L;

	protected List<MonitorResult> results = Lists.newArrayList();

	/**
	 * @Title aggregate
	 * @Description 聚合结果，不加入版本控制
	 * @Author JieHong
	 * @Date 2016年8月11日 下午2:32:52
	 * @return
	 */
	public abstract MonitorResult aggregate();

	/**
	 * @Title version
	 * @Description 获取结果版本号
	 * @Author JieHong
	 * @Date 2016年8月3日 下午3:19:41
	 * @return
	 */
	public int version() {
		results.add(aggregate());
		return results.size() - 1;
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
