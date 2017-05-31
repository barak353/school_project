package student;

import Secretary.SecretaryMainController;
import application.QueryController;
import javafx.event.ActionEvent;

public class WatchPersonalFolderController extends QueryController {

	public WatchPersonalFolderController(String controllerID) {
		super(controllerID);
		
	}

	Object nextController=null;
	
	  void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new SecretaryMainController("StudentMainController");
	    	this.Back("/Secretary/MainWindowStudent.fxml",nextController, event);
	    } 

}
