package com.glassera.railgadi.entity;

import java.io.Serializable;


public class BerthAvailibility implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;
	private String date;
	private String firstAC;
	private String secondAC;
	private String thirdAC;
	private String thirdACEconomy;
	private String chariCarAC;
	private String sleeper;
	private String secondSeat;
        private String firstClass;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
	public String getFirstAC() {
		return firstAC;
	}
	public void setFirstAC(String firstAC) {
		this.firstAC = firstAC;
	}
	public String getSecondAC() {
		return secondAC;
	}
	public void setSecondAC(String secondAC) {
		this.secondAC = secondAC;
	}
	public String getThirdAC() {
		return thirdAC;
	}
	public void setThirdAC(String thirdAC) {
		this.thirdAC = thirdAC;
	}
	public String getThirdACEconomy() {
		return thirdACEconomy;
	}
	public void setThirdACEconomy(String thirdACEconomy) {
		this.thirdACEconomy = thirdACEconomy;
	}
	public String getChariCarAC() {
		return chariCarAC;
	}
	public void setChariCarAC(String chariCarAC) {
		this.chariCarAC = chariCarAC;
	}
	public String getSleeper() {
		return sleeper;
	}
	public void setSleeper(String sleeper) {
		this.sleeper = sleeper;
	}
	public String getSecondSeat() {
		return secondSeat;
	}
	public void setSecondSeat(String secondSeat) {
		this.secondSeat = secondSeat;
	}
        public String getFirstClass() {
            return firstClass;
        }

        public void setFirstClass(String firstClass) {
            this.firstClass = firstClass;
        }
}
