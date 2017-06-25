package application;

import Entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

 @Override
 public void start(Stage stage) {
	 try {//Creating new Stage and the root scene.
      Parent root = FXMLLoader.load(getClass().getResource("PortWindow.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
    	  @Override
    	  public void handle(WindowEvent event){//update current logged in user to status not logged in.
    		  if(User.getCurrentLoggedIn() != null){//if user is logged in.
	    		  QueryController qController = new QueryController("mainID");
	    		  qController.transfferQueryToServer("UPDATE user SET status = 00 WHERE userID="+User.getCurrentLoggedIn().GetID());
	    		  qController = null;//kill object.
	    		  System.runFinalization();//garbage collector will call object finalize.
    		  }
    	  }
      });
  } catch(Exception e) {
	  System.out.println("Unable to show PortWindow.fxml from Main.");
     e.printStackTrace();
     }
 }

 public static void main(String[] args) {//Here we start the Client.
  launch(args);
 }
}