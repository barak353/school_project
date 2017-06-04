package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Class.Class;;
public class ClassDefineController extends QueryController implements Initializable{
	
	@FXML
	private Text userID;
	@FXML
	private Button logout;
	@FXML
	private Button back;
	@FXML
	private Button OK;
	@FXML
	private Button NEXT;
    @FXML
    private Text ErrorMSG;
    @FXML
    private Text successMSG;
	@FXML
	private TextField ClassName;
	@FXML
	private TextField ClassNum;
	private static String CLASSNAME;  
	private static String CLASSNUM;
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
    void Next(ActionEvent event)
    {
    			 try {
    				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/ClassDefineNext.fxml"));
    				   loader.setController(new AddStudentToClassController("AddStudentToClassController"));
    				   Pane login_screen_parent = loader.load();
    				        Scene login_screen_scene=new Scene(login_screen_parent);
    						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
    						app_stage.hide();
    						app_stage.setScene(login_screen_scene);
    						app_stage.show(); 
    			        } catch (IOException e) {
    						System.err.println("Missing AddStudentToClassController.fxml file");
    						e.printStackTrace();
    					}
    }  
    //--------------------------------------------//
    @FXML
    void OK (ActionEvent event)
    {
    	int flag=0;
    	CLASSNAME=ClassName.getText();
    	CLASSNUM=ClassNum.getText();
    	//-----------------------------------------//
        if(CLASSNAME.equals("")==true){
        	ErrorMSG.setText("Please Enter Class Name");//show error message.
        	successMSG.setText("");
        	flag=1;
        }
        if(CLASSNUM.equals("")==true){
        	ErrorMSG.setText("Please Enter Class Num");//show error message.
        	successMSG.setText("");
        	flag=1;
        }
        //-----------------------------------------//
        if (flag==0)
        {
        	ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM class WHERE classNum='" + CLASSNUM + "' AND className='" + CLASSNAME + "'");
       
        	if(resultArray==null)
        	{
        		ErrorMSG.setText("There is NO such class");//show error message.
        		successMSG.setText("");
        	}	
        	else
        	{
        		successMSG.setText("The class:  "+CLASSNAME+CLASSNUM+"  was selected\nPress Next to continue...");
        		ErrorMSG.setText("");
        	}
        }
    }
    //--------------------------------------------//
	public static String GetClassName(){
		return CLASSNAME;
	}
    //--------------------------------------------//
	public static String GetClassNum(){
		return CLASSNUM;
	}
   //--------------------------------------------//
}
