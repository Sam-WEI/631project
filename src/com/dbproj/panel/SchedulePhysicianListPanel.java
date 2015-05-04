package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class SchedulePhysicianListPanel extends DataListPanel {

	final static String sql_prefix = "SELECT ss.date, e.name, e.id, e.type, ph.specialty"
			+ " FROM shift_schedule ss, employee e, physician ph";
	
	final static String sql_without_specific_date = sql_prefix
			+ " WHERE ss.emp_id = e.id AND ph.emp_id = e.id"
			+ " ORDER BY ss.date, e.name";
	
	final static String sql_with_specific_date = sql_prefix
			+ " WHERE ss.date = '%s' AND ss.emp_id = e.id AND ph.emp_id = e.id"
			+ " ORDER BY ss.date, e.name";
	
	JDatePicker datePicker;
	JCheckBox checkbox;
	
	int patientID;
	
	public SchedulePhysicianListPanel(int patientID) {
		super(sql_without_specific_date);
		this.patientID = patientID;
	}
	@Override
	void initUI() {
		super.initUI();
		
		JPanel panelTop = new JPanel(new FlowLayout());
		datePicker = Toolbox.getDatePicker();
		checkbox = new JCheckBox("Specify time");
		checkbox.setSelected(false);
		
		panelTop.add(checkbox);
		panelTop.add((JDatePickerImpl)datePicker);
		
		add(panelTop, BorderLayout.NORTH);
		
		checkbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					getDataFromDBAndShowInList(String.format(sql_with_specific_date, Toolbox.getDateFromDatePicker(datePicker)));
				} else {
					getDataFromDBAndShowInList(sql_without_specific_date);
				}
			}
		});
		
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkbox.isSelected()){
					getDataFromDBAndShowInList(String.format(sql_with_specific_date, Toolbox.getDateFromDatePicker(datePicker)));
				}
			}
		});
		
		generateControlPanel(true);
		JButton OK = new JButton("Schedule");
		addToControlPanel(OK);
		OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int phyID = (int) tableModel.getValueAt(row, 2);
					Date date = (Date) tableModel.getValueAt(row, 0);
					schedule(phyID, patientID, date);
					
				}
			}
		});
		
	}
	
	void schedule(int phyID, int pID, Date date){
		try {
			PreparedStatement st = DBToolbox.connection.prepareStatement("insert into consultation values(?,?,?,?)");
			st.setInt(1, phyID);
			st.setInt(2, pID);
			st.setDate(3, date);
			st.setString(4, "");
			st.execute();
			
			JOptionPane.showMessageDialog(this, "Consultation is scheduled!", "Done", JOptionPane.PLAIN_MESSAGE);
			Toolbox.closeDialog(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
