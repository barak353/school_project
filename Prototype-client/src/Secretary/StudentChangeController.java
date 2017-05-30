package Secretary;

import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentChangeController extends QueryController {

	
	Object nextController=null;
	
	public StudentChangeController(String controllerID)
	{
			super(controllerID);
	} 
	
    //-------------------------------------------------------------------------------------------// 
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	} 
	
}
