package com.glassera.railgadi.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glassera.railgadi.entity.PNRInfo;
import com.glassera.railgadi.exception.RailException;
import com.glassera.railgadi.service.PNRInquiryService;

public class PNRInquiryServiceImpl implements PNRInquiryService{

    private String url = "";
    private PNRInfo pnrInfo;
    private String pnrNumber;
    private String htmlOutPut;
	
	public PNRInquiryServiceImpl() {
		super();
		this.url = "http://www.indianrail.gov.in/cgi_bin/inet_pnrstat_cgi.cgi";
	}

	public PNRInfo getPNRStatus(String pnrNumber) throws RailException {
		pnrInfo = null;
        try{
            this.pnrNumber = pnrNumber;
            pnrInfo = new PNRInfo();
            getPNRDetailHTML();
            parseHTMl();
        }catch(Exception e){
            pnrInfo = null;
            throw new RailException(e.getMessage());
        }
        return pnrInfo;
	}
	
	public String getPNRInquiryHTML() throws RailException {
		try{
			getPNRDetailHTML();
    	return "<html><table>"+getHTMLTable()+"</table></html>";
		}catch(Exception e){
			throw new RailException(e.getMessage());
		}
	}

	
	private void getPNRDetailHTML() throws RailException {
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
        try {
            String data = URLEncoder.encode("lccp_pnrno1", "UTF-8") + "=" + URLEncoder.encode(pnrNumber, "UTF-8");
            URL url = new URL(this.url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20100101 Firefox/15.0");
            
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            htmlOutPut = new String();
            while ((line = rd.readLine()) != null) {
                htmlOutPut += line;
                //System.out.println(line);
            }
            wr.close();
            rd.close();
        } catch (UnsupportedEncodingException e) {
        	throw new RailException(e.getMessage());
		} catch (MalformedURLException e) {
			throw new RailException(e.getMessage());
		} catch (IOException e) {
			throw new RailException(e.getMessage());
		} finally {
			htmlOutPut = "";
        	try {
				if(wr != null) wr.close();
				if(rd != null) rd.close();
			} catch (IOException e) {
				throw new RailException(e.getMessage());
			}
        }
    }
	
	private void parseHTMl() throws RailException {       
        try {
            Document doc = Jsoup.parse(htmlOutPut);
            Element jouneyDetaiTable = doc.getElementsByClass("table_border").get(0);
            Elements journeyDetails = jouneyDetaiTable.getElementsByClass("table_border_both");

            Element pnrDetailTable = doc.getElementById("center_table");

            Elements pnrDetailTRs = pnrDetailTable.getElementsByTag("tr");
            Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

            for (int i = 0; i < pnrDetailTRs.size(); i++) {
                if (i == 0) {
                    continue;
                } else {
                    Elements details = pnrDetailTRs.get(i).getElementsByClass("table_border_both");
                    if (details.get(0) != null && details.get(0).getElementsByTag("b").size() > 0 && details.get(0).getElementsByTag("b").get(0).text().contains("Passenger")) {
                        List<String> list = new ArrayList<String>();
                        list.add(details.get(0).getElementsByTag("b").get(0).text());
                        list.add(details.get(1).getElementsByTag("b").get(0).text());
                        list.add(details.get(2).getElementsByTag("b").get(0).text());
                        map.put(i, list);
                    } else {
                        pnrInfo.setChartStatus(details.get(0).text());
                        break;
                    }
                }

            }
            pnrInfo.setPnrDetails(map);
            pnrInfo.setPnrNumber(pnrNumber);
            pnrInfo.setTrainNumber(journeyDetails.get(0).text());
            pnrInfo.setTrainName(journeyDetails.get(1).text());
            pnrInfo.setJourneyDate(journeyDetails.get(2).text());
            pnrInfo.setFromStation(journeyDetails.get(3).text());
            pnrInfo.setToStation(journeyDetails.get(4).text());
            pnrInfo.setTrainClass(journeyDetails.get(7).text());
        } catch (Exception e) {
            htmlOutPut = "";
            pnrInfo = null;
            throw new RailException(e.getMessage());
        }
    }
	
	private String getHTMLTable(){
    	Document doc = Jsoup.parse(htmlOutPut);
        //Element jouneyDetaiTable = doc.getElementsByClass("table_border").get(0);
        //Elements journeyDetails = jouneyDetaiTable.getElementsByClass("table_border_both");

        Element pnrDetailTable = doc.getElementById("center_table");
        
        return pnrDetailTable.html();
    }

	
}
