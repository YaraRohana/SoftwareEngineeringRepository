package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class ConnectionController implements Initializable{
	@FXML
	private Text message;

	public ConnectionController() {}
	
	void initData(String _message){
		message.setText(_message);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
