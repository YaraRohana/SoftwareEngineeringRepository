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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddParkingLotButtonController implements javafx.fxml.Initializable {

@FXML
private Button addParkingLotButton;

@FXML
private AnchorPane rootPane;


public AddParkingLotButtonController() {}

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
          }
          in.close();
      }
      catch ( MalformedURLException ex ) {
          // a real program would need to handle this exception
      }
      catch ( IOException ex ) {
          // a real program would need to handle this exception
      }
	
	
	  try {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/AddParkingLotScene.fxml"));
		rootPane.getChildren().setAll(pane);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  
//	  
//	  try {
//		  Main main = new Main();
//	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddParkingLotScene.fxml"));
//	                Parent root1 = (Parent) fxmlLoader.load();
//	                Stage stage = main.getPrimaryStage();
//	             //   Stage stage = new Stage();
//	                stage.setScene(new Scene(root1));  
//	                stage.show();
//	        } catch(Exception e) {
//	           e.printStackTrace();
//	          }

}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	

	
}
