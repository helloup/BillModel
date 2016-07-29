package org.homjie.bill.core;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class MonitorResult
 * @Description 监控器结果
 * @Author JieHong
 * @Date 2016年7月29日 上午10:20:21
 */
public class MonitorResult implements Serializable {

	private static final long serialVersionUID = 3719852564186558313L;

	/**
	 * 应还金额
	 */
	BigDecimal pay_yh = BigDecimal.ZERO;

	/**
	 * 实还金额
	 */
	BigDecimal pay_sh = BigDecimal.ZERO;

	/**
	 * 待还金额
	 */
	BigDecimal pay_dh = BigDecimal.ZERO;

	/**
	 * 可还金额
	 */
	BigDecimal pay_kh = BigDecimal.ZERO;

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

}
