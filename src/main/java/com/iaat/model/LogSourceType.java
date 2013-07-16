package com.iaat.model;


public enum LogSourceType {
	 TIME(1),TOTAL(2),OUTER(3),TOM(4),XPRESS(5);
	  private final int value;

	  private LogSourceType(int value) {
	    this.value = value;
	  }
	  public int getValue() {
	    return value;
	  }

	  public static LogSourceType findByValue(int value) { 
	    switch (value) {
	      case 1:
	        return TIME;
	      case 2:
	        return TOTAL;
	      case 3:
	        return OUTER;
	      case 4:
	        return TOM;
	      case 5:
	        return XPRESS;
	      default:
	        	return null;
	    }
	  }

}
