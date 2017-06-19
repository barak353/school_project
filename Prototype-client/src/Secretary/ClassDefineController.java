package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Entity.Class;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;;
public class ClassDefineController extends QueryController implements Initializable{
	
	@FXML
	private Text userID;
	@FXML
	private Button logout;
	@FXML
	private Button back;
    @FXML
    private Text ErrorMSG;
    @FXML
    private Text successMSG;
    @FXML
    private ComboBox<?> ComboClass;
	
	private static String CLASSNAME;  
	Object nextController=null;
	//------------------------------------------------//
	public ClassDefineController(String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//------------------------------------------------//
    	ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM class");
    	if(resultArray==null)
    	{
    		ErrorMSG.setText("There is no classes in DB.");
    	}
    	else
    	{
    		ArrayList<String> listClasses = new ArrayList<String>();
    		for(int i=0;i<resultArray.size();i++)
    		{
    			listClasses.add(resultArray.get(i).get(0));
    		}
    		ObservableList L= FXCollections.observableList( listClasses);
    		ComboClass.setItems(L);	
    	}
		
	}
	//------------------------------------------------//
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new SecretaryMainController("SecretaryMainController");
    	this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
    } 
    //-----------------------------------------------//

    @FXML
    void ChoosenClass (ActionEvent event)
    {
    	CLASSNAME=(String) ComboClass.getValue();
    	//-----------------------------------------//
             		 try {
          			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/ClassDefineNext.fxml"));
          			   loader.setController(new AddStudentToClassController("AddStudentToClassController"));
          			   Pane login_screen_parent = loader.load();
          			        Scene login_screen_scene=new Scene(login_screen_parent);
          					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
          					app_stage.hide();
          					app_stage.setScene(login_screen_scene);
          					app_stage.show(); 
          		        } catch (IOException k) {
          					System.err.println("Missing AddStudentToClassController.fxml file");
          					k.printStackTrace();
          				}
    }
    //--------------------------------------------//
	public static String GetClassName(){
		return CLASSNAME;
	}
    //--------------------------------------------//
}
