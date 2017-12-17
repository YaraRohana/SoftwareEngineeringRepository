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
import javafx.stage.Stage;
import application.Main;


public class AddParkingLotButtonController implements javafx.fxml.Initializable {

@FXML
private Button addParkingLotButton;

public void AddParkingLotController() {}

@FXML
void addParkingLotButton(ActionEvent event) {
	
	
	
	  try {
          
          URL url = new URL("http://localhost:8080/tempProject/MyServlet");
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
	
	
	
//	Main main = new Main();
//	Stage primaryStage = main.getPrimaryStage(); 
//	java.net.URL url = getClass().getResource("/AddParkingLotScene.fxml");
//	 AnchorPane pane = null;
//		System.out.print("pane2222");

//	 try {
//		pane = FXMLLoader.load( url );
//		System.out.print("paneeeeeeee");
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}   
//	
//	System.out.print("pane");
//	 Scene scene = new Scene( pane ); // setting the stage
//			 primaryStage.setScene( scene );
//			 primaryStage.setTitle( "Add new parking lot" );
//			 primaryStage.show();
			 
}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	
}
