package org.homjie.bill.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;

/**
 * @Class Item
 * @Description 明细项
 * @Author JieHong
 * @Date 2016年7月26日 下午4:58:30
 */
public class Item {

	Priority<Item> priority;

	/**
	 * 应还金额
	 */
	BigDecimal pay_yh;

	/**
	 * 实还金额
	 */
	BigDecimal pay_sh;

	/**
	 * 待还金额
	 */
	BigDecimal pay_dh;

	/**
	 * 可还金额
	 */
	BigDecimal pay_kh = BigDecimal.ZERO;

	public Item(BigDecimal pay_yh, BigDecimal pay_sh) {
		checkNotNull(pay_yh);
		checkNotNull(pay_sh);
		this.pay_yh = pay_yh;
		this.pay_sh = pay_sh;
		this.pay_dh = pay_yh.subtract(pay_sh);
	}

	public Item priority(int priority) {
		this.priority = new Priority<Item>(priority, this);
		return this;
	}

}
