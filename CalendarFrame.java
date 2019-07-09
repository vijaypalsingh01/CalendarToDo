import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class CalendarFrame{
	
		private GregorianCalendar calendar;
		private CalendarTable cTable;
		private JFrame calendarFrame;
		
		public CalendarFrame()  {
			calendarFrame = new JFrame("Calendar");
			calendarFrame.setLayout(new BorderLayout());
			JPanel buttons = new JPanel(new GridLayout(1,4));
			calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			calendar = new GregorianCalendar(Locale.getDefault());
			cTable = new CalendarTable(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			String [] months = {"January", "February", "March", "April", "May", "June", "July","August", "September","October", "November", "December"};
			JButton monthLabel = new JButton(months[calendar.get(Calendar.MONTH)]);
			Integer years[] = new Integer [1418];
			int year = 1582;
			for(int i = 0; i < 1418; i++) {
				years[i] = year++;
			}
			monthLabel.setBackground(Color.WHITE);
		    JComboBox<Integer> yearList = new JComboBox<Integer>(years);  
		    yearList.setPreferredSize(new Dimension(100,30));
		    yearList.setSelectedItem(calendar.get(Calendar.YEAR));
		    yearList.setBounds(50, 5 ,90,20); 
		    yearList.addActionListener(event -> {
		    	calendar.set((int) yearList.getSelectedItem(), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				monthLabel.setText(months[calendar.get(Calendar.MONTH)]);
		    	cTable.updateCalendar(calendar);
		    });
		    BasicArrowButton nextMonth  = new BasicArrowButton(BasicArrowButton.EAST);
		    nextMonth.setPreferredSize((new Dimension(25,25)));
		    nextMonth.addActionListener(event -> {
		    	calendar.set((int) yearList.getSelectedItem(), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
				monthLabel.setText(months[calendar.get(Calendar.MONTH)]);
		    	cTable.updateCalendar(calendar);
		    });
		    BasicArrowButton previousMonth  = new BasicArrowButton(BasicArrowButton.WEST);
		    previousMonth.setPreferredSize(new Dimension(25,25));
		    previousMonth.addActionListener(event -> {
		    	calendar.set((int) yearList.getSelectedItem(), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DAY_OF_MONTH));
				monthLabel.setText(months[calendar.get(Calendar.MONTH)]);
		    	cTable.updateCalendar(calendar);
		    });
		    
		    
		    monthLabel.setPreferredSize(new Dimension(100,30));
		    monthLabel.addActionListener(event -> {
		    	cTable.getMonthView();
		    });
		    
		    JLabel todayDate = new JLabel();
		    todayDate.setPreferredSize(new Dimension(FRAME_WIDTH, 50));
		    todayDate.setText("Today: "+ (calendar.get(Calendar.MONTH)+1) + "/ "+ calendar.get(Calendar.DAY_OF_MONTH) +"/ "+ calendar.get(Calendar.YEAR));
		    buttons.setPreferredSize(new Dimension(FRAME_WIDTH,50));
		    buttons.add(previousMonth);
		    buttons.add(monthLabel);
		    buttons.add(yearList);
		    buttons.add(nextMonth);
	        calendarFrame.add(buttons,BorderLayout.NORTH);
	        calendarFrame.add(cTable,BorderLayout.CENTER);
	        calendarFrame.add(todayDate, BorderLayout.SOUTH);
	        calendarFrame.pack();
	        calendarFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	        calendarFrame.setVisible(true);
		}
		
		public void attach(Observer o) {
			cTable.attach(o);
		}
		
	   private static final int FRAME_WIDTH = 590;
	   private static final int FRAME_HEIGHT = 450;


}