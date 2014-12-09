package com.Ranjith.testapp;

public class Items {
	
	private String name;
	private double price;
	private double qty_in_stock;
	
	
	public Items(String name, double i, double d) {
		this.name = name;
		this.price = i;
		this.qty_in_stock = d;
	}
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public double getQty_in_stock() {
		return qty_in_stock;
	}

}


