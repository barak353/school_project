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
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> comboBoxID;
    
    @FXML
    private TextField updateTeacherDetailsTextField;
    
    @FXML
    private Text updateTeacherDetailsText;
    
    @FXML
    private TextField updateTeacherDetailTextField;
    
    @FXML
    private TextField updateTeacherIDTextField;

    @FXML
    private Text updateTeacherDetailsError;
    
    private int SelectedIndex=-1;
    
    @FXML
    void chooseUpdateOperation(ActionEvent event) {
    	SelectedIndex=comboBoxID.getSelectionModel().getSelectedIndex();//save the number of row in the comboBox that the user choose.
    }
    
    
    @FXML
    void getDetails(ActionEvent event) {//enter teacher ID and get her details.
    	errorTextID.setText("");
    	if(this.teacherID.getText()=="")errorTextID.setText("Field is empty. Please enter teacher ID.");
    	else{
    		ArrayList<ArrayList<String>> resultArray=transfferQueryToServer("SELECT id,name,unit FROM teachers WHERE id='"+teacherID.getText()+"'");
    		if(resultArray.isEmpty()==false){//if there is no rows returned in the result.
	    		ArrayList<String> row1=resultArray.get(0);//return empty result in second time
	    		resultTextID.setText("Teacher Details:\nTeacher ID: "+row1.get(0)+"\nTeacher name: "+row1.get(1)+"\nTeaching Unit: "+row1.get(2));
    		}
    	}
    }
    
    @FXML
    void setUpdateDetails(ActionEvent event) {
    	ArrayList<ArrayList<String>> resultArray;
    	switch(SelectedIndex){
    	case 0://Update unit teaching
    		System.out.println("0");
    		 transfferQueryToServer("UPDATE teachers SET unit='"+updateTeacherDetailsText.getText()+"' WHERE id='"+updateTeacherIDTextField.getText()+"'");
     		 resultArray=transfferQueryToServer("SELECT id,name,unit FROM teachers WHERE id='"+updateTeacherIDTextField.getText()+"'");
    		 if(resultArray!=null && resultArray.isEmpty()==false){//if there is no rows returned in the result.
 	    		ArrayList<String> row1=resultArray.get(0);//return empty result in second time
 	    		updateTeacherDetailsText.setText("Teacher Details:\nTeacher ID: "+row1.get(0)+"\nTeacher name: "+row1.get(1)+"\nTeaching Unit: "+row1.get(2));
    		 }
 	    	break;
    	case 1://Update Teacher name
    		System.out.println("1");
    		
    		resultArray=transfferQueryToServer("UPDATE teachers SET name='"+updateTeacherDetailTextField.getText()+"' WHERE id='"+updateTeacherIDTextField.getText()+"'");
    		System.out.println("2");
    		if(resultArray!=null && resultArray.isEmpty()==false){//if there is no rows returned in the result.
        		System.out.println("3");
	    		ArrayList<String> row1=resultArray.get(0);//return empty result in second time
	    		System.out.println("4");
	    		updateTeacherDetailsText.setText("Teacher Details:\nTeacher ID: "+row1.get(0)+"\nTeacher name: "+row1.get(1)+"\nTeaching Unit: "+row1.get(2));
	    		System.out.println("5");
    		}
    	case -1://No choose in the comboBox was made.
            	errorTextID.setText("Please make a selection in the combo box.");
            	break;
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameText.setText(LoginController.getUsernameID());
		comboBoxID.getItems().addAll(
			    "Update Unit teaching",
			    "Update Teacher name"
			);
	}

}

