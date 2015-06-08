import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;


public class updater implements ActionListener {
	public updater(JComboBox<String> start_day, JComboBox<String> start_month, JComboBox<String> start_year, JComboBox<String> end_day, JComboBox<String> end_month, JComboBox<String> end_year, JComboBox<Double>[] hours, JLabel answer){
		this.start_day = start_day;
		this.start_month = start_month;
		this.start_year = start_year;
		this.end_day = end_day;
		this.end_month = end_month;
		this.end_year = end_year;
		this.hours = hours;
		this.answer = answer;
	}
	
	public void actionPerformed(ActionEvent e){
		// do nothing if an invalid date is selected
		if(start_day.getSelectedIndex() == 0 || start_month.getSelectedIndex() == 0 || start_year.getSelectedIndex() == 0 || end_day.getSelectedIndex() == 0 || end_month.getSelectedIndex() == 0 || end_year.getSelectedIndex() == 0){
			return;
		}
		
		// create calendar objects
		GregorianCalendar start_date = new GregorianCalendar();
		GregorianCalendar end_date = new GregorianCalendar();
		
		// start date calendar
		start_date.set(GregorianCalendar.YEAR, Integer.parseInt((String) start_year.getSelectedItem()));
		start_date.set(GregorianCalendar.MONTH, start_month.getSelectedIndex()-1);
		start_date.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt((String) start_day.getSelectedItem()));
		
		// end date calendar
		end_date.set(GregorianCalendar.YEAR, Integer.parseInt((String) end_year.getSelectedItem()));
		end_date.set(GregorianCalendar.MONTH, end_month.getSelectedIndex()-1);
		end_date.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt((String) end_day.getSelectedItem()));
		
		// do nothing if the end date is before the start date
		if(start_date.compareTo(end_date) > 0){
			answer.setText("The end date is before the start date!");
			return;
		}
		
		// calculate the amount of hours
		GregorianCalendar counter_date = start_date;
		
		double total_hours = 0.0;
		while(!comp_calendar(end_date, counter_date)){
			int day_of_week = counter_date.get(GregorianCalendar.DAY_OF_WEEK)-2;
			if(day_of_week == -1){
				day_of_week = 6;
			}
			total_hours += (double)hours[day_of_week].getSelectedItem();
			counter_date.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		
		// output the answer
		answer.setText(Double.toString(total_hours));
	}
	
	private Boolean comp_calendar(GregorianCalendar cal1, GregorianCalendar cal2){
		if(cal1.get(GregorianCalendar.DAY_OF_MONTH) == cal2.get(GregorianCalendar.DAY_OF_MONTH) && cal1.get(GregorianCalendar.MONTH) == cal2.get(GregorianCalendar.MONTH) && cal1.get(GregorianCalendar.YEAR) == cal2.get(GregorianCalendar.YEAR)){
			return true;
		}
		
		return false;
	}
	
	public JComboBox<String> start_day;
	public JComboBox<String> start_month;
	public JComboBox<String> start_year;
	public JComboBox<String> end_day;
	public JComboBox<String> end_month;
	public JComboBox<String> end_year;
	public JLabel answer;
	public JComboBox<Double>[] hours;
}
