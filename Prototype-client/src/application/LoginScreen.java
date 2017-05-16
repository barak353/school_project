package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import ocsf.client.*;

public class LoginScreen extends ControllerQuery{


    @FXML
    private PasswordField passwordID;

    @FXML
    private Text wrongTextID;

    @FXML
    private TextField usernameID;

    @FXML
    private Button connectID;

    @FXML
    void loginIntoTheSystem(ActionEvent event) {
    	//create query for searching for teacher user, and check if the password that was entered is correct.
        String username=usernameID.getText();
        String password=passwordID.getText();
        System.out.println("usernameID: "+username+"passwordID: "+password);
        String strQuery="SELECT password FROM users WHERE userID='"+username+"'";
        System.out.println("in loginIntoTheSystem");
        sendQueryToServer(strQuery,'U','L');
        

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
