package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Messages implements Initializable{

	@FXML
	private Text status;
	@FXML
	private Button goTo;
	@FXML
	private AnchorPane rootPane;
	
	void initData(String msg){
		status.setText(msg);
	}
	
	@FXML
	void goToMainPage(ActionEvent event) throws IOException{
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml" ));
	        WelcomePageController controller = new WelcomePageController();
	        loader.setController(controller);
	        AnchorPane pane = loader.load();	
	        rootPane.getChildren().setAll(pane);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

