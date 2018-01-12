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
 * Servlet implementation class EmployeePriceChange
 */
@WebServlet("/EmployeePriceChange")
public class EmployeePriceChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeePriceChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int uponArrival=0 , preOrder=0;
		String name = request.getParameter("Username");
		String preorder = request.getParameter("preOrder");
		String uponarrival = request.getParameter("uponArrival");
	
		DataAccess da = new DataAccess();
		if(name!=null&&preorder!=null&&uponarrival!=null) {
			uponArrival = Integer.parseInt(uponarrival);
			preOrder = Integer.parseInt(preorder);
			
			try {
				da.employeePriceChange(name, preOrder, uponArrival);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
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
