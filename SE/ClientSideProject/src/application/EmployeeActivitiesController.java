package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class EmployeeActivitiesController {

	@FXML
	private Button setupParkingLot;
	@FXML
	private Button getAllActiveParkingLots;
	@FXML
	private Button saveParkingSpot;
	@FXML
	private Button addDisabledSpot;
	@FXML
	private Button goToMainPage;
	
	@FXML
	private AnchorPane rootPane;
	
	

	@FXML
	void chooseActivity(ActionEvent event) throws IOException{

			String myIp = Main.getIP();
			String myPort = Main.getPort();
			
			FXMLLoader loader = null;
			if(event.getSource().equals(getAllActiveParkingLots)){
				loader = new FXMLLoader(getClass().getResource("/GetAllActiveParkingLots.fxml" ));
				ApproveParkingLotController controller = new ApproveParkingLotController();
				loader.setController(controller);
			}
			if(event.getSource().equals(setupParkingLot)){
				loader = new FXMLLoader(getClass().getResource("/SetupParkingLot.fxml" ));
				ApproveParkingLotController controller = new ApproveParkingLotController();
				loader.setController(controller);
			}
			
			if(event.getSource().equals(saveParkingSpot)){
				loader = new FXMLLoader(getClass().getResource("/SaveParkingSpot.fxml" ));
				ApproveParkingLotController controller = new ApproveParkingLotController();
				loader.setController(controller);
			}		
			if(event.getSource().equals(addDisabledSpot)){
				loader = new FXMLLoader(getClass().getResource("/AddDisabledSpot.fxml" ));
				ApproveParkingLotController controller = new ApproveParkingLotController();
				loader.setController(controller);
			}				
			if(event.getSource().equals(goToMainPage)){
				loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml" ));
				ApproveParkingLotController controller = new ApproveParkingLotController();
				loader.setController(controller);
			}
			
			 AnchorPane pane = null;
			try {
				pane = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			URL url = new URL("http://"+myIp+":"+myPort+"/myProject/MyServlet");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 rootPane.getChildren().setAll(pane);
					
		}
	
	@FXML
	void getAvailable(ActionEvent event) throws IOException, JSONException{

		String myIp = Main.getIP();
		String myPort = Main.getPort();
        ArrayList<JSONObject> parkingLots = new ArrayList<JSONObject>();

		 URL url = new URL("http://"+myIp+":"+myPort+"/myProject/GetAvailableParkingLots");
         HttpURLConnection conn =(HttpURLConnection) url.openConnection();
         conn.setReadTimeout(10000);
         conn.setConnectTimeout(15000);
         conn.setDoInput(true);
         conn.setDoOutput(true);
         conn.connect();
         BufferedReader in = 
             new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
         String response ;
         while ( (response = in.readLine()) != null ) {
       	JSONObject obj = null;
		try {
			obj = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	  parkingLots.add(obj);
         }
         in.close();
	
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetAllActiveParkingLots.fxml" ));
         GetAllActiveParkingLots controller = new GetAllActiveParkingLots();
		 loader.setController(controller);
		 AnchorPane pane = loader.load();	
		 controller.initData(parkingLots);
		 rootPane.getChildren().setAll(pane);
	
	}
}
	
