package application;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class AddParkingLotButtonController implements Initializable {

@FXML
private Button addParkingLotButton;

@FXML
private AnchorPane rootPane;
@FXML
private Text host;
@FXML
private Text port;

public AddParkingLotButtonController() {}

void initData(String _host,String _port){
	host.setText(_host);
	port.setText(_port);
}

@FXML
void addParkingLotButton(ActionEvent event) {
//	String _host = host.getText();
//	String _port = port.getText();
	
	try {
		URL url = new URL("http://localhost:8080/myProject/MyServlet");
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       
       
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddParkingLotScene.fxml" ));
			 AddParkingLotController controller = new AddParkingLotController();
			 loader.setController(controller);
			 AnchorPane pane1 = loader.load();
			 System.out.println(host+"add parking lot controller");
		//	 controller.initData(_host,_port);
			 rootPane.getChildren().setAll(pane1);
		  
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
}
