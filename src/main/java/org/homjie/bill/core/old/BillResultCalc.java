package org.homjie.bill.core.old;

import java.math.BigDecimal;
import java.util.List;

public final class BillResultCalc {

	static <T> void calc(Bill<T>.Result result) {
		switch (result.getStrategy()) {
		case TOTAL:
			total(result);
			break;
		case SINGLE:
		case SINGLE_BIGGER:
		case SINGLE_SMALLER:
			single(result);
			break;
		case NONE:
		case NONE_BIGGER:
		case NONE_SMALLER:
			none(result);
			break;
		}
	}

	private static <T> void total(Bill<T>.Result result) {
		BigDecimal pay_dh = result.pay_dh;
		if (result.money.compareTo(pay_dh) >= 0) {
			// 账单可成功结算
			result.pay_kh = pay_dh;
			List<Fee> fees = result.getFees();
			for (Fee fee : fees) {
				fee.pay_kh = fee.pay_dh;
			}
		}
	}

	private static <T> void single(Bill<T>.Result result) {
		BigDecimal money = result.money;
		BigDecimal clone = money;
		List<Fee> fees = result.getFees();
		for (Fee fee : fees) {
			BigDecimal pay_dh = fee.pay_dh;
			if (clone.compareTo(pay_dh) >= 0) {
				// 单个结算项可成功结算
				fee.pay_kh = pay_dh;
				result.pay_kh = result.pay_kh.add(pay_dh);
				clone = clone.subtract(pay_dh);
			} else {
				break;
			}
		}
	}

	private static <T> void none(Bill<T>.Result result) {
		BigDecimal money = result.money;
		BigDecimal clone = money;
		List<Fee> fees = result.getFees();
		for (Fee fee : fees) {
			BigDecimal pay_dh = fee.pay_dh;
			if (clone.compareTo(pay_dh) >= 0) {
				// 单个结算项可成功结算
				fee.pay_kh = pay_dh;
				result.pay_kh = result.pay_kh.add(pay_dh);
				clone = clone.subtract(pay_dh);
			} else {
				fee.pay_kh = clone;
				result.pay_kh = result.pay_kh.add(clone);
				break;
			}
		}
	}

}
