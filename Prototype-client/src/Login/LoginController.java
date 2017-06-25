package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Timer;
import SystemManager.SystemManagerMainController;
import Parent.ParentMainController;
import SchoolManager.SchoolManagerMainController;
import Parent.ChoiceChildController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Teacher.TeacherMainController;
import Entity.Log;
import Entity.Semester;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.MainWindowStudentController;


public class LoginController extends QueryController implements Initializable{//Second window: login to system.


    @FXML
    private PasswordField passwordID;

    @FXML
    private Text successfulID;
    
    @FXML
    private Text wrongTextID;

    @FXML
    private TextField usernameID;

    @FXML
    private Button connectID;
    
    private boolean showNextWindow;//this flag will say if we stay in this scene or if go to the next scene.
    
    private static String userName;
    
    private static String password;
    
    public LoginController(String controllerID){
    	super(controllerID);
    }
    
    
    @FXML
    void loginIntoTheSystem(ActionEvent event) throws IOException {//Handler of the login button.
    	//create query for searching for teacher User, and check if the password that was entered is correct.
    	showNextWindow = true;
    	String userID = usernameID.getText();
		Log.print("LoginController: "+"userID: "+userID);
        password=passwordID.getText();
        if(userID.equals("")){
        	showNextWindow=false;//stay in this scene.
        	wrongTextID.setText("Please enter username");//show error message.

        }
        if(password.equals("")){
        	showNextWindow=false;//stay in this scene.
        	wrongTextID.setText("Please enter password.");//show error message.
        }
        ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID=" + userID );
        ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE status='true'");
        if(res != null){
    		ArrayList<String> row = res.get(0);
    		String[] parts = row.get(0).split(":");
    		Semester sem = null;
    		if(row != null)Semester.setCurrentSemester(sem =new Semester(parts[0],parts[1],row.get(1).equals("true"),"",""));
        }
        String userPassword = null;
        boolean isUserExist = false;
        ArrayList<String> userDetails = null;
        if(resultArray == null){showNextWindow = false; wrongTextID.setText("User is not exist.");}
        else if(resultArray.isEmpty() == false){//check if there is first row.
        			userDetails=resultArray.get(0);//get first row.
        		if(userDetails.isEmpty() == false){//check if there is first column.
        		      userPassword = userDetails.get(2);//get first userPSW.
        		      isUserExist = true;
        		}else{wrongTextID.setText("Please enter password.");//show error message.
        			showNextWindow = false;
        		}
        }else{
			showNextWindow = false;
        }

        if(showNextWindow==true){//if required fields are ok then perform their code, else stay in these scene.
        	if(isUserExist==true){
	        	if(userPassword.equals(password)){
	        		ArrayList<ArrayList<String>> status = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT status FROM user WHERE userID="+userID);
	                if(status != null)
	                	if(status.get(0) != null)
	                		if(status.get(0).get(0).equals("11")){wrongTextID.setText("User is already logged in into the system.");return;}
	        		String nextScreen=(resultArray.get(0)).get(3);
	                Object nextController = null;
	                switch(nextScreen)
	                {
	                 case "P":
	                	 nextScreen="/Parent/ParentMain.fxml";
	                	 nextController=new ParentMainController("ParentMainController");
	                	 break;
	                 case "S":
	             		Log.print("1");
	                	 nextScreen="/Secretary/SecretaryMainWindow.fxml";
	                	 nextController=new SecretaryMainController("SecretaryMainControllerID");
	             		Log.print("2");
	                	 break;
	                 case "T":
	                	 nextScreen="/Teacher/TeacherMain.fxml";
	                	 nextController=new TeacherMainController("TeacherMainControllerID");
	                	 break;
	                 case "ST":
	                	 nextScreen="/student/MainWindowStudent.fxml";
	                	 nextController=new MainWindowStudentController("StudentControllerID");
	                	 break;
	                 case "SM":
	                	 nextScreen="/SystemManager/SystemManagerMainWindow.fxml";
	                	 nextController=new SystemManagerMainController("SystemManagerControllerID");
	                	 break;
	                 case "M":
	                	 nextScreen="/SchoolManager/SchoolManagerMainWindow.fxml";
	                	 nextController=new SchoolManagerMainController("SchoolManagerMainControllerID");
	                	 break;
	                }
		         	//set current logged in user.
		         	User currentUser = new User(userDetails.get(0),userName = userDetails.get(1),userPassword,userDetails.get(3),userDetails.get(4),userDetails.get(5));
		         	User.setCurrentLoggedIn(currentUser);
		            try {//change to login scene.
				        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScreen));
				        loader.setController(nextController);
				        Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);	
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			    		transfferQueryToServer("UPDATE user SET status = 11 WHERE userID="+User.getCurrentLoggedIn().GetID());
			         } catch (IOException e) {
	            		Log.print("LoginController: "+e.getStackTrace().toString());
					}
	            }else{
	            	wrongTextID.setText("Wrong password, please try again.");//show error message.
	            }
            }
        }
    }

    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method preform when this controller scene is showing up.
        Timer t = new Timer(4000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	try{
            		successfulID.setText("");// make successfulID disappear after 4 seconds.
            	}catch(java.lang.NullPointerException e1){
            		Log.print("LoginController: "+e1.getStackTrace().toString());
            	}
            }
        });
        t.setRepeats(false);
        t.start();
	}
	
	public static String getUsernameID(){
		return userName;
	}
}
