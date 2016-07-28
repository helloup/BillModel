package org.homjie.bill.core;

import java.math.BigDecimal;

/**
 * @Class Result
 * @Description 结果类
 * @Author JieHong
 * @Date 2016年7月28日 下午4:57:19
 */
public class Result {

	private Result() {
	}

	/**
	 * @Title calculate
	 * @Description 对收费进行还款
	 * @Author JieHong
	 * @Date 2016年7月28日 下午4:59:44
	 * @param charge
	 * @param money
	 *            还款金额
	 * @return 剩余金额
	 */
	public static BigDecimal calculate(Charge charge, BigDecimal money) {
		charge.validate();

		boolean items_empty = charge.items.isEmpty();
		if (items_empty) {
			// 包含多个Charge

			SettleCharge sc = charge.sCharge;
			if (sc.equals(SettleCharge.TOTAL)) {
				// 能够全部结清
				BigDecimal complete = charge.complete();
				if (money.compareTo(complete) >= 0) {
					complete(charge);
					money = money.subtract(complete);
				}
				return money;
			} else if (sc.equals(SettleCharge.SINGLE) || sc.equals(SettleCharge.SINGLE_IGNORE)) {
				// 子Charge收费结算策略默认为TOTAL
				for (Charge c : charge.charges) {
					BigDecimal complete = c.complete();
					if (money.compareTo(complete) >= 0) {
						complete(c);
						money = money.subtract(complete);
					} else {
						break;
					}
				}
				return money;

			} else {
				// 包含多个Charge
				for (Charge c : charge.charges) {
					money = calculate(c, money);
				}
			}

		} else {
			// 包含多个Item

			// 启动当前的明细项结算策略
			SettleItem sItem = charge.sItem;
			switch (sItem) {
			case TOTAL:
				money = total(charge, money);
				break;
			case SINGLE:
			case SINGLE_MORE:
			case SINGLE_LESS:
				money = single(charge, money);
				break;
			case NONE:
			case NONE_MORE:
			case NONE_LESS:
				money = none(charge, money);
				break;
			}

		}

		return money;
	}

	private static void complete(Charge charge) {
		boolean items_empty = charge.items.isEmpty();
		if (items_empty) {
			// 包含多个Charge
			charge.charges.forEach(Result::complete);
		} else {
			// 包含多个Item
			for (Item item : charge.items) {
				item.pay_kh = item.pay_dh;
			}
		}
	}

	private static BigDecimal total(Charge charge, BigDecimal money) {
		BigDecimal pay_dh = charge.complete();
		if (money.compareTo(pay_dh) >= 0) {
			for (Item item : charge.items) {
				item.pay_kh = item.pay_dh;
			}
			money = money.subtract(pay_dh);
		}
		return money;
	}

	private static BigDecimal single(Charge charge, BigDecimal money) {
		for (Item item : charge.items) {
			BigDecimal pay_dh = item.pay_dh;
			if (money.compareTo(pay_dh) >= 0) {
				// 单个结算项可成功结算
				item.pay_kh = pay_dh;
				money = money.subtract(pay_dh);
			} else {
				break;
			}
		}
		return money;
	}

	private static BigDecimal none(Charge charge, BigDecimal money) {
		for (Item item : charge.items) {
			BigDecimal pay_dh = item.pay_dh;
			if (money.compareTo(pay_dh) >= 0) {
				// 单个结算项可成功结算
				item.pay_kh = pay_dh;
				money = money.subtract(pay_dh);
			} else {
				item.pay_kh = money;
				money = BigDecimal.ZERO;
				break;
			}
		}
		return money;
	}

}