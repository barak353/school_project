package Secretary;

import java.net.URL;
import java.util.ResourceBundle;

import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class StudentRequestFormController extends QueryController implements Initializable{

	Object nextController=null;
    @FXML
    private Text userID;
    
	public StudentRequestFormController(String controllerID)
	{
			super(controllerID);
	} 
	
    //-------------------------------------------------------------------------------------------// 
	 @FXML
	 void TurningBack(ActionEvent event)
	 {
		  this.nextController = new AskRequestFormController("AskRequestFormController");
	      this.Back("/Secretary/WhatFormChoose.fxml",nextController, event);
	 } 
    //-------------------------------------------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
}
