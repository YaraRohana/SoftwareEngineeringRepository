package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Table {

	private final SimpleStringProperty name;
	private final SimpleStringProperty location;
	private final SimpleStringProperty manager;
	private final SimpleIntegerProperty width;
	
	public Table(String name, String location, String manager , Integer width){
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleStringProperty(location);
		this.manager = new SimpleStringProperty(manager);
		this.width = new SimpleIntegerProperty(width);
	}
	
	public String getName(){
		return name.get();
	}
	public String getLocation(){
		return location.get();
	}
	public String getManager(){
		return manager.get();
	}
	public Integer getWidth(){
		return width.get();
	}
	
	public void setName(String name){
		this.name.set(name);
	}
	public void setLocation(String location){
		this.location.set(location);
	}
	public void setManager(String manager){
		this.manager.set(manager);
	}
	public void setManager(Integer width){
		this.width.set(width);
	}
}
