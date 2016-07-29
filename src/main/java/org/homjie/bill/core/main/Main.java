package org.homjie.bill.core.main;

import java.math.BigDecimal;

import org.homjie.bill.core.Charge;
import org.homjie.bill.core.Item;
import org.homjie.bill.core.MonitorCharge;
import org.homjie.bill.core.MonitorResult;
import org.homjie.bill.core.Repay;
import org.homjie.bill.core.SettleCharge;
import org.homjie.bill.core.SettleItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) {
		// test1();
		// test2();
		test3();
	}

	public static void test3() {
		MonitorCharge mc1 = new MonitorCharge(new Object());

		Item item1 = new Item(new BigDecimal("2"), new BigDecimal("1")).priority(2);
		Item item2 = new Item(new BigDecimal("3"), new BigDecimal("2")).priority(1);
		Charge charge1 = new Charge().addItem(item1).addItem(item2).priority(3);

		int ci1 = charge1.addMonitor(mc1);

		Item item3 = new Item(new BigDecimal("4"), new BigDecimal("3")).priority(2);
		Item item4 = new Item(new BigDecimal("5"), new BigDecimal("4")).priority(1);
		Charge charge2 = new Charge().addItem(item3).addItem(item4).priority(1).settle(SettleItem.SINGLE);

		Item item5 = new Item(new BigDecimal("6"), new BigDecimal("5")).priority(1);
		Item item6 = new Item(new BigDecimal("7"), new BigDecimal("6")).priority(2);
		Charge charge3 = new Charge().addItem(item5).addItem(item6).priority(4).settle(SettleItem.TOTAL);

		int ci2 = charge3.addMonitor(mc1);

		Item item7 = new Item(new BigDecimal("8"), new BigDecimal("7")).priority(1);
		Item item8 = new Item(new BigDecimal("9"), new BigDecimal("8")).priority(2);
		Charge charge4 = new Charge().addItem(item7).addItem(item8).priority(2);

		Charge charge5 = new Charge().addCharge(charge1).addCharge(charge4).priority(2).settle(SettleCharge.SINGLE);
		Charge charge6 = new Charge().addCharge(charge2).addCharge(charge3).priority(1).settle(SettleCharge.NONE);

		Charge charge = new Charge().addCharge(charge5).addCharge(charge6).settle(SettleCharge.NONE);
		BigDecimal money = Repay.result(charge, new BigDecimal("3"));

		System.out.println(money);
		System.out.println(gson.toJson(charge));

		System.out.println("-------------");
		int v1 = mc1.version(ci1);
		int v2 = mc1.version(ci2);

		MonitorResult h1 = mc1.history(v1);
		System.out.println(gson.toJson(h1));

		System.out.println("-------------");

		MonitorResult h2 = mc1.history(v2);
		System.out.println(gson.toJson(h2));
		
	}

	public static void test2() {
		Item item1 = new Item(new BigDecimal("8"), new BigDecimal("6")).priority(2);
		Item item2 = new Item(new BigDecimal("6"), new BigDecimal("0")).priority(1);
		Charge charge1 = new Charge().addItem(item1).addItem(item2).priority(1).settle(SettleItem.NONE);

		Item item3 = new Item(new BigDecimal("10"), new BigDecimal("5")).priority(3);
		Item item4 = new Item(new BigDecimal("3"), new BigDecimal("2")).priority(1);
		Item item5 = new Item(new BigDecimal("7"), new BigDecimal("7")).priority(2);
		Charge charge2 = new Charge().addItem(item3).addItem(item4).addItem(item5).priority(2).settle(SettleItem.SINGLE);

		Charge charge = new Charge().addCharge(charge1).addCharge(charge2).settle(SettleCharge.SINGLE);
		BigDecimal money = Repay.result(charge, new BigDecimal("12"));

		System.out.println(money);
		System.out.println(gson.toJson(charge));
	}

	public static void test1() {
		Item item1 = new Item(new BigDecimal("10"), new BigDecimal("5")).priority(3);
		Item item2 = new Item(new BigDecimal("6"), new BigDecimal("0")).priority(1);
		Item item3 = new Item(new BigDecimal("8"), new BigDecimal("6")).priority(2);

		Charge charge = new Charge();

		charge.addItem(item1).addItem(item2).addItem(item3).settle(SettleItem.SINGLE_MORE);
		BigDecimal money = Repay.result(charge, new BigDecimal("12"));

		System.out.println(money);
	}

}
