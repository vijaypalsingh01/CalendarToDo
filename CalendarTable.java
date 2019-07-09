import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JTable;


public class CalendarTable  extends JComponent{
	private static final long serialVersionUID = 1L;
	private GregorianCalendar calendar;
	private JTable table;
	private Observer toDo;
	
	public CalendarTable(int year, int month, int date){
		setLayout(new FlowLayout());
		calendar = new GregorianCalendar(Locale.getDefault());
	    calendar.set(year, month, date);
	    table= new JTable(7,7);
	    table.addMouseListener(new MousePressedListener());
	    table.setDefaultEditor(Object.class,null);
	    table.setShowHorizontalLines(false);
	    table.setShowVerticalLines(false);
	    table.setCellSelectionEnabled(true);	    
	    String [] day = {"Sun", "Mon","Tue","Wed","Thu","Fri","Sat"};
	    for(int i = 0; i <7; i++) {
	    	table.setValueAt(day[i], 0, i);
	    }
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    int firstDay = c.get(Calendar.DAY_OF_WEEK)-1;
	    int dayOfWeek = 1;
	    for(int i = 0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
	    	table.setValueAt(i+1, dayOfWeek, firstDay++);
	    	if(firstDay == 7) {
	    		firstDay = 0;
	    		dayOfWeek++;
	    	}
	    }
	    add(table);

	}
	
	public void updateCalendar(GregorianCalendar c ) {
		calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		this.removeAll();
		repaint();
	}
	
	public void getMonthView() {
		toDo.notify(calendar,true);
	}
	
	private class MousePressedListener extends MouseAdapter {
		public void mousePressed(MouseEvent event){
			 int row = table.getSelectedRow();
			 int column = table.getSelectedColumn();
			 int date = (int)table.getValueAt(row, column);
			 if(date > 0) {
				 calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), date);
				 toDo.notify(calendar,false);
			 }
		 }
	 }
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
		}

	public void draw(Graphics g) {
	    String [] day = {"Sun", "Mon","Tue","Wed","Thu","Fri","Sat"};
	    for(int i = 0; i <7; i++) {
	    	table.setValueAt(day[i], 0, i);
		    table.setRowHeight(i, 40);
		    //table.setAlignmentY(JTable.CENTER_ALIGNMENT);
	    }
	    GregorianCalendar c = (GregorianCalendar) calendar.clone();
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    int firstDay = c.get(Calendar.DAY_OF_WEEK)-1;
	    int dayOfWeek = 1;
	    for(int i = 0; i<firstDay; i++) {
	    	table.setValueAt("", 1, i); 
	    }
	    for(int i = 0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
	    	table.setValueAt(i+1, dayOfWeek, firstDay++); 
	    	if(firstDay == 7) {
	    		firstDay = 0;
	    		dayOfWeek++;
	    	}
	    }
	    
	    while(dayOfWeek < table.getRowCount()) {
	    	table.setValueAt("", dayOfWeek, firstDay++); 
	    	if(firstDay == 7) {
	    		firstDay = 0;
	    		dayOfWeek++;
	    	}
	    }
	    add(table);
	}
	
	public void attach(Observer o) {
		toDo = o;
	}
}