package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import Parent.ParentMainController;
import Entity.User;
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

/**
 * 
 * This controller handles the secretary's actions and is responsible for presenting the secretary's main screen
 *
 */
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

/**
 * 
 * After pressing the appropriate button, this function leads to the: Open new semester action.
 * @param event
 */
@FXML
void openSemester(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/OpenNewSemesterWindow.fxml"));
			   loader.setController(new OpenNewSemesterController("openNewSemesterControllerID"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing openNewSemesterWindow.fxml file");
					e.printStackTrace();
				}
}

/**
 * 
 * After pressing the appropriate button, this function leads to the: Send request to school manager option.
 * @param event
 */
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
/**
 * 
 * After pressing the appropriate button, this function leads to the: Teacher placement option.
 * @param event
 */
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
/**
 * 
 * After pressing the appropriate button, this function leads to the: Student change option.
 * @param event
 */
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
				System.err.println("Missing StudentChangeController.fxml file");
				e.printStackTrace();
			}	
} 
//-------------------------------------------------------------------------------------------------//
/**
 * 
 * After pressing the appropriate button, this function leads to the: Class define option.
 * @param event
 */
@FXML
void ClassDefine(ActionEvent event)
{
			
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
/**
 * 
 * Initialize function, shows the logged in user.
 * @param arg0
 * @param arg1
 */
public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
	User user = User.getCurrentLoggedIn();
	userID.setText(user.GetUserName());

}


//-------------------------------------------------------------------------------------------------//
}