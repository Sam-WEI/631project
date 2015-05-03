package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.dbproj.util.DBToolbox;

public class RoomBedListPanel extends DataListPanel {

	static String sql = "SELECT CB.room_no, CB.bed_no, CB.unit, P.name, A.patient_id, A.date_in, A.date_out"
			+ " FROM clinic_bed CB"
			+ " LEFT JOIN admitted_to_bed A"
			+ " ON CB.room_no  = A.room_no AND CB.bed_no = A.bed_no"
			+ " LEFT JOIN patient P ON A.patient_id = P.id"
			+ " ORDER BY CB.room_no, CB.bed_no";
	
	public RoomBedListPanel() {
		super(sql);
	}

	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		JButton btnRemove = new JButton("Remove Patient");
		controlPanel.add(btnRemove);
		
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					Object pID = table.getValueAt(row, 4);
					if (pID != null) {
						int patientID = (int) pID;

						removePatientFromBed(patientID);

						getDataFromDBAndShowInList();
					}
				}

			}
			
		});
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
