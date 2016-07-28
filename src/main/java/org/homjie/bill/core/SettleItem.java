package org.homjie.bill.core;

import java.util.Comparator;

/**
 * @Class SettleItem
 * @Description 明细项结算策略
 * @Author JieHong
 * @Date 2016年7月28日 下午2:33:19
 */
public enum SettleItem {

	/**
	 * 从有效级高的开始结算，所有子Item必须还清
	 */
	TOTAL(ItemComparators.PRIORITY),

	/**
	 * 从有效级高的开始结算，单个子Item必须还清
	 */
	SINGLE(ItemComparators.PRIORITY),

	/**
	 * 从有效级高的开始结算，单个子Item够还多少就还多少
	 */
	NONE(ItemComparators.PRIORITY),

	/**
	 * 忽略有效级，从待还金额大的开始结算，单个子Item必须还清
	 */
	SINGLE_MORE(ItemComparators.CASEMORE),

	/**
	 * 忽略有效级，从待还金额大的开始结算，单个子Item够还多少就还多少
	 */
	NONE_MORE(ItemComparators.CASEMORE),

	/**
	 * 忽略有效级，从待还金额小的开始结算，单个子Item必须还清
	 */
	SINGLE_LESS(ItemComparators.CASELESS),

	/**
	 * 忽略有效级，从待还金额小的开始结算，单个子Item够还多少就还多少
	 */
	NONE_LESS(ItemComparators.CASELESS);

	private Comparator<Item> comparator;

	private SettleItem(Comparator<Item> comparator) {
		this.comparator = comparator;
	}

	Comparator<Item> getComparator() {
		return comparator;
	}

}
