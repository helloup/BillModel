package org.homjie.bill.core;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class MonitorCharge
 * @Description 收费监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:41:38
 */
public class MonitorCharge extends AbstractMonitor {

	public MonitorCharge(Object monitor) {
		super(monitor);
	}

	private List<Charge> charges = Lists.newArrayList();

	private List<MonitorResult> results = Lists.newArrayList();

	/**
	 * @Title link
	 * @Description 监控Charge
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:44:26
	 * @param charge
	 * @return 获取该Charge在监控器的索引
	 */
	int link(Charge charge) {
		charges.add(charge);
		return charges.size() - 1;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public Charge getCharge(int index) {
		if (index < 0 || charges.size() <= index)
			throw new NullPointerException("Index does not exist!");
		return charges.get(index);
	}

	/**
	 * @Title version
	 * @Description 获取索引对应Charge的结果版本号
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:55:06
	 * @param index
	 * @return
	 */
	public int version(int index) {
		if (index < 0 || charges.size() <= index)
			throw new NullPointerException("Index does not exist!");
		Charge charge = charges.get(index);

		MonitorResult mr = new MonitorResult();
		version(charge, mr);

		results.add(mr);
		return results.size() - 1;
	}

	private void version(Charge charge, MonitorResult mr) {
		if (charge.items.isEmpty()) {
			// 包含多个Charge
			charge.charges.forEach(c -> version(c, mr));
		} else {
			// 包含多个Item
			for (Item item : charge.items) {
				mr.pay_yh = mr.pay_yh.add(item.pay_yh);
				mr.pay_sh = mr.pay_sh.add(item.pay_sh);
				mr.pay_dh = mr.pay_dh.add(item.pay_dh);
				mr.pay_kh = mr.pay_kh.add(item.pay_kh);
			}
		}
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
