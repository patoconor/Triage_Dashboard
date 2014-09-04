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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//Test Commit
public class MainGUI2 extends JPanel
                          implements ListSelectionListener {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static JFrame frame;
	
	private String sFistUser;
	private String sFistPass;
	private String sServerUser;
	private String sServerPass;
	private String sOfficeLocation;
	
	private static MainGUI2 window;
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
    private JComboBox<String> cbServerLocationSelect;
    private JButton bServer;
    
    private JComboBox<String> cbExpectationSelect;
    private JButton bViewExpectations;
    
    private JTextPane tpErrorText;
    
    private JButton bLookingIntoIt;
    private JButton bReplyInFist;
    private JTextPane tpRecommendedAction;
    private JButton bTakeAction;
    
    
    private JDialog dCred;
    String[] textData= new String[5];
    private JLabel lblFistInfo;
        
    
    
    public MainGUI2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	
    	//list of errors in left pane
    	errorList= new DefaultListModel<>();
    	
    	setupList();
    	updater();
        list = new JList<>(errorList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setCellRenderer(new ListItemRenderer());
        
        //right panel for viewing error details
        errorViewPanel = new JPanel();
        errorViewPanel.setPreferredSize(new Dimension(899, 559));
        
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
			        	setupList();
			                
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
        splitPane.setDividerLocation(190);
        //splitPane.setMinimumSize(new Dimension(1*screenSize.width/6, 1*screenSize.height/6));

        //create a horizontally split pane for the credentials and options
        splitPaneBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT,	splitPane, pCredentials);
        splitPaneBottom.setOneTouchExpandable(false);
        splitPaneBottom.setEnabled(false);
        splitPaneBottom.setResizeWeight(1);
    
        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(1123, 570));
        
        
	    credCheck();
	    if(new File("C://Triage_Dashboard//credentials.txt").isFile()==true){
			  	sFistUser = Encrypt.symmetricDecrypt(textData[0]);
				sFistPass = Encrypt.symmetricDecrypt(textData[1]);
				sServerUser = Encrypt.symmetricDecrypt(textData[2]);
				sServerPass = Encrypt.symmetricDecrypt(textData[3]);
				sOfficeLocation = Encrypt.symmetricDecrypt(textData[4]);
		  }
	       
        
        setupErrorPanel();
        populateErrorPanel();
        
    }
    
    public void setupList()
    {
    	
    	/*
    	ListItem li = new ListItem("");
    	li.setFileID("63001");
    	errorList.addElement(li);
    	*/
    	BufferedReader br;
    	String line;
    	
    	
    	Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
		try {
			try {
	            Class.forName("org.firebirdsql.jdbc.FBDriver");
	            connection = DriverManager
	                    .getConnection(
	                            "jdbc:firebirdsql://NUSDD2F0J6M1:3050/C:/database/BASE.fdb",
	                            "sysdba", "masterkey");
	            statement = connection.createStatement();
	            
	            resultSet = statement.executeQuery("select DISTINCT * from TRIAGE WHERE STATUS != '2' OR STATUS ='2' AND ERDATE = '09/02/2014'");
	            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
	            while(resultSet.next()){
	            	String text = resultSet.getString("FILEID")+"|"+resultSet.getString("STATUS")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
	            	int erid = resultSet.getInt("ERRORID");
	            	System.out.println(text);
	            	errorList.addElement(new ListItem(text,erid));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                statement.close();
	                connection.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}finally{
			
		}
//		Connection connection1 = null;
//        ResultSet resultSet1 = null;
//        Statement statement1 = null;
//		try {
//			try {
//	            Class.forName("org.firebirdsql.jdbc.FBDriver");
//	            connection1 = DriverManager
//	                    .getConnection(
//	                            "jdbc:firebirdsql://L4DWIPDS011.hewitt.com:13163",
//	                            "poconor", "Spektor29!");
//	            statement1 = connection1.createStatement();
//	            resultSet1 = statement1.executeQuery("SELECT TOP 1000 [msg_id],[msg_severity],[msg_mtid],[msg_fileid],[msg_expectid],[msg_expect_iteration],[msg_filename],[msg_procdttime],[msg_isprocessed],[msg_toaddressoverride],[msg_addldetails],[msg_inserted_dttime]FROM [FileConfig].[dbo].[messageQ]WHERE msg_toaddressoverride NOT LIKE '%phuong.dam@aonhewitt.com%' and msg_inserted_dttime > '2014-09-02'  AND msg_toaddressoverride NOT LIKE '%rob.neilson@aonhewitt.com%'  AND msg_toaddressoverride NOT LIKE '%tammy.adams@aonhewitt.com%'  AND msg_toaddressoverride NOT LIKE '%jon.petit@aonhewitt.com%'  AND msg_toaddressoverride NOT LIKE '%rob.neilson@aonhewitt.com%' AND msg_toaddressoverride NOT LIKE '%sethiya.um@aonhewitt.com%'AND msg_toaddressoverride NOT LIKE '%chad.smith@aonhewitt.com%'AND msg_toaddressoverride NOT LIKE '%art.feld@aonhewitt.com% AND msg_addldetails NOT LIKE '%Your document has been received,%'AND msg_addldetails NOT LIKE '%No valid Expectations found% AND msg_addldetails NOT LIKE '%completed successfully.%'AND msg_addldetails NOT LIKE '%is completed successfully% AND msg_addldetails NOT LIKE '%Promoting audit profile% AND msg_addldetails NOT LIKE '%Removed expiration date% AND msg_expect_iteration NOT LIKE 'NULL' order by msg_inserted_dttime desc");
//	            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
//	            while(resultSet1.next()){
//	            	String text = resultSet1.getString("msg_id");
//	            	System.out.println(text);
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                statement1.close();
//	                connection1.close();
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	            }
//	        }
//		}finally{
//			
//		}
		
		
    }
//			br = new BufferedReader(new FileReader("A://Triage_Dashboard//ActiveErrors.txt"));
//			//br = new BufferedReader(new FileReader("R://BenIT//Files//All//WMInstall//ActiveErrors.txt"));
//			ArrayList <Boolean> foundItems = new ArrayList<Boolean> ();
//			for(int i=0; i<errorList.getSize();i++)
//			{
//				foundItems.add(false);
//			}
//			while((line =br.readLine())!=null && line.length()!=0)
//			{
//				boolean bLineFound=false;
//				for(int i=0; i<errorList.getSize();i++)
//				{
//					if(errorList.get(i).getFullLine().equals(line))
//					{
//						bLineFound=true;
//						foundItems.set(i, true);
//						i=errorList.getSize();
//					}
//				}
//				if(bLineFound==false)
//				{
//					errorList.addElement(new ListItem(line));
//				}
//			}
//			int removedObjects=0;
//			for(int i=0;  i<foundItems.size();i++)
//			{
//				if(foundItems.get(i)==false)
//				{
//					errorList.remove(i-removedObjects);
//					removedObjects++;
//				}
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		  	
    
    
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
    	errorViewPanel.setLayout(null);
    	
    	int yShift=30;
		String [] serverStrings = {"View Server","View Server Log","View Error Log"};
        
    	
        JPanel pErrorInfo = new JPanel();
        pErrorInfo.setBounds(0, 446, 601, 301);
        pErrorInfo.setLayout(null);
        pErrorInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pErrorInfo);
    	
    	
    	JLabel lErrorHeader = new JLabel("Error Information:");
    	lErrorHeader.setBounds(250, 0 , 100, 20);
    	pErrorInfo.add(lErrorHeader);
    	
    	
    	tpErrorText = new JTextPane();
    	tpErrorText.setBounds(18, 29, 580, 270);
    	tpErrorText.setEditable(false);
    	tpErrorText.setBackground(Color.WHITE);
    	pErrorInfo.add(tpErrorText);
    	
    	
    	JPanel pActions = new JPanel();
    	pActions.setBounds(599, -1, 301, 81);
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
			        		WebDriver dr = new ChromeDriver();
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,sOfficeLocation,false,dr);
			        		Fdriver.loginConfig();
			        		Fdriver.getExpectationsPage(getCurrentItem().getFileID(),getCurrentItem().getDate());
			        		Fdriver.errorReply((getCurrentItem().getExpectNum() +(1 - cbExpectationSelect.getSelectedIndex())), getCurrentItem().getFileID(), true);
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
			        		WebDriver dr = new ChromeDriver();
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,sOfficeLocation,false,dr);
			        		Fdriver.loginConfig();
			        		Fdriver.getExpectationsPage(getCurrentItem().getFileID(),getCurrentItem().getDate());
			        		Fdriver.errorReply((getCurrentItem().getExpectNum() +(1 - cbExpectationSelect.getSelectedIndex())), getCurrentItem().getFileID(), true);
			        	}
			                
			            }}).start();}});
        
    	JPanel pRecommended = new JPanel();
    	pRecommended.setBounds(599, 79, 301, 321);
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
        
        JLabel lblStatus = new JLabel("TRIAGE STATUS:");
        lblStatus.setForeground(new Color(255, 0, 0));
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblStatus.setBounds(103, 411, 110, 14);
        errorViewPanel.add(lblStatus);
        
        final JComboBox comboBox = new JComboBox();
        comboBox.setBounds(209, 407, 110, 20);
        errorViewPanel.add(comboBox);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"In Progress", "Complete"}));
        
        JButton btnNewButton = new JButton("Change Status");
        btnNewButton.setBounds(329, 404, 127, 23);
        errorViewPanel.add(btnNewButton);
        
        bGatherInfo = new JButton();
        bGatherInfo.setBounds(624, 446, 280, 40);
        errorViewPanel.add(bGatherInfo);
        bGatherInfo.setText("Gather Info");
        JLabel lServerName = new JLabel("Server Name:");
        lServerName.setForeground(Color.BLUE);
        lServerName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lServerName.setBounds(27, 125, 79, 20);
        errorViewPanel.add(lServerName);
        
    	tfServerName = new JTextField();
    	tfServerName.setBounds(109, 126, 180, 20);
    	errorViewPanel.add(tfServerName);
    	tfServerName.setEditable(false);
    	tfServerName.setBackground(Color.WHITE);
    	
    	
    	cbServerLocationSelect = new JComboBox <String> ();
    	cbServerLocationSelect.setBounds(155, 164, 150, 20);
    	errorViewPanel.add(cbServerLocationSelect);
    	cbServerLocationSelect.setModel(new DefaultComboBoxModel<String>(serverStrings));
    	
    	
    	
        bServer = new JButton();
        bServer.setBounds(318, 164, 110, 20);
        errorViewPanel.add(bServer);
        bServer.setText("View");
        
        tfFileID = new JTextField();
        tfFileID.setBounds(109, 68, 50, 20);
        errorViewPanel.add(tfFileID);
        tfFileID.setEditable(false);
        tfFileID.setBackground(Color.WHITE);
        JLabel lFileID = new JLabel("File ID:");
        lFileID.setForeground(Color.BLUE);
        lFileID.setFont(new Font("Tahoma", Font.BOLD, 11));
        lFileID.setBounds(64, 67, 39, 20);
        errorViewPanel.add(lFileID);
        JLabel lFailTime = new JLabel("Error Date/Time:");
        lFailTime.setForeground(Color.BLUE);
        lFailTime.setFont(new Font("Tahoma", Font.BOLD, 11));
        lFailTime.setBounds(7, 95, 109, 20);
        errorViewPanel.add(lFailTime);
        
        
		
		tfFailTime = new JTextField();
		tfFailTime.setBounds(109, 97, 180, 20);
		errorViewPanel.add(tfFailTime);
		tfFailTime.setEditable(false);
		tfFailTime.setBackground(Color.WHITE);
		JLabel lServiceName = new JLabel("Location:");
		lServiceName.setForeground(Color.BLUE);
		lServiceName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lServiceName.setBounds(339, 126, 61, 20);
		errorViewPanel.add(lServiceName);
		
        
		
		tfServiceName = new JTextField();
		tfServiceName.setBounds(400, 126, 180, 20);
		errorViewPanel.add(tfServiceName);
		tfServiceName.setEditable(false);
		tfServiceName.setBackground(Color.WHITE);
		
		tfDeveloperName = new JTextField();
		tfDeveloperName.setBounds(400, 97, 180, 20);
		errorViewPanel.add(tfDeveloperName);
		tfDeveloperName.setEditable(false);
		tfDeveloperName.setBackground(Color.WHITE);
		JLabel lDeveloperName = new JLabel("Developer:");
		lDeveloperName.setForeground(Color.BLUE);
		lDeveloperName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lDeveloperName.setBounds(331, 97, 100, 20);
		errorViewPanel.add(lDeveloperName);
		JLabel lExpectationSelect = new JLabel("Pick Expectation:");
		lExpectationSelect.setForeground(new Color(0, 128, 0));
		lExpectationSelect.setFont(new Font("Tahoma", Font.BOLD, 11));
		lExpectationSelect.setBounds(8, 298, 99, 20);
		errorViewPanel.add(lExpectationSelect);
		
		
		cbExpectationSelect = new JComboBox <String> ();
		cbExpectationSelect.setBounds(110, 298, 180, 20);
		errorViewPanel.add(cbExpectationSelect);
		
		bViewExpectations = new JButton();
		bViewExpectations.setBounds(110, 358, 180, 20);
		errorViewPanel.add(bViewExpectations);
		bViewExpectations.setText("Open Expectations Page");
		
        tfAnalystName = new JTextField();
        tfAnalystName.setBounds(400, 68, 180, 20);
        errorViewPanel.add(tfAnalystName);
        tfAnalystName.setEditable(false);
        tfAnalystName.setBackground(Color.WHITE);
        JLabel lAnalystName = new JLabel("Analyst:");
        lAnalystName.setForeground(Color.BLUE);
        lAnalystName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lAnalystName.setBounds(345, 68, 100, 20);
        errorViewPanel.add(lAnalystName);
        
    	tfExpectationID = new JTextField();
    	tfExpectationID.setBounds(303, 263, 80, 20);
    	errorViewPanel.add(tfExpectationID);
    	tfExpectationID.setEditable(false);
    	tfExpectationID.setBackground(Color.WHITE);
    	JLabel lExpectationID = new JLabel("Expectation ID:");
    	lExpectationID.setForeground(new Color(0, 128, 0));
    	lExpectationID.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
    	lExpectationID.setBounds(201, 264, 100, 20);
    	errorViewPanel.add(lExpectationID);
    	JLabel lExpectStatus = new JLabel("Status:");
    	lExpectStatus.setForeground(new Color(0, 128, 0));
    	lExpectStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
    	lExpectStatus.setBounds(352, 298, 46, 20);
    	errorViewPanel.add(lExpectStatus);
    	
    	tfExpectStatus = new JTextField();
    	tfExpectStatus.setBounds(400, 298, 180, 20);
    	errorViewPanel.add(tfExpectStatus);
    	tfExpectStatus.setEditable(false);
    	tfExpectStatus.setBackground(Color.WHITE);
    	JLabel lEnvironment = new JLabel("Environment:");
    	lEnvironment.setForeground(new Color(0, 128, 0));
    	lEnvironment.setFont(new Font("Tahoma", Font.BOLD, 11));
    	lEnvironment.setBounds(28, 326, 79, 20);
    	errorViewPanel.add(lEnvironment);
    	
		tfEnvironment = new JTextField();
		tfEnvironment.setBounds(110, 326, 180, 20);
		errorViewPanel.add(tfEnvironment);
		tfEnvironment.setEditable(false);
		tfEnvironment.setBackground(Color.WHITE);
		JLabel lEndTime = new JLabel("End Time:");
		lEndTime.setForeground(new Color(0, 128, 0));
		lEndTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lEndTime.setBounds(337, 358, 60, 20);
		errorViewPanel.add(lEndTime);
		JLabel lStartTime = new JLabel("Start Time:");
		lStartTime.setForeground(new Color(0, 128, 0));
		lStartTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lStartTime.setBounds(329, 326, 82, 20);
		errorViewPanel.add(lStartTime);
		
		tfStartTime = new JTextField();
		tfStartTime.setBounds(400, 326, 180, 20);
		errorViewPanel.add(tfStartTime);
		tfStartTime.setEditable(false);
		tfStartTime.setBackground(Color.WHITE);
		
		tfEndTime = new JTextField();
		tfEndTime.setBounds(400, 358, 180, 20);
		errorViewPanel.add(tfEndTime);
		tfEndTime.setEditable(false);
		tfEndTime.setBackground(Color.WHITE);
		
		JLabel lblFileInfo = new JLabel("File Info");
		lblFileInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFileInfo.setBounds(254, 20, 76, 37);
		errorViewPanel.add(lblFileInfo);
		
		lblFistInfo = new JLabel("FIST Info");
		lblFistInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFistInfo.setBounds(251, 222, 79, 40);
		errorViewPanel.add(lblFistInfo);
		bViewExpectations.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	WebDriver dr = new ChromeDriver();
			        	FIST Fdriver = new FIST(sFistUser,sFistPass,sOfficeLocation,false,dr);
			        	Fdriver.loginConfig();
			        	Fdriver.getExpectationsPage(getCurrentItem().getFileID(),getCurrentItem().getDate());
			                
			            }}).start();}});
		
		cbExpectationSelect.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {			  
				  getCurrentItem().setSelectedExpectNum(cbExpectationSelect.getSelectedIndex());
				  selectExpectation();
			  
			  
			  }});
        bServer.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	Server.user=sServerUser;
			        	Server.pw=sServerPass;			        	
			        	Server.serverLogin(getCurrentItem().getServer(),cbServerLocationSelect.getSelectedIndex());
			        	
			            }}).start();}});
        bGatherInfo.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	list.removeAll();
			        	errorList.clear();
			        	
			        	
			        	errorList= new DefaultListModel<>();
			        	
			        	setupList();
			        	
			            list = new JList<>(errorList);
			            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			            list.setSelectedIndex(0);
			            list.addListSelectionListener(null);
			            list.setCellRenderer(new ListItemRenderer());
			            
			            //right panel for viewing error details
			            errorViewPanel = new JPanel();
			            errorViewPanel.setLayout(null);
			            errorViewPanel.setPreferredSize(new Dimension(899, 559));
			            
			            setupErrorPanel();
			            populateErrorPanel();
						try {
							window = new MainGUI2();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedLookAndFeelException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            frame.getContentPane().add(window.getPane());
			       //     frame.setMinimumSize(new Dimension(500, 300));

			            //Display the window.
			            frame.pack();
			            frame.setVisible(true);
			            }}).start();}});
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(comboBox.getSelectedItem().toString().equals("In Progress")){
        			Connection connection = null;
        	        ResultSet resultSet = null;
        	        Statement statement = null;
        			try {
        	            Class.forName("org.firebirdsql.jdbc.FBDriver");
        	            connection = DriverManager
        	                    .getConnection(
        	                            "jdbc:firebirdsql://NUSDD2F0J6M1:3050/C:/database/BASE.fdb",
        	                            "sysdba", "masterkey");
        	            statement = connection.createStatement();
        	            statement.executeUpdate("UPDATE TRIAGE set STATUS = '1'where ERRORID = "+getCurrentItem().getErid()+"");
        			} catch (Exception e1) {
        	            e1.printStackTrace();
        	        }
        		}
        		if(comboBox.getSelectedItem().toString().equals("Complete")){
        			Connection connection = null;
        	        ResultSet resultSet = null;
        	        Statement statement = null;
        			try {
        	            Class.forName("org.firebirdsql.jdbc.FBDriver");
        	            connection = DriverManager
        	                    .getConnection(
        	                            "jdbc:firebirdsql://NUSDD2F0J6M1:3050/C:/database/BASE.fdb",
        	                            "sysdba", "masterkey");
        	            statement = connection.createStatement();
        	            statement.executeUpdate("UPDATE TRIAGE set STATUS = '2'where ERRORID = "+getCurrentItem().getErid()+"");
        	            
        			} catch (Exception e1) {
        	            e1.printStackTrace();
        	        }
        		}
        	//	awd
        	}
        });
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
        	tfExpectStatus.setCaretPosition(0);
        	tfAnalystName.setText(getCurrentItem().getAnalystNameList().get(getCurrentItem().getSelectedExpectNum()));
        	tfAnalystName.setCaretPosition(0);
        	tfStartTime.setText(getCurrentItem().getStartTimeList().get(getCurrentItem().getSelectedExpectNum()));
        	tfEndTime.setText(getCurrentItem().getEndTimeList().get(getCurrentItem().getSelectedExpectNum()));
    	}
    }
    
   
    
    private void populateErrorPanel () {
    	if(errorList.size()==0){
    		tfFileID.setText("");
        	tfDeveloperName.setText("");
        	tfDeveloperName.setCaretPosition(0);
        	tfServiceName.setText("");
        	tfServiceName.setCaretPosition(0);
        	tfFailTime.setText("   |   ");
        	tfServerName.setText("");
        	String[] DateArray = new String[0];
    	}
    	if(errorList.size()>0){
    	tfFileID.setText(getCurrentItem().getFileID());
    	tfDeveloperName.setText(getCurrentItem().getDevName());
    	tfDeveloperName.setCaretPosition(0);
    	tfServiceName.setText(getCurrentItem().getServiceLocation());
    	tfServiceName.setCaretPosition(0);
    	tfFailTime.setText(getCurrentItem().getDate() + "  |  " + getCurrentItem().getTime());
    	tfServerName.setText(getCurrentItem().getServer());
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
    		bLookingIntoIt.setEnabled(false);
    		bReplyInFist.setEnabled(false);
    	}
    	else
    	{
    		cbExpectationSelect.setEnabled(true);
    		bLookingIntoIt.setEnabled(true);
    		bReplyInFist.setEnabled(true);
        	cbExpectationSelect.setSelectedIndex(getCurrentItem().getSelectedExpectNum());
    	}
    	selectExpectation();
    	
    	tpErrorText.setText(getCurrentItem().getErrorMessage()+"\n\n"+getCurrentItem().getStackTrace());
    	
    }
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
    	dCred.setBounds((screenSize.width/2)-120,(screenSize.height/2)-145,240, 290);
    	dCred.getContentPane().setLayout(null);

    	
    	int yShift=20;
    	JLabel lFistUser=new JLabel("Fist Username:");
    	lFistUser.setBounds(10, 10, 200, 20);
        JLabel lFistPass=new JLabel("Fist Password:");
        lFistPass.setBounds(10, 10 + 2*yShift, 200, 20);
        final JTextField tfFistUser=new JTextField(10);
        tfFistUser.setBounds(10, 10 + yShift, 200, 20);
        tfFistUser.setText(sFistUser);
        final JPasswordField pfFistPass=new JPasswordField(10);
        pfFistPass.setBounds(10, 10 + 3*yShift, 200, 20);
        pfFistPass.setText(sFistPass);
        JLabel lServerUser=new JLabel("Servers Username:");
        lServerUser.setBounds(10, 10 + 4*yShift, 200, 20);
        JLabel lServerPass=new JLabel("Servers Password:");
        lServerPass.setBounds(10, 10 + 6*yShift, 200, 20);
        final JTextField tfServerUser=new JTextField(10);
        tfServerUser.setBounds(10, 10 + 5*yShift, 200, 20);
        tfServerUser.setText(sServerUser);
        final JPasswordField pfServerPass=new JPasswordField(10);
        pfServerPass.setBounds(10, 10 + 7*yShift, 200, 20);
        pfServerPass.setText(sServerPass);
        JLabel lOffice=new JLabel("Select Your Office Location:");
        lOffice.setBounds(10, 10 + 8*yShift, 200, 20);
        final JComboBox<String> cbOffice=new JComboBox<String>();
        cbOffice.setBounds(10, 10 + 9*yShift, 200, 20);
        String [] officeStrings = {"Winston Salem","Hunt Valley"};
        cbOffice.setModel(new DefaultComboBoxModel<String>(officeStrings));
        if(sOfficeLocation.equals("Winston Salem"))
        {
        	cbOffice.setSelectedIndex(0);
        }
        else if (sOfficeLocation.equals("Hunt Valley"))
        {
        	cbOffice.setSelectedIndex(1);
        }
        JButton bSave = new JButton("Save");
        bSave.setBounds(10, 20 + 10*yShift, 200, 20);
        bSave.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	
			        	String FistUser = tfFistUser.getText();
			    		String FistPass = new String(pfFistPass.getPassword());
			    		String ServerUser = tfServerUser.getText();
			    		String ServerPass = new String(pfServerPass.getPassword());
			    		String Office = "";
			    		if(cbOffice.getSelectedIndex()==0)
			    		{
			    			Office = "Winston Salem";
			    		}
			    		else
			    		{
			    			Office = "Hunt Valley";
			    		}
			    		if(FistUser.contentEquals("")||FistPass.contentEquals("")||ServerUser.contentEquals("")||ServerPass.contentEquals("")){
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
			    			sFistUser=FistUser;
			    			sFistPass=FistPass;
			    			sServerUser=ServerUser;
			    			sServerPass=ServerPass;
			    			sOfficeLocation=Office;
			    			FistUser = Encrypt.symmetricEncrypt(FistUser);
			    			FistPass = Encrypt.symmetricEncrypt(FistPass);
			    			ServerUser = Encrypt.symmetricEncrypt(ServerUser);
			    			ServerPass = Encrypt.symmetricEncrypt(ServerPass);
			    			Office = Encrypt.symmetricEncrypt(Office);
			    			//Write to credentials file
			    			writer.println(FistUser);
			    			writer.println(FistPass);
			    			writer.println(ServerUser);
			    			writer.println(ServerPass);
			    			writer.println(Office);
			    			writer.close();
			    			dCred.dispose();
			    		}
			        	
			            }}).start();}});
        
        dCred.getContentPane().add(lFistUser);
        dCred.getContentPane().add(tfFistUser);
        dCred.getContentPane().add(lFistPass);
        dCred.getContentPane().add(pfFistPass);
        dCred.getContentPane().add(lServerUser);
        dCred.getContentPane().add(tfServerUser);
        dCred.getContentPane().add(lServerPass);
        dCred.getContentPane().add(pfServerPass);
        dCred.getContentPane().add(lOffice);
        dCred.getContentPane().add(cbOffice);
        
        dCred.getContentPane().add(bSave);
        
        
        dCred.setVisible(true);
        
    }
    
    public void credCheck()
    {
    	BufferedReader br = null;
    	
    	if(new File("C://Triage_Dashboard//credentials.txt").isFile()==true)
    	{
			try{
				br = new BufferedReader(new FileReader("C://Triage_Dashboard//credentials.txt"));
				for(int i=0; i<5; i++){
					
					textData[i]=br.readLine();
				}
				br.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			if(textData[0]==null || textData[0].contentEquals("") || textData[1]==null || textData[1].contentEquals("") || textData[2]==null || textData[2].contentEquals("") || textData[3]==null || textData[3].contentEquals("") || textData[4]==null || textData[4].contentEquals("")){
				new File("C://Triage_Dashboard//credentials.txt").delete();
				setupLoginPanel();
			}
				
    	}
    	else
    	{    	
    	//if user does not have stored login credentials, prompt for their information
			  setupLoginPanel();
    	}
    }

    private static void createAndShowGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        //Create and set up the window.
        frame = new JFrame("Triage Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window = new MainGUI2();
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
                try {
					createAndShowGUI();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });
    }
    

    public class ListItemRenderer extends JLabel implements ListCellRenderer<ListItem> {
        /**
		 * 
		 */
        public Component getListCellRendererComponent(JList<? extends ListItem> list, ListItem item, int index, boolean isSelected, boolean cellHasFocus) {
        	setOpaque(true);
        	
            setText(item.getFileID() + " | " +item.getTime() + " | " + item.getDate());
            if(item.getStatus().equals("2"))
            {
            	setBackground(Color.GREEN);
            }
            if(item.getStatus().equals("1"))
            {
            	setBackground(Color.YELLOW);
            }
            if(item.getStatus().equals("0"))
            {
            	setBackground(Color.RED);
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
   public void updater(){
	   ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			//This method runs once every second, it is used to set the progress bars, automate FIST deployment, and automate Maestro automation
		  @Override
		  public void run() {
		  }
		}, 0, 15, TimeUnit.SECONDS);
   }

}