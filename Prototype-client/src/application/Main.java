package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Console.ClientConsole;


public class Main extends Application {
public static int s=0;
 
 @Override
 public void start(Stage stage) {
	 try {
      Parent root = FXMLLoader.load(getClass().getResource("PortWindow.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      
  } catch(Exception e) {
	  System.out.println("Unable to show PortWindow from Main.");
     e.printStackTrace();
     }
 }

 public static void main(String[] args) {
  launch(args);
 }
}