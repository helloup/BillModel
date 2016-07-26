package org.homjie.bill.core.old;

import java.util.Comparator;

/**
 * 账单的结算策略
 *
 */
public enum BillStrategy {

	/**
	 * 从有效级高的开始结算，整个账单必须还清
	 */
	TOTAL(FeeComparator.getComparator()),

	/**
	 * 从有效级高的开始结算，单个结算项必须还清
	 */
	SINGLE(FeeComparator.getComparator()),

	/**
	 * 从有效级高的开始结算，够还多少就还多少
	 */
	NONE(FeeComparator.getComparator()),

	/**
	 * 忽略有效级，从待还金额大的开始结算，单个结算项必须还清
	 */
	SINGLE_BIGGER(FeeBiggerComparator.getComparator()),

	/**
	 * 忽略有效级，从待还金额大的开始结算，够还多少就还多少
	 */
	NONE_BIGGER(FeeBiggerComparator.getComparator()),

	/**
	 * 忽略有效级，从待还金额小的开始结算，单个结算项必须还清
	 */
	SINGLE_SMALLER(FeeSmallerComparator.getComparator()),

	/**
	 * 忽略有效级，从待还金额小的开始结算，够还多少就还多少
	 */
	NONE_SMALLER(FeeSmallerComparator.getComparator());

	private Comparator<Fee> comparator;

	private BillStrategy(Comparator<Fee> comparator) {
		this.comparator = comparator;
	}

	/**
	 * @return 结算顺序
	 */
	public Comparator<Fee> getComparator() {
		return comparator;
	}
}
