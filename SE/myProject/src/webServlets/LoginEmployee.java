package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Employee.employeeType;
import da.DataAccess;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginEmployee")
public class LoginEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginEmployee() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String name = request.getParameter("name");
		String pass = request.getParameter("password");
		DataAccess da = new DataAccess();
		boolean res = false;
		if (name != null & pass != null) {
			try {
				res = da.checkIfEmployeeExists(name, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!res) {
				System.out.println("Employee does not exist!");
				PrintWriter out=response.getWriter();
				out.println(res);
				return;
			} else {
				String type=null;
				String parkingLot=null;
				try {
					da.loginEmployee(name,pass);
					type=da.getEmployeeType(name);
					parkingLot=da.getEmployeeParkingLot(name);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			PrintWriter out=response.getWriter();
			out.println(res);
			PrintWriter out1=response.getWriter();
			out1.println(type);
			if(parkingLot!=null) {
				PrintWriter out2=response.getWriter();
				out2.println(parkingLot);
			}
			
			}	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
