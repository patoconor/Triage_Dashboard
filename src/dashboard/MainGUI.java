package dashboard;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.swing.event.*;


import java.util.*;
//Test Commit
//SplitPaneDemo itself is not a visible component.
public class MainGUI extends JPanel
                          implements ListSelectionListener {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static JFrame frame;
	
	private String sFistUser;
	private String sFistPass;
	private String sServerUser;
	private String sServerPass;
	
	
	private JSplitPane splitPane;
    private JSplitPane splitPaneBottom;
    
    private JButton bRefreshList;
    private JButton bChangeCredentials;
	private JButton bOptions;
	
    
    private JList<ListItem> list;
    private JPanel errorViewPanel;
    private JPanel pCredentials;
    
    DefaultListModel<ListItem> errorList;
    private JButton bGatherInfo;
    
    private JTextField tfFileID;
    private JTextField tfFailTime;
    private JTextField tfServiceName;
    private JTextField tfDeveloperName;
    
    
    private JTextField tfExpectationID;
    private JTextField tfEnvironment;
    private JTextField tfAnalystName;
    private JTextField tfExpectStatus;
    private JTextField tfStartTime;
    private JTextField tfEndTime;
   
    private JTextField tfServerName;
    private JButton bServer;
    
    private JComboBox<String> cbExpectationSelect;
    private JButton bViewExpectations;
    
    private JButton bServerLog;
    private JButton bErrorLog;
    
    private JTextPane tpErrorText;
    
    private JButton bLookingIntoIt;
    private JButton bReplyInFist;
    private JTextPane tpRecommendedAction;
    private JButton bTakeAction;
    
    
    private JDialog dCred;
    String[] textData= new String[4];
    
    
    
    public MainGUI() {

    	
    	
    	//list of errors in left pane
    	errorList= new DefaultListModel<>();
    	setupList();
        list = new JList<>(errorList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setCellRenderer(new ListItemRenderer());
        
        //right panel for viewing error details
        errorViewPanel = new JPanel();
        errorViewPanel.setLayout(null);
        errorViewPanel.setPreferredSize(new Dimension(900,500));
        
        //bottom panel for credentials and options
        pCredentials = new JPanel();
        pCredentials.setLayout(new GridLayout(0,3,10,0));
        pCredentials.setMinimumSize(new Dimension(0,1*screenSize.height/30));
        
        bRefreshList = new JButton();
        bRefreshList.setText("Refresh List");
        pCredentials.add(bRefreshList);
        bRefreshList.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run
			                
			            }}).start();}});
        
        bChangeCredentials = new JButton();
        bChangeCredentials.setText("Change Credentials");
        pCredentials.add(bChangeCredentials);
    	bChangeCredentials.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run
			        	if (JOptionPane.showConfirmDialog(null, "Are you sure you want to change your credentials?", "Request", 
							    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
							    == JOptionPane.YES_OPTION)
							{
								new File("C://Triage_Dashboard//credentials.txt").delete();
								setupLoginPanel();
							}
			        	
			        	
			            }}).start();}});
        
        
    	bOptions = new JButton();
    	bOptions.setText("Options");
        pCredentials.add(bOptions);
        bOptions.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run
			                
			            }}).start();}});
        
        //left pane
        JScrollPane errorListScroll = new JScrollPane(list);
        //right pane
        JScrollPane errorViewScroll = new JScrollPane(errorViewPanel);
        
        //Create a vertically split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,	errorListScroll, errorViewScroll);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(screenSize.width/20);
        //splitPane.setMinimumSize(new Dimension(1*screenSize.width/6, 1*screenSize.height/6));

        //create a horizontally split pane for the credentials and options
        splitPaneBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT,	splitPane, pCredentials);
        splitPaneBottom.setOneTouchExpandable(false);
        splitPaneBottom.setEnabled(false);
        splitPaneBottom.setResizeWeight(1);
    
        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(990, 2*screenSize.height/3));
        
        
	    credCheck();
	    if(new File("C://schema_creation//LoginCredentials.txt").isFile()==true){
			  	sFistUser = Encrypt.symmetricDecrypt(textData[0]);
				sFistPass = Encrypt.symmetricDecrypt(textData[1]);
				sServerUser = Encrypt.symmetricDecrypt(textData[2]);
				sServerPass = Encrypt.symmetricDecrypt(textData[3]);
		  }
	       
        
        setupErrorPanel();
        populateErrorPanel();
        
    }
    
    public void setupList()
    {
    		ListItem li1= new ListItem("63055");
    		errorList.addElement(li1);
    		ListItem li2= new ListItem("62177");
    		errorList.addElement(li2);
    		// need to fix developer on this one 
    		/*
    		ListItem li3= new ListItem("28098");
    		errorList.addElement(li3);
    		*/
    	
    	
    }
    
    //Listens to the list
    public void valueChanged(ListSelectionEvent e) {
        populateErrorPanel();
        
    }
    
    public ListItem getCurrentItem()
    {
    	return errorList.get(list.getSelectedIndex());
    }
    
    //Shows the corresponding information for a selected error
    private void setupErrorPanel() {
    	
    	JPanel pGatherArea = new JPanel();
    	pGatherArea.setBounds(-1,-1,301,61);
    	pGatherArea.setLayout(null);
    	pGatherArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pGatherArea);
    	
    	bGatherInfo = new JButton();
    	bGatherInfo.setBounds(10, 10 , 280,40);
    	bGatherInfo.setText("Gather Info");
    	pGatherArea.add(bGatherInfo);
    	bGatherInfo.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run Info Gather
			        	bGatherInfo.setEnabled(false);
			        	int gatherItem = list.getSelectedIndex();
			        	errorList.get(gatherItem).setGatheringInfo(true);
			        	clearExpectations();
			        	
			        	FIST Fdriver = new FIST(sFistUser,sFistPass,screenSize,true);
			        	frame.requestFocus();
			        	Fdriver.loginProd();
			            errorList.get(gatherItem).setDevName(Fdriver.getDevName(errorList.get(gatherItem).getFileID()));
			        	errorList.get(gatherItem).setServiceLocation(Fdriver.getLocation(errorList.get(gatherItem).getFileID()));
			        	
			        	Fdriver.loginConfig();
			        	Fdriver.getExpectationsPage(errorList.get(gatherItem).getFileID(),"07/28/2014");
			        	
			        	int iExpectNum=Fdriver.getNumberOfExpectations();
			        	
			        	int counter=1;
			        	for (int i = iExpectNum+1;i>1;i--)
			        	{
			        		errorList.get(gatherItem).addEnvironmentListItem(getEnvironmentName(Fdriver.getEnvironment(i)));
			        		errorList.get(gatherItem).addExpectIDListItem(Fdriver.getExpectID(i));
			        		errorList.get(gatherItem).addStatusListItem(Fdriver.getExpectStatus(i));
			        		errorList.get(gatherItem).addExpectDateListItem(counter+": "+Fdriver.getExpectDate(i));
			        		errorList.get(gatherItem).addStartTimeListItem(Fdriver.getStartTime(i));
			        		errorList.get(gatherItem).addEndTimeListItem(Fdriver.getEndTime(i));
			        		errorList.get(gatherItem).addAnalystNameListItem(Fdriver.getAnalyst(i));
			        	counter++;
			        	}
			        	
			        	errorList.get(gatherItem).setGatheringInfo(false);
			        	errorList.get(gatherItem).setExpectNum(iExpectNum);
			        	if(errorList.get(gatherItem).getExpectNum()>0)
			        	{
			        		errorList.get(gatherItem).setSelectedExpectNum(0);
			        	}

			        	if(gatherItem == list.getSelectedIndex())
			        	{
				        	populateErrorPanel();
			        	}
			        	
			        	Fdriver.closeDriver();
			        	
			            }}).start();}});
		
    	
    	JPanel pInfoFields = new JPanel();
    	pInfoFields.setBounds(299,-1,301,131);
    	
    	pInfoFields.setLayout(null);
    	pInfoFields.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pInfoFields);
    	
    	int yShift=30;
    	
        tfFileID = new JTextField();
        tfFileID.setBounds(50,10, 50, 20);
        tfFileID.setEditable(false);
        tfFileID.setBackground(Color.WHITE);
        pInfoFields.add(tfFileID);
		JLabel lFileID = new JLabel("File ID:");
		lFileID.setBounds(10, 10, 100, 20);
		pInfoFields.add(lFileID);
        
        
		
		tfFailTime = new JTextField();
		tfFailTime.setBounds(110, 10 + 1*yShift, 180, 20);
		tfFailTime.setEditable(false);
		tfFailTime.setBackground(Color.WHITE);
        pInfoFields.add(tfFailTime);
		JLabel lFailTime = new JLabel("Error Date/Time:");
		lFailTime.setBounds(10, 10 +1*yShift, 100, 20);
		pInfoFields.add(lFailTime);
		
        
		
		tfServiceName = new JTextField();
		tfServiceName.setBounds(70, 10 + 2*yShift, 220, 20);
		tfServiceName.setEditable(false);
		tfServiceName.setBackground(Color.WHITE);
        pInfoFields.add(tfServiceName);
		JLabel lServiceName = new JLabel("Location:");
		lServiceName.setBounds(10, 10 +2*yShift, 100, 20);
		pInfoFields.add(lServiceName);
		
		tfDeveloperName = new JTextField();
        tfDeveloperName.setBounds(77, 10 + 3*yShift, 213, 20);
        tfDeveloperName.setEditable(false);
        tfDeveloperName.setBackground(Color.WHITE);
        pInfoFields.add(tfDeveloperName);
		JLabel lDeveloperName = new JLabel("Developer:");
		lDeveloperName.setBounds(10, 10 +3*yShift, 100, 20);
		pInfoFields.add(lDeveloperName);
		
		
		
		JPanel pExpectations = new JPanel();
		pExpectations.setBounds(-1,129,601,131);
		pExpectations.setLayout(null);
		pExpectations.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pExpectations);
    	
    	/*
    	JLabel lExpectationHeader = new JLabel("Expectation Information:");
    	lExpectationHeader.setBounds(232, 0, 200, 20);
		pExpectations.add(lExpectationHeader);
		*/
		
		cbExpectationSelect = new JComboBox <String> ();
		cbExpectationSelect.setBounds(115, 10, 174, 20);
		pExpectations.add(cbExpectationSelect);
		JLabel lExpectationSelect = new JLabel("Pick Expectation:");
		lExpectationSelect.setBounds(10,10, 100, 20);
		pExpectations.add(lExpectationSelect);
		
		cbExpectationSelect.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {			  
				  getCurrentItem().setSelectedExpectNum(cbExpectationSelect.getSelectedIndex());
				  selectExpectation();
			  
			  
			  }});
		
		bViewExpectations = new JButton();
		bViewExpectations.setBounds(362, 10, 228, 20);
		bViewExpectations.setText("Open Expectations Page");
		pExpectations.add(bViewExpectations);
    	bViewExpectations.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	FIST Fdriver = new FIST(sFistUser,sFistPass,screenSize,false);
			        	Fdriver.loginConfig();
			        	Fdriver.getExpectationsPage(getCurrentItem().getFileID(),"07/28/2014");
			                
			            }}).start();}});
		
    	tfExpectationID = new JTextField();
        tfExpectationID.setBounds(100,10 + 1*yShift, 80, 20);
        tfExpectationID.setEditable(false);
        tfExpectationID.setBackground(Color.WHITE);
        pExpectations.add(tfExpectationID);
		JLabel lExpectationID = new JLabel("Expectation ID:");
		lExpectationID.setBounds(10, 10 + 1*yShift, 100, 20);
		pExpectations.add(lExpectationID);
		
		tfExpectStatus = new JTextField();
		tfExpectStatus.setBounds(56,10 + 2*yShift, 234, 20);
		tfExpectStatus.setEditable(false);
		tfExpectStatus.setBackground(Color.WHITE);
        pExpectations.add(tfExpectStatus);
		JLabel lExpectStatus = new JLabel("Status:");
		lExpectStatus.setBounds(10, 10 + 2*yShift, 100, 20);
		pExpectations.add(lExpectStatus);
		
		tfStartTime = new JTextField();
		tfStartTime.setBounds(380, 10 + 2*yShift, 210, 20);
		tfStartTime.setEditable(false);
		tfStartTime.setBackground(Color.WHITE);
        pExpectations.add(tfStartTime);
		JLabel lStartTime = new JLabel("Start Time:");
		lStartTime.setBounds(310, 10 + 2*yShift, 100, 20);
		pExpectations.add(lStartTime);
		
		tfEndTime = new JTextField();
		tfEndTime.setBounds(380, 10 + 3*yShift, 210, 20);
		tfEndTime.setEditable(false);
		tfEndTime.setBackground(Color.WHITE);
        pExpectations.add(tfEndTime);
		JLabel lEndTime = new JLabel("End Time:");
		lEndTime.setBounds(310, 10 + 3*yShift, 100, 20);
		pExpectations.add(lEndTime);
		
        tfAnalystName = new JTextField();
        tfAnalystName.setBounds(362, 10 + 1*yShift, 228, 20);
        tfAnalystName.setEditable(false);
        tfAnalystName.setBackground(Color.WHITE);
        pExpectations.add(tfAnalystName);
		JLabel lAnalystName = new JLabel("Analyst:");
		lAnalystName.setBounds(310, 10 + 1*yShift, 100, 20);
		pExpectations.add(lAnalystName);
        
		tfEnvironment = new JTextField();
        tfEnvironment.setBounds(90, 10 + 3*yShift, 200 , 20);
        tfEnvironment.setEditable(false);
        tfEnvironment.setBackground(Color.WHITE);
        pExpectations.add(tfEnvironment);
		JLabel lEnvironment = new JLabel("Environment:");
		lEnvironment.setBounds(10, 10 +3*yShift, 100, 20);
		pExpectations.add(lEnvironment);
		
        
		
		JPanel pServer = new JPanel();
		pServer.setBounds(-1,59,301,71);
		pServer.setLayout(null);
		pServer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pServer);
        
    	tfServerName = new JTextField();
    	tfServerName.setBounds(93, 10, 197, 20);
    	tfServerName.setEditable(false);
    	tfServerName.setBackground(Color.WHITE);
    	pServer.add(tfServerName);
		JLabel lServerName = new JLabel("Server Name:");
		lServerName.setBounds(10, 10 , 100, 20);
		pServer.add(lServerName);
		
        bServer = new JButton();
        bServer.setBounds(10, 10 + 1*yShift , 280, 20);
        bServer.setText("View Server");
        pServer.add(bServer);
    	bServer.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run Info Gather
			                
			            }}).start();}});
        /*
        bServerLog = new JButton();
        bServerLog.setBounds(20, 10 + 2*yShift  , 260, 20);
        bServerLog.setText("View Server Log");
        pServer.add(bServerLog);
        bServerLog.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run Info Gather
			                
			            }}).start();}});
        
        bErrorLog = new JButton();
        bErrorLog.setBounds(20, 10 + 3*yShift , 260, 20);
        bErrorLog.setText("View Error Log");
        pServer.add(bErrorLog);
        bErrorLog.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run Info Gather
			                
			            }}).start();}});
        */
    	/*
        JPanel pErrorInfo = new JPanel();
        pErrorInfo.setBounds(-1,199,601,301);
        pErrorInfo.setLayout(null);
        pErrorInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pErrorInfo);
    	
    	
    	JLabel lErrorHeader = new JLabel("Error Information:");
    	lErrorHeader.setBounds(250, 0 , 100, 20);
    	pErrorInfo.add(lErrorHeader);
    	
    	
    	tpErrorText = new JTextPane();
    	tpErrorText.setBounds(10, 20, 580, 270);
    	tpErrorText.setEditable(false);
    	tpErrorText.setBackground(Color.WHITE);
    	pErrorInfo.add(tpErrorText);
    	*/
    	JPanel pActions = new JPanel();
    	pActions.setBounds(599,-1,301,81);
    	pActions.setLayout(null);
    	pActions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pActions);
    	
    	JLabel lActionHeader = new JLabel("Reply to                                     expectation in FIST:");
    	lActionHeader.setBounds(18, 0 , 300, 20);
    	pActions.add(lActionHeader);
    	
    	JLabel lActionHeader2 = new JLabel("currently selected");
    	lActionHeader2.setBounds(67, 0 , 150, 20);
    	pActions.add(lActionHeader2);
    	Font font = lActionHeader2.getFont();
    	Map attributes = font.getAttributes();
    	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    	lActionHeader2.setFont(font.deriveFont(attributes));
    	
    	
    	
    	bLookingIntoIt = new JButton();
    	bLookingIntoIt.setBounds(20, 20 , 260, 20);
    	bLookingIntoIt.setText("Reply \"Looking into it...\"");
    	pActions.add(bLookingIntoIt);
        bLookingIntoIt.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	if(getCurrentItem().getExpectNum()!=0)
			        	{
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,screenSize,false);
			        		Fdriver.loginConfig();
			        		Fdriver.getExpectationsPage(getCurrentItem().getFileID(),"07/28/2014");
			        		Fdriver.errorReply((getCurrentItem().getExpectNum() +(1 - cbExpectationSelect.getSelectedIndex())), true);
			        	}
			            }}).start();}});
        
        bReplyInFist = new JButton();
        bReplyInFist.setBounds(20, 20 + 1*yShift , 260, 20);
        bReplyInFist.setText("Open reply page");
    	pActions.add(bReplyInFist);
    	bReplyInFist.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	if(getCurrentItem().getExpectNum()!=0)
			        	{
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,screenSize,false);
			        		Fdriver.loginConfig();
			        		Fdriver.getExpectationsPage(getCurrentItem().getFileID(),"07/28/2014");
			        		Fdriver.errorReply((getCurrentItem().getExpectNum() +(1 - cbExpectationSelect.getSelectedIndex())), true);
			        	}
			                
			            }}).start();}});
        
    	JPanel pRecommended = new JPanel();
    	pRecommended.setBounds(599,79,301,321);
    	pRecommended.setLayout(null);
    	pRecommended.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pRecommended);
    	
    	JLabel lRecommendedHeader = new JLabel("Recommended actions to take:");
    	lRecommendedHeader.setBounds(58, 0 , 200, 20);
    	pRecommended.add(lRecommendedHeader);
    	
        tpRecommendedAction = new JTextPane();
        tpRecommendedAction.setBounds(10, 20, 280, 260);
        tpRecommendedAction.setEditable(false);
        tpRecommendedAction.setBackground(Color.WHITE);
    	pRecommended.add(tpRecommendedAction);
    	
        bTakeAction = new JButton();
        bTakeAction.setBounds(20, 290 , 260, 20);
        bTakeAction.setText("Take Recommended Action");
        pRecommended.add(bTakeAction);
    	bTakeAction.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	//Run Info Gather
			                
			            }}).start();}});
        
    	
    }
    
    private void selectExpectation()
    {
    	if(getCurrentItem().getExpectNum()==0)
    	{
    		tfEnvironment.setText("n/a");
        	tfExpectationID.setText("n/a");
        	tfExpectStatus.setText("n/a");
        	tfAnalystName.setText("n/a");
        	tfStartTime.setText("n/a");
        	tfEndTime.setText("n/a");
    	}
    	else
    	{
    		tfEnvironment.setText(getCurrentItem().getEnvironmentList().get(getCurrentItem().getSelectedExpectNum()));
        	tfExpectationID.setText(getCurrentItem().getExpectIDList().get(getCurrentItem().getSelectedExpectNum()));
        	tfExpectStatus.setText(getCurrentItem().getStatusList().get(getCurrentItem().getSelectedExpectNum()));
        	tfAnalystName.setText(getCurrentItem().getAnalystNameList().get(getCurrentItem().getSelectedExpectNum()));
        	tfStartTime.setText(getCurrentItem().getStartTimeList().get(getCurrentItem().getSelectedExpectNum()));
        	tfEndTime.setText(getCurrentItem().getEndTimeList().get(getCurrentItem().getSelectedExpectNum()));
    	}
    }
    
    private String getEnvironmentName(String Enum)
    {
    	String displayName =Enum;
    	
    	if(Enum.contentEquals("B")){displayName= "B: Blue (Prod P)";}
    	else if(Enum.contentEquals("C")){displayName= "C: Green (Prod P)";}
    	else if(Enum.contentEquals("D")){displayName= "D: Yellow (Prod P)";}
    	else if(Enum.contentEquals("F")){displayName= "F: Crimson (Prod P)";}
    	else if(Enum.contentEquals("H")){displayName= "H: Silver (Prod P)";}
    	else if(Enum.contentEquals("N")){displayName= "N: Gray (Prod P)";}
    	else if(Enum.contentEquals("A")){displayName= "A: Indigo (Prod W)";}
    	else if(Enum.contentEquals("E")){displayName= "E: Topaz (Prod W)";}
    	else if(Enum.contentEquals("G")){displayName= "G: Navy (Prod W)";}
    	else if(Enum.contentEquals("J")){displayName= "J: Cobalt (Prod W)";}
    	else if(Enum.contentEquals("O")){displayName= "O: Red (Prod W)";}
    	else if(Enum.contentEquals("R")){displayName= "R: Orange (Prod W)";}
    	else if(Enum.contentEquals("K")){displayName= "K: White (Prod X)";}
    	else if(Enum.contentEquals("V")){displayName= "V: Gold (Prod X)";}
    	else if(Enum.contentEquals("S")){displayName= "S: Peach (Prod X)";}
    	else if(Enum.contentEquals("Y")){displayName= "Y: Bronze (Prod X)";}
    	else if(Enum.contentEquals("Z")){displayName= "Z: Plum (Prod X)";}
    	else if(Enum.contentEquals("L")){displayName= "L: Brown (Prod X)";}
    	else if(Enum.contentEquals("1")){displayName= "1: Cherry (Prod X)";}
    	else if(Enum.contentEquals("6")){displayName= "6: Ruby (Prod X)";}
    	else if(Enum.contentEquals("7")){displayName= "7: Emerald (Prod X)";}
    	else if(Enum.contentEquals("8")){displayName= "8: Tan (Prod X)";}
    	else if(Enum.contentEquals("9")){displayName= "9: Black (Prod X)";}
    	
    	return displayName;
    }
    
    private void populateErrorPanel () {
    	tfFileID.setText(getCurrentItem().getFileID());
    	tfDeveloperName.setText(getCurrentItem().getDevName());
    	tfServiceName.setText(getCurrentItem().getServiceLocation());
    	tfServiceName.setCaretPosition(0);
    	String[] DateArray = new String[getCurrentItem().getExpectDateList().size()];
    	DateArray = getCurrentItem().getExpectDateList().toArray(DateArray);
    	cbExpectationSelect.setModel(new DefaultComboBoxModel<String>(DateArray));
    	if(getCurrentItem().isGatheringInfo()==false)
    	{
    		bGatherInfo.setEnabled(true);
    	}
    	else
    	{
    		bGatherInfo.setEnabled(false);
    	}
    	
    	if(getCurrentItem().getExpectNum()==0)
    	{
    		cbExpectationSelect.setEnabled(false);
    	}
    	else
    	{
    		cbExpectationSelect.setEnabled(true);
        	cbExpectationSelect.setSelectedIndex(getCurrentItem().getSelectedExpectNum());
    	}
    	selectExpectation();
    }
    

    public JSplitPane getPane() {
        return splitPaneBottom;
    }
    
    
    public void setupLoginPanel()
    {
    	
    	dCred = new JDialog();
    	dCred.setModal(true);
    	dCred.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	dCred.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
            	if (JOptionPane.showConfirmDialog(null, "You must have credentials set to use the program. Close program?", "Request", 
					    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
					    == JOptionPane.YES_OPTION)
					{
            		System.exit(0);
					}
            	else
            	{
            		
            	}
            }
        });
    	dCred.setTitle("Enter your credentials");
    	dCred.setBounds((screenSize.width/2)-120,(screenSize.height/2)-125,240, 250);
    	dCred.setLayout(null);

    	
    	int yShift=20;
    	JLabel lFistUser=new JLabel("Fist Username:");
    	lFistUser.setBounds(10, 10, 200, 20);
        JLabel lFistPass=new JLabel("Fist Password:");
        lFistPass.setBounds(10, 10 + 2*yShift, 200, 20);
        final JTextField tfFistUser=new JTextField(10);
        tfFistUser.setBounds(10, 10 + yShift, 200, 20);
        final JPasswordField pfFistPass=new JPasswordField(10);
        pfFistPass.setBounds(10, 10 + 3*yShift, 200, 20);
        JLabel lServerUser=new JLabel("Servers Username:");
        lServerUser.setBounds(10, 10 + 4*yShift, 200, 20);
        JLabel lServerPass=new JLabel("Servers Password:");
        lServerPass.setBounds(10, 10 + 6*yShift, 200, 20);
        final JTextField tfServerUser=new JTextField(10);
        tfServerUser.setBounds(10, 10 + 5*yShift, 200, 20);
        final JPasswordField pfServerPass=new JPasswordField(10);
        pfServerPass.setBounds(10, 10 + 7*yShift, 200, 20);
        JButton bSave = new JButton("Save");
        bSave.setBounds(10, 20 + 8*yShift, 200, 20);
        bSave.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	
			        	String sFistUser = tfFistUser.getText();
			    		String sFistPass = new String(pfFistPass.getPassword());
			    		String sServerUser = tfServerUser.getText();
			    		String sServerPass = new String(pfServerPass.getPassword());
			    		
			    		if(sFistUser.contentEquals("")||sFistPass.contentEquals("")||sServerUser.contentEquals("")||sServerPass.contentEquals("")){
			    			JOptionPane.showMessageDialog(null,"All fields are required");
			    		}
			    		else
			    		{
			    		PrintWriter writer = null;
			    		
			    			try {
			    				
			    					writer = new PrintWriter("C://Triage_Dashboard//credentials.txt", "UTF-8");
			    				
			    			} catch (FileNotFoundException e1) {
			    				// TODO Auto-generated catch block
			    				e1.printStackTrace();
			    			} catch (UnsupportedEncodingException e1) {
			    				// TODO Auto-generated catch block
			    				e1.printStackTrace();
			    			}
			    			
			    			//Use encrypt class to hide username and PW
			    			sFistUser = Encrypt.symmetricEncrypt(sFistUser);
			    			sFistPass = Encrypt.symmetricEncrypt(sFistPass);
			    			sServerUser = Encrypt.symmetricEncrypt(sServerUser);
			    			sServerPass = Encrypt.symmetricEncrypt(sServerPass);
			    			//Write to credentials file
			    			writer.println(sFistUser);
			    			writer.println(sFistPass);
			    			writer.println(sServerUser);
			    			writer.println(sServerPass);
			    			writer.close();
			    			dCred.dispose();
			    		}
			        	
			            }}).start();}});
        
        dCred.add(lFistUser);
        dCred.add(tfFistUser);
        dCred.add(lFistPass);
        dCred.add(pfFistPass);
        dCred.add(lServerUser);
        dCred.add(tfServerUser);
        dCred.add(lServerPass);
        dCred.add(pfServerPass);
        dCred.add(bSave);
        
        
        dCred.setVisible(true);
        
    }
    
    public void credCheck()
    {
    	BufferedReader br = null;
    	
    	if(new File("C://Triage_Dashboard//credentials.txt").isFile()==true)
    	{
			try{
				br = new BufferedReader(new FileReader("C://Triage_Dashboard//credentials.txt"));
				for(int i=0; i<4; i++){
					
					textData[i]=br.readLine();
				}
				br.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			if(textData[0]==null || textData[0].contentEquals("") || textData[1]==null || textData[1].contentEquals("") || textData[2]==null || textData[2].contentEquals("") || textData[3]==null || textData[3].contentEquals("")){
				new File("C://Traige_Dashboard//credentials.txt").delete();
				setupLoginPanel();
			}
				
    	}
    	else
    	{    	
    	//if user does not have stored login credentials, prompt for their information
			  setupLoginPanel();
    	}
    }

    private static void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("Triage Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGUI window = new MainGUI();
        frame.getContentPane().add(window.getPane());
   //     frame.setMinimumSize(new Dimension(500, 300));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                
            }
        });
    }
    

    public class ListItemRenderer extends JLabel implements ListCellRenderer<ListItem> {
        @Override
        public Component getListCellRendererComponent(JList<? extends ListItem> list, ListItem item, int index, boolean isSelected, boolean cellHasFocus) {
        	setOpaque(true);
        	
            setText(item.getFileID());
            if(item.isFinished())
            {
            	setBackground(new Color(Integer.parseInt( "ABEDA8",16)));
            }
            else
            {
            	setBackground(new Color(Integer.parseInt( "EDA8B4",16)));
            }
            
            if (isSelected) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            }
            else
            {
            	setIcon(null);
            	setBorder(null);
            }
            return this;
        }
   }
    
   public void clearExpectations()
   {
	    getCurrentItem().getEnvironmentList().clear();
	   	getCurrentItem().getExpectIDList().clear();
	   	getCurrentItem().getStatusList().clear();
	   	getCurrentItem().getExpectDateList().clear();
	   	getCurrentItem().getStartTimeList().clear();
	   	getCurrentItem().getEndTimeList().clear();
	   	getCurrentItem().getAnalystNameList().clear();
   }
    
   /*
   final FocusListener fcsListener = new FocusListener() {

        @Override
        public void focusGained(FocusEvent e) {
        	selectExpectation(cbExpectationSelect.getSelectedIndex());
        }

        @Override
        public void focusLost(FocusEvent e) {
        	selectExpectation(cbExpectationSelect.getSelectedIndex());
        }
        
        
        };

*/

      
}
