package com.dbproj.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;

public class RegisterPatientPanel extends PatientViewerPanel {

	JButton btnSave;
	
	public RegisterPatientPanel() {
		super(true);
	}

	@Override
	protected void initUI() {
		super.initUI();
		
		tfNo.setText(getNextEmpNo() + "");
		tfNo.setEditable(false);
		
		btnSave = new JButton("Save");
		JPanel btnPanel = new JPanel(new FlowLayout());
		btnPanel.add(btnSave);
		add(btnPanel);
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToSQL();
			}
		});
	}
	
	private void saveToSQL(){
		try {
			String gender = (rbGenderM.isSelected()? "M" : "F");
			String type;
			if(rbBloodA.isSelected()){
				type = "A";
			} else if(rbBloodB.isSelected()){
				type = "B";
			} else if(rbBloodAB.isSelected()){
				type = "AB";
			} else {
				type = "O";
			}
			
			int id = Integer.valueOf(tfNo.getText().trim());
			
			PreparedStatement st = DBToolbox.connection.prepareStatement("insert into patient values(?, ?, ?, ?, ?, ?, ?)");
			st.setInt(1, id);
			st.setInt(2, Integer.valueOf(tfSSN.getText().trim()));
			st.setString(3, tfName.getText().trim());
			Calendar selectedValue = (Calendar) datePicker.getModel().getValue();
			
			st.setDate(4, new Date(selectedValue.getTimeInMillis()));
			st.setString(5, type);
			st.setString(6, tfAddr.getText().trim());
			st.setString(7, gender);
			
			st.execute();
			
			JOptionPane.showMessageDialog(this, "Registration done!", "Done", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getNextEmpNo(){
		int no = -1;
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select max(id) from patient");
			if(rs.next()){
				no = rs.getInt(1) + 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return no;
	}
}
