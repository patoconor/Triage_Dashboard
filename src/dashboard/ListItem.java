package dashboard;

public class ListItem {

	private String fileID;
	private String expectID;
	private String dateTime;
	private boolean isFinished;
	private String devName;
	private String analystName;
	private String serviceLocation;
	private String environment;
	
	public ListItem(String fileID)
	{
		this.fileID=fileID;
		isFinished=false;
		devName="";
		setServiceLocation("");
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


	public String getEnvironment() {
		return environment;
	}


	public void setEnvironment(String environment) {
		this.environment = environment;
	}


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
	
	
}
