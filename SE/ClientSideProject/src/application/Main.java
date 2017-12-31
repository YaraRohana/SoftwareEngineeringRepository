package application;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Main extends Application {

	@FXML
	private static Text ip;
	@FXML
	private static Text port;
	
	@Override
	 public void start(Stage primaryStage) { // constructing our scene 

		Parameters parameters = getParameters();
		String IP = parameters.getRaw().get(0);
		String Port = parameters.getRaw().get(1);
		
		ip.setText(IP);
		port.setText(Port);
		
		
		   URL url;
		try {
			url = new URL("http://"+IP+":"+Port+"/myProject/MyServlet");

	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	       System.out.println(conn.getResponseCode());

		URL url1 = getClass().getResource("/Main.fxml");
		AnchorPane pane = FXMLLoader.load( url1 ); 
		Scene scene = new Scene( pane ); // setting the stage
			 primaryStage.setScene( scene );
			 primaryStage.setTitle( "CPS Demo" );
			 primaryStage.show();
			
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml" ));
			 WelcomePageController controller = new WelcomePageController();
			 loader.setController(controller);
			 AnchorPane pane1 = loader.load();
			 pane.getChildren().setAll(pane1);
			 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			URL url1 = getClass().getResource("/Main.fxml");
			AnchorPane pane = null;
			try {
				pane = FXMLLoader.load( url1 );
				Scene scene = new Scene( pane ); // setting the stage
				 primaryStage.setScene( scene );
				 primaryStage.setTitle( "CPS Demo" );
				 primaryStage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			} 
			
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml" ));
			 ConnectionController controller = new ConnectionController();
			 loader.setController(controller);
			 AnchorPane pane1 = null;
			try {
				pane1 = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
			 System.out.println(IP);
			 controller.initData("Connection failed!!! Due to no proper connection");
			 pane.getChildren().setAll(pane1);
			
		}
			 
			 }

	public static void main(String[] args) {
		if(args.length>1){
		String IP=args[0];
		String port=args[1];
		String[] arg ={IP,port}; 
		launch(arg);
		}
	}

	public static String getIP() {
		return ip.getText();
	}

	public static void setIP(String IP) {
		Main.ip.setText(IP); 
	}
	    
	public static String getPort() {
		return port.getText();
	}

	public static void setPort(String Port) {
	    	Main.port.setText(Port); 
	}
	    
	
	public Main() {
		super();
	}

}
