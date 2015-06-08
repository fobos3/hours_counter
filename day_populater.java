import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;


public class day_populater implements ActionListener {
	public day_populater(JComboBox<String> day, JComboBox<String> month, JComboBox<String> year){
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public void actionPerformed(ActionEvent e){
		// if a valid date has not been selected
		if(month.getSelectedIndex() == 0 || this.year.getSelectedIndex() == 0){
			return;
		}
		
		// set the calendar to the specified date
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(GregorianCalendar.MONTH, month.getSelectedIndex()-1);
		cal.set(GregorianCalendar.YEAR, Integer.parseInt((String) year.getSelectedItem()));
		
		// get the number of days
		int days_count = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		for(int i = day.getItemCount()-1; i > days_count; i--){
			day.removeItemAt(i);
		}
		for(int i = day.getItemCount()-1; i < days_count; i++){
			day.addItem(Integer.toString(i+1));
		}
	}
	
	public JComboBox<String> day;
	public JComboBox<String> month;
	public JComboBox<String> year;
}
