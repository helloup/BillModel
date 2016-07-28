package org.homjie.bill.core.main;

import java.math.BigDecimal;

import org.homjie.bill.core.Charge;
import org.homjie.bill.core.Item;
import org.homjie.bill.core.Result;
import org.homjie.bill.core.SettleCharge;

public class Main {
	
	public static void main(String[] args) {
		Item item1 = new Item(new BigDecimal("100.86"), new BigDecimal("45.5")); // 55.36
		Item item2 = new Item(new BigDecimal("65.15"), new BigDecimal("18")); // 47.15 += 102.51
		Item item3 = new Item(new BigDecimal("19"), new BigDecimal("0")); // 19 += 121.51
		Item item4 = new Item(new BigDecimal("5.5"), new BigDecimal("2")); // 3.5 += 125.01
		
		Charge charge1 = new Charge();
		Charge charge2 = new Charge();
		Charge charge3 = new Charge();
		
		charge1.addItem(item1);
		charge1.addItem(item2);
		charge2.addItem(item3);
		charge2.addItem(item4);
		
		charge3.addCharge(charge1).addCharge(charge2).settle(SettleCharge.TOTAL);
		
		BigDecimal money = Result.calculate(charge3, new BigDecimal("100"));
		System.out.println(money);
	}

}
