import java.util.GregorianCalendar;

/**
 * Interface that will be used to notify Observers for any change
 * @author Vijaypal
 *
 */
public interface Observer {
	void notify(Object list,boolean monthView);
}