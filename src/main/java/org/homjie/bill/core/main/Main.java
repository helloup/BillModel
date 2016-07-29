package org.homjie.bill.core.main;

import java.math.BigDecimal;

import org.homjie.bill.core.Charge;
import org.homjie.bill.core.Item;
import org.homjie.bill.core.Repay;
import org.homjie.bill.core.SettleCharge;
import org.homjie.bill.core.SettleItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) {
		// test1();
		test2();
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
