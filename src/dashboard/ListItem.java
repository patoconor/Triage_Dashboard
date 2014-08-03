package dashboard;

import java.util.ArrayList;

public class ListItem {

	private boolean isFinished;
	private boolean isGatheringInfo;
	private String fileID;
	private String devName;
	private String dateTime;
	private String serviceLocation;
	private String errorMessage;
	private String server;
	private String stackTrace;
	private String service;
	
	
	private int expectNum;
	private int selectedExpectNum;
	private ArrayList <String> expectDateList;
	private ArrayList <String> expectIDList;
	private ArrayList <String> statusList;
	private ArrayList <String> analystNameList;
	private ArrayList <String> environmentList;
	private ArrayList <String> startTimeList;
	private ArrayList <String> endTimeList;
	
	public ListItem(String fileID)
	{
		isFinished=false;
		this.fileID=fileID;
		devName="";
		dateTime="";
		serviceLocation="";
		errorMessage="";
		server="";
		stackTrace="";
		service="";
		
		
		expectNum=0;
		selectedExpectNum=-1;
		expectIDList = new ArrayList <String> ();
		statusList = new ArrayList <String> ();
		analystNameList = new ArrayList <String> ();
		environmentList = new ArrayList <String> ();
		expectDateList = new ArrayList <String> ();
		startTimeList = new ArrayList <String> ();
		endTimeList = new ArrayList <String> ();
	}

	
	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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

	
}
