package com.glassera.railgadi.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glassera.railgadi.entity.BerthAvailibility;
import com.glassera.railgadi.entity.TrainInfo;
import com.glassera.railgadi.entity.TrainRoute;
import com.glassera.railgadi.exception.RailException;
import com.glassera.railgadi.service.TrainService;

public class TrainServiceImpl implements TrainService{

	private List<String> url;
	private static String FIRST_AC = "1A";
	private static String SECOND_AC = "2A";
	private static String THIRD_AC = "3A";
	private static String THIRD_AC_ECONOMY = "3E";
	private static String AC_CHAIR_CAR = "CC";
	private static String FIRST_CLASS = "FC";
	private static String SLEEPER_CLASS = "SL";
	private static String SECOND_CLASS = "2S";

	private final static LinkedList<String> CLASS_LIST = new LinkedList<String>();
	private static boolean AVAIL_SL, AVAIL_3A, AVAIL_2A, AVAIL_1A, AVAIL_3E, AVAIL_FC, AVAIL_CC, AVAIL_2S;
	private static int INDEX_SL, INDEX_3A, INDEX_2A, INDEX_1A, INDEX_3E, INDEX_FC, INDEX_CC, INDEX_2S;

	public TrainServiceImpl() {
		super();
		url = new ArrayList<String>();
		url.add(0,"http://erail.in/");
		url.add(1,"http://www.indianrail.gov.in/cgi_bin/inet_trnnum_cgi.cgi");
		url.add(2,"http://www.indianrail.gov.in/cgi_bin/inet_accavl_cgi1.cgi");

		CLASS_LIST.add("SL");
		CLASS_LIST.add("3A");
		CLASS_LIST.add("2A");
		CLASS_LIST.add("1A");
		CLASS_LIST.add("3E");
		CLASS_LIST.add("FC");
		CLASS_LIST.add("CC");
		CLASS_LIST.add("2S");
	}

	public List<TrainInfo> searchTrains(String from, String to)
			throws RailException {
		List<TrainInfo> trains = new ArrayList<TrainInfo>();
        try {
            String completeURL = url.get(0) + "/rail/getTrains.aspx?Station_From="+from+"&Station_To="+to+"&DataSource=0&&Language=0";
            URL siteURL = new URL(completeURL);
            Document document = Jsoup.parse(siteURL, 50000);
            String output = document.text();

            String[] tokens = output.split("\\^");

            for(int i=0; i<tokens.length; i++){
                tokens[i]+="~~~~~~";
                String[] innerTokens = tokens[i].split("~");
                if(i!=0){
                   
                    TrainInfo train = new TrainInfo();

                    train.setTrainId(i+"");
                    train.setTrainNumber(innerTokens[0]);
                    train.setTrainName(innerTokens[1]);
                    train.setPantryAvailable(innerTokens[35].equalsIgnoreCase("1") ? true : false);
                    train.setOrigin(innerTokens[7]);
                    train.setArrivalTime(innerTokens[10]);
                    train.setDestination(innerTokens[9]);
                    train.setDepartureTime(innerTokens[11]);
                    train.setTravelHour(innerTokens[12]);

                     
                    train.setMonday(innerTokens[13].charAt(0) == ('1') ? true : false);
                    if(train.isMonday()){
                        train.getAvailableDays().replace(0, 1, "Y");
                    }
                    train.setTuesday(innerTokens[13].charAt(1) == ('1') ? true : false);
                    if(train.isTuesday()){
                        train.getAvailableDays().replace(1, 2, "Y");
                    }
                    train.setWednesday(innerTokens[13].charAt(2) == ('1') ? true : false);
                    if(train.isWednesday()){
                        train.getAvailableDays().replace(2, 3, "Y");
                    }
                    train.setThursday(innerTokens[13].charAt(3) == ('1') ? true : false);
                    if(train.isThursday()){
                        train.getAvailableDays().replace(3, 4, "Y");
                    }
                    train.setFriday(innerTokens[13].charAt(4) == ('1') ? true : false);
                    if(train.isFriday()){
                        train.getAvailableDays().replace(4, 5, "Y");
                    }
                    train.setSaturday(innerTokens[13].charAt(5) == ('1') ? true : false);
                    if(train.isSaturday()){
                        train.getAvailableDays().replace(5, 6, "Y");
                    }
                    train.setSunday(innerTokens[13].charAt(6) == ('1') ? true : false);
                    if(train.isSunday()){
                        train.getAvailableDays().replace(6, 7, "Y");
                    }


                    //21
                    train.setAvailable1A(innerTokens[21].charAt(0) == ('1')  ? true : false);
                    train.setAvailable2A(innerTokens[21].charAt(1) == ('1') ? true : false);
                    train.setAvailable3A(innerTokens[21].charAt(2) == ('1') ? true : false);
                    train.setAvailableCC(innerTokens[21].charAt(3) == ('1') ? true : false);
                    train.setAvailableFC(innerTokens[21].charAt(4) == ('1') ? true : false);
                    train.setAvailableSL(innerTokens[21].charAt(5) == ('1') ? true : false);
                    train.setAvailable2S(innerTokens[21].charAt(6) == ('1') ? true : false);
                    train.setAvailable3E(innerTokens[21].charAt(7) == ('1') ? true : false);

                    trains.add(train);
                }

            }
        } catch (IOException ex) {
            throw new RailException(ex.getMessage());
        } catch (Exception e){
        	throw new RailException(e.getMessage());
        }
        return trains;
	}

