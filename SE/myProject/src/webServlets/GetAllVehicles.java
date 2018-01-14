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

import allClasses.Vehicle;
import da.DataAccess;

/**
 * Servlet implementation class GetAllVehicles
 */
@WebServlet("/GetAllVehicles")
public class GetAllVehicles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllVehicles() {
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
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		DataAccess da = new DataAccess();
		try {
			vehicles = da.getAllVehicles();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonVhicle;
		for (Vehicle vehicle : vehicles) {
			jsonVhicle = new JSONObject();
			try {
				jsonVhicle.put("vehicle number", vehicle.getVehicleNumber());
				jsonVhicle.put("customer id", vehicle.getCustomerId());
				out.println(jsonVhicle);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
			/*
			 * jsonVhicle.put("row", vehicle.getRow()); jsonVhicle.put("column",
			 * vehicle.getColumn()); jsonVhicle.put("width", vehicle.getWidth());
			 */
			out.flush();
			System.out.println(vehicle.toString());
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
