package org.homjie.bill.core;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class View
 * @Description 视图
 * @Author JieHong
 * @Date 2016年9月14日 下午4:08:36
 * @param <T>
 */
public class View<T> implements Serializable {

	private static final long serialVersionUID = -810108245213753947L;

	// multi charge as a view, a view will have a monitor like charge
	private List<Charge> list = Lists.newArrayList();

	private Charge aggrate = new Charge();

	private T t;

	public View(T t) {
		this.t = t;
	}

	public T baseOn() {
		return t;
	}

	/**
	 * @Title add
	 * @Description 添加Charge，默认对还清结果产生影响
	 * @Author JieHong
	 * @Date 2016年9月14日 下午4:13:34
	 * @param charge
	 * @return 索引
	 */
	public int add(Charge charge) {
		return add(charge, true);
	}

	/**
	 * @Title add
	 * @Description 添加Charge
	 * @Author JieHong
	 * @Date 2016年9月14日 下午4:13:34
	 * @param charge
	 * @param master
	 *            true对还清结果产生影响，false无影响
	 * @return 索引
	 */
	public int add(Charge charge, boolean master) {
		boolean items_empty = charge.items.isEmpty();
		boolean charges_empty = charge.charges.isEmpty();
		// item不为空，且charge为空
		if (items_empty || !charges_empty)
			throw new RuntimeException("Charge should only contain Items");
		list.add(charge);
		// 随借随还的部分还款中，如果金额足够还清将还本金，本金额外作为一个Charge，不需还清本金结果仍为还清
		if (master)
			aggrate.addCharge(charge);
		return list.size() - 1;
	}

	/**
	 * @Title get
	 * @Description 通过索引获取Charge
	 * @Author JieHong
	 * @Date 2016年9月14日 下午4:13:56
	 * @param index
	 * @return
	 */
	public Charge get(int index) {
		return list.get(index);
	}

	/**
	 * @Title getList
	 * @Description 获取Charges
	 * @Author JieHong
	 * @Date 2016年9月14日 下午4:51:13
	 * @return
	 */
	public List<Charge> getList() {
		return list;
	}

	/**
	 * @Title unique
	 * @Description 当确保Charge记录只有一条
	 * @Author JieHong
	 * @Date 2016年9月18日 下午2:18:45
	 * @return
	 */
	public Charge unique() {
		if (list.size() != 1)
			throw new RuntimeException("charge list contains more than one record!");
		return list.get(0);
	}

	/**
	 * @Title monitor
	 * @Description 获取监控器
	 * @Author JieHong
	 * @Date 2016年9月14日 下午5:07:16
	 * @return
	 */
	public Monitor monitor() {
		return aggrate.getMonitor();
	}

	/**
	 * @Title isComplete
	 * @Description 是否还清
	 * @Author JieHong
	 * @Date 2016年9月14日 下午4:13:08
	 * @return
	 */
	public boolean isComplete() {
		return monitor().aggregate().isComplete();
	}

}
