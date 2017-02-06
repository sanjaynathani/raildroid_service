package com.glassera.railgadi.service;

import com.glassera.railgadi.service.impl.PNRInquiryServiceImpl;
import com.glassera.railgadi.service.impl.TrainServiceImpl;

public final class RailgadiRegistry {
	
	private static TrainService trainService;
	private static PNRInquiryService pnrInquiryService;

	private RailgadiRegistry() {
		super();
	}
	
	public static final TrainService getTrainService(){
		if(trainService == null){
			trainService = new TrainServiceImpl();
		}
		return trainService;
	}
	
	public static final PNRInquiryService getPNRInquiryService(){
		if(pnrInquiryService == null){
			pnrInquiryService = new PNRInquiryServiceImpl();
		}
		return pnrInquiryService;
	}

	
}
