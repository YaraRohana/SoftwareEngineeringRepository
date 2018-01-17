package allClasses;

import java.util.Arrays;
import java.util.regex.Pattern;

public class stam {
	public static final String PLAYER = "1||1||Abdul-Jabbar||Karim||1996||1974";
	public static final String date="2018-12-27";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String[] data = date.split("-");
//		System.out.println(Arrays.toString(data));
//		String tmp=Arrays.toString(data);
//		System.out.println("tmp"+tmp);
		 String[] data = PLAYER.split("\\|\\|");
	     Pattern pattern = Pattern.compile("\\|\\|");
	        data = pattern.split(PLAYER);
	        System.out.println(Arrays.toString(data));

	}
}