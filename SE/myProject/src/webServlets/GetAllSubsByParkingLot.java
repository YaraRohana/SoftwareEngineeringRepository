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

import allClasses.FullSubscription;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Subscription;
import da.DataAccess;

/**
 * Servlet implementation class GetAllSubsByParkingLot
 */
@WebServlet("/GetAllSubsByParkingLot")
public class GetAllSubsByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllSubsByParkingLot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot=request.getParameter("parkingLot");
		DataAccess da=new DataAccess();
		if(parkingLot!=null) {
			FullSubscription fs = null;
			OneCarRegularSubscription oc = null;
			OneCarBusinessSubscription bs = null;
			ArrayList<Subscription> allsubs=new ArrayList<Subscription>();
			try {
				allsubs=da.getAllSubsByParkingLot(parkingLot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/json");
			JSONObject jsonSub;
			PrintWriter out = response.getWriter();
			for (Subscription subscription : allsubs) {
				jsonSub=new JSONObject();
				try {
					//jsonSub.put("subscriptionId", subscription.getSubsciptionId());
					jsonSub.put("vehicleNumber", subscription.getVehicleNumber());
					jsonSub.put("startingDate", subscription.getStartDate());
					jsonSub.put("email", subscription.getEmail());
					jsonSub.put("subsId", subscription.getSubsciptionId());
					if(subscription instanceof FullSubscription){
						//System.out.println("yes");
						fs = (FullSubscription) subscription;
						jsonSub.put("type", "fullSubscription");
						jsonSub.put("arrivedSince", fs.getArrivedSince());
					}
					if(subscription instanceof OneCarRegularSubscription){
						oc = (OneCarRegularSubscription) subscription;
						jsonSub.put("type", oc.getType());
						jsonSub.put("parkingLot", oc.getParkingLot());
						jsonSub.put("leavingTime", oc.getLeavingAt());
					}
					if(subscription instanceof OneCarBusinessSubscription){
						bs = (OneCarBusinessSubscription) subscription;
						jsonSub.put("type", bs.getType());
						jsonSub.put("parkingLot", bs.getParkingLot());
						jsonSub.put("leavingTime", bs.getLeavingAt());
					}
					out.println(jsonSub);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				out.flush();
				//System.out.println(subscription.toString());
				System.out.println(jsonSub.toString());
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
