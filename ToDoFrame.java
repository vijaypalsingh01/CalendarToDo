import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ToDoFrame implements Observer {
	private JFrame todoFrame;
	private GregorianCalendar calendar;
	private Data todo;
	private ToDoListTable list;
	private JLabel selectedDate;
	private String dateE;
	private boolean edit;

	
	public ToDoFrame() {
		edit = false;
		todoFrame = new JFrame("To-do List");
		calendar = new GregorianCalendar(Locale.getDefault());
		selectedDate = new JLabel();
		selectedDate.setPreferredSize(new Dimension(FRAME_WIDTH, 50));
		selectedDate.setText("Date: "+(calendar.get(Calendar.MONTH)+1)+ "/ " +calendar.get(Calendar.DAY_OF_MONTH)+  "/ "+calendar.get(Calendar.YEAR) );
		
		todo = new Data();
		list =  new ToDoListTable(todo,calendar);
		list.setSize(500, 500);
		JPanel buttons = new JPanel(new FlowLayout());
		JPanel allPanel = new JPanel(new BorderLayout());
		todoFrame.setLayout(new BorderLayout());
		
		todoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextField area = new JTextField();
		area.setEditable(true);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(event->{
				todo.addData(calendar, area.getText());
				list.updateCalendar(calendar,todo,false);
				area.setText("");
		});

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(event->{
			if(list.isSelected()) {
				edit = true;
				area.setText(list.getSelectedItem());
			}
		});
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(event->{
			if(list.isSelected()) {
				todo.deleteData(list.getSelectedItem(), calendar);
				list.updateCalendar(calendar,todo,false);
			}
		});
		
		
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(event->{			
				try {
					dateE = "" +(calendar.get(Calendar.MONTH)+1)+ "_" +calendar.get(Calendar.DAY_OF_MONTH)+ "_" +calendar.get(Calendar.YEAR) + ".txt";
					todo.exportData(calendar, dateE);
				} 
				catch (IOException e) {
				}
		});
		
		area.addActionListener(event->{
			if(edit) {
				todo.editData(list.getSelectedItem(), area.getText(),calendar);
				list.updateCalendar(calendar,todo,false);
				edit = false;
				area.setText("");
			}
		});
		area.setPreferredSize(new Dimension(FRAME_WIDTH,50));

		buttons.add(addButton);
		buttons.add(deleteButton);
		buttons.add(editButton);
		buttons.add(exportButton);
		allPanel.add(new JPanel(), BorderLayout.NORTH);
		allPanel.add(area,BorderLayout.CENTER);
		allPanel.add(buttons,BorderLayout.SOUTH);
		
		

		todoFrame.add(selectedDate, BorderLayout.NORTH);
		todoFrame.add(allPanel,BorderLayout.SOUTH);
		
		JScrollPane js = new JScrollPane(list);
		
		todoFrame.add(js,BorderLayout.CENTER);
		todoFrame.pack(); 
		todoFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		todoFrame.setVisible(true);
	}

	@Override
	public void notify(Object list, boolean monthView) {
		calendar = (GregorianCalendar) list;
		this.list.updateCalendar(calendar,todo, monthView);
		selectedDate.setText("Date: "+(calendar.get(Calendar.MONTH)+1)+ "/ " +calendar.get(Calendar.DAY_OF_MONTH)+  "/ "+calendar.get(Calendar.YEAR) );
	}
	
	   private static final int FRAME_WIDTH = 450;
	   private static final int FRAME_HEIGHT = 400;
}
