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
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final static String _USERNAME = "username";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		System.out.println("im heree");
		ParkingLot p = new ParkingLot(24, "Mall", "haifa", true, true, "Yaman");
		DataAccess da = new DataAccess();
		boolean res = false;
		try {
			res = da.AddParkingLot(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);
		
		PrintWriter out = response.getWriter();
	        String username = request.getParameter( _USERNAME );
	        
	        response.setContentType("text/html");
	        out.println("");
	        out.println("");
	            out.println("HelloGhada");
	        out.println("");
	        out.println("");
	        out.println("");
	        out.println("");
        out.println("");
        out.println("");
		
		
		
		
		
		
		
		doGet(request, response);
	}

}
