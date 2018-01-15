package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import da.DataAccess;

/**
 * Servlet implementation class SetFaultedParkingSpot
 */
@WebServlet("/SetFaultedParkingSpot")
public class SetFaultedParkingSpot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetFaultedParkingSpot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot = request.getParameter("parkingLot");
		String row = request.getParameter("row");
		String column = request.getParameter("column");
		String width = request.getParameter("width");
		DataAccess da = new DataAccess();
		if (parkingLot != null && column != null && row != null && width != null) {
			int realRow = Integer.parseInt(row);
			int realCol = Integer.parseInt(column);
			int realWidth = Integer.parseInt(width);
			boolean res = false;
			try {
				res = da.setFaultedParkingSpot(parkingLot, realRow, realCol, realWidth);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(res);
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
