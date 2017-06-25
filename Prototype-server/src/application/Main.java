package application;

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
	SchoolServer.setStage(stage);
	 try {//Creating new Stage and the root scene.
      Parent root = FXMLLoader.load(getClass().getResource("PortWindow.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
    	  
    	  @Override
    	  public void handle(WindowEvent event){
    		  Platform.exit();
    		  System.exit(0);;
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