package org.homjie.bill.core.old;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 单个结算项<br>
 * 1、应还金额 = 实还金额 + 待还金额<br>
 * 2、待还金额 >= 可还金额
 *
 */
public class Fee implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 结算优先级（唯一，优先级越高，最先结算）
	 */
	int priority;

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

	public Fee(int priority, BigDecimal pay_yh, BigDecimal pay_sh) {
		checkNotNull(pay_yh);
		checkNotNull(pay_sh);
		this.priority = priority;
		this.pay_yh = pay_yh;
		this.pay_sh = pay_sh;
		this.pay_dh = pay_yh.subtract(pay_sh);
	}

	/*************** dubbo ***************/

	@SuppressWarnings("unused")
	private Fee() {
	}

	/*************** dubbo ***************/

	public int getPriority() {
		return priority;
	}

	public BigDecimal getPay_yh() {
		return pay_yh;
	}

	public void setPay_yh(BigDecimal pay_yh) {
		this.pay_yh = pay_yh;
		this.pay_dh = pay_yh.subtract(pay_sh);
	}

	public BigDecimal getPay_sh() {
		return pay_sh;
	}

	public void setPay_sh(BigDecimal pay_sh) {
		this.pay_sh = pay_sh;
		this.pay_dh = pay_yh.subtract(pay_sh);
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
		return toStringHelper(this).add("结算优先级", this.priority).add("应还金额", this.pay_yh).add("实还金额", this.pay_sh).add("待还金额", this.pay_dh).add("可还金额", this.pay_kh)
				.add("是否还清", this.isComplete()).toString();
	}

}
