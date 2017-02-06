package com.glassera.railgadi.entity;

import java.io.Serializable;
import java.util.List;

public class TrainInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trainId;
        private String trainNumber;
	private String trainName;
	private String origin;
	private String destination;
	private String departureTime;
	private String arrivalTime;
	private String journeyDays;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;

        private boolean pantryAvailable;
        private String travelHour;
        private boolean available1A;
        private boolean available2A;
        private boolean available3A;
        private boolean availableCC;
        private boolean availableFC;
        private boolean availableSL;
        private boolean available2S;
        private boolean available3E;

        private String journeyDay;
        private String journeyMonth;
        private String quota;
	
	private List<TrainRoute> trainRoute;
	private List<BerthAvailibility> availibility; //Upto Next 6 days

        private StringBuffer availableDays = new StringBuffer("NNNNNNN");

        public String getTrainId() {
            return trainId;
        }

        public void setTrainId(String trainId) {
            this.trainId = trainId;
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getJourneyDays() {
		return journeyDays;
	}
	public void setJourneyDays(String journeyDays) {
		this.journeyDays = journeyDays;
	}
	public boolean isMonday() {
		return monday;
	}
	public void setMonday(boolean monday) {
		this.monday = monday;
	}
	public boolean isTuesday() {
		return tuesday;
	}
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}
	public boolean isWednesday() {
		return wednesday;
	}
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}
	public boolean isThursday() {
		return thursday;
	}
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}
	public boolean isFriday() {
		return friday;
	}
	public void setFriday(boolean friday) {
		this.friday = friday;
	}
	public boolean isSaturday() {
		return saturday;
	}
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}
	public boolean isSunday() {
		return sunday;
	}
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}
	public List<TrainRoute> getTrainRoute() {
		return trainRoute;
	}
	public void setTrainRoute(List<TrainRoute> trainRoute) {
		this.trainRoute = trainRoute;
	}
	public List<BerthAvailibility> getAvailibility() {
		return availibility;
	}
	public void setAvailibility(List<BerthAvailibility> availibility) {
		this.availibility = availibility;
	}

        public boolean isPantryAvailable() {
            return pantryAvailable;
        }

        public void setPantryAvailable(boolean pantryAvailable) {
            this.pantryAvailable = pantryAvailable;
        }

        public String getTravelHour() {
            return travelHour;
        }

        public void setTravelHour(String travelHour) {
            this.travelHour = travelHour;
        }

        public boolean isAvailable1A() {
            return available1A;
        }

        public void setAvailable1A(boolean available1A) {
            this.available1A = available1A;
        }

        public boolean isAvailable2A() {
            return available2A;
        }

        public void setAvailable2A(boolean available2A) {
            this.available2A = available2A;
        }

        public boolean isAvailable2S() {
            return available2S;
        }

        public void setAvailable2S(boolean available2S) {
            this.available2S = available2S;
        }

        public boolean isAvailable3A() {
            return available3A;
        }

        public void setAvailable3A(boolean available3A) {
            this.available3A = available3A;
        }

        public boolean isAvailable3E() {
            return available3E;
        }

        public void setAvailable3E(boolean available3E) {
            this.available3E = available3E;
        }

        public boolean isAvailableCC() {
            return availableCC;
        }

        public void setAvailableCC(boolean availableCC) {
            this.availableCC = availableCC;
        }

        public boolean isAvailableFC() {
            return availableFC;
        }

        public void setAvailableFC(boolean availableFC) {
            this.availableFC = availableFC;
        }

        public boolean isAvailableSL() {
            return availableSL;
        }

        public void setAvailableSL(boolean availableSL) {
            this.availableSL = availableSL;
        }

        public String getJourneyDay() {
            return journeyDay;
        }

        public void setJourneyDay(String journeyDay) {
            this.journeyDay = journeyDay;
        }

        public String getJourneyMonth() {
            return journeyMonth;
        }

        public void setJourneyMonth(String journeyMonth) {
            this.journeyMonth = journeyMonth;
        }

        public StringBuffer getAvailableDays() {
            return availableDays;
        }
        public String getQuota() {
            return quota;
        }

        public void setQuota(String quota) {
            this.quota = quota;
        }
}
