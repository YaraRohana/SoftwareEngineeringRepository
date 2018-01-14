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

import allClasses.Customer;
import da.DataAccess;

/**
 * Servlet implementation class GetAllCustomers
 */
@WebServlet("/GetAllCustomers")
public class GetAllCustomers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllCustomers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<Customer> allCustomers=new ArrayList<Customer>();
		DataAccess da=new DataAccess();
		try {
			allCustomers=da.getAllCustomers();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonCustomer;
		for (Customer customer : allCustomers) {
			jsonCustomer=new JSONObject();
			try {
				jsonCustomer.put("customerID", customer.getId());
				jsonCustomer.put("email", customer.getEmail());
				jsonCustomer.put("credit", customer.getCredit());
				out.println(jsonCustomer);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();

			System.out.println(customer.toString());
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
