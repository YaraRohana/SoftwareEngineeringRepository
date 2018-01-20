package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import da.DataAccess;

/**
 * Servlet implementation class GetAllInfoByParkingLot
 */
@WebServlet("/GetAllInfoByParkingLot")
public class GetAllInfoByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllInfoByParkingLot() {
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
		String parkingLot = request.getParameter("parkingLot");
		DataAccess da = new DataAccess();
		if (parkingLot != null) {
			PrintWriter out = response.getWriter();

				try {
					out.println(da.getNumberOfExecutedOrdersByParkingLot(parkingLot));
					out.println(da.getNumberOfCanceledOrdersByParkingLot(parkingLot));
					out.println(da.getNumberOfLateArrivalOrdersByParkingLot(parkingLot));
					out.println( da.getNumOfSubscriptionsByParkingLot(parkingLot));
					out.println(da.getNumOfRegularSubsWithMultipleVehiclesByParkingLot(parkingLot));
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
