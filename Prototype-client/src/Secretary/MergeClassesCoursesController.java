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
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;

public class MergeClassesCoursesController extends QueryController implements Initializable {
	
	  private Object nextController=null;
	  @FXML
      private Text chooseteachertext;
	  @FXML
      private ComboBox<?> teacherList;
	  @FXML
	  private Button AssignB;
	  @FXML
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
      @FXML
      private DialogPane diaID;
      private String courseID;
      private Semester sem;
      @FXML
      private Text err;

      @FXML
      private DialogPane dialog2;

      @FXML
      private Text finishtxt;
	  //-----------------------------------------------------------//
	  public MergeClassesCoursesController (String controllerID)
	  {
			super(controllerID);
	  }
	  //-----------------------------------------------------------// 
	  @FXML
	  void AssignHandler(ActionEvent event) 
	  {
			String CourseChoise =  (String) CourseL.getValue();//get the item that was pressed in the combo box.
	    	String RequiredStringCourse = CourseChoise.substring(CourseChoise.indexOf("(") + 1, CourseChoise.indexOf(")"));
		    String ClassChoise = (String) ClassL.getValue();
		    //--------------------------------------------------//
		    ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM classincourse WHERE clasID='" + ClassChoise + "' AND coID='" +RequiredStringCourse+ "'");
		    boolean flag=true;
		    if (CourseChoise==null || ClassChoise==null )
		    {
		    	t.setText("Please fill all the details");
				text.setText("");
				flag=false;
		    }
		    if (result!=null)
		    {
				t.setText("The class: "+ClassChoise+"is already assgined to the course: "+RequiredStringCourse);
				text.setText("");
				flag=false;
		    }
		    else if (flag==true)
		    {
		    	ArrayList<ArrayList<String>> teaching= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + RequiredStringCourse +"'");
		    	String TeachingUnit=teaching.get(0).get(2); //Get teachingUnit number
		    	ArrayList<ArrayList<String>> TeachersID= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinteachunit WHERE teachUnitNum='" + TeachingUnit +"'");
		    	ArrayList<String> listtteachers = new ArrayList<String>();
		    	for (int i=0;i<TeachersID.size();i++)
			    {
			    	ArrayList<ArrayList<String>> usersID= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE USid='" + TeachersID.get(i).get(0) +"'");
			    	ArrayList<ArrayList<String>> TeachersName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + TeachersID.get(i).get(0) +"'");
			    	listtteachers.add(TeachersName.get(0).get(1));
			    }
			    //------------------------------------------------------------------//
			    ObservableList L= FXCollections.observableList(listtteachers);
			    teacherList.setItems(L);
		    	 //---------------------------------------------------//
		    	   t.setText("");
		    	   text.setText("");
		    	   diaID.setVisible(true);
		    	   chooseteachertext.setVisible(true);
		    	   teacherList.setVisible(true);
		    	   AssignB.setVisible(true);
		    	 //---------------------------------------------------//
		    	 String a="1";
		    	 String b="1";
		    	 transfferQueryToServer("INSERT INTO classincourse (clasID,coID,AVG,Tidentity) VALUES ('" + ClassChoise + "','" + RequiredStringCourse + "','" +a+"','"+b+"')");
		    	// t.setText("");
				// text.setText("The class: "+ClassChoise+" assgined successfully to the course: "+RequiredStringCourse);
		    }
		   
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
		    ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM class");
			ArrayList<String> list2 = new ArrayList<String>();
		    for (int i=0;i<res.size();i++)
		    {
		    	list2.add(res.get(i).get(0));
		    }
		    //------------------------------------------------------------------//
		    ObservableList comlist= FXCollections.observableList(list2);
		    ClassL.setItems(comlist);
		    ClassL.setVisible(true);
		}
	}
	//-----------------------------------------------------------------------------------------//
	 @FXML
	    void AssignTeacher(ActionEvent event) {

	    }
}

