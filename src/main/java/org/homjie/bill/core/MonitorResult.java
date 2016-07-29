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

	MonitorResult() {
	}

	/**
	 * @Title getPay_yh
	 * @Description 应还金额
	 * @Author JieHong
	 * @Date 2016年7月29日 下午2:20:36
	 * @return
	 */
	public BigDecimal getPay_yh() {
		return pay_yh;
	}

	/**
	 * @Title getPay_sh
	 * @Description 实还金额
	 * @Author JieHong
	 * @Date 2016年7月29日 下午2:20:45
	 * @return
	 */
	public BigDecimal getPay_sh() {
		return pay_sh;
	}

	/**
	 * @Title getPay_dh
	 * @Description 待还金额
	 * @Author JieHong
	 * @Date 2016年7月29日 下午2:20:54
	 * @return
	 */
	public BigDecimal getPay_dh() {
		return pay_dh;
	}

	/**
	 * @Title getPay_kh
	 * @Description 可还金额
	 * @Author JieHong
	 * @Date 2016年7月29日 下午2:21:04
	 * @return
	 */
	public BigDecimal getPay_kh() {
		return pay_kh;
	}

	@Override
	public String toString() {
		return "MonitorResult [pay_yh=" + pay_yh + ", pay_sh=" + pay_sh + ", pay_dh=" + pay_dh + ", pay_kh=" + pay_kh + "]";
	}

}
