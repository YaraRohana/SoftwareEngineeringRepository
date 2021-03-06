package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.ParkingLot;
import da.DataAccess;

/**
 * Servlet implementation class AddParkingLot
 */
@WebServlet("/AddParkingLot")
public class AddParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddParkingLot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("yara");
		int ID=0;
		String id =request.getParameter("id");
		if(id!=null)
		ID=Integer.parseInt(id);
		System.out.println("id is" + ID);
		String name=request.getParameter("name");
		String location=request.getParameter("location");
		ParkingLot p=new ParkingLot(ID, "haifa", "haifa", true, true, "ya");	
		DataAccess da=new DataAccess();
		boolean res=false;
		try {
			res=da.AddParkingLot(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
