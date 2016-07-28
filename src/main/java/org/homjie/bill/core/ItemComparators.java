package org.homjie.bill.core;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

/**
 * @Class ItemComparators
 * @Description 明细项比较器
 * @Author JieHong
 * @Date 2016年7月28日 下午2:32:57
 */
public class ItemComparators {

	/**
	 * @Description 明细项优先级越高，越先结算
	 */
	public static final Comparator<Item> PRIORITY = new PriorityComparator();
	/**
	 * @Description 明细项金额越大，越先结算
	 */
	public static final Comparator<Item> CASEMORE = new MoreComparator();
	/**
	 * @Description 明细项金额越小，越先结算
	 */
	public static final Comparator<Item> CASELESS = new LessComparator();

	private ItemComparators() {
	}

	private static class PriorityComparator implements Comparator<Item> {

		@Override
		public int compare(Item o1, Item o2) {
			return o2.priority - o1.priority;
		}

	}

	private static class MoreComparator implements Comparator<Item> {

		@Override
		public int compare(Item o1, Item o2) {
			return ComparisonChain.start().compare(o2.pay_dh, o1.pay_dh).compare(o2.priority, o1.priority).result();
		}

	}

	private static class LessComparator implements Comparator<Item> {

		@Override
		public int compare(Item o1, Item o2) {
			return ComparisonChain.start().compare(o1.pay_dh, o2.pay_dh).compare(o2.priority, o1.priority).result();
		}

	}

}
