package org.homjie.bill.core.old;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Objects.toStringHelper;

/**
 * 账单
 *
 */
public class Bill<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 还款项
	 */
	private final List<Fee> fees = new ArrayList<Fee>();

	/**
	 * 关联对象
	 */
	private T t;

	public Bill(T t) {
		this.t = t;
	}

	public Bill<T> add(Fee fee) {
		fees.add(fee);
		return this;
	}

	/**
	 * 账单总待还
	 */
	public BigDecimal sum_dh() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Fee fee : fees) {
			sum = sum.add(fee.pay_dh);
		}
		return sum;
	}

	public List<Fee> getFees() {
		return fees;
	}

	public T getT() {
		return t;
	}

	public Fee getFee(int priority) {
		for (Fee fee : fees) {
			if (fee.priority == priority)
				return fee;
		}
		return null;
	}

	/**
	 * @param strategy
	 *            结算策略
	 * @param money
	 *            还款金额
	 * @return
	 */
	public Result result(BillStrategy strategy, BigDecimal money) {
		checkNotNull(money);
		Result result = new Result(strategy, money);
		BillResultCalc.calc(result);
		return result;
	}

	/*************** dubbo ***************/

	@SuppressWarnings("unused")
	private Bill() {
	}

	/*************** dubbo ***************/

	/**
	 * 账单结果
	 *
	 */
	public class Result implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * 结算策略
		 */
		BillStrategy strategy;

		/**
		 * 还款金额
		 */
		BigDecimal money;

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

		public List<Fee> getFees() {
			return fees;
		}

		public T getT() {
			return t;
		}

		public Fee getFee(int priority) {
			for (Fee fee : fees) {
				if (fee.priority == priority)
					return fee;
			}
			return null;
		}

		private Result(BillStrategy strategy, BigDecimal money) {
			this.strategy = strategy;
			this.money = money;
			BigDecimal total_yh = BigDecimal.ZERO;
			BigDecimal total_sh = BigDecimal.ZERO;
			BigDecimal total_dh = BigDecimal.ZERO;
			Collections.sort(fees, strategy.getComparator());
			for (Fee fee : fees) {
				total_yh = total_yh.add(fee.getPay_yh());
				total_sh = total_sh.add(fee.getPay_sh());
				total_dh = total_dh.add(fee.getPay_dh());
				fee.pay_kh = BigDecimal.ZERO;
			}
			this.pay_yh = total_yh;
			this.pay_sh = total_sh;
			this.pay_dh = total_dh;
		}

		/*************** dubbo ***************/

		private Result() {
		}

		/*************** dubbo ***************/

		public BillStrategy getStrategy() {
			return strategy;
		}

		public BigDecimal getMoney() {
			return money;
		}

		public BigDecimal getPay_yh() {
			return pay_yh;
		}

		public BigDecimal getPay_sh() {
			return pay_sh;
		}

		public BigDecimal getPay_dh() {
			return pay_dh;
		}

		public BigDecimal getPay_kh() {
			return pay_kh;
		}

		/**
		 * @return 是否还清
		 */
		public boolean isComplete() {
			// 待还金额 == 可还金额
			return pay_dh.compareTo(pay_kh) == 0;
		}

		@Override
		public String toString() {
			return toStringHelper(this).add("关联对象", this.getT()).add("还款金额", this.money).add("结算策略", this.strategy).add("应还金额", this.pay_yh).add("实还金额", this.pay_sh)
					.add("待还金额", this.pay_dh).add("可还金额", this.pay_kh).add("是否还清", this.isComplete()).add("还款项", this.getFees()).toString();
		}

	}

}
