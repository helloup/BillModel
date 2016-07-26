package org.homjie.bill.core.old;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

/**
 * 优先级降序排列
 *
 */
public class FeeComparator implements Comparator<Fee> {

	private static final FeeComparator COMPARATOR = new FeeComparator();

	private FeeComparator() {
	}

	public static FeeComparator getComparator() {
		return COMPARATOR;
	}

	@Override
	public int compare(Fee o1, Fee o2) {
		return ComparisonChain.start().compare(o2.priority, o1.priority).result();
	}

}
