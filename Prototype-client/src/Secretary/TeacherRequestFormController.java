package Secretary;

import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TeacherRequestFormController extends QueryController {
	
	Object nextController=null;
	//------------------------------------------------//
	public TeacherRequestFormController(String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------// 
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new AskRequestFormController("AskRequestFormController");
    	this.Back("/Secretary/WhatFormChoose.fxml",nextController, event);
    } 
    //-------------------------------------------------------------------------------------------//
}
