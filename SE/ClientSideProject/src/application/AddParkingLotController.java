package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


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
	void createNewParkingLot(ActionEvent event) {
		
		HashMap<String,String> params= new HashMap<String,String>(); 		
		  
		String _name = name.getText();
		String _location = location.getText();
		String _manager = manager.getText();
		
		try {
	          
	          URL url = new URL("http://localhost:8080/myProject/AddParkingLot");
	          URLConnection conn = url.openConnection();
	          conn.setReadTimeout(10000);
	          conn.setConnectTimeout(15000);
	          conn.setDoInput(true);
	          conn.setDoOutput(true);
	        
	          OutputStream  os =  conn.getOutputStream();
	          BufferedWriter out =  new BufferedWriter( new OutputStreamWriter( os , "UTF-8" ) );
	          params.put("name", _name);
	          params.put("location", _location);
	          params.put("manager", _manager);
	          out.write(getPostDataString(params));
	          out.flush();
	          out.close();
	          os.close();
	          conn.connect();
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
	}
	
	
	private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first){
                first = false;
            }
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
	
	@Override
	public void initialize(URL location1, ResourceBundle resources) {
		// TODO Auto-generated method stub
		name.setText("");
		location.setText("");
		manager.setText("");

	}
	

}
