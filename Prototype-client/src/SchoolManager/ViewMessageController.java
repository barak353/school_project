package SchoolManager;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Entity.User;
import Secretary.SecretaryMainController;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


import javafx.fxml.FXML;

import javafx.scene.text.Text;

public class ViewMessageController extends QueryController implements Initializable{
	
	Object nextController=null;

    @FXML
    private Button logout;

    @FXML
    private Text userID;

    @FXML
    private Button back;
   
    @FXML
    private Text err;
  
    @FXML
    private ListView<String> List;
   
    @FXML
    private Button ReadB;
    
    @FXML
    private TextArea TextA;

    @FXML
    private Button Approve;

    @FXML
    private Button Disapprove;
    private ArrayList<ArrayList<String>> AllTheMessagesStudents;
    private ArrayList<ArrayList<String>> AllTheMessagesTeachers;
    private int TypeFlag=0;
    private int flag=0;
    private String RequiredMessageNumber;
	//----------------------------------------------------------------------//
	public ViewMessageController(String controllerID)
	{
			super(controllerID);
	} 
    //----------------------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		flag=0;
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//--------------------------------------//
		//Taking all the messages:
    	AllTheMessagesStudents= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messagestudent WHERE Answer='"+"NULL"+"'");
    	if(AllTheMessagesStudents!=null)
    	{
    		
    		//Setting the student's message's:
    		for(int i=0;i<AllTheMessagesStudents.size();i++)
    		{
    			List.getItems().addAll("("+AllTheMessagesStudents.get(i).get(0)+") Subject: "+AllTheMessagesStudents.get(i).get(3)+"\nDate: "+AllTheMessagesStudents.get(i).get(4));
    		}
    	}
    	else
    	{
    		flag=1;
    	}
    	AllTheMessagesTeachers= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messageteacher WHERE Answer='"+"NULL"+"'");
    	if(AllTheMessagesTeachers!=null)
    	{
    		//Setting the teacher's message's:
    		for(int i=0;i<AllTheMessagesTeachers.size();i++)
    		{
    			List.getItems().addAll("("+AllTheMessagesTeachers.get(i).get(0)+") Subject: "+AllTheMessagesTeachers.get(i).get(4)+"\nDate: "+AllTheMessagesTeachers.get(i).get(6));
    		}
    	}
    	else
    	{
    		if(flag==1)err.setText("There is no message's for the school director.");
    		List.setVisible(false);
    		ReadB.setVisible(false);
    	}
    	flag=0;
	}
	 //----------------------------------------------------------------------//
	  @FXML
	    void TurningBack(ActionEvent event) {

			this.nextController = new SchoolManagerMainController("SchoolManagerMainController");
			this.Back("/SchoolManager/SchoolManagerMainWindow.fxml",nextController, event);
	    }
	  //----------------------------------------------------------------------//
	  @FXML
	  void ReadButtonHandler(ActionEvent event) 
	  {
		  int i;
		  //---------------------------------------------//
		  String ChosenMessage=List.getSelectionModel().getSelectedItem();
		  if(ChosenMessage==null)
		  {
			  err.setText("Choose message please.");
			  Timer t = new Timer(2000, new java.awt.event.ActionListener() {
	                @Override
	                public void actionPerformed(java.awt.event.ActionEvent e) {
	                	try{
	                		err.setText("");
	                	}catch(java.lang.NullPointerException e1){
	                		
	                	}
	                }
	            });
	            t.setRepeats(false);
	            t.start();
		  }
		  else
		  {		
			  //Set visible the message and decisions button's:  
			  Approve.setVisible(true);
			  Disapprove.setVisible(true);
			  TextA.setVisible(true);
			  RequiredMessageNumber = ChosenMessage.substring(ChosenMessage.indexOf("(") + 1, ChosenMessage.indexOf(")"));
			  String requiredType= ChosenMessage.substring(13,20);
			  //If the choise is student change:
			  if(requiredType.equals("Student")==true)
			  {
				  TypeFlag=1; //Student Type
				  //Search the message:
				  for(i=0;i<AllTheMessagesStudents.size();i++)
				  {
					  if(AllTheMessagesStudents.get(i).get(0).equals(RequiredMessageNumber)==true)
					  {
						  break;
					  } 
				  }
				  TextA.setText(AllTheMessagesStudents.get(i).get(6));
			  }
			  else //Teacher Change
			  {
				  TypeFlag=2; //Teacher Type
				  //Search the message:
				  for(i=0;i<AllTheMessagesTeachers.size();i++)
				  {
					  if(AllTheMessagesTeachers.get(i).get(0).equals(RequiredMessageNumber)==true)
					  {
						  break;
					  } 
				  }
				  TextA.setText(AllTheMessagesTeachers.get(i).get(7));
			  }
		  }

	  }
	 //----------------------------------------------------------------------//
	  @FXML
	    void ApproveHandler(ActionEvent event)
	  	{
		  if(TypeFlag==1)//Student
		  {
		    	transfferQueryToServer("UPDATE messagestudent SET Answer='YES' WHERE num="+ RequiredMessageNumber); 
		    	Approve.setVisible(false);
				Disapprove.setVisible(false);
				TextA.setVisible(false);
				List.getSelectionModel().clearSelection();
		  }
		  else if(TypeFlag==2)
		  {
		    	transfferQueryToServer("UPDATE messageteacher SET Answer='YES' WHERE n="+ RequiredMessageNumber);  
		    	Approve.setVisible(false);
				Disapprove.setVisible(false);
				TextA.setVisible(false);
				List.getSelectionModel().clearSelection();
		  }
		  
	  	}
	    //----------------------------------------------------------------------//
	    @FXML
	    void DisapproveHandler(ActionEvent event)
	    {
	    	  if(TypeFlag==1)//Student
			  {
			    	transfferQueryToServer("UPDATE messagestudent SET Answer='NO' WHERE num="+ RequiredMessageNumber); 
			    	Approve.setVisible(false);
					Disapprove.setVisible(false);
					TextA.setVisible(false);
					List.getSelectionModel().clearSelection();
			  }
			  else if(TypeFlag==2)
			  {
			    	transfferQueryToServer("UPDATE messageteacher SET Answer='NO' WHERE n="+ RequiredMessageNumber);  
			    	Approve.setVisible(false);
					Disapprove.setVisible(false);
					TextA.setVisible(false);
					List.getSelectionModel().clearSelection();
			  }
	    }
	    //----------------------------------------------------------------------//
}

