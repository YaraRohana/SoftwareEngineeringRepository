package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class AddPreOrderController implements Initializable {

	@FXML
	private Button submit;
	@FXML
	private Button cancel;
	@FXML
	private TextField id;
	@FXML
	private TextField vehicle;
	@FXML
	private TextField email;
	@FXML
	private TextField parkingLot;
	@FXML
	private DatePicker arrivingDate;
	@FXML
	private DatePicker leavingDate;
	@FXML
	private ComboBox<String> arrivingHour;
	@FXML
	private ComboBox<String> arrivingMin;
	@FXML
	private ComboBox<String> leavingHour;
	@FXML
	private ComboBox<String> leavingMin;
	@FXML 
	private AnchorPane rootPane;

	@FXML
	void toDo(ActionEvent event) throws JSONException, IOException {
		String myIp = Main.getIP();
		String myPort = Main.getPort();
		
	//	msg.setVisible(false);

		HashMap<String,String> params= new HashMap<String,String>(); 		
		String _id = id.getText();
		String _vehicle = vehicle.getText();
		String _email = email.getText();
		String _parkingLot = parkingLot.getText();
		String _arrivingTime = arrivingHour.getValue() + ":" + arrivingMin.getValue();
		String _leavingTime = leavingHour.getValue() + ":" + leavingMin.getValue();

        boolean status = false;
        String response;

      		try {
      	          
      	          URL url = new URL("http://"+myIp+":"+myPort+"/myProject/AddOrder");
      	          HttpURLConnection conn =(HttpURLConnection) url.openConnection();
      	          conn.setReadTimeout(10000);
      	          conn.setConnectTimeout(15000);
      	          conn.setDoInput(true);
      	          conn.setDoOutput(true);
      	        
      	          OutputStream  os =  conn.getOutputStream();
      	          BufferedWriter out =  new BufferedWriter( new OutputStreamWriter( os , "UTF-8" ) );
      	          params.put("id", _id);
      	          params.put("vehicle", _vehicle);
      	          params.put("email", _email);
      	          params.put("parkingLot", _parkingLot);
      	          params.put("arrivingTime", _arrivingTime);
      	          params.put("leavingTime", _leavingTime);

      	          out.write(MyFunctions.getPostDataString(params));
  	          out.flush();
  	          out.close();
  	          os.close();
  	          conn.connect();
  	          BufferedReader in = 
  	              new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
  	   
  	          response = in.readLine();
  	          status =Boolean.parseBoolean(response);
		
		FXMLLoader loader = null;
		if(event.getSource().equals(submit)){
			if(status==true){
				//must change fxml file
			loader = new FXMLLoader(getClass().getResource("/EmployeeActivities.fxml" ));
			ApproveParkingLotController controller = new ApproveParkingLotController();
			loader.setController(controller);
			}
			if(status==false){
		//	msg.setVisible(true);
			loader = new FXMLLoader(getClass().getResource("/AddPreOrder.fxml" ));
			AddPreOrderController controller = new AddPreOrderController();
			loader.setController(controller);
			}
			
		}
		if(event.getSource().equals(cancel)){
			loader = new FXMLLoader(getClass().getResource("/CustomerActivities.fxml" ));
			CustomerActivitiesController controller = new CustomerActivitiesController();
			loader.setController(controller);
		}
		
		 AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 rootPane.getChildren().setAll(pane);
}    	finally{}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		arrivingHour.setItems(MyFunctions.getHours());
		arrivingMin.setItems(MyFunctions.getMins());
		leavingHour.setItems(MyFunctions.getHours());
		leavingMin.setItems(MyFunctions.getMins());

	}
}
