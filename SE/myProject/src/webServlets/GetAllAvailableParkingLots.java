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

import org.json.JSONException;
import org.json.JSONObject;

import allClasses.ParkingLot;
import da.DataAccess;

/**
 * Servlet implementation class GetAllAvailableParkingLots
 */
@WebServlet("/GetAllAvailableParkingLots")
public class GetAllAvailableParkingLots extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllAvailableParkingLots() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		int size;
		DataAccess da = new DataAccess();
		ArrayList<ParkingLot> ParkingLots = new ArrayList<ParkingLot>();
		try {
			ParkingLots = da.GetAllParkingLots();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ParkingLot temp = null;
		size = ParkingLots.size();
		while (--size >= 0) {
			temp = ParkingLots.get(size);
			if (temp.isActive() == false || temp.isFull() == true) {
				ParkingLots.remove(size);
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonParkingLot;
		for (ParkingLot parkingLot : ParkingLots) {
			try {
				jsonParkingLot = new JSONObject();
				jsonParkingLot.put("name", parkingLot.getName());
				jsonParkingLot.put("location", parkingLot.getLocation());
				out.println(jsonParkingLot);
			} catch (JSONException ex) {

			}
			out.flush();
			System.out.println(parkingLot.toString());
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
