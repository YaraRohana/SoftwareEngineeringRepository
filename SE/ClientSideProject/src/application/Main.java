package application;

import java.io.IOException;

import javax.print.DocFlavor.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	private Stage primaryStage;
	private Scene scene;

	@Override
	 public void start(Stage primaryStage) throws IOException { // constructing our scene 
		java.net.URL url = getClass().getResource("/AddParkingLotButtonScene.fxml");
	 AnchorPane pane = FXMLLoader.load( url ); Scene scene
			 = new Scene( pane ); // setting the stage
			 primaryStage.setScene( scene );
			 primaryStage.setTitle( "CPS Demo" );
			 primaryStage.show();
			 this.primaryStage = primaryStage;
			 this.scene = scene;
			 }
	
	public Scene getScene() {
		return scene;
	}



	public static void main(String[] args) {
		launch(args);
	}

	public Main() {
		super();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
