package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ApproveParkingLotController implements Initializable{

	@FXML
	private Button back;
	@FXML
	private Text addParkingLotStatus;
	@FXML
	private TableView<Table> table;
	@FXML
	private TableColumn<Table,String> name;
	@FXML
	private TableColumn<Table,String> location;
	@FXML
	private TableColumn<Table,String> manager;
	@FXML
	private TableColumn<Table,Integer> width;
	@FXML
	private AnchorPane rootPane;
	
	public ApproveParkingLotController() {}
	
	
	
	void initData(ArrayList<JSONObject> parkingLots,boolean status) throws JSONException{
		
		if(status==false){
			table.setVisible(false);
			addParkingLotStatus.setText("Not able to add new parking lot because it's already in the CPS");
		}
		else	if(status==false){
			addParkingLotStatus.setText("The proccess of adding Parking Lot Status was stopped due to not filling required fields");
			}
		else	if(status==true){
			final ObservableList<Table> data = FXCollections.observableArrayList();
			for(JSONObject parkingLot : parkingLots){
			String name = parkingLot.getString("name");
			String location = parkingLot.getString("location");
			String manager = parkingLot.getString("manager");
			int width = parkingLot.getInt("width");
			data.add(new Table(name,location,manager,width));
			}
			
	name.setCellValueFactory(new PropertyValueFactory<Table,String>("name") );
	location.setCellValueFactory(new PropertyValueFactory<Table,String>("location") );
	manager.setCellValueFactory(new PropertyValueFactory<Table,String>("manager") );
	width.setCellValueFactory(new PropertyValueFactory<Table,Integer>("width") );
	table.setItems(data);

	addParkingLotStatus.setText("The parking lot was added successfully to the CPS. /n A Table of all parking Lots in the CPS is given bellow: ");
		}
	}
	
	@FXML
	void returnToTheFormerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddParkingLotScene.fxml" ));
        AddParkingLotController controller = new AddParkingLotController();
        loader.setController(controller);
        AnchorPane pane = loader.load();	
        rootPane.getChildren().setAll(pane);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
