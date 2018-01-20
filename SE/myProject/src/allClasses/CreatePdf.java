package allClasses;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf {

	private String destination;
	private ParkingLot parkingLot;

	public CreatePdf(ParkingLot parkingLot, String destination) {
		this.parkingLot = parkingLot;
		this.destination = destination;
	}

	public Document create() throws DocumentException, IOException {
		int x = 100;
		int y = 600;

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destination));
		document.open();
		Paragraph header = new Paragraph(parkingLot.getName() + "'s ParkingLot Map",
				FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD, BaseColor.BLACK));
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
		for (int floor = 0; floor < 3; floor++) {
			PdfContentByte canvas = writer.getDirectContent();
			Rectangle rect = new Rectangle(x, y - (floor * 150), 10 + x + (parkingLot.getWidth() * 30),
					y + 100 - (floor * 150));
			rect.setBorder(Rectangle.BOX);
			rect.setBackgroundColor(new BaseColor(BaseColor.BLACK.getRGB()));
			canvas.rectangle(rect);

			ParkingSpot[][][] parkingSpots = parkingLot.getParkingSpots();
			for (int i = 0; i < parkingLot.getWidth(); i++) {
				for (int j = 0; j < 3; j++) {
					ParkingSpot spot = parkingSpots[floor][j][i];
					PdfContentByte innerCanvas = writer.getDirectContent();
					// set the size and coords of each rectangle
					Rectangle innerRect = new Rectangle(x + 10 + (i * 30), y + 10 + (j * 30) - (floor * 150),
							x + 30 + (i * 30), y + 30 + (j * 30) - (floor * 150));
					innerRect.setBorder(Rectangle.BOX);

					/*
					 * Color according to whether it's active, occupied, not occupied, or reserved.
					 */
					if (spot.isOccupied()) {
						innerRect.setBackgroundColor(new BaseColor(BaseColor.BLUE.getRGB()));
					} else if (spot.isSaved()) {
						innerRect.setBackgroundColor(new BaseColor(BaseColor.GREEN.getRGB()));
					} else if (spot.isFaulted()) {
						innerRect.setBackgroundColor(new BaseColor(BaseColor.RED.getRGB()));
					} else {
						innerRect.setBackgroundColor(new BaseColor(BaseColor.LIGHT_GRAY.getRGB()));
					}
					innerCanvas.rectangle(innerRect);
				}
			}
		}

		PdfContentByte innerCanvas = writer.getDirectContent();
		innerCanvas = asd(innerCanvas, x, y - (3 * 150), BaseColor.LIGHT_GRAY.getRGB(), "- Free Parking Spot");
		innerCanvas = asd(innerCanvas, x, y - (3 * 150) + 30, BaseColor.RED.getRGB(), "- Faulted Parking Spot");
		innerCanvas = asd(innerCanvas, x, y - (3 * 150) + 60, BaseColor.GREEN.getRGB(), "- Saved Parking Spot");
		innerCanvas = asd(innerCanvas, x, y - (3 * 150) + 90, BaseColor.BLUE.getRGB(), "- Occupied Parking Spot");
		
		Paragraph row = new Paragraph("Parking lot manager: " + parkingLot.getManager(),
				FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK));
		row.setAlignment(Element.ALIGN_CENTER);
		document.add(row);
		document.close();
		return document;
	}

	private PdfContentByte asd(PdfContentByte innerCanvas, int x, int y, int colorRGB, String text)
			throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		Rectangle innerRect = new Rectangle(x, y, x + 20, y + 20);
		innerRect.setBorder(Rectangle.BOX);
	//	innerRect.setBorderColorBottom(BaseColor.BLACK);
		innerRect.setBackgroundColor(new BaseColor(colorRGB));
		innerCanvas.rectangle(innerRect);
		
		innerCanvas.saveState();
		innerCanvas.beginText();
		innerCanvas.moveText(x + 25, y + 5);
		innerCanvas.setFontAndSize(bf, 12);
		innerCanvas.showText(text);
		innerCanvas.endText();
		innerCanvas.restoreState();

		return innerCanvas;
	}
}
