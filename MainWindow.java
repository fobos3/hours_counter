import java.awt.Dimension;
import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	@SuppressWarnings("unchecked")
	public MainWindow(){
		// get the current date
		today = new GregorianCalendar();
		
		// dimensions of components
		final Dimension vspace = new Dimension(0, 15);
		final Dimension hspace = new Dimension(5, 15);
		final int row_count = 12;
		final int len_label = 100;
		
		// window dimensions
		setTitle("Planner");
		setSize(400, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// start calendar
		//label
		JLabel start_label = new JLabel("Start:");
		set_len(start_label, len_label);
		
		// year
		start_year = new JComboBox<String>();
		start_year.addItem("Year");
		for(int i = 0; i < 10; i++){
			start_year.addItem(Integer.toString(today.get(GregorianCalendar.YEAR) + i));
		}
		start_year.setMaximumSize(start_year.getPreferredSize());
		start_year.setMaximumRowCount(row_count);
		
		// month
		start_month = new JComboBox<String>();
		start_month.addItem("Month");
		for(int i = 1; i <= 12; i++){
			start_month.addItem(get_month(i));
		}
		start_month.setMaximumSize(start_month.getPreferredSize());
		start_month.setMaximumRowCount(row_count);
		
		start_day = new JComboBox<String>();
		start_day.addItem("Day");
		start_day.setMaximumSize(start_day.getPreferredSize());
		start_day.setMaximumRowCount(row_count);
		
		// end calendar
		//label
		JLabel end_label = new JLabel("End:");
		set_len(end_label, len_label);
		
		answer = new JLabel();
		
		// year
		end_year = new JComboBox<String>();
		end_year.addItem("Year");
		for(int i = 0; i < 10; i++){
			end_year.addItem(Integer.toString(today.get(GregorianCalendar.YEAR) + i));
		}
		end_year.setMaximumSize(end_year.getPreferredSize());
		end_year.setMaximumRowCount(row_count);
		
		// month
		end_month = new JComboBox<String>();
		end_month.addItem("Month");
		for(int i = 1; i <= 12; i++){
			end_month.addItem(get_month(i));
		}
		end_month.setMaximumSize(end_month.getPreferredSize());
		end_month.setMaximumRowCount(row_count);
		
		end_day = new JComboBox<String>();
		end_day.addItem("Day");
		end_day.setMaximumSize(end_day.getPreferredSize());
		end_day.setMaximumRowCount(row_count);
		
		// hours
		JLabel hours_label = new JLabel("Hours:");
		set_len(hours_label, len_label);
		
		hours = new JComboBox<Double>();
		for(double i = 0; i < 24; i+= 0.5){
			hours.addItem(i);
		}
		hours.setMaximumSize(hours.getPreferredSize());
		hours.setMaximumRowCount(row_count);
		set_len(hours, start_year.getPreferredSize().width);
		
		// days of week hours
		JLabel[] hours_day_label = new JLabel[7];
		hours_day = new JComboBox[7];
		for(int i = 0; i < 7; i++){
			// label
			hours_day_label[i] = new JLabel();
			hours_day_label[i].setText(get_day(i+1)+":");
			set_len(hours_day_label[i], len_label);
			
			// hours drop down
			hours_day[i] = new JComboBox<Double>();
			for(double j = 0.0; j < 24; j+= 0.5){
				hours_day[i].addItem(j);
				hours_day[i].setMaximumSize(hours_day[i].getPreferredSize());
				hours_day[i].setMaximumRowCount(row_count);
				set_len(hours_day[i], start_year.getPreferredSize().width);
			}
		}
		
		// answer
		JLabel answer_label = new JLabel("Total:");
		set_len(answer_label, len_label);
		
		// action listeners to populate the day drop down
		// start date
		start_year.addActionListener(new day_populater(start_day, start_month, start_year));
		start_month.addActionListener(new day_populater(start_day, start_month, start_year));
		
		// end date
		end_year.addActionListener(new day_populater(end_day, end_month, end_year));
		end_month.addActionListener(new day_populater(end_day, end_month, end_year));
		
		// action listener to calculate the total hours
		updater calc = new updater(start_day, start_month, start_year, end_day, end_month, end_year, hours_day, answer);
		start_year.addActionListener(calc);
		start_month.addActionListener(calc);
		start_day.addActionListener(calc);
		end_year.addActionListener(calc);
		end_month.addActionListener(calc);
		end_day.addActionListener(calc);
		hours.addActionListener(calc);
		for(int i = 0; i < 7; i++){
			hours_day[i].addActionListener(calc);
		}
		
		// action listener to update the hours per day
		hours.addActionListener(new hours_updater(hours, hours_day));
		
		// select the current date
		start_year.setSelectedIndex(1);
		start_month.setSelectedIndex(today.get(GregorianCalendar.MONTH)+1);
		start_day.setSelectedIndex(today.get(GregorianCalendar.DAY_OF_MONTH)+1);
		end_year.setSelectedIndex(1);
		end_month.setSelectedIndex(today.get(GregorianCalendar.MONTH)+1);
		end_day.setSelectedIndex(today.get(GregorianCalendar.DAY_OF_MONTH)+1);
		
		
		// panels
		JPanel panel = new JPanel();
		JPanel calendar_start = new JPanel();
		JPanel calendar_end = new JPanel();
		JPanel UI = new JPanel();
		JPanel[] UI_days = new JPanel[7];
		for(int i = 0; i < 7; i++){
			UI_days[i] = new JPanel();
		}
		JPanel ans = new JPanel();
		
		// panel layouts
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		calendar_start.setLayout(new BoxLayout(calendar_start, BoxLayout.X_AXIS));
		calendar_end.setLayout(new BoxLayout(calendar_end, BoxLayout.X_AXIS));
		UI.setLayout(new BoxLayout(UI, BoxLayout.X_AXIS));
		for(int i = 0; i < 7; i++){
			UI_days[i].setLayout(new BoxLayout(UI_days[i], BoxLayout.X_AXIS));
		}
		ans.setLayout(new BoxLayout(ans, BoxLayout.X_AXIS));
		
		// start calendar panel
		calendar_start.add(Box.createRigidArea(hspace));
		calendar_start.add(start_label);
		calendar_start.add(Box.createRigidArea(hspace));
		calendar_start.add(start_year);
		calendar_start.add(Box.createRigidArea(hspace));
		calendar_start.add(start_month);
		calendar_start.add(Box.createRigidArea(hspace));
		calendar_start.add(start_day);
		calendar_start.add(Box.createHorizontalGlue());

		// end calendar panel
		calendar_end.add(Box.createRigidArea(hspace));
		calendar_end.add(end_label);
		calendar_end.add(Box.createRigidArea(hspace));
		calendar_end.add(end_year);
		calendar_end.add(Box.createRigidArea(hspace));
		calendar_end.add(end_month);
		calendar_end.add(Box.createRigidArea(hspace));
		calendar_end.add(end_day);
		calendar_end.add(Box.createHorizontalGlue());
		
		// hours panel
		UI.add(Box.createRigidArea(hspace));
		UI.add(hours_label);
		UI.add(Box.createRigidArea(hspace));
		UI.add(hours);
		UI.add(Box.createHorizontalGlue());
		
		// hours per day panel
		for(int i = 0; i < 7 ; i++){
			UI_days[i].add(Box.createRigidArea(hspace));
			UI_days[i].add(hours_day_label[i]);
			UI_days[i].add(Box.createRigidArea(hspace));
			UI_days[i].add(hours_day[i]);
			UI_days[i].add(Box.createHorizontalGlue());
		}
		
		// answer panel
		ans.add(Box.createRigidArea(hspace));
		ans.add(answer_label);
		ans.add(Box.createRigidArea(hspace));
		ans.add(answer);
		ans.add(Box.createHorizontalGlue());
		
		// add all the panels to the master panel
		panel.add(Box.createRigidArea(vspace));
		panel.add(calendar_start);
		panel.add(Box.createRigidArea(vspace));
		panel.add(calendar_end);
		panel.add(Box.createRigidArea(vspace));
		panel.add(UI);
		panel.add(Box.createRigidArea(vspace));
		for(int i = 0; i < 7; i++){
			panel.add(UI_days[i]);
			panel.add(Box.createRigidArea(vspace));
		}
		panel.add(ans);
		panel.add(Box.createVerticalGlue());
		
		add(panel);
		
		setVisible(true);
	}
	
	// return the month as a name from the month number
	private String get_month(int month){
		return new DateFormatSymbols().getMonths()[month-1];
	}
	
	// return the day as a name from the day of the week number
	private String get_day(int day){
		if(day == 7){
			return new DateFormatSymbols().getWeekdays()[1];
		}
		return new DateFormatSymbols().getWeekdays()[day+1];
	}
	
	// set the length of the object
	private void set_len(JComponent o, int len){
		o.setPreferredSize(new Dimension(len, o.getMaximumSize().height));
		o.setMinimumSize(new Dimension(len, o.getMaximumSize().height));
		o.setMaximumSize(new Dimension(len, o.getMaximumSize().height));
	}
	
	// date input fields
	private JComboBox<String> start_year;
	private JComboBox<String> end_year;
	
	private JComboBox<String> start_month;
	private JComboBox<String> end_month;
	
	private JComboBox<String> start_day;
	private JComboBox<String> end_day;
	
	// hours
	private JComboBox<Double> hours;
	
	// hours per day
	private JComboBox<Double>[] hours_day;
	
	// answer
	private JLabel answer;
	
	// calendars
	private GregorianCalendar today;
}
