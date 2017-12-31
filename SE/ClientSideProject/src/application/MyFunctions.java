package application;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyFunctions {

	public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first){
                first = false;
            }
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
	
	
	public static ObservableList<String> getHours(){
		return FXCollections.observableArrayList(
				"00","01","02","03","04","05","06","07","08","09","10","11","12","13"
				,"14","15","16","17","18","19","20","21","22","23"
				);
	}
	
	public static ObservableList<String> getMins(){
		return FXCollections.observableArrayList(
				"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14"
				,"15","16","17","18","19","20","21","22","23","24","25","26","27","28"
				,"29","30","31","32","33","34","35","36","37","38","39","40","41","42"
				,"43","44","45","46","47","48","49","50","51","52","53","54","55","56"
				,"57","58","59"
				);
	}
	
}
