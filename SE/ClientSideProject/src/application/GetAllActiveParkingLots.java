package application;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GetAllActiveParkingLots {

	@FXML
	private Button goBackToPreviousPage;
	@FXML
	private Text availableStatus;
	@FXML
	private TableView<Table> table;
	@FXML
	private TableColumn<Table,String> name;
	@FXML
	private TableColumn<Table,String> location;
	@FXML
	private TableColumn<Table,String> manager;
	@FXML
	private TableColumn<Table,Integer> emptySpots;
	@FXML
	private AnchorPane rootPane;
	
	public GetAllActiveParkingLots() {}
	
	
	
	void initData(ArrayList<JSONObject> parkingLots) throws JSONException{
		
		if(parkingLots==null){
			table.setVisible(false);
			availableStatus.setText("None of the parking lots in the CPS are available Currently ");
		}
		else{
			final ObservableList<Table> data = FXCollections.observableArrayList();
			for(JSONObject parkingLot : parkingLots){
			String name = parkingLot.getString("name");
			String location = parkingLot.getString("location");
			String manager = parkingLot.getString("manager");
			int emptySpots = parkingLot.getInt("emptySpots");
			data.add(new Table(name,location,manager,emptySpots));
			}
			
	name.setCellValueFactory(new PropertyValueFactory<Table,String>("name") );
	location.setCellValueFactory(new PropertyValueFactory<Table,String>("location") );
	manager.setCellValueFactory(new PropertyValueFactory<Table,String>("manager") );
	emptySpots.setCellValueFactory(new PropertyValueFactory<Table,Integer>("emptySpots") );
	table.setItems(data);

	availableStatus.setText("The following table shows all parking lots available at the moment ");
		}
	}
	
	@FXML
	void returnToTheFormerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeActivities.fxml" ));
        EmployeeActivitiesController controller = new EmployeeActivitiesController();
        loader.setController(controller);
        AnchorPane pane = loader.load();	
        rootPane.getChildren().setAll(pane);
	}
}
