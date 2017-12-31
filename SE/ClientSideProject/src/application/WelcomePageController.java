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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class WelcomePageController {

	@FXML
	private RadioButton customer;
	@FXML
	private RadioButton employee;
	@FXML 
	private Text msg;
	@FXML
	private Text fail;
	@FXML
	private Text nameText;
	@FXML
	private Text passwordText;
	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Button confirm;
	@FXML
	private Button logIn;
	@FXML
	private AnchorPane rootPane;
	
	
	@FXML
	void continueAs(ActionEvent event) throws IOException{
		String myIp = Main.getIP();
		String myPort = Main.getPort();

		URL _url = new URL("http://"+myIp+":"+myPort+"/myProject/MyServlet");
		HttpURLConnection _conn = (HttpURLConnection) _url.openConnection();
	
		FXMLLoader loader = null;
		
		if(customer.isSelected()){
			msg.setText("Are You Sure?");
			fail.setVisible(false);
			nameText.setVisible(false);
			passwordText.setVisible(false);
			name.setVisible(false);
			password.setVisible(false);
			logIn.setVisible(false);
			logIn.setDisable(true);
		}
		else if(employee.isSelected()){
			msg.setText("Please Log In in order to continue");
			fail.setVisible(false);
			nameText.setVisible(true);
			passwordText.setVisible(true);
			name.setVisible(true);
			password.setVisible(true);
			logIn.setVisible(true);
			confirm.setVisible(false);
			confirm.setDisable(true);
		}
			
		if(event.getSource().equals(confirm)){
		 loader = new FXMLLoader(getClass().getResource("/CustomerActivities.fxml" ));
		 CustomerActivitiesController controller = new CustomerActivitiesController();
		 loader.setController(controller);
		}
		
		if(event.getSource().equals(employee)){
			HashMap<String,String> params= new HashMap<String,String>(); 		
			String _name = name.getText();
			String _password = password.getText();

	        boolean status = false;
	        String response;
	      	          
	        URL url = new URL("http://"+myIp+":"+myPort+"/myProject/EmployeeLogIn");
	        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        
	        OutputStream  os =  conn.getOutputStream();
	        BufferedWriter out =  new BufferedWriter( new OutputStreamWriter( os , "UTF-8" ) );
	        params.put("name", _name);
	        params.put("password", _password);
	        
	        out.write(MyFunctions.getPostDataString(params));
	        out.flush();
	        out.close();
	        os.close();
	        conn.connect();
	        BufferedReader in = 
	        		new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
	  	   
	        response = in.readLine();
	        status =Boolean.parseBoolean(response);
	  	          
	        if(status==true){
	        	loader = new FXMLLoader(getClass().getResource("/EmployeeActivities.fxml" ));
	        	EmployeeActivitiesController controller = new EmployeeActivitiesController();
	        	loader.setController(controller);
	        }
	        if(status==false){
	        	fail.setVisible(true);
	        	name.setText(null);
	        	password.setText(null);
	        }
		}
		 AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 rootPane.getChildren().setAll(pane);
	}

}
