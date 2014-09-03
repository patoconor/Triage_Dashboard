package dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ListItem {

	private String FullLine;
	
	private boolean isFinished;
	private boolean isGatheringInfo;
	private String fileID;
	private String devName;
	private String date;
	private String time;
	private String serviceLocation;
	private String errorMessage;
	private String server;
	private String stackTrace;
	private String service;
	private String dateTime;
	private String status;
	private int erid;
	private int expectNum;

	private int selectedExpectNum;
	private ArrayList <String> expectDateList;
	private ArrayList <String> expectIDList;
	private ArrayList <String> statusList;
	private ArrayList <String> analystNameList;
	private ArrayList <String> environmentList;
	private ArrayList <String> startTimeList;
	private ArrayList <String> endTimeList;
	
	public ListItem(String Line,int erid)
	{
		FullLine=Line;
		
		isFinished=false;
		this.fileID="";
		devName="";
		date="";
		time="";
		serviceLocation="";
		errorMessage="";
		server="";
		stackTrace="";
		status="";
		service="";
		this.erid=erid;
		
		expectNum=0;
		selectedExpectNum=0;
		expectIDList = new ArrayList <String> ();
		statusList = new ArrayList <String> ();
		analystNameList = new ArrayList <String> ();
		environmentList = new ArrayList <String> ();
		expectDateList = new ArrayList <String> ();
		startTimeList = new ArrayList <String> ();
		endTimeList = new ArrayList <String> ();
		
	    parseLineFromFile(getFullLine(),erid);
	}
	
	public ListItem(String fileID, String dateTime, String server, String stack, String error, String service)
	{
		setFileID(fileID);
		setDateTime(dateTime);
		setServer(server);
		setStackTrace(stack);
		setErrorMessage(error);
		setService(service);
	}
	
	private void parseLineFromFile(String Line, int erid)
	{
		List<String> list = Lists.newArrayList(Splitter.on("|").split(Line));
		if(list.size()<=10)
		{
		setFileID(list.get(0));
		setStatus(list.get(1));
		setErid(erid);
		setDate(list.get(2));
		setTime(list.get(3));
		setServer(list.get(4));
		setServiceLocation(list.get(5));
		setDevName(list.get(6));
		setErrorMessage(list.get(7));
		setStackTrace(list.get(8));
		//setExpectNum(Integer.parseInt(list.get(9)));
		}
		if(list.size()>10)
		{
		setFileID(list.get(0));
		setStatus(list.get(1));
		setDate(list.get(2));
		setTime(list.get(3));
		setServer(list.get(4));
		setServiceLocation(list.get(5));
		setDevName(list.get(6));
		setErrorMessage(list.get(7));
		setStackTrace(list.get(8));
		setExpectNum(Integer.parseInt(list.get(9)));
		}
		int i = 10;
		int expectNum=0;
		if(list.size()>10){
		while(!list.get(i).equals("ENDOFEXPECTATIONS"))
		{
			if(list.get(i).equals("STARTEXPECTATION"))
			{
				i++;
				addExpectIDListItem(list.get(i));
				i++;
				addStatusListItem(list.get(i));
				i++;
				addAnalystNameListItem(list.get(i));
				i++;
				addEnvironmentListItem(list.get(i));
				i++;
				addExpectDateListItem(list.get(i));
				i++;
				addStartTimeListItem(list.get(i));
				i++;
				addEndTimeListItem(list.get(i));
			}
			i++;	
			expectNum++;
		}
		}
	}
	public int getErid() {
		return erid;
	}

	public void setErid(int erid) {
		this.erid = erid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}


	public String getServiceLocation() {
		return serviceLocation;
	}


	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	
	public String getStackTrace() {
		return stackTrace;
	}


	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public ArrayList <String> getExpectIDList() {
		return expectIDList;
	}


	public void addExpectIDListItem(String expectIDList) {
		this.expectIDList.add(expectIDList);
	}


	public ArrayList <String> getStatusList() {
		return statusList;
	}


	public void addStatusListItem(String statusList) {
		this.statusList.add(statusList);
	}


	public ArrayList <String> getAnalystNameList() {
		return analystNameList;
	}


	public void addAnalystNameListItem(String analystNameList) {
		this.analystNameList.add(analystNameList);
	}


	public ArrayList <String> getEnvironmentList() {
		return environmentList;
	}


	public void addEnvironmentListItem(String environmentList) {
		this.environmentList.add(environmentList);
	}


	public ArrayList <String> getStartTimeList() {
		return startTimeList;
	}


	public void addStartTimeListItem(String startTimeList) {
		this.startTimeList.add(startTimeList);
	}


	public ArrayList <String> getEndTimeList() {
		return endTimeList;
	}


	public void addEndTimeListItem(String endTimeList) {
		this.endTimeList.add(endTimeList);
	}


	public int getExpectNum() {
		return expectNum;
	}


	public void setExpectNum(int expectNum) {
		this.expectNum = expectNum;
	}


	public ArrayList <String> getExpectDateList() {
		return expectDateList;
	}


	public void addExpectDateListItem(String expectDate) {
		this.expectDateList.add(expectDate);
	}


	public int getSelectedExpectNum() {
		return selectedExpectNum;
	}


	public void setSelectedExpectNum(int selectedExpectNum) {
		this.selectedExpectNum = selectedExpectNum;
	}


	public boolean isGatheringInfo() {
		return isGatheringInfo;
	}


	public void setGatheringInfo(boolean isGatheringInfo) {
		this.isGatheringInfo = isGatheringInfo;
	}


	public String getFullLine() {
		return FullLine;
	}


	public void setFullLine(String fullLine) {
		FullLine = fullLine;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	
}
