package Secretary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Login.*;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SecretaryController extends QueryController implements Initializable{

    public SecretaryController(String controllerID) {
		super(controllerID);
	}

    @FXML
    private TextField teacherID;

    @FXML
    private Button buttonGetDetailsID;

    @FXML
    private Text userNameText;

    @FXML
    private Text resultTextID;

    @FXML
    private Text errorTextID;
    
    @FXML
    void getDetails(ActionEvent event) {
    	errorTextID.setText("");
    	if(this.teacherID.getText()=="")errorTextID.setText("Field is empty. Please enter teacher ID.");
    	else{
    		ArrayList<ArrayList<String>> resultArray=transfferQueryToServer("SELECT id,name,unit FROM teachers WHERE id='"+teacherID.getText()+"'");
    		ArrayList<String> row1=resultArray.get(0);
    		resultTextID.setText("Teacher Details:\nTeacher ID: "+row1.get(0)+"\nTeacher name: "+row1.get(1)+"\nTeaching Unit: "+row1.get(2));
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameText.setText(LoginController.getUsernameID());
	}

}

