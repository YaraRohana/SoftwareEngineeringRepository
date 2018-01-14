package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import da.DataAccess;

/**
 * Servlet implementation class InsertVehicleIntoParkingLot
 */
@WebServlet("/InsertVehicleIntoParkingLot")
public class InsertVehicleIntoParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertVehicleIntoParkingLot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot=request.getParameter("parkingLot");
		String vehicleNumber=request.getParameter("vehicleNumber");
		DataAccess da=new DataAccess();
		boolean res=false;
		if(parkingLot!=null && vehicleNumber!=null) {
			try {
				res=da.insertCarIntoParkingLot(parkingLot, vehicleNumber);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
