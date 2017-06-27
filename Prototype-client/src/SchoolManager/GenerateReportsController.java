package SchoolManager;

import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.swing.Timer;
import Login.LoginController;
import SystemManager.SystemManagerAddCourseController;
import Parent.ParentMainController;
import Parent.ChoiceChildController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Secretary.TeacherRequestFormController;
import Teacher.TeacherMainController;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.MainWindowStudentController;


public class GenerateReportsController extends QueryController implements Initializable{

	//-----------------------------------------------------------//
	
	public GenerateReportsController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	
    
    @FXML
    private Button logout;
    @FXML
    private Button back;
    @FXML
    private Text userID;
    @FXML
    private Button view;
    @FXML
    private ComboBox<String> of;
    @FXML
    private ComboBox<String> between;
    @FXML
    private ComboBox<String> choose;
    @FXML
    private ComboBox<Integer> semester;
    @FXML
    private Text ErrorMSG;
    @FXML
    private Text thechoose;
    @FXML
    private Text semesterText;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    
    @FXML
    private BarChart<String, Number> histID;

    private String chooseOf;
    private String chooseBetween;
    private String chooseChoose;
    private Integer chooseSemester;

    private int flagOf=0;
    private int flagBetween=0;
    private int flagChoose=0;
    private int flagSemester=0;
    private ObservableList<String> classesNames = FXCollections.observableArrayList();;
    
	//-----------------------------------------------------------//
    
