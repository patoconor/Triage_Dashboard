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

import com.wm.util.FileUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private static JScrollPane errorListScroll;
	private static MainGUI2 window;
	private JSplitPane splitPane;
    private JSplitPane splitPaneBottom;
    private static Boolean hideComplete = false;
    private JButton bRefreshList;
    private JButton bChangeCredentials;
	private JButton bOptions;
	
    private static String daysPast;
    private static JList<ListItem> list;
    private JPanel errorViewPanel;
    private JPanel pCredentials;
    
    private static DefaultListModel<ListItem> errorList;
  //variables to keep stuff the same on refresh
    private static int index = 0;
    private static int selectedViewPastDays=0;
    private static int selectedServerBox=0;
    private static Dimension frameSize= new Dimension(1220, 671);
    
    private JTextField tfFileID;
    private JTextField tfFailTime;
    private JTextField tfServiceName;
    private JTextField tfDeveloperName;
    
    private JComboBox<String> comboBox_1;
    private JTextField tfExpectationID;
    private JTextField tfEnvironment;
    private JTextField tfAnalystName;
    private JTextField tfExpectStatus;
    private JTextField tfStartTime;
    private JTextField tfEndTime;
    private JTextArea textArea;
    private JTextField tfServerName;
    private JComboBox<String> cbServerLocationSelect;
    private JButton bServer;
    
    private static JComboBox<String> cbExpectationSelect;
    private JButton bViewExpectations;
    
    private JButton bLookingIntoIt;
    private JButton bReplyInFist;
    private static JTextPane tpReplyText;
    private static int previousIndex;
    private JButton bTakeAction;
    private static String dateSelect;
    private static String currentDate;
    
	private JDialog dCred;
    String[] textData= new String[5];
    private JLabel lblTriageDashboard;
    private JTextArea textArea_1;
    private JToggleButton tglbtnNewToggleButton;
        
    
    
    public MainGUI2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	
    	//list of errors in left pane
    	errorList= new DefaultListModel<>();
    	setupList();
    	int setindex = 0;
        list = new JList<>(errorList);
        for(int i = 0; i<errorList.size();i++){
        	if(errorList.get(i).getErid()==index){
        		setindex = i;
        		previousIndex=i;
        	}
        }
        list.setSelectedIndex(setindex);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setCellRenderer(new ListItemRenderer());
        
        //right panel for viewing error details
        errorViewPanel = new JPanel();
        errorViewPanel.setPreferredSize(new Dimension(1049, 640));
        
        //bottom panel for credentials and options
        pCredentials = new JPanel();
        pCredentials.setLayout(new GridLayout(0,4,10,0));
        pCredentials.setMinimumSize(new Dimension(0,20));
        
        bRefreshList = new JButton();
        bRefreshList.setText("Refresh List");
        pCredentials.add(bRefreshList);
        bRefreshList.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	try {
							createAndShowGUI2();
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
			                
			            }}).start();}});
        
        tglbtnNewToggleButton = new JToggleButton("Hide/Show Completed");
        tglbtnNewToggleButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(hideComplete == false){
        			hideComplete = true;
        		}
        		else{
        			hideComplete = false;
        		}
        		try {
					createAndShowGUI2();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        pCredentials.add(tglbtnNewToggleButton);
        
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
    	bOptions.setText("Update");
        pCredentials.add(bOptions);
        bOptions.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	int value = (JOptionPane.showConfirmDialog(
			                    frame,
			                      "Are you sure you want to update? This will close the program.",
			                      "Warning",
			                      JOptionPane.YES_NO_OPTION));
			                if (value == JOptionPane.YES_OPTION) {
			                  if(new File("C://Triage_Dashboard/updateTD.bat").isFile()==false){
			                    File source2 = new File("R://BenIT/Files/All/Tools/TriageDashboard/updateTD.bat");
			                    File desc1 = new File("C://Triage_Dashboard/updateTD.bat");
			                    try {
			                      FileUtil.copyTo(source2, desc1);
			                    } catch (IOException e1) {
			                      // TODO Auto-generated catch block
			                      e1.printStackTrace();
			                    }
			                  }
			                  
			                  
			                  try {
			                    Runtime.getRuntime().exec("cmd /c start C://Triage_Dashboard/updateTD.bat");
			                    System.exit(0);
			                  } catch (IOException e1) {
			                    // TODO Auto-generated catch block
			                    e1.printStackTrace();
			                  }
			                }else if (value == JOptionPane.NO_OPTION) {
			                }
			                


			            }}).start();}});
        
        //left pane
        errorListScroll = new JScrollPane(list);
        //right pane
        JScrollPane errorViewScroll = new JScrollPane(errorViewPanel);
        
        //Create a vertically split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,	errorListScroll, errorViewScroll);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(161);
        //splitPane.setMinimumSize(new Dimension(1*screenSize.width/6, 1*screenSize.height/6));

        //create a horizontally split pane for the credentials and options
        splitPaneBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT,	splitPane, pCredentials);
        splitPaneBottom.setOneTouchExpandable(false);
        splitPaneBottom.setEnabled(false);
        splitPaneBottom.setResizeWeight(1);
        splitPaneBottom.setMinimumSize(new Dimension(1*screenSize.width/6, 1*screenSize.height/6));
        //Provide a preferred size for the split pane.
        splitPaneBottom.setPreferredSize(frameSize);
        
        
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
    
    public static void setupList()
    {
    	
    	/*
    	ListItem li = new ListItem("");
    	li.setFileID("63001");
    	errorList.addElement(li);
    	*/
    	BufferedReader br;
    	String line;
    	System.out.println(daysPast);
//    	if(errorList.size()>0){
//    		for(int i=0;i<errorList.size();i++){
//    		errorList.remove(i);
//    		}
//    		errorList= new DefaultListModel<>();
//    	}
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
	            Date d = new Date();
            	Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DATE, -Integer.parseInt(daysPast));
                d.setTime( c.getTime().getTime() );
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                setDateSelect(dateFormat.format(d));
                String one = "select DISTINCT * from TRIAGE WHERE ERDATE >= '"+dateSelect+"' order by ERDATE desc, ERTIME desc";
                String two = "select DISTINCT * from TRIAGE WHERE ERDATE >= '"+dateSelect+"' AND STATUS != 2 order by ERDATE desc, ERTIME desc";
	            String finalone = "";
                if(hideComplete==true){
                	finalone = two;
	            }
                if(hideComplete==false){
                	finalone = one;
                }
                resultSet = statement.executeQuery(finalone);
	            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
	            while(resultSet.next()){
	            	String text = resultSet.getString("FILEID")+"|"+resultSet.getString("STATUS")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
	            	int erid = resultSet.getInt("ERRORID");
	            	String res = resultSet.getString("RESOLUTION");
	            	System.out.println(text);
	            	errorList.addElement(new ListItem(text,erid,res));
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
    	errorList.get(previousIndex).setReplyText(tpReplyText.getText());
        populateErrorPanel();
        previousIndex=list.getSelectedIndex();
    }
    
    public static ListItem getCurrentItem()
    {
    	return errorList.get(list.getSelectedIndex());
    }
    
    
    //Shows the corresponding information for a selected error
    private void setupErrorPanel() {
    	errorViewPanel.setLayout(null);
    	
    	int yShift=30;
		String [] serverStrings = {"View Server","View Server Log","View Error Log"};
        
    	
        JPanel pErrorInfo = new JPanel();
        pErrorInfo.setBounds(-1, 400, 601, 241);
        pErrorInfo.setLayout(new BoxLayout(pErrorInfo,BoxLayout.Y_AXIS));
        pErrorInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pErrorInfo);
    	
    	
    	JLabel lErrorHeader = new JLabel("Error Information:");
    	lErrorHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
    	pErrorInfo.add(lErrorHeader);
    	
    	textArea_1 = new JTextArea();
    	textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
    	textArea_1.setLineWrap(true);
    	textArea_1.setBounds(10, 20, 582, 213);
    	textArea_1.setWrapStyleWord(true);
    	
    	JScrollPane scroll = new JScrollPane (textArea_1, 
    			   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    	pErrorInfo.add(scroll);
    	
    	
    	JPanel pActions = new JPanel();
    	pActions.setBounds(599, -1, 451, 402);
    	pActions.setLayout(null);
    	pActions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pActions);
    	
    	JLabel lActionHeader = new JLabel("Reply to                                 expectation in FIST:");
    	lActionHeader.setBounds(110, 0 , 300, 20);
    	pActions.add(lActionHeader);
    	
    	JLabel lActionHeader2 = new JLabel("currently selected");
    	lActionHeader2.setBounds(156, 0 , 150, 20);
    	pActions.add(lActionHeader2);
    	Font font = lActionHeader2.getFont();
    	Map attributes = font.getAttributes();
    	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    	lActionHeader2.setFont(font.deriveFont(attributes));
    	
    	
    	
    	bLookingIntoIt = new JButton();
    	bLookingIntoIt.setBounds(95, 20 , 260, 20);
    	bLookingIntoIt.setText("Reply \"Looking into it...\"");
    	pActions.add(bLookingIntoIt);
        bLookingIntoIt.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	if(getCurrentItem().getExpectNum()!=0)
			        	{
			        		int replyIndex = list.getSelectedIndex();
			        		int expectIndex = cbExpectationSelect.getSelectedIndex();
			        		
			        		
			        		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
			        		WebDriver dr = new ChromeDriver();
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,sOfficeLocation,false,dr);
			        		try{
				        		
				        		Fdriver.loginConfig();
				        		Fdriver.getExpectationsPage(errorList.get(replyIndex).getFileID(),errorList.get(replyIndex).getDate());
				        		if(Fdriver.errorReply((errorList.get(replyIndex).getExpectNum() +(1 - expectIndex)), errorList.get(replyIndex).getFileID(), "Looking into it..."))
				        		{
				        		Fdriver.closeDriver();
				        		changeErrorStatus(1,errorList.get(replyIndex));
				        		}
			        		}
			        		catch(Exception e)
			        		{
			        			Fdriver.closeDriver();
			        			JOptionPane.showMessageDialog(null,"There was an error while attempting to reply.");
			        		}
			        	}
			            }}).start();}});
        
        JLabel lRecommendedHeader = new JLabel("Send this reply to fist for current expectation:");
    	lRecommendedHeader.setBounds(113, 60 , 300, 20);
    	pActions.add(lRecommendedHeader);
    	
        tpReplyText = new JTextPane();
        tpReplyText.setBounds(10, 85,430, 280);
        tpReplyText.setBackground(Color.WHITE);
        pActions.add(tpReplyText);
        
    	
        bReplyInFist = new JButton();
        bReplyInFist.setBounds(95, 375 , 260, 20);
        bReplyInFist.setText("Send Reply!");
    	pActions.add(bReplyInFist);
    	bReplyInFist.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	if(getCurrentItem().getExpectNum()!=0)
			        	{
			        		int replyIndex = list.getSelectedIndex();
			        		int expectIndex = cbExpectationSelect.getSelectedIndex();
			        		errorList.get(replyIndex).setReplyText(tpReplyText.getText());
			        		
			        		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
			        		WebDriver dr = new ChromeDriver();
			        		FIST Fdriver = new FIST(sFistUser,sFistPass,sOfficeLocation,false,dr);
			        		try{
				        		
				        		Fdriver.loginConfig();
				        		Fdriver.getExpectationsPage(errorList.get(replyIndex).getFileID(),errorList.get(replyIndex).getDate());
				        		if(Fdriver.errorReply((errorList.get(replyIndex).getExpectNum() +(1 - expectIndex)), errorList.get(replyIndex).getFileID(), errorList.get(replyIndex).getReplyText()))
				        		{
				        			Fdriver.closeDriver();
				        			submitResolution(errorList.get(replyIndex).getReplyText());
				        		
				        		if (JOptionPane.showConfirmDialog(null, "Do you want to mark this error as completed?", "Request", 
									    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
									    == JOptionPane.YES_OPTION)
									{
				        			changeErrorStatus(2,errorList.get(replyIndex));
									}
				        		}
				        		
			        		}
			        		catch(Exception e)
			        		{
			        			Fdriver.closeDriver();
			        			JOptionPane.showMessageDialog(null,"There was an error while attempting to reply.");
			        		}
			        	}
			                
			            }}).start();}});
        
        JLabel lblStatus = new JLabel("TRIAGE STATUS:");
        lblStatus.setForeground(new Color(255, 0, 0));
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblStatus.setBounds(269, 350, 110, 14);
        errorViewPanel.add(lblStatus);
        /*
        final JComboBox comboBox = new JComboBox();
        comboBox.setBounds(209, 399, 110, 20);
        errorViewPanel.add(comboBox);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"In Progress", "Complete"}));
        */
        
        JButton bIncomplete = new JButton("Incomplete");
        bIncomplete.setBounds(154, 370, 100, 20);
        errorViewPanel.add(bIncomplete);
        
        JButton bInProgress = new JButton("In Progress");
        bInProgress.setBounds(264, 370, 100, 20);
        errorViewPanel.add(bInProgress);
        
        JButton bCompleted = new JButton("Completed");
        bCompleted.setBounds(374, 370, 100, 20);
        errorViewPanel.add(bCompleted);
        
        JLabel lServerName = new JLabel("Server Name:");
        lServerName.setForeground(Color.BLUE);
        lServerName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lServerName.setBounds(29, 134, 79, 20);
        errorViewPanel.add(lServerName);
        
    	tfServerName = new JTextField();
    	tfServerName.setBounds(111, 134, 180, 20);
    	errorViewPanel.add(tfServerName);
    	tfServerName.setEditable(false);
    	tfServerName.setBackground(Color.WHITE);
    	
    	
    	cbServerLocationSelect = new JComboBox <String> ();
    	cbServerLocationSelect.setBounds(18, 166, 150, 20);
    	errorViewPanel.add(cbServerLocationSelect);
    	cbServerLocationSelect.setModel(new DefaultComboBoxModel<String>(serverStrings));
    	cbServerLocationSelect.setSelectedIndex(selectedServerBox);
    	cbServerLocationSelect.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	        selectedServerBox=cbServerLocationSelect.getSelectedIndex();
    	    }
    	});
    	
    	
        bServer = new JButton();
        bServer.setBounds(181, 166, 110, 20);
        errorViewPanel.add(bServer);
        bServer.setText("View");
        
        tfFileID = new JTextField();
        tfFileID.setBounds(299, 57, 80, 20);
        errorViewPanel.add(tfFileID);
        tfFileID.setEditable(false);
        tfFileID.setBackground(Color.WHITE);
        JLabel lFileID = new JLabel("FILE ID:");
        lFileID.setForeground(Color.BLACK);
        lFileID.setFont(new Font("Tahoma", Font.BOLD, 11));
        lFileID.setBounds(250, 55, 51, 20);
        errorViewPanel.add(lFileID);
        JLabel lFailTime = new JLabel("Error Date/Time:");
        lFailTime.setForeground(Color.BLUE);
        lFailTime.setFont(new Font("Tahoma", Font.BOLD, 11));
        lFailTime.setBounds(9, 104, 109, 20);
        errorViewPanel.add(lFailTime);
        
        
		
		tfFailTime = new JTextField();
		tfFailTime.setBounds(111, 104, 180, 20);
		errorViewPanel.add(tfFailTime);
		tfFailTime.setEditable(false);
		tfFailTime.setBackground(Color.WHITE);
		JLabel lServiceName = new JLabel("Location:");
		lServiceName.setForeground(Color.BLUE);
		lServiceName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lServiceName.setBounds(341, 163, 61, 20);
		errorViewPanel.add(lServiceName);
		
        
		
		tfServiceName = new JTextField();
		tfServiceName.setBounds(402, 163, 180, 20);
		errorViewPanel.add(tfServiceName);
		tfServiceName.setEditable(false);
		tfServiceName.setBackground(Color.WHITE);
		
		tfDeveloperName = new JTextField();
		tfDeveloperName.setBounds(402, 134, 180, 20);
		errorViewPanel.add(tfDeveloperName);
		tfDeveloperName.setEditable(false);
		tfDeveloperName.setBackground(Color.WHITE);
		JLabel lDeveloperName = new JLabel("Developer:");
		lDeveloperName.setForeground(Color.BLUE);
		lDeveloperName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lDeveloperName.setBounds(333, 134, 100, 20);
		errorViewPanel.add(lDeveloperName);
		JLabel lExpectationSelect = new JLabel("Pick Expectation:");
		lExpectationSelect.setForeground(new Color(0, 128, 0));
		lExpectationSelect.setFont(new Font("Tahoma", Font.BOLD, 11));
		lExpectationSelect.setBounds(9, 260, 99, 20);
		errorViewPanel.add(lExpectationSelect);
		
		
		cbExpectationSelect = new JComboBox <String> ();
		cbExpectationSelect.setBounds(111, 260, 180, 20);
		errorViewPanel.add(cbExpectationSelect);
		
		bViewExpectations = new JButton();
		bViewExpectations.setBounds(111, 320, 180, 20);
		errorViewPanel.add(bViewExpectations);
		bViewExpectations.setText("Open Expectations Page");
		
        tfAnalystName = new JTextField();
        tfAnalystName.setBounds(402, 104, 180, 20);
        errorViewPanel.add(tfAnalystName);
        tfAnalystName.setEditable(false);
        tfAnalystName.setBackground(Color.WHITE);
        JLabel lAnalystName = new JLabel("Analyst:");
        lAnalystName.setForeground(Color.BLUE);
        lAnalystName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lAnalystName.setBounds(347, 104, 100, 20);
        errorViewPanel.add(lAnalystName);
        
    	tfExpectationID = new JTextField();
    	tfExpectationID.setBounds(300, 225, 80, 20);
    	errorViewPanel.add(tfExpectationID);
    	tfExpectationID.setEditable(false);
    	tfExpectationID.setBackground(Color.WHITE);
    	JLabel lExpectationID = new JLabel("EXPECTATION ID:");
    	lExpectationID.setForeground(Color.BLACK);
    	lExpectationID.setFont(new Font("Tahoma", Font.BOLD, 11));
    	lExpectationID.setBounds(202, 226, 100, 20);
    	errorViewPanel.add(lExpectationID);
    	JLabel lExpectStatus = new JLabel("Status:");
    	lExpectStatus.setForeground(new Color(0, 128, 0));
    	lExpectStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
    	lExpectStatus.setBounds(353, 260, 46, 20);
    	errorViewPanel.add(lExpectStatus);
    	
    	tfExpectStatus = new JTextField();
    	tfExpectStatus.setBounds(401, 260, 180, 20);
    	errorViewPanel.add(tfExpectStatus);
    	tfExpectStatus.setEditable(false);
    	tfExpectStatus.setBackground(Color.WHITE);
    	JLabel lEnvironment = new JLabel("Environment:");
    	lEnvironment.setForeground(new Color(0, 128, 0));
    	lEnvironment.setFont(new Font("Tahoma", Font.BOLD, 11));
    	lEnvironment.setBounds(29, 288, 79, 20);
    	errorViewPanel.add(lEnvironment);
    	
		tfEnvironment = new JTextField();
		tfEnvironment.setBounds(111, 288, 180, 20);
		errorViewPanel.add(tfEnvironment);
		tfEnvironment.setEditable(false);
		tfEnvironment.setBackground(Color.WHITE);
		JLabel lEndTime = new JLabel("End Time:");
		lEndTime.setForeground(new Color(0, 128, 0));
		lEndTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lEndTime.setBounds(338, 320, 60, 20);
		errorViewPanel.add(lEndTime);
		JLabel lStartTime = new JLabel("Start Time:");
		lStartTime.setForeground(new Color(0, 128, 0));
		lStartTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lStartTime.setBounds(330, 288, 82, 20);
		errorViewPanel.add(lStartTime);
		
		tfStartTime = new JTextField();
		tfStartTime.setBounds(401, 288, 180, 20);
		errorViewPanel.add(tfStartTime);
		tfStartTime.setEditable(false);
		tfStartTime.setBackground(Color.WHITE);
		
		tfEndTime = new JTextField();
		tfEndTime.setBounds(401, 320, 180, 20);
		errorViewPanel.add(tfEndTime);
		tfEndTime.setEditable(false);
		tfEndTime.setBackground(Color.WHITE);
		
		JPanel pResolution = new JPanel();
		pResolution.setBounds(599, 400, 451, 241);
		pResolution.setLayout(new BoxLayout(pResolution,BoxLayout.Y_AXIS));
		pResolution.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	errorViewPanel.add(pResolution);
    	
    	JLabel lblResolution = new JLabel("Previously Submitted Resolution:");
    	lblResolution.setAlignmentX(Component.CENTER_ALIGNMENT);
		pResolution.add(lblResolution);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea.setBounds(10, 20, 431, 213);
		
		JScrollPane scroll2 = new JScrollPane (textArea, 
 			   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pResolution.add(scroll2);
		
		
		lblTriageDashboard = new JLabel("Triage Dashboard");
		lblTriageDashboard.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 18));
		lblTriageDashboard.setBounds(217, 11, 167, 20);
		errorViewPanel.add(lblTriageDashboard);
		
		JLabel lblViewPastDays = new JLabel("View Past Days:");
		lblViewPastDays.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblViewPastDays.setBounds(18, 58, 98, 14);
		errorViewPanel.add(lblViewPastDays);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3"}));
		comboBox_1.setBounds(119, 55, 40, 20);
		errorViewPanel.add(comboBox_1);
		comboBox_1.setSelectedIndex(selectedViewPastDays);
		comboBox_1.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	selectedViewPastDays=comboBox_1.getSelectedIndex();
    	    }
    	});
		
		JButton btnGo = new JButton("Go!");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				daysPast = comboBox_1.getSelectedItem().toString();
				try {
					createAndShowGUI2();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGo.setBounds(169, 54, 51, 23);
		errorViewPanel.add(btnGo);
		
		JLabel lblV = new JLabel("v_1.1.0");
		lblV.setBounds(722, 681, 46, 14);
		errorViewPanel.add(lblV);
		bViewExpectations.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {			  
			  new Thread(new Runnable() {
			        public void run() {
			        	System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
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
        
        bIncomplete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		changeErrorStatus(0,getCurrentItem());
        	}
        });
        
        bInProgress.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		changeErrorStatus(1,getCurrentItem());
        	}
        });
        
        bCompleted.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		changeErrorStatus(2,getCurrentItem());
        	}
        });
        
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
        	textArea.setText("");
        	String[] DateArray = new String[0];
    	}
    	
    	
    	if(errorList.size()>0){
    	tfFileID.setText(getCurrentItem().getFileID());
    	textArea.setText(getCurrentItem().getResolution());
    	tfDeveloperName.setText(getCurrentItem().getDevName());
    	tfDeveloperName.setCaretPosition(0);
    	tfServiceName.setText(getCurrentItem().getServiceLocation());
    	tfServiceName.setCaretPosition(0);
    	tfFailTime.setText(getCurrentItem().getDate() + "  |  " + getCurrentItem().getTime());
    	tfServerName.setText(getCurrentItem().getServer());
    	String[] DateArray = new String[getCurrentItem().getExpectDateList().size()];
    	DateArray = getCurrentItem().getExpectDateList().toArray(DateArray);
    	cbExpectationSelect.setModel(new DefaultComboBoxModel<String>(DateArray));
    	
    	tpReplyText.setText(getCurrentItem().getReplyText());
    	
    	
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
    	textArea_1.setText(getCurrentItem().getErrorMessage()+"\n\n"+getCurrentItem().getStackTrace());
    	//tpErrorText.setText(getCurrentItem().getErrorMessage()+"\n\n"+getCurrentItem().getStackTrace());
    	
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
//        if(sOfficeLocation.equals("Winston Salem"))
//        {
//        	cbOffice.setSelectedIndex(0);
//        }
//        else if (sOfficeLocation.equals("Hunt Valley"))
//        {
//        	cbOffice.setSelectedIndex(1);
//        }
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
    	previousIndex=0;
        frame = new JFrame("Triage Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window = new MainGUI2();
        frame.getContentPane().add(window.getPane());
        //frame.setMinimumSize(new Dimension(500, 300));

        //Display the window.
        
        frame.pack();
        frame.setVisible(true);
        
    }
    
    private static void createAndShowGUI2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    	
    	frame.getContentPane().removeAll();
    	index = list.getSelectedValue().getErid();
    	frameSize = window.getPane().getSize();
    	String ReplyText = tpReplyText.getText();
    //	int expectIndex = cbExpectationSelect.getSelectedIndex();
    	window = new MainGUI2();
        frame.getContentPane().add(window.getPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
      //  cbExpectationSelect.setSelectedIndex(expectIndex);
        tpReplyText.setText(ReplyText);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                	daysPast = "0";
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
        	if(item.getFileID().equals("?")){
        		setText("    "+item.getFileID() + "     | " +item.getTime() + " | " + item.getDate());
        	}
        	else{
            setText(item.getFileID() + " | " +item.getTime() + " | " + item.getDate());
        	}
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
            	setBackground(Color.WHITE);
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
    public String getDateSelect() {
		return dateSelect;
	}

	public static void setDateSelect(String dateSelect1) {
		dateSelect = dateSelect1;
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
   
   public void submitResolution(String resolution)
   {
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
           statement.executeUpdate("UPDATE TRIAGE set RESOLUTION = '"+resolution+"'where ERRORID = "+getCurrentItem().getErid()+"");
           
		} catch (Exception e1) {
           e1.printStackTrace();
       }
		try {
			createAndShowGUI2();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		
	}
   }
   
   public void changeErrorStatus(int Status, ListItem item)
   {
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
           statement.executeUpdate("UPDATE TRIAGE set STATUS = '"+Status+"'where ERRORID = "+item.getErid()+"");
           
		} catch (Exception e1) {
           e1.printStackTrace();
       }
		try {
			createAndShowGUI2();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		
	}
   }
   
   public static  void updater(){
	   ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			//This method runs once every second, it is used to set the progress bars, automate FIST deployment, and automate Maestro automation
		  @Override
		  public void run() {
			  //window.list.removeAll();
			  System.out.println("15");
			  try {
				createAndShowGUI2();
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
		}, 0, 15, TimeUnit.SECONDS);
   }
}
