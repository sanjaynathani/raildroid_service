package com.glassera.railgadi.entity;

import java.io.Serializable;


public class TrainRoute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;
	private String stationCode;
	private String stationName;
	private String routeNumber;
	private String arrivalTime;
	private String departureTime;
	private String haltTime;
	private String distance;
	private String day;
	private String remarks;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getRouteNumber() {
		return routeNumber;
	}
	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getHaltTime() {
		return haltTime;
	}
	public void setHaltTime(String haltTime) {
		this.haltTime = haltTime;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
