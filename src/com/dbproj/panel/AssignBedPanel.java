package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class AssignBedPanel extends DataListPanel {

	static String sql = "SELECT cb.clinic_name, cb.room_no, cb.bed_no FROM clinic_bed cb "
			+ "WHERE (cb.clinic_name, cb.room_no, cb.bed_no) "
			+ "NOT IN (SELECT clinic_name, room_no, bed_no FROM admitted_to_bed WHERE date_out >= '%s' AND date_in <= '%s')";
	
	JDatePicker jdpFrom;
	JDatePicker jdpTo;
	int patientID;
	
	private static Date now = new Date(System.currentTimeMillis());
	
	public AssignBedPanel(int patientID) {
		super(String.format(sql, now, now));
		this.patientID = patientID;
	}

	@Override
	void initUI() {
		super.initUI();
		JDateComponentFactory jdcf = new JDateComponentFactory(); 
		jdpFrom = jdcf.createJDatePicker();
		jdpTo = jdcf.createJDatePicker();
		
		JPanel pDateRangePicker = new JPanel(new FlowLayout());
		
		pDateRangePicker.add(new JLabel("Will Stay From:"));
		pDateRangePicker.add((JDatePickerImpl)jdpFrom);
		pDateRangePicker.add(new JLabel("To:"));
		pDateRangePicker.add((JDatePickerImpl)jdpTo);
		
		
		jdpFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date from = Toolbox.getDateFromDatePicker(jdpFrom);
				Date to = Toolbox.getDateFromDatePicker(jdpTo);
				if(from.after(to)){
					Toolbox.setDateToDatePicker(jdpTo, from);
					to = from;
				}
				String sql2 = String.format(sql, from, to);
				getDataFromDBAndShowInList(sql2);
			}
		});
		
		
		jdpTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date from = Toolbox.getDateFromDatePicker(jdpFrom);
				Date to = Toolbox.getDateFromDatePicker(jdpTo);
				if(from.after(to)){
					Toolbox.setDateToDatePicker(jdpFrom, to);
					from = to;
				}
				String sql2 = String.format(sql, from, to);
				getDataFromDBAndShowInList(sql2);
			
			}
		});
		
		add(pDateRangePicker, BorderLayout.NORTH);
		
		generateControlPanel(false);
		JButton bAssign = new JButton("Assign");
		addToControlPanel(bAssign);
		bAssign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertAssignment();
			}
		});
	}
	
	
	private void insertAssignment(){
		int row = table.getSelectedRow();
		if(row != -1){
			TableModel model = table.getModel();
			String cli_name = (String) model.getValueAt(row, 0);
			int room_no = (int) model.getValueAt(row, 1);
			String bed_no = (String) model.getValueAt(row, 2);
			
			String assignSQL = String.format("INSERT INTO admitted_to_bed VALUES (%d, '%s', %d, '%s', '%s', '%s')", 
					patientID, cli_name, room_no, bed_no, Toolbox.getDateFromDatePicker(jdpFrom), Toolbox.getDateFromDatePicker(jdpTo));
			
			try {
				Statement st = DBToolbox.connection.createStatement();
				st.execute(assignSQL);
				JOptionPane.showMessageDialog(AssignBedPanel.this, "Bed is successfully assigned!", "Done", JOptionPane.PLAIN_MESSAGE);
				
				Toolbox.closeDialog(AssignBedPanel.this);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
