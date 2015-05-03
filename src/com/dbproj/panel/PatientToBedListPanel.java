package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dbproj.util.DBToolbox;

public class PatientToBedListPanel extends DataListPanel {

	
	static String sql = "SELECT p.id, p.name, p.date_of_birth, p.gender, a.room_no, a. bed_no, a.date_in, a.date_out FROM patient p"
			+ " LEFT JOIN admitted_to_bed a"
			+ " ON p.id = a.patient_id"
			+ " ORDER BY p.id";
	
	public PatientToBedListPanel() {
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
				if(row != -1){
					Object pID = table.getValueAt(row, 0);
					int patientID = (int) pID ;
					removePatientFromBed(patientID);
					getDataFromDBAndShowInList();
				}
			}
		});
		addToControlPanel(btnRemoveBed);
		
		JButton btnAssignBed = new JButton("Assign Bed");
		btnAssignBed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					Object pID = table.getValueAt(row, 0);
					int patientID = (int) pID ;
					String patientName = (String) table.getValueAt(row, 1);
					removePatientFromBed(patientID);
					getDataFromDBAndShowInList();
					
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PatientToBedListPanel.this);
					JDialog dialog = new JDialog(topFrame, "Choose A Bed For Patient \"" + patientName + "\"", true);
					dialog.setSize(600, 400);
					dialog.setResizable(false);
					dialog.setLocationRelativeTo(topFrame);
					dialog.setContentPane(new AssignBedPanel(patientID));
					dialog.setVisible(true);
				}
			}
		});
		addToControlPanel(btnAssignBed);
		
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
