package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Email;
import da.DataAccess;

/**
 * Servlet implementation class SendParkingLotManagerAnEmail
 */
@WebServlet("/SendParkingLotManagerAnEmail")
public class SendParkingLotManagerAnEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SendParkingLotManagerAnEmail() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String name = request.getParameter("userName");
		String text = request.getParameter("reportText");
		String reportType = request.getParameter("type");

		
		String email = null;
		String managerMail = null;
		String parkingLotName = null;
		parkingLotName =request.getParameter("parkingLot");
		parkingLotName = "'"+ parkingLotName + "'";
		DataAccess da = new DataAccess();
		try {
			email = da.getEmployeeEmailByName(name);
			managerMail = da.getManagerMail();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (email != null) {
			String parkingLotManager = "Report from parking Lot Manager " + name + "\n";
			String parkingLot = "Parking Lot: " + parkingLotName +"\n";
			Email.sendEmail(managerMail, parkingLotName +" Parking Lot "+ reportType +" \n",parkingLotManager+ parkingLot + text);
		}
		else {
			System.out.println("There is no/ couldn't get the email for this employee");
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
