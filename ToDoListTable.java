import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ToDoListTable extends JComponent{
	private static final long serialVersionUID = 1L;
	private boolean isSelected;
	private String selectedItem;
	private GregorianCalendar cal;
	private DefaultTableModel model;
	private JTable list;
	private Data data;
	private boolean monthlyView;
	
	public ToDoListTable(Data d,GregorianCalendar c) {
		monthlyView = false;
		setLayout(new BorderLayout());
		cal = c;
		data = d;
		isSelected = false;
	    model = new DefaultTableModel();
	    list = new JTable(model);
	    model.addColumn("To-do list");
		list.setSize(300, 300);
		for(int i = model.getRowCount() -1; i>=0;i--) {
			model.removeRow(i);
		}
		ArrayList<String> items = new ArrayList<>();
	    if(data.getData(cal) !=null) {
			items = data.getData(cal);
			if(items.size()>0) {
				for(int i =0; i< items.size();i++) {
					model.addRow(new Object[] { items.get(i) });
				}
			}
		}
		list.addMouseListener(new MousePressedListener2());
	    list.setDefaultEditor(Object.class,null);
	    list.setShowHorizontalLines(false);
	    list.setShowVerticalLines(false);
	    list.setCellSelectionEnabled(true);
	    add(list);
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public String getSelectedItem() {
		isSelected = false;
		return selectedItem;
	}
	
	private class MousePressedListener2 extends MouseAdapter {
		public void mousePressed(MouseEvent event){
			 int row = list.getSelectedRow();
			 int column = list.getSelectedColumn();
			 isSelected = true;
			 selectedItem = ""+ (String) model.getValueAt(row, column);
		 }
	 }
	
	public void paintComponent(Graphics g) {
		      Graphics2D g2 = (Graphics2D) g;
		      draw(g2);
	}

	public void updateCalendar(GregorianCalendar ca, Data d, boolean monthlyView) {
		if(monthlyView == true) {
			setMonthlyView(true);
		}
		else
			setMonthlyView(false);

		cal = ca;
		data = d;
		this.removeAll();
		repaint();
	}
	
	public void setMonthlyView(boolean set) {
		monthlyView = set;
	}
	
	
	public boolean getMonthlyView()
	{
		return monthlyView;
	}
	public void draw(Graphics g) {
		for(int i = model.getRowCount() -1; i>=0;i--) {
			model.removeRow(i);
		}
		if(!monthlyView) {
			ArrayList<String> items = new ArrayList<>();
		    if(data.getData(cal) !=null) {
				items = data.getData(cal);
				if(items.size()>0) {
					for(int i =0; i< items.size();i++) {
						model.addRow(new Object[] { items.get(i) });
					}
				}
			}
		}
		else if(monthlyView) {
			for(int i = cal.getActualMinimum(Calendar.DAY_OF_MONTH) ;i<cal.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
			    if(data.getData(cal) !=null) {
					ArrayList<String> items = new ArrayList<>();
					items = data.getData(cal);
					for(int j =0; j< items.size();j++) {
						model.addRow(new Object[] { items.get(j) });
					}
				}
			}
			monthlyView = false;
		}

		add(list);
	}
}