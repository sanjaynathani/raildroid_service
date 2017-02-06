package com.glassera.railgadi.service;

import com.glassera.railgadi.entity.PNRInfo;
import com.glassera.railgadi.exception.RailException;

public interface PNRInquiryService {

	public PNRInfo getPNRStatus(String pnrNumber) throws RailException;
	public String getPNRInquiryHTML() throws RailException;
}
