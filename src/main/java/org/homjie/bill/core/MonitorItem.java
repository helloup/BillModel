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

	private List<MonitorResult> results = Lists.newArrayList();

	/**
	 * @Title link
	 * @Description 监控明细项
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:46:04
	 * @param item
	 * @return 获取该Item在监控器的索引
	 */
	int link(Item item) {
		items.add(item);
		return items.size() - 1;
	}

	public List<Item> getItems() {
		return items;
	}

	public Item getItem(int index) {
		if (index < 0 || items.size() <= index)
			throw new NullPointerException("Index does not exist!");
		return items.get(index);
	}

	/**
	 * @Title version
	 * @Description 获取结果版本号
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:56:18
	 * @return
	 */
	public int version() {
		MonitorResult mr = new MonitorResult();

		for (Item item : items) {
			mr.pay_yh = mr.pay_yh.add(item.pay_yh);
			mr.pay_sh = mr.pay_sh.add(item.pay_sh);
			mr.pay_dh = mr.pay_dh.add(item.pay_dh);
			mr.pay_kh = mr.pay_kh.add(item.pay_kh);
		}

		results.add(mr);
		return results.size() - 1;
	}

	/**
	 * @Title history
	 * @Description 通过版本号获取结果
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:57:02
	 * @param version
	 * @return
	 */
	public MonitorResult history(int version) {
		if (version >= 0 && results.size() > version)
			return results.get(version);
		throw new NullPointerException("Version does not exist!");
	}

}
