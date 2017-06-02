package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import Parent.ParentMainController;
import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecretaryMainController extends QueryController implements Initializable{
	
//------------------------------------------------//
public SecretaryMainController (String controllerID)
{
		super(controllerID);
} 
//------------------------------------------------// 
@FXML
private Text userID;

Object nextController=null;	
@FXML
private Button OpenNewID;
@FXML
private Button SendRequestID;
@FXML
private Button TeacherPlacementID;
@FXML
private Button StudentChangeID;  
@FXML
private Button ClassDefineID;  
@FXML
private Button logout;
@FXML
private Button back;
@FXML
void SendRequest(ActionEvent event)
{
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/WhatFormChoose.fxml"));
				   loader.setController(new AskRequestFormController("AskRequestID"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing WhatFormChoose.fxml file");
						e.printStackTrace();
					}
}  
//-------------------------------------------------------------------------------------------------//
@FXML
void TeacherPlacement(ActionEvent event)
{
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/TeacherPlacement.fxml"));
				   loader.setController(new TeacherPlacementController("TeacherID"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing TeacherPlacement.fxml file");
						e.printStackTrace();
					}
} 
//-------------------------------------------------------------------------------------------------//
@FXML
void StudentChange(ActionEvent event)
{
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/StudentChange.fxml"));
				   loader.setController(new StudentChangeController("StudentChangeController"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing StudentChange.fxml file");
						e.printStackTrace();
					}
} 
//-------------------------------------------------------------------------------------------------//
@FXML
void ClassDefine(ActionEvent event)
{
			
	Object result = transfferQueryToServer("INSERT INTO class (classNum,className) VALUES (2,'A')");
	Object result2 = transfferQueryToServer("INSERT INTO class (classNum,className) VALUES (3,'B')");
	Object result3 = transfferQueryToServer("INSERT INTO class (classNum,className) VALUES (4,'C')");
	/*if(result instanceof Integer){
		System.out.println("error");
	}*/
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/ClassDefine.fxml"));
				   loader.setController(new ClassDefineController("ClassDefineController"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing ClassDefine.fxml file");
						e.printStackTrace();
					}
} 
//-------------------------------------------------------------------------------------------------//
@FXML
void TurningBack(ActionEvent event)
{
	this.nextController = new LoginController("SecretaryLoginController");
	this.Back("/Login/LoginWindow.fxml",nextController, event);
}
//-------------------------------------------------------------------------------------------------//

public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
	User user = User.getCurrentLoggedIn();
	userID.setText(user.GetUserName());

}


//-------------------------------------------------------------------------------------------------//
}