package Secretary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entity.Semester;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class MergeClassesCoursesController extends QueryController implements Initializable {
	
	  private Object nextController=null;
	  private Button logout;
      @FXML
      private Text userID;
      @FXML
      private Button back;
      @FXML
      private ComboBox<?> CourseL;
      @FXML
      private ComboBox<?> ClassL;
      @FXML
      private Button Assign;
      @FXML
      private Text text;
      @FXML
      private Text t;
      @FXML
      private Button Next;
      private String courseID;
      private Semester sem;
	  //-----------------------------------------------------------//
	  public MergeClassesCoursesController (String controllerID)
	  {
			super(controllerID);
	  }
	  //-----------------------------------------------------------// 
	  @FXML
	  void AssignHandler(ActionEvent event) 
	  {
		  	
		  
	  }
      //-----------------------------------------------------------// 
	    @FXML
	    void NextHandler(ActionEvent event)
	    {

	    }
	

    //-----------------------------------------------------------------------------------------//
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new OpenNewSemesterController("OpenNewID");
		this.Back("/Secretary/OpenNewSemesterWindow.fxml",nextController,event);
	}
	//-----------------------------------------------------------------------------------------//
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//------------------------------------------------------------------//
		sem=Semester.getCurrentSemester();
		//Checking if there are courses in this semester in DB:
		if (sem.getCourseList().isEmpty()==true)
		{
			t.setText("There is no courses in this semester");
			text.setText("");
		}
		//-----------------------------------------//
		else
		{
			ArrayList<String> list = new ArrayList<String>();
		    for (int i=0;i<sem.getCourseList().size();i++)
		    {
		    	list.add(sem.getCourseList().get(i));
		    }
		    //------------------------------------------------------------------//
		    ObservableList obList= FXCollections.observableList(list);
		    CourseL.setItems(obList);
		    CourseL.setVisible(true);
		    //------------------------------------------------------------------//
			ArrayList<String> res= (ArrayList<String>) transfferQueryToServer("SELECT * FROM class");
			ArrayList<String> list2 = new ArrayList<String>();
		    for (int i=0;i<res.size();i++)
		    {
		    	list.add(res.get(i));
		    }
		    //------------------------------------------------------------------//
		    ObservableList comlist= FXCollections.observableList(list2);
		    ClassL.setItems(comlist);
		    ClassL.setVisible(true);
		}
		

	}
	//-----------------------------------------------------------------------------------------//
}
