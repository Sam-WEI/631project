package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dbproj.util.DBToolbox;

public class PatientToBedList extends DataListPanel {

	
	static String sql = "SELECT p.id, p.name, p.date_of_birth, p.gender, a.room_no, a. bed_no, a.date_in, a.date_out FROM patient p"
			+ " LEFT JOIN admitted_to_bed a"
			+ " ON p.id = a.patient_id"
			+ " ORDER BY p.id";
	
	public PatientToBedList() {
		super(sql);
	}
	
	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		JButton btnRemoveBed = new JButton("Remove Patient From Bed");
		btnRemoveBed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Object pID = table.getValueAt(row, 0);
				if(pID != null){
					int patientID = (int) pID ;
					removePatientFromBed(patientID);
					getDataFromDBAndShowInList();
				}
			}
		});
		addToControlPanel(btnRemoveBed);
		
		
	}
	
	private void removePatientFromBed(int patientID) {
		try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute("delete from admitted_to_bed WHERE patient_id = " + patientID);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
