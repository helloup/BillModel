package org.homjie.bill.core;

import static org.homjie.bill.core.SettleCharge.NONE;
import static org.homjie.bill.core.SettleCharge.NONE_IGNORE;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Charge
 * @Description 收费
 * @Author JieHong
 * @Date 2016年7月26日 下午4:58:11
 */
public class Charge implements Comparable<Charge> {

	int priority;

	List<Item> items = Lists.newArrayList();

	List<Charge> charges = Lists.newArrayList();

	List<MonitorCharge> monitors = Lists.newArrayList();

	SettleItem sItem;

	SettleCharge sCharge;

	Charge parent;

	private Charge root;

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
		this.sItem = sItem;
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
		this.sCharge = sCharge;
		return this;
	}

	public Charge addMonitor(MonitorCharge monitor) {
		monitors.add(monitor);
		monitor.link(this);
		return this;
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

		boolean items_empty = items.isEmpty();
		boolean charges_empty = charges.isEmpty();
		if (items_empty && charges_empty)
			throw new IllegalStateException("The structure is not illegal!");
		if (!items_empty && !charges_empty)
			throw new IllegalStateException("The structure is not illegal!");
		if (items_empty) {
			// 包含多个Charge
			SettleCharge sc = getRoot().sCharge;
			if (sCharge == null && (sc.equals(NONE) || sc.equals(NONE_IGNORE)))
				throw new IllegalStateException("The structure is not illegal!");
			charges.forEach(Charge::validate);
		} else {
			// 包含多个Item
			SettleCharge parentSettle = parent.sCharge;
			if (parentSettle.equals(NONE) || parentSettle.equals(NONE_IGNORE)) {
				if (sItem == null)
					throw new IllegalStateException("The structure is not illegal!");
			}
		}

		needValidate = false;
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

	public SettleItem getsItem() {
		return sItem;
	}

	public SettleCharge getsCharge() {
		return sCharge;
	}

	public Charge getParent() {
		return parent;
	}

	public Charge getRoot() {
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
}
