package com.glassera.railgadi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PNRInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pnrNumber;
	private String trainNumber;
	private String trainName;
	private String fromStation;
	private String toStation;
	private String journeyDate;
	private String trainClass;
	private String chartStatus;
	private Map<Integer,List<String>> pnrDetails;
	public String getPnrNumber() {
		return pnrNumber;
	}
	public void setPnrNumber(String pnrNumber) {
		this.pnrNumber = pnrNumber;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public String getToStation() {
		return toStation;
	}
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	public String getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	public String getTrainClass() {
		return trainClass;
	}
	public void setTrainClass(String trainClass) {
		this.trainClass = trainClass;
	}
	public Map<Integer, List<String>> getPnrDetails() {
		return pnrDetails;
	}
	public void setPnrDetails(Map<Integer, List<String>> pnrDetails) {
		this.pnrDetails = pnrDetails;
	}
	public String getChartStatus() {
		return chartStatus;
	}
	public void setChartStatus(String chartStatus) {
		this.chartStatus = chartStatus;
	}
	
	
	
}