    /**
     * function that return to the last screen
     * @param event
     */
	
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SchoolManagerMainController("SchoolManagerMainController");
		this.Back("/SchoolManager/SchoolManagerMainWindow.fxml",nextController, event);
	}
	//------------------------------------------// 
    
	
    @FXML
    void Of(ActionEvent event) {
		flagOf = 1;
		// save the of choice//
		chooseOf = of.getValue();
	   if(chooseOf.equals("Teacher")){
		    thechoose.setText("Choose Teacher :");
		    choose.setVisible(true);
		    ArrayList<ArrayList<String>> List = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT teacherid FROM teacher");
	        System.out.println(List);
	        if(List == null){
	        	ErrorMSG.setText("No found teacher");//show error message.
	        	return;
	        }
	        else{
	        	//save list of the names of the teachers
	        	ArrayList<String> nameList = new ArrayList<String>();
	        	ArrayList<ArrayList<String>> NameList;	    	
	        	for(ArrayList<String> row:List){
	                // put the teacher list at the comboBoxChooseCourse//
	        		NameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userID,userName FROM user WHERE userID="+row.get(0));
	        		if (NameList==null){
	        			ErrorMSG.setText("There is NO found teacher.");//show error message
	        		}
	        		
	        		else{
	        			ErrorMSG.setText("");//show error message
		        		// show the student's course  
	        			nameList.add((NameList.get(0)).get(1)+" ("+(NameList.get(0)).get(0)+")");
		                System.out.println(nameList);
		                ObservableList obList= FXCollections.observableList(nameList);
		                choose.setItems(obList);
	        		}
	        	}
	        }
	    }
	   
	   if(chooseOf.equals("Class")){
		    thechoose.setText("Choose Class :");
		    choose.setVisible(true);
		    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT ClassID FROM Class");
	        if(res==null){
	        	ErrorMSG.setText("NO found course");//show error message.
	        }
	        else{
			    ArrayList<String> List = new ArrayList<String>();
		    	for(ArrayList<String> row:res){
		    		List.add(row.get(0));
		    	}
			    ObservableList obList= FXCollections.observableList(List);
			    choose.setItems(obList);
	        }
	    }
	   
	   if(chooseOf.equals("Course")){
		    thechoose.setText("Choose Course :");
		    choose.setVisible(true);
		    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses, courseName FROM courses");
	        if(res==null){
	        	ErrorMSG.setText("NO found course");//show error message.
	        }
	        else{
			    ArrayList<String> List = new ArrayList<String>();
		    	for(ArrayList<String> row:res){
		    		List.add(row.get(1)+" ("+row.get(0)+")");
		    	}
			    ObservableList obList= FXCollections.observableList(List);
			    choose.setItems(obList);
	        }
	    }

    }
    
	//------------------------------------------// 
   
    
    @FXML
    void Between(ActionEvent event) {
    	flagBetween=1;
		// save the between choice//
		chooseBetween = between.getValue();
    }
    
	//------------------------------------------// 
    
    @FXML
    void Choose(ActionEvent event) {
    	flagChoose = 1;
		// save the Choose choice//
    	semesterText.setVisible(true);
    	semester.setVisible(true);

		chooseChoose = choose.getValue();
		if(chooseOf.equals("Course") || chooseOf.equals("Teacher")){
			chooseChoose = chooseChoose.substring(chooseChoose.indexOf("(")+1,chooseChoose.indexOf(")"));
		}
		
    	ArrayList<Integer> ListSemester = new ArrayList<Integer>();
    	ArrayList<ArrayList<String>> semestersC = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT COUNT(semID) FROM semester");
    	if(semestersC != null){
    		if(semestersC.get(0) != null){
    			int end = Integer.parseInt(semestersC.get(0).get(0));
    			for(int i = 1 ; i <= end ; i++){
    				ListSemester.add(i);
    			}
    		}
    	}
	    ObservableList obList3= FXCollections.observableList(ListSemester);
	    semester.setItems(obList3);
    }
    
	//------------------------------------------// 
    
    @FXML
    void Semester(ActionEvent event) {
    	flagSemester = 1;
		// save the Choose choice//
    	chooseSemester = semester.getValue();
    	
    }
    
	//------------------------------------------// 
     
    @FXML
    void View(ActionEvent event) {
    	if(flagOf==0 || flagChoose==0 || flagBetween==0){
    		ErrorMSG.setText("You have not selected all fields");//show error message.
    		return;
    	}
    	
    	if(flagOf==1 && flagChoose==1 && flagBetween==1 && flagSemester==1){
    		if(chooseOf.equals("Course") && chooseBetween.equals("Courses")){
    			ErrorMSG.setText("You can not choose the same thing");//show error message.
    			return;
    		}
    		else if(chooseOf.equals("Class") && chooseBetween.equals("Classes")){
    			ErrorMSG.setText("You can not choose the same thing");//show error message.
    			return;
		   }
    		else if(chooseOf.equals("Teacher") && chooseBetween.equals("Teachers")){
    			ErrorMSG.setText("You can not choose the same thing");//show error message.
    			return;
		   }
    		
    		histID.getData().clear();
    		switch(between.getValue()+"-"+of.getValue()){
    		case "Classes-Teacher":
    			if(chooseSemester > 0){
	    			ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID FROM semester GROUP BY semID ORDER BY semID DESC");
	        		System.out.println("res: " + res);
	        		ArrayList<String> semesterList = new ArrayList<String>();
	        		for(int i=0 ; i < chooseSemester; i++)
	        			semesterList.add(res.get(i).get(0));
	        		System.out.println("semesterList: "+semesterList);
	        		String semesters = "";
	        		for(String str : semesterList){
	        			semesters += " Semesid='" + str + "' OR ";
	        		}
	        		semesters = semesters.substring(0,semesters.length() - 3);
	        		System.out.println("semesters: "+semesters);
	    			ArrayList<ArrayList<String>>  avgBetClas= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT clasID,AVG(AVG) FROM teacherinclassincourse WHERE Tidentity=" + chooseChoose + " AND (" + semesters +") GROUP BY clasID");
	        		System.out.println("avgBetClas: "+avgBetClas);
	        		//if(avgBetClas == )
	        		int numOfClass = avgBetClas.size();
	        		System.out.println("numOfClass: "+numOfClass);

	                x.setLabel("Teacher");
	        		y.setLabel("Class");

	        		XYChart.Series series = new XYChart.Series<>();
	        		String[] str = new String[numOfClass];
		        	for(int j = 0 ; j < numOfClass; j++){
		        		str[j] = avgBetClas.get(j).get(0);
		        		series.getData().add(new XYChart.Data(avgBetClas.get(j).get(0), Double.parseDouble(avgBetClas.get(j).get(1))));
		        		System.out.println("A: "+ avgBetClas.get(j).get(0));
		        	}
		        	for(String tm : str) System.out.println("tm: "+tm);
		        	classesNames.clear();
		        	
		        	classesNames.addAll(Arrays.asList(str));
		        	x.setCategories(classesNames);
	                histID.getData().addAll(series);

	    			histID.setVisible(true);

    			}
    			break;
    			
			case "Teachers-Class":
    			if(chooseSemester > 0){
	    			ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID FROM semester GROUP BY semID ORDER BY semID DESC");
	        		System.out.println("res: " + res);
	        		ArrayList<String> semesterList = new ArrayList<String>();
	        		for(int i=0 ; i < chooseSemester; i++)
	        			semesterList.add(res.get(i).get(0));
	        		System.out.println("semesterList: "+semesterList);
	        		String semesters = "";
	        		for(String str : semesterList){
	        			semesters += " Semesid='" + str + "' OR ";
	        		}
	        		semesters = semesters.substring(0,semesters.length() - 3);
	        		System.out.println("semesters: "+semesters);
	    			ArrayList<ArrayList<String>>  avgBetClas= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Tidentity,AVG(AVG) FROM teacherinclassincourse WHERE clasID='" + chooseChoose + "' AND (" + semesters +") GROUP BY Tidentity");
	        		System.out.println("avgBetClas: "+avgBetClas);
	        		/*
	        		int numOfClass = avgBetClas.get(0).size();
	        		System.out.println("numOfClass: "+numOfClass);

	                x.setLabel("Teacher");
	        		y.setLabel("Class");

	        		XYChart.Series series = new XYChart.Series<>();
	        		String[] str = new String[numOfClass +1];
		        	for(int j = 0 ; j <= numOfClass; j++){
		        		str[j] = avgBetClas.get(j).get(0);
		        		series.getData().add(new XYChart.Data(avgBetClas.get(j).get(0), Double.parseDouble(avgBetClas.get(j).get(1))));
		        		System.out.println("A: "+ avgBetClas.get(j).get(0));
		        	}
		        	System.out.println("str: "+str);
		        	ObservableList<String> classesNames = FXCollections.observableArrayList();
		        	classesNames.addAll(Arrays.asList(str));
		        	x.setCategories(classesNames);
	                histID.getData().addAll(series);

	    			histID.setVisible(true);
	    			*/
    			}
				break;
				
				
			case "Courses-Class":
    			if(chooseSemester > 0){
	    			ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID FROM semester GROUP BY semID ORDER BY semID DESC");
	        		System.out.println("res: " + res);
	        		ArrayList<String> semesterList = new ArrayList<String>();
	        		for(int i=0 ; i < chooseSemester; i++)
	        			semesterList.add(res.get(i).get(0));
	        		System.out.println("semesterList: "+semesterList);
	        		String semesters = "";
	        		for(String str : semesterList){
	        			semesters += " Semesid='" + str + "' OR ";
	        		}
	        		semesters = semesters.substring(0,semesters.length() - 3);
	        		System.out.println("semesters: "+semesters);
	    			ArrayList<ArrayList<String>>  avgBetClas= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT coID,AVG(AVG) FROM teacherinclassincourse WHERE clasID='" + chooseChoose + "' AND (" + semesters +") GROUP BY coID");
	        		System.out.println("avgBetClas: "+avgBetClas);
    			}
			break;
    		
			default:
    			ErrorMSG.setText("There is no reports that available for this option.");//show error message.
    		break;
    		}
       	}
	
    }
    
	//------------------------------------------//    
    /**
     * this function initialize the screen whit the name of the user.
     * 
     */

    	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
	    	ArrayList<String> ListBetween = new ArrayList<String>();
		    ListBetween.add("Teachers");
		    ListBetween.add("Classes");
		    ListBetween.add("Courses");
		    System.out.println(ListBetween);
		    ObservableList obList= FXCollections.observableList(ListBetween);
		    between.setItems(obList);
		    
	    	ArrayList<String> ListOf = new ArrayList<String>();
	    	ListOf.add("Teacher");
	    	ListOf.add("Class");
	    	ListOf.add("Course");
		    System.out.println(ListOf);
		    ObservableList obList2= FXCollections.observableList(ListOf);
		    of.setItems(obList2);
		}  	
        //-----------------------------------------------------------//
}
