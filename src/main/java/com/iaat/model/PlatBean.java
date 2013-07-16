package com.iaat.model;
public class PlatBean implements Comparable<PlatBean> {
	private String name;
	private int usernum;
	private int newuser;
	private String stats;
	private int weeknum;
	public int getWeeknum() {
		return weeknum;
	}
	public void setWeeknum(int weeknum) {
		this.weeknum = weeknum;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	@SuppressWarnings("unused")
	private int olduser;
	private String date;
	public void setOlduser(int olduser) {
		this.olduser = this.usernum-this.newuser;
	}
	public int getNewuser() {
		return newuser;
	}
	public void setNewuser(int newuser) {
		this.newuser = newuser;
	}
	public int getOlduser() {
		return olduser=(this.usernum-this.newuser);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = getDate(date,true);
	}
	
	public void setDate(String date,boolean flag) {
		this.date = getDate(date,flag);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUsernum() {
		return usernum;
	}
	public void setUsernum(int usernum) {
		this.usernum = usernum;
	}
	private String getDate(String str,boolean flag){
			if(!flag)return str;
			if(str.contains("|")){
				String[] temp = str.split("\\|",2);
				if(temp!=null){
						return temp[1].substring(0,10)+"~"+temp[0].substring(0,10);
				}else{
					return "";
				}
			}else{
				if(str.length()>10)
				return str.substring(0,10);
				return str;
			}
		
	}
	@Override
	public int compareTo(PlatBean o) {
		return this.getDate().compareTo(o.getDate());
	}
}
