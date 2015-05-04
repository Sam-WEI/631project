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

	
	static String sql = "SELECT p.id, p.name, p.date_of_birth, p.gender, d.type, d.comment FROM patient p, diagnosis d, consultation c"
			+ " WHERE d.type = 'H' AND d.consultation_id = c.id"
			+ " AND c.patient_id = p.id"
			+ " AND p.id NOT IN (SELECT patient_id FROM admitted_to_bed)";
	
	public PatientToBedListPanel() {
		super(sql);
	}
	
	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		
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
