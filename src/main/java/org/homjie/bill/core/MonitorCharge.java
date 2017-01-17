package org.homjie.bill.core;

/**
 * @Class MonitorCharge
 * @Description 收费监控器
 * @Author JieHong
 * @Date 2016年7月28日 下午1:41:38
 */
public class MonitorCharge extends Monitor {

	private static final long serialVersionUID = -1895717671613791841L;

	private Charge charge;

	public MonitorCharge(Charge charge) {
		this.charge = charge;
	}

	@Override
	public MonitorResult aggregate() {
		MonitorResult mr = new MonitorResult();
		aggregate(charge, mr);
		return mr;
	}

	private void aggregate(Charge charge, MonitorResult mr) {
		if (charge.haveCharges()) {
			// 包含多个Charge
			charge.charges.forEach(c -> aggregate(c, mr));
		} else {
			// 包含多个Item
			for (Item item : charge.items) {
				mr.pay_yh = mr.pay_yh.add(item.pay_yh);
				mr.pay_sh = mr.pay_sh.add(item.pay_sh);
				mr.pay_dh = mr.pay_dh.add(item.pay_dh);
				mr.pay_kh = mr.pay_kh.add(item.pay_kh);
			}
		}
	}

}
