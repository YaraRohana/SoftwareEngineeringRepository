package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import org.json.JSONException;
import org.json.JSONObject;


public class AddParkingLotController implements Initializable {
	
	public AddParkingLotController() {
		super();
	}
	
	@FXML
	private Button createNewParkingLot;
	@FXML
	private TextField name;
	@FXML
	private TextField location;
	@FXML
	private TextField manager;
	@FXML
	private TextField width;
	@FXML
	private AnchorPane rootPane;

	@FXML
	void createNewParkingLot(ActionEvent event) throws JSONException {
		String IP = Main.getIP();
		String Port = Main.getPort();
		
		HashMap<String,String> params= new HashMap<String,String>(); 		
		  
		String _name = name.getText();
		String _location = location.getText();
		String _manager = manager.getText();
		String _width = width.getText();
        ArrayList<JSONObject> parkingLots = new ArrayList<JSONObject>();

        boolean status = false;
        String response;

		try {
	          
	          URL url = new URL("http://"+IP+":"+Port+"/myProject/AddParkingLot");
	          HttpURLConnection conn =(HttpURLConnection) url.openConnection();
	          conn.setReadTimeout(10000);
	          conn.setConnectTimeout(15000);
	          conn.setDoInput(true);
	          conn.setDoOutput(true);
	        
	          OutputStream  os =  conn.getOutputStream();
	          BufferedWriter out =  new BufferedWriter( new OutputStreamWriter( os , "UTF-8" ) );
	          params.put("name", _name);
	          params.put("location", _location);
	          params.put("manager", _manager);
	          params.put("width", _width);
	          out.write(MyFunctions.getPostDataString(params));
	          out.flush();
	          out.close();
	          os.close();
	          conn.connect();
	          BufferedReader in = 
	              new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
	   
	          response = in.readLine();
	          status =Boolean.parseBoolean(response);
	          URL url1 = new URL("http://"+IP+":"+Port+"/myProject/GetAllParkingLots");
	          HttpURLConnection conn1 =(HttpURLConnection) url1.openConnection();
	          conn1.setReadTimeout(10000);
	          conn1.setConnectTimeout(15000);
	          conn1.setDoInput(true);
	          conn1.setDoOutput(true);
	          conn1.connect();
	          BufferedReader in1 = 
	              new BufferedReader( new InputStreamReader( conn1.getInputStream() ) );
	          
	          while ( (response = in1.readLine()) != null ) {
	        	JSONObject obj = new JSONObject(response);
	        	  parkingLots.add(obj);
	          }
	          in1.close();
	      }
	      catch ( MalformedURLException ex ) {
	          // a real program would need to handle this exception
	      }
	      catch ( IOException ex ) {
	          // a real program would need to handle this exception
	      }
		
// Setting the next scene		
		 try {
			 
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/ApproveParkingLotScene.fxml" ));
			 ApproveParkingLotController controller = new ApproveParkingLotController();
			 loader.setController(controller);
			 AnchorPane pane = loader.load();	
			 controller.initData(parkingLots,status);
			 rootPane.getChildren().setAll(pane);
				
		 } catch (IOException e) {
				// TODO Auto-generated catch block
			 e.getCause();
			 e.printStackTrace();
		 }
	}
	
	@Override
	public void initialize(URL location1, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		name.setText("");
//		location.setText("");
//		manager.setText("");
//		width.setText("");

	}
	

}
