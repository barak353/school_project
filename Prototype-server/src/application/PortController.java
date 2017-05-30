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
    protected TextField DBuserID;

    @FXML
    protected TextField serverPortD;

    @FXML
    protected TextField DBhostID;

    @FXML
    protected TextField DBschemaID;

    @FXML
    protected Button connectID;

    @FXML
    protected Text errorTextID;

    @FXML
    protected PasswordField DBpasswordID;
    
    
    @FXML
    void connect(ActionEvent event) {
    	String errorMessage="Please provide: ";
    	boolean isvalid = true;
        if(DBuserID.getText().equals("")){
  	   	errorMessage+=" DB user,";
    	errorTextID.setText(errorMessage);
	    isvalid = false;
    	}
        if(serverPortD.getText().equals("")){
    	    errorMessage+=" Server port.";
    	    errorTextID.setText(errorMessage);
    	    isvalid = false;
    	}
        if(DBhostID.getText().equals("")){
  	   	errorMessage+=" DB host,";
    	    errorTextID.setText(errorMessage);
    	    isvalid = false;
    	}
        if(DBschemaID.getText().equals("")){
  	   	errorMessage+=" DB schema,";
    	errorTextID.setText(errorMessage);
	    isvalid = false;
    	}
        if(DBpasswordID.getText().equals("")){
  	   	errorMessage+=" DB password,";
    	errorTextID.setText(errorMessage);
	    isvalid = false;
    	}
        if(isvalid){
        	SchoolServer.startServer(this);
        }
    }
 
}
