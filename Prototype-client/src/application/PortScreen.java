package application;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Console.ClientConsole;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PortScreen {



    @FXML
    private TextField hostID;

    @FXML
    private TextField portID;

    @FXML
    private Button connectID;

    @FXML
    void connect(ActionEvent event) {
    	System.out.println("hey");
    	String hostID=this.hostID.getText();
    	String portID=this.portID.getText();
    	ClientConsole.start(new String[]{hostID,portID});
    }


}

