package application;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class ExitParkingLotController {
	@FXML
	private RadioButton full;
	@FXML
	private RadioButton regular;
	@FXML
	private RadioButton preOrder;
	@FXML
	private RadioButton uponArrival;
	@FXML
	private Button next;
	@FXML
	private Button back;
	
	@FXML
	private AnchorPane rootPane;
	
	
	@FXML
	void next(ActionEvent event) throws IOException{
		String myIp = Main.getIP();
		String myPort = Main.getPort();
		
		FXMLLoader loader = null;
		
		if(event.getSource().equals(back)){
			loader = new FXMLLoader(getClass().getResource("/KioskServices.fxml" ));
			KioskServicesController controller = new KioskServicesController();
			loader.setController(controller);
		}
		
		if(event.getSource().equals(next)){
			
			if(full.isSelected()&&!regular.isSelected()&&!preOrder.isSelected()&&!uponArrival.isSelected()){
				loader = new FXMLLoader(getClass().getResource("/ExitFullSubscription.fxml" ));
				ExitFullSubscription controller = new ExitFullSubscription();
				loader.setController(controller);
			}
			else	if(regular.isSelected()&&!full.isSelected()&&!preOrder.isSelected()&&!uponArrival.isSelected()){
				loader = new FXMLLoader(getClass().getResource("/ExitRegularSubscription.fxml" ));
				ExitRegularSubscription controller = new ExitRegularSubscription();
				loader.setController(controller);
			}
			
			else	if(preOrder.isSelected()&&!full.isSelected()&&!regular.isSelected()&&!uponArrival.isSelected()){
				loader = new FXMLLoader(getClass().getResource("/EnterPreOrder.fxml" ));
				ExitPreOrder controller = new ExitPreOrder();
				loader.setController(controller);
			}
			else	if(uponArrival.isSelected()&&!full.isSelected()&&!regular.isSelected()&&!preOrder.isSelected()){
				loader = new FXMLLoader(getClass().getResource("/EnterPreOrder.fxml" ));
				ExitUponArrival controller = new ExitUponArrival();
				loader.setController(controller);
			}
		}
		
		if(loader!=null){
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
}
