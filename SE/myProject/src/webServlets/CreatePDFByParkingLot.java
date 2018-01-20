package webServlets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import allClasses.CreatePdf;
import allClasses.ParkingLot;
import allClasses.ParkingSpot;
import da.DataAccess;

/**
 * Servlet implementation class CreatePDFByParkingLot
 */
@WebServlet("/CreatePDFByParkingLot")
public class CreatePDFByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatePDFByParkingLot() {
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
		String parkingLot = request.getParameter("parkingLot");
		DataAccess da = new DataAccess();
		if (parkingLot != null) {
			ParkingLot pl = null;
			// int width=da.getWidthByParkingLot(parkingLot);
			try {
				pl = da.getParkingLotByName(parkingLot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pl.setParkingSpots(da.getParkingLotImageNew(parkingLot));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CreatePdf pdf = new CreatePdf(pl, "C:\\Users\\Yara Rohana\\Documents\\pdfViewOfPL.pdf");
			try {
				pdf.create();
			} catch (DocumentException e) {
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
