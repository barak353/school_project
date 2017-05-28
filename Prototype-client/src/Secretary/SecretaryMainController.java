package Secretary;

import java.io.IOException;

import Login.LoginController;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecretaryMainController extends QueryController  {
	
//------------------------------------------------//
public SecretaryMainController (String controllerID)
{
		super(controllerID);
} 
//------------------------------------------------// 
	
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
private Button XID; 
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
				   //loader.setController(new TeacherPlacementController());
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
void Exit(ActionEvent event)
{
			 try {
				    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginWindow.fxml"));
			        loader.setController(new LoginController("LoginController"));
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
}