	public TrainInfo getRoute(TrainInfo train) throws RailException {
		try {
            String htmlOutPut = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(this.url.get(1));
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("lccp_trnname", train.getTrainNumber()));
            nameValuePairs.add(new BasicNameValuePair("getIt", "Get+Schedule"));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20100101 Firefox/15.0");
            post.setHeader("Referer", "http://www.indianrail.gov.in/inet_trn_num.html");
            HttpResponse response = client.execute(post);
            client.getParams().setParameter("http.socket.timeout", 10000);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                htmlOutPut += line;
                System.out.println(line);
            }

            Document doc = Jsoup.parse(htmlOutPut);
            Element routeDetailTable = doc.getElementsByClass("table_border_both").get(2);
            Elements routeTableRows = routeDetailTable.getElementsByTag("tr");

            train.setTrainRoute(new ArrayList<TrainRoute>());
            for (int i = 0; i < routeTableRows.size(); i++) {
                if (i == 0) {
                    continue;
                }
                TrainRoute trainRoute = new TrainRoute();
                trainRoute.setIndex(Integer.parseInt(routeTableRows.get(i).getElementsByTag("td").get(0).text()));
                trainRoute.setStationCode(routeTableRows.get(i).getElementsByTag("td").get(1).text());
                trainRoute.setStationName(routeTableRows.get(i).getElementsByTag("td").get(2).text());
                trainRoute.setRouteNumber(routeTableRows.get(i).getElementsByTag("td").get(3).text());
                if (i == 1) {
                    trainRoute.setArrivalTime(routeTableRows.get(i).getElementsByTag("td").get(4).getElementsByTag("font").text());
                } else {
                    trainRoute.setArrivalTime(routeTableRows.get(i).getElementsByTag("td").get(4).text());
                }
                if (i == routeTableRows.size() - 1) {
                    trainRoute.setDepartureTime(routeTableRows.get(i).getElementsByTag("td").get(5).getElementsByTag("font").text());
                } else {
                    trainRoute.setDepartureTime(routeTableRows.get(i).getElementsByTag("td").get(5).text());
                }
                trainRoute.setHaltTime(routeTableRows.get(i).getElementsByTag("td").get(6).text());
                trainRoute.setDistance(routeTableRows.get(i).getElementsByTag("td").get(7).text());
                trainRoute.setDay(routeTableRows.get(i).getElementsByTag("td").get(8).text());
                train.getTrainRoute().add(trainRoute);
            }

        } catch (URISyntaxException uri) {
        	throw new RailException(uri.getMessage());
        } catch (UnsupportedEncodingException ex) {
        	throw new RailException(ex.getMessage());
        } catch (MalformedURLException me) {
        	throw new RailException(me.getMessage());
        } catch (IOException io) {
        	throw new RailException(io.getMessage());
        } catch (HttpException he) {
        	throw new RailException(he.getMessage());
        }
        return train;
	}

	public TrainInfo getBerthAvailibility(TrainInfo train) throws RailException {
		try {
            StringBuffer trainDetail = new StringBuffer("                      ");
            trainDetail.replace(0, 5, train.getTrainNumber());
            trainDetail.replace(5, 9, train.getOrigin());
            trainDetail.replace(9, 13, train.getDestination());
            trainDetail.replace(13, 14, "O");
            trainDetail.replace(14, 21, train.getAvailableDays().toString());
            trainDetail.replace(21, 22, "B");

            List<BerthAvailibility> list = new ArrayList<BerthAvailibility>(6);
            for(int i=0; i<7; i++){
                BerthAvailibility avail = new BerthAvailibility();
                list.add(avail);
            }
            train.setAvailibility(list);

            String htmlOutPut = "";
            Document doc;
            Elements berthDetailTables;

            if (train.isAvailableSL() && !AVAIL_SL) {
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, SLEEPER_CLASS);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(SLEEPER_CLASS, availibilityTableRows, train.getAvailibility(), 0);
                }
            }

            if (train.isAvailable1A() && !AVAIL_1A) {
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, FIRST_AC);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(FIRST_AC, availibilityTableRows, train.getAvailibility(), 0);
                }
            }
            if(train.isAvailable2A() && !AVAIL_2A){
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, SECOND_AC);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(SECOND_AC, availibilityTableRows, train.getAvailibility(), 0);
                }
            }
            if(train.isAvailable3A() && !AVAIL_3A){
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, THIRD_AC);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(THIRD_AC, availibilityTableRows, train.getAvailibility(), 0);
                }
            }

            if (train.isAvailable3E() && !AVAIL_3E) {
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, THIRD_AC_ECONOMY);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(THIRD_AC_ECONOMY, availibilityTableRows, train.getAvailibility(), 0);
                }
            }

            if (train.isAvailableCC() && !AVAIL_CC) {
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, AC_CHAIR_CAR);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(AC_CHAIR_CAR, availibilityTableRows, train.getAvailibility(), 0);
                }
            }else if(train.isAvailable2S() && !AVAIL_2S){
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, SECOND_CLASS);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(SECOND_CLASS, availibilityTableRows, train.getAvailibility(), 0);
                }
            }

            if (train.isAvailableFC() && !AVAIL_FC) {
                htmlOutPut = getAvailibilityString(train.getTrainNumber(), train.getJourneyDay(), train.getJourneyMonth(), train.getQuota(), trainDetail, FIRST_CLASS);
                doc = Jsoup.parse(htmlOutPut);
                berthDetailTables = doc.getElementsByClass("table_border");
                if (berthDetailTables != null && berthDetailTables.size() > 0) {
                    Elements availibilityTableRows = berthDetailTables.get(1).getElementsByTag("tr");
                    populateAvailibility(FIRST_CLASS, availibilityTableRows, train.getAvailibility(), 0);
                }
            }

        } catch (URISyntaxException uri) {
        	throw new RailException(uri.getMessage());
        } catch (UnsupportedEncodingException ex) {
        	throw new RailException(ex.getMessage());
        } catch (MalformedURLException me) {
        	throw new RailException(me.getMessage());
        } catch (IOException io) {
        	throw new RailException(io.getMessage());
        } catch (HttpException he) {
        	throw new RailException(he.getMessage());
        }
        return train;
	}
	
	private String getAvailibilityString(String trainNumber, String jouneyDay, String jouneyMonth, String quota, StringBuffer trainDetail, String berthClass) throws URISyntaxException, UnsupportedEncodingException, HttpException, IOException{
        String output = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.url.get(2));
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("lccp_trnno", trainNumber));
        nameValuePairs.add(new BasicNameValuePair("lccp_day", jouneyDay));
        nameValuePairs.add(new BasicNameValuePair("lccp_month", jouneyMonth));
        nameValuePairs.add(new BasicNameValuePair("lccp_trndtl", trainDetail.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lccp_quota", quota));
        nameValuePairs.add(new BasicNameValuePair("lccp_classopt", berthClass));
        nameValuePairs.add(new BasicNameValuePair("lccp_class1", berthClass));
        nameValuePairs.add(new BasicNameValuePair("lccp_class2", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class3", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class4", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class5", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class6", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class7", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class8", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_class9", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_cls10", "ZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_conc", "ZZZZZZ"));
        nameValuePairs.add(new BasicNameValuePair("lccp_age", "ADULT_AGE"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20100101 Firefox/15.0");
        post.setHeader("Referer", "http://www.indianrail.gov.in/cgi_bin/inet_srcdest_cgi_time.cgi");
        HttpResponse response = client.execute(post);
        client.getParams().setParameter("http.socket.timeout", 10000);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            output+=line;
            System.out.println(line);
        }
        return output;
    }
	
	private void populateAvailibility(String berthClass, Elements availibilityTableRows, List<BerthAvailibility> availibilityList, int index){
        //List<BerthAvailibility> list = new ArrayList<BerthAvailibility>();
         AVAIL_SL = false;
        AVAIL_3A = false;
        AVAIL_2A = false;
        AVAIL_1A = false;
        AVAIL_3E = false;
        AVAIL_FC = false;
        AVAIL_CC = false;
        AVAIL_2S = false;
        for(int i=0; i<availibilityTableRows.size(); i++){
            if(i==0 || i%2 == 0){
                if(i == 0){
                    updateClassAvailableFlags(availibilityTableRows.get(i).getElementsByTag("th").get(2).text().trim(), 2);
                    updateClassAvailableFlags(availibilityTableRows.get(i).getElementsByTag("th").get(3).text().trim(), 3);
                }
                continue;
            }
            availibilityList.get(index).setIndex(Integer.parseInt(availibilityTableRows.get(i).getElementsByTag("td").get(0).text().trim()));
            availibilityList.get(index).setDate(availibilityTableRows.get(i).getElementsByTag("td").get(1).text().trim());
            if(AVAIL_SL){
                availibilityList.get(index).setSleeper(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_SL).text().trim());
            }
            if(AVAIL_3A){
                availibilityList.get(index).setThirdAC(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_3A).text().trim());
            }
            if(AVAIL_3E){
                availibilityList.get(index).setThirdACEconomy(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_3E).text().trim());
            }
            if(AVAIL_CC){
                availibilityList.get(index).setChariCarAC(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_CC).text().trim());
            }
            if(AVAIL_2S){
                availibilityList.get(index).setSecondSeat(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_2S).text().trim());
            }
            if(AVAIL_FC){
                availibilityList.get(index).setFirstClass(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_FC).text().trim());
            }
            if(AVAIL_1A){
                availibilityList.get(index).setFirstAC(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_1A).text().trim());
            }
            if(AVAIL_2A){
                availibilityList.get(index).setSecondAC(availibilityTableRows.get(i).getElementsByTag("td").get(INDEX_2A).text().trim());
            }
            index++;
        }
    }
	
	private void updateClassAvailableFlags(String strClass, int classIndex){
        strClass = strClass.split("-")[1].trim();
        if(CLASS_LIST.contains(strClass)){
            if(CLASS_LIST.indexOf(strClass) == 0){
                AVAIL_SL = true;
                INDEX_SL = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 1){
                AVAIL_3A = true;
                INDEX_3A = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 2){
                AVAIL_2A = true;
                INDEX_2A = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 3){
                AVAIL_1A = true;
                INDEX_1A = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 4){
                AVAIL_3E = true;
                INDEX_3E = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 5){
                AVAIL_FC = true;
                INDEX_FC = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 6){
                AVAIL_CC = true;
                INDEX_CC = classIndex;
            }else if(CLASS_LIST.indexOf(strClass) == 7){
                AVAIL_2S = true;
                INDEX_2S = classIndex;
            }
        }
    }

}
