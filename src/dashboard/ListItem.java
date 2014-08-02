package dashboard;

public class ListItem {

	private String fileID;
	private String dateTime;
	private boolean isFinished;
	private String devName;
	private String serviceLocation;
	private String errorMessage;
	private String server;
	private String environment;
	private String stackTrace;
	private String service;
	private String expectID;
	private String analystName;
	
 
 	public String getExpectID() {
 		return expectID;
 	}
 
 
 	public void setExpectID(String expectID) {
 		this.expectID = expectID;
 	}
 
 
 	public String getAnalystName() {
 		return analystName;
 	}
 
 
 	public void setAnalystName(String analystName) {
 		this.analystName = analystName;
 	}
	
	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public String getEnvironment() {
		return environment;
	}


	public void setEnvironment(String environment) {
		this.environment = environment;
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


	public ListItem(String fileID)
	{
		this.fileID=fileID;
		isFinished=false;
		devName="";
		setServiceLocation("");
	}
	
	public ListItem(String fileID, String dateTime, String server, String stack, String error, String service)
	{
		this.fileID=fileID;
		this.dateTime = dateTime;
		this.server = server;
		this.stackTrace = stack;
		this.errorMessage = error;
		this.service = service;
	}
	
	public ListItem() {
		// TODO Auto-generated constructor stub
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
	
	
}

