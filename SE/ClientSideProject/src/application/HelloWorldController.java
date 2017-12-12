/**
 * Sample Skeleton for 'HelloWorldScene.fxml' Controller Class
 */

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class HelloWorldController {

    @FXML // fx:id="helloTF"
    private TextArea helloTF; // Value injected by FXMLLoader

    @FXML // fx:id="helloBtn"
    private Button helloBtn; // Value injected by FXMLLoader

    @FXML
    void sayHello(ActionEvent event) {

    }

}
