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

public class KioskServicesController {
	@FXML
	private Button entrance;
	@FXML
	private Button exit;
	@FXML
	private Button order;
	@FXML
	private Button complaint;
	
	@FXML
	private AnchorPane rootPane;
	
	

	@FXML
	void doService(ActionEvent event) throws IOException{

			String myIp = Main.getIP();
			String myPort = Main.getPort();
			
			FXMLLoader loader = null;
			if(event.getSource().equals(entrance)){
				loader = new FXMLLoader(getClass().getResource("/EnterParkingLot.fxml" ));
				EnterParkingLotController controller = new EnterParkingLotController();
				loader.setController(controller);
			}
			if(event.getSource().equals(exit)){
				loader = new FXMLLoader(getClass().getResource("/ExitParkingLot.fxml" ));
				ExitParkingLotController controller = new ExitParkingLotController();
				loader.setController(controller);
			}
			
			if(event.getSource().equals(order)){
				loader = new FXMLLoader(getClass().getResource("/AddUponArrivalOrder.fxml" ));
				AddUponArrivalOrderController controller = new AddUponArrivalOrderController();
				loader.setController(controller);
			}		
			if(event.getSource().equals(complaint)){
				loader = new FXMLLoader(getClass().getResource("/SendComplaint.fxml" ));
				SendComplaintController controller = new SendComplaintController();
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
}
