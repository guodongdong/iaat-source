package com.iaat.json.bounce;

public enum BounceType {
	RATE1(1),RATE2(2),RATE3(3),RATE4(4),RATE5(5),RATE6(6),RATE7(7),RATE8(8),RATE9(9),RATE10(10),RATE11(11),RATE12(12);
	private final int value;
	BounceType(int value){
		this.value  = value;
	}
	public int getValue() {
		return value;
	}
	
}
