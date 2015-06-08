import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;


public class hours_updater implements ActionListener {
	public hours_updater(JComboBox<Double> hours, JComboBox<Double>[] days){
		this.hours = hours;
		this.days = days;
	}
	
	public void actionPerformed(ActionEvent e){
		int index = hours.getSelectedIndex();
		for(int i = 0; i < 7; i++){
			days[i].setSelectedIndex(index);
		}
	}
	
	private JComboBox<Double>[] days;
	private JComboBox<Double> hours;
}
