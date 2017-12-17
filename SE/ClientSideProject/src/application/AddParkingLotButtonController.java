package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
private Button addParkingLotButton;

public void AddParkingLotController() {}

@FXML
void addParkingLotButton(ActionEvent event) {
	
	
	
	  try {
          
          URL url = new URL("http://localhost:8080/myProject/MyServlet");
          URLConnection conn = url.openConnection();
          conn.setDoOutput(true);
          
          BufferedWriter out = 
              new BufferedWriter( new OutputStreamWriter( conn.getOutputStream() ) );
          out.write("username=javaworld\r\n");
          out.flush();
          out.close();
          BufferedReader in = 
              new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
          
          String response;
          while ( (response = in.readLine()) != null ) {
              System.out.println( response +"loveeeeeee");
          }
          in.close();
          System.out.print("geeeeeer");
      }
      catch ( MalformedURLException ex ) {
          // a real program would need to handle this exception
      }
      catch ( IOException ex ) {
          // a real program would need to handle this exception
      }
	
	
	
	
	
//	java.net.URL url = getClass().getResource("AddParkingLotButton.fxml");
//	 AnchorPane pane = FXMLLoader.load( url );   
//	 Scene scene = new Scene( pane ); // setting the stage
//			 primaryStage.setScene( scene );
//	//		 primaryStage.setTitle( "Add new parking lot" );
//			 primaryStage.show();
			 
			 
	
}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	
}
