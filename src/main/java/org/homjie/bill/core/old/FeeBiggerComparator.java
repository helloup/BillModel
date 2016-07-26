package org.homjie.bill.core.old;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

/**
 * 待还金额降序排列
 *
 */
public class FeeBiggerComparator implements Comparator<Fee> {

	private static final FeeBiggerComparator COMPARATOR = new FeeBiggerComparator();

	private FeeBiggerComparator() {
	}

	public static FeeBiggerComparator getComparator() {
		return COMPARATOR;
	}

	@Override
	public int compare(Fee o1, Fee o2) {
		return ComparisonChain.start().compare(o2.pay_dh, o1.pay_dh).compare(o2.priority, o1.priority).result();
	}

}
