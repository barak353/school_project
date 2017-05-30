package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PortController {

    @FXML
    protected PasswordField passwordID;

    @FXML
    protected TextField hostID;	

    @FXML
    protected TextField portID;

    @FXML
    protected Text errorTextID;

	@FXML
	protected Button closeButton;

    @FXML
    protected boolean isError=true;

    @FXML
    protected Button connectID;
    
    
    @FXML
    void connect(ActionEvent event) {
    	String errorMessage="Please provide: ";
    	boolean isvalid = true;
        if(hostID.getText().equals("")){
  	   	errorMessage+=" host name,";
    	    errorTextID.setText(errorMessage);
    	    isvalid = false;
    	}
        if(portID.getText().equals("")){
  	   	errorMessage+=" port number,";
    	errorTextID.setText(errorMessage);
	    isvalid = false;

    	}
        if(passwordID.getText().equals("")){
    	    errorMessage+=" password.";
    	    errorTextID.setText(errorMessage);
    	    isvalid = false;
    	}
        if(isvalid){
        	SchoolServer.startServer(this);
        }
    }
 
}
