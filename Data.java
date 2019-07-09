import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Data{
	private GregorianCalendar cal;
	private Map<GregorianCalendar, ArrayList<String>> todoMap;
	
	public Data() {
		cal = new GregorianCalendar(Locale.getDefault());
		todoMap = new HashMap<>();
	}
	
	public ArrayList<String> getData(GregorianCalendar c) {
		return todoMap.get(c);
	}
	
	public void addData(GregorianCalendar c, String item) {
		ArrayList<String> temp = new ArrayList<>();
		if(item != null) {
			if(todoMap.containsKey(c)) {
				temp.addAll(todoMap.get(c));
			}
			temp.add(item);
			todoMap.put(c, temp);
		}
		//System.out.println(todoMap.toString());
	}
	
	public void deleteData(String item, GregorianCalendar c) {
		ArrayList<String> temp = new ArrayList<>();
		temp = todoMap.get(c);
		temp.remove(item);
		todoMap.put(c, temp);
		//System.out.println(todoMap.toString());
	}
	
	public void editData(String item, String newItem, GregorianCalendar c) {
		ArrayList<String> temp = new ArrayList<>();
		temp = todoMap.get(c);
		int index = temp.indexOf(item);
		temp.set(index, newItem);
		todoMap.put(c, temp);
		//System.out.println(todoMap.toString());
	}
	
	
	public void exportData(GregorianCalendar c, String fileName) throws IOException
	{
		ArrayList<String> temp = new ArrayList<>();
		temp = todoMap.get(c);
		BufferedWriter Out = new BufferedWriter (new FileWriter(fileName));
		for (int i = 0; i < temp.size(); i++)
		{
			Out.write(temp.get(i));
			Out.newLine();
			
		}
		Out.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		GregorianCalendar d = (GregorianCalendar) obj;
		if(d.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && d.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && d.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}
}