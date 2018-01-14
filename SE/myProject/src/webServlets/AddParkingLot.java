package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.CPS;
import allClasses.ParkingLot;
import da.DataAccess;

/**
 * Servlet implementation class AddParkingLot1
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
		// TODO Auto-generated method stub
	//response.getWriter().append("Served at: ").append(request.getContextPath());
		String name = request.getParameter("name");
		String location = request.getParameter("location");
		String manager = request.getParameter("manager");
		String width = request.getParameter("width");
		CPS cps = CPS.getInstance();
		if(cps==null) {
			System.out.println("problem");
		}
		if (name != null && location != null && manager != null && width != null) {
			System.out.println("we're here");
			int realWidth = 0;
			realWidth = Integer.parseInt(width);
			ParkingLot p = new ParkingLot(name, location, true, false, manager, realWidth);
			DataAccess da = new DataAccess();
			boolean res = false;
			try {
				res = da.addParkingLot(p);
				if (res) {
					if(cps.getParkingLots().add(p)) {
						System.out.println("added to ParkingLots arraylist in CPS");
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
