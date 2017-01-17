package org.homjie.bill.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Item
 * @Description 明细项
 * @Author JieHong
 * @Date 2016年7月26日 下午4:58:30
 */
public class Item implements Serializable {

	private static final long serialVersionUID = -8662626703345363496L;

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

	transient List<Monitor> monitors = Lists.newArrayList();

	public Item(BigDecimal pay_yh, BigDecimal pay_sh) {
		checkNotNull(pay_yh);
		checkNotNull(pay_sh);
		this.pay_yh = pay_yh;
		this.pay_sh = pay_sh;
		this.pay_dh = pay_yh.subtract(pay_sh);
	}

	/**
	 * @Title addMonitor
	 * @Description 添加监控器
	 * @Author JieHong
	 * @Date 2016年7月29日 上午10:47:26
	 * @param monitor
	 */
	public void addMonitor(MonitorItem monitor) {
		monitors.add(monitor);
		monitor.link(this);
	}

	/**
	 * @Title priority
	 * @Description 设置优先级
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:30:44
	 * @param priority
	 * @return
	 */
	public Item priority(int priority) {
		this.priority = priority;
		return this;
	}

	public int getPriority() {
		return priority;
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

	public List<Monitor> getMonitors() {
		return monitors;
	}

	/**
	 * @Title isComplete
	 * @Description 是否还清
	 * @Author JieHong
	 * @Date 2016年7月28日 下午3:31:08
	 * @return
	 */
	public boolean isComplete() {
		// 待还金额 == 可还金额
		return pay_dh.compareTo(pay_kh) == 0;
	}

	@Override
	public String toString() {
		return "Item [priority=" + priority + ", pay_yh=" + pay_yh + ", pay_sh=" + pay_sh + ", pay_dh=" + pay_dh + ", pay_kh=" + pay_kh + "]";
	}

}
