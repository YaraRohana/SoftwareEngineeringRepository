package da;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import allClasses.Order;
import allClasses.Order.OrderType;

class DataAccessTest {
	DataAccess da=new DataAccess();
	@Test
	void testAddOrder() throws SQLException {

		Order o=new Order(0, OrderType.preOrder, "Univ", "12-1-2018", "12-1-2018", "16:00", "20:00", "22", "777", false, false, false);
		boolean res=da.addOrder(o);
		
	}

	@Test
	void testGetAllOrders() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllOrdersByCustomerId() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllOrdersByVehicleNumber() {
		fail("Not yet implemented");
	}

	@Test
	void testAddVehicle() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckIfOrderExistsByAllParameters() {
		fail("Not yet implemented");
	}

	@Test
	void testCancelOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOrderCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCancelOrderCredit() {
		fail("Not yet implemented");
	}

}
