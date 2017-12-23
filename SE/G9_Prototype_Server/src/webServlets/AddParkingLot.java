package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("this is a new age");
		String name = request.getParameter("name");
		String location = request.getParameter("location");
		String manager=request.getParameter("manager");
		int width=Integer.parseInt(request.getParameter("width"));
				
		if (name!= null && location!=null && manager!=null) {
			ParkingLot p = new ParkingLot(name,location,false,true,manager,width);
			DataAccess da = new DataAccess();
			boolean res = false;
			try {
				res = da.addParkingLot(p);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			PrintWriter out=response.getWriter();
			out.println(res);
		}
	}

}
