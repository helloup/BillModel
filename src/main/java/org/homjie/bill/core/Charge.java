package org.homjie.bill.core;

import static org.homjie.bill.core.SettleCharge.NONE;
import static org.homjie.bill.core.SettleCharge.NONE_IGNORE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Charge
 * @Description 收费
 * @Author JieHong
 * @Date 2016年7月26日 下午4:58:11
 */
public class Charge implements Serializable, Comparable<Charge> {

	private static final long serialVersionUID = 5042097570166836993L;

	int priority;

	List<Item> items = Lists.newArrayList();

	List<Charge> charges = Lists.newArrayList();

	transient List<MonitorCharge> monitors = Lists.newArrayList();

	SettleItem stlItem;

	SettleCharge stlCharge;

	transient Charge parent;

	private boolean needValidate = true;

	/**
	 * @Title priority
	 * @Description 设置优先级
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:30:21
	 * @param priority
	 * @return
	 */
	public Charge priority(int priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * @Title addCharge
	 * @Description 添加收费
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:29:54
	 * @param charge
	 * @return
	 */
	public Charge addCharge(Charge charge) {
		needValidate = true;
		charges.add(charge);
		charge.parent = this;
		return this;
	}

	/**
	 * @Title addItem
	 * @Description 添加明细项
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:30:08
	 * @param item
	 * @return
	 */
	public Charge addItem(Item item) {
		needValidate = true;
		items.add(item);
		return this;
	}

	/**
	 * @Title settle
	 * @Description 明细项结算策略
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:29:27
	 * @param sItem
	 * @return
	 */
	public Charge settle(SettleItem sItem) {
		needValidate = true;
		this.stlItem = sItem;
		return this;
	}

	/**
	 * @Title settle
	 * @Description 收费结算策略
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:29:37
	 * @param sCharge
	 * @return
	 */
	public Charge settle(SettleCharge sCharge) {
		needValidate = true;
		this.stlCharge = sCharge;
		return this;
	}

	/**
	 * @Title addMonitor
	 * @Description 添加监控器
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:42:58
	 * @param monitor
	 * @return 获取该Charge在监控器的索引
	 */
	public int addMonitor(MonitorCharge monitor) {
		monitors.add(monitor);
		return monitor.link(this);
	}

	/**
	 * @Title complete
	 * @Description 结清收费所需金额
	 * @Author JieHong
	 * @Date 2016年7月28日 下午5:01:17
	 * @return
	 */
	public BigDecimal complete() {
		if (needValidate)
			validate();
		return complete(BigDecimal.ZERO);
	}

	BigDecimal completeWithoutValidate() {
		return complete(BigDecimal.ZERO);
	}

	private BigDecimal complete(BigDecimal sum) {
		boolean items_empty = items.isEmpty();
		if (items_empty) {
			// 包含多个Charge
			for (Charge charge : charges) {
				sum = charge.complete(sum);
			}
			return sum;
		} else {
			// 包含多个Item
			for (Item item : items) {
				sum = sum.add(item.pay_dh);
			}
			return sum;
		}
	}

	/**
	 * @Title validate
	 * @Description 校验结构是否合法
	 * @Author JieHong
	 * @Date 2016年7月26日 下午5:16:27
	 */
	void validate() {

		validate(this);

		needValidate = false;
	}

	/**
	 * @Title validate
	 * @Description 校验当前Charge和其后代Charge
	 * @Author JieHong
	 * @Date 2016年7月29日 上午9:24:39
	 * @param charge
	 */
	private void validate(Charge charge) {
		boolean items_empty = charge.items.isEmpty();
		boolean charges_empty = charge.charges.isEmpty();
		if (items_empty && charges_empty)
			throw new IllegalStateException("The structure is not illegal, not both be empty!");
		if (!items_empty && !charges_empty)
			throw new IllegalStateException("The structure is not illegal, one should be empty!");

		if (items_empty) {
			// 包含多个Charge
			SettleCharge sc = charge.stlCharge;
			if (sc == null)
				throw new IllegalStateException("The structure is not illegal, need to specify SettleCharge!");
			if (!sc.equals(NONE) && !sc.equals(NONE_IGNORE))
				return;
			charge.charges.forEach(Charge::validate);
		} else {
			// 包含多个Item
			if (charge.stlItem == null)
				throw new IllegalStateException("The structure is not illegal, need to specify SettleItem!");
		}

	}

	@Override
	public int compareTo(Charge c) {
		return c.priority - this.priority;
	}

	public int getPriority() {
		return priority;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public List<MonitorCharge> getMonitors() {
		return monitors;
	}

	public SettleItem getStlItem() {
		return stlItem;
	}

	public SettleCharge getStlCharge() {
		return stlCharge;
	}

	public Charge getRoot() {
		Charge root = null;
		if (parent == null) {
			root = this;
		} else {
			Charge r = parent;
			while (r != null) {
				root = r;
				r = r.parent;
			}
		}
		return root;
	}

	@Override
	public String toString() {
		return "Charge [priority=" + priority + ", items=" + items + ", charges=" + charges + "]";
	}
}
