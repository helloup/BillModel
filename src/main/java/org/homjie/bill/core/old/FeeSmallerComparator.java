package org.homjie.bill.core.old;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

/**
 * 待还金额升序排列
 *
 */
public class FeeSmallerComparator implements Comparator<Fee> {

	private static final FeeSmallerComparator COMPARATOR = new FeeSmallerComparator();

	private FeeSmallerComparator() {
	}

	public static FeeSmallerComparator getComparator() {
		return COMPARATOR;
	}

	@Override
	public int compare(Fee o1, Fee o2) {
		return ComparisonChain.start().compare(o1.pay_dh, o2.pay_dh).compare(o2.priority, o1.priority).result();
	}

}
