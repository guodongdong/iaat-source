package com.iaat.model;

public enum CrossContrastType {
	
	UV(1),PV(2),WEB_PV(3),WAP_PV(4),TOTALUSER(5),NEWUSER(6),OLDUSER(7),ACTIVEUSER(8),ONCEACCESSUSER(9),ONLYLOGINUSER(10),AVGLOGINUSER(11),AVGACCESSUSER(12),AVGINTERVALTIME(13);
	  private final int value;

	  private CrossContrastType(int value) {
	    this.value = value;
	  }
	  public int getValue() {
	    return value;
	  }

	  public static CrossContrastType findByValue(int value) { 
	    switch (value) {
	      case 1:
	        return UV;
	      case 2:
	        return PV;
	      case 3:
	        return WEB_PV;
	      case 4:
	        return WAP_PV;
	      case 5:
	        return TOTALUSER;
	      case 6:
		    return NEWUSER;
	      case 7:
			return OLDUSER;
	      case 8:
		    return ACTIVEUSER;
	      case 9:
		    return ONCEACCESSUSER;
	      case 10:
		    return ONLYLOGINUSER;
	      case 11:
		    return AVGLOGINUSER;
	      case 12:
		    return AVGACCESSUSER;
	      case 13:
		    return AVGINTERVALTIME;
	      default:
	        	return null;
	    }
	  }
		
}
