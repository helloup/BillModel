package org.homjie.bill.core;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @Class Settle
 * @Description 结算
 * @Author JieHong
 * @Date 2016年7月28日 下午4:57:19
 */
public class Settle {

	private Settle() {
	}

	/**
	 * @Title result
	 * @Description 对收费进行还款计算
	 * @Author JieHong
	 * @Date 2016年7月28日 下午4:59:44
	 * @param charge
	 * @param money
	 *            还款金额
	 * @return 剩余金额
	 */
	public static BigDecimal result(Charge charge, BigDecimal money) {
		charge.validate();

		boolean items_empty = charge.items.isEmpty();
		if (items_empty) {
			// 包含多个Charge

			SettleCharge sc = charge.stlCharge;
			if (sc.equals(SettleCharge.TOTAL)) {
				// 能够全部结清
				BigDecimal complete = charge.completeWithoutValidate();
				if (money.compareTo(complete) >= 0) {
					complete(charge);
					money = money.subtract(complete);
				}
				return money;
			} else if (sc.equals(SettleCharge.SINGLE) || sc.equals(SettleCharge.SINGLE_IGNORE)) {
				// 子Charge收费结算策略默认为TOTAL
				if (sc.equals(SettleCharge.SINGLE))
					Collections.sort(charge.charges);
				for (Charge c : charge.charges) {
					BigDecimal complete = c.completeWithoutValidate();
					if (money.compareTo(complete) >= 0) {
						complete(c);
						money = money.subtract(complete);
					} else {
						break;
					}
				}
				return money;

			} else {
				if (sc.equals(SettleCharge.NONE))
					Collections.sort(charge.charges);
				// 包含多个Charge
				for (Charge c : charge.charges) {
					money = result(c, money);
				}
			}

		} else {
			// 包含多个Item

			// 启动当前的明细项结算策略
			SettleItem sItem = charge.stlItem;
			Collections.sort(charge.items, sItem.getComparator());
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
			charge.charges.forEach(Settle::complete);
		} else {
			// 包含多个Item
			for (Item item : charge.items) {
				item.pay_kh = item.pay_dh;
			}
		}
	}

	private static BigDecimal total(Charge charge, BigDecimal money) {
		BigDecimal pay_dh = charge.completeWithoutValidate();
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
