package com.glassera.railgadi.service;

import java.util.List;

import com.glassera.railgadi.entity.TrainInfo;
import com.glassera.railgadi.exception.RailException;

public interface TrainService {

	public List<TrainInfo> searchTrains(String from, String to) throws RailException;
	public TrainInfo getRoute(TrainInfo train) throws RailException;
	public TrainInfo getBerthAvailibility(TrainInfo train) throws RailException;
	
}
