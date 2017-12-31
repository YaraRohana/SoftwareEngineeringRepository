package application;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CustomerActivitiesController {

	@FXML
	private Button addUponArrivalOrder;
	@FXML
	private Button addPreOrder;
	@FXML
	private Button addFullSubscription;
	@FXML
	private Button addRegularSubscription;
	@FXML
	private Button sendComplaint;
	@FXML
	private Button followOrdersAndSubscriptions;
	@FXML
	private Button goToMainPage;
	
	@FXML
	private AnchorPane rootPane;
	
	
	@FXML
	void chooseActivity(ActionEvent event) throws IOException{
		String myIp = Main.getIP();
		String myPort = Main.getPort();
		
		FXMLLoader loader = null;
		if(event.getSource().equals(addUponArrivalOrder)){
			loader = new FXMLLoader(getClass().getResource("/AddUponArrivalOrder.fxml" ));
			AddUponArrivalOrderController controller = new AddUponArrivalOrderController();
			loader.setController(controller);
		}
		if(event.getSource().equals(addPreOrder)){
			loader = new FXMLLoader(getClass().getResource("/AddPreOrder.fxml" ));
			AddPreOrderController controller = new AddPreOrderController();
			loader.setController(controller);
		}
		
		if(event.getSource().equals(addFullSubscription)){
			loader = new FXMLLoader(getClass().getResource("/AddFullSubscription.fxml" ));
			AddFullSubscriptionController controller = new AddFullSubscriptionController();
			loader.setController(controller);
		}		
		if(event.getSource().equals(addRegularSubscription)){
			loader = new FXMLLoader(getClass().getResource("/AddRegularSubscription.fxml" ));
			AddRegularSubscriptionController controller = new AddRegularSubscriptionController();
			loader.setController(controller);
		}	
		if(event.getSource().equals(sendComplaint)){
			loader = new FXMLLoader(getClass().getResource("/SendComplaint.fxml" ));
			SendComplaintController controller = new SendComplaintController();
			loader.setController(controller);
		}	
		if(event.getSource().equals(followOrdersAndSubscriptions)){
			loader = new FXMLLoader(getClass().getResource("/FollowOrdersAndSubscription.fxml" ));
			FollowOrdersAndSubscriptions controller = new FollowOrdersAndSubscriptions();
			loader.setController(controller);
		}
		
		
		if(event.getSource().equals(goToMainPage)){
			loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml" ));
			WelcomePageController controller = new WelcomePageController();
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
