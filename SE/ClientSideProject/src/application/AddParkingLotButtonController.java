package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class AddParkingLotButtonController implements javafx.fxml.Initializable {

@FXML
private Button addParkingLot;

public void AddParkingLotController() {}

@FXML
void addParkingLotButton(ActionEvent event) {
	
	
	java.net.URL url = getClass().getResource("AddParkingLotButton.fxml");
	 AnchorPane pane = FXMLLoader.load( url ); Scene scene
			 = new Scene( pane ); // setting the stage
			 primaryStage.setScene( scene );
	//		 primaryStage.setTitle( "Add new parking lot" );
			 primaryStage.show();
			 
			 
	
}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	
}
