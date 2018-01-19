package listeners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class Notifications
 *
 */
@WebListener
public class Notifications implements ServletContextListener {

	private ScheduledExecutorService scheduler; 
    /**
     * Default constructor. 
     */
    public Notifications() {
       
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
        scheduler.shutdown();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	//scheduler = Executors.newSingleThreadScheduledExecutor();
    	scheduler = Executors.newScheduledThreadPool(3);
    	scheduler.scheduleAtFixedRate(new NotifySubscriptionEnd(), 0, 1, TimeUnit.DAYS);
    	scheduler.scheduleAtFixedRate(new Notify14DaysParking(), 0, 1, TimeUnit.DAYS);
    	scheduler.scheduleAtFixedRate(new NotifyLateArrival(), 0, 1, TimeUnit.MINUTES);
    }
	
}
