package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.event.ActionEvent;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginScreen extends ControllerQuery implements Initializable{//Second window: login to system.


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
    
    private boolean showNextWindow = true;//this flag will say if we stay in this scene or if go to the next scene.
    
    @FXML
    void loginIntoTheSystem(ActionEvent event) {//Handler of the login button.

    	//create query for searching for teacher user, and check if the password that was entered is correct.
        String username=usernameID.getText();
        String password=passwordID.getText();
        if(username.equals("") || password.equals("")){
        	showNextWindow=false;//stay in this scene.
        	wrongTextID.setText("Please enter Username and Password.");//show error message.
        }
        if(showNextWindow==true){//if required field are ok then preform there code, else stay in these scene.
	       // sendQueryToServer(strQuery,'U','L');
            String strQuery="SELECT password FROM users WHERE userID='"+username+"'";//Create new query for getting this username password.
        	//ControllerQuery.sendQueryToServer(strQuery);//Send query to server.
        	
	         try {//change to login scene.
	        	Parent login_screen_parent=FXMLLoader.load(getClass().getResource("teacherWindow.fxml"));
				Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
				
	         } catch (IOException e) {
				System.out.println("Missing teacherWindow.fxml file");
				e.printStackTrace();
			}
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method preform when this controller scene is showing up.
        Timer t = new Timer(4000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	successfulID.setText(null);// make successfulID disappear after 4 seconds.
            }
        });
        t.setRepeats(false);
        t.start();
	}
}
