package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;

import com.dbproj.util.DBToolbox;

public class NurseAssignmentListPanel extends DataListPanel {

	final static String sql = "select p.id, p.name, e.id, e.name, at.begin_date, at.end_date"
			+ " from patient p, employee e, attend_to at"
			+ " where at.nurse_id = e.id and at.patient_id = p.id"
			+ " order by p.id";
	
	public NurseAssignmentListPanel() {
		super(sql);
	}
	
	@Override
	void initUI() {
		Vector<String> columnName = new Vector<>();
		columnName.add("PatientID");
		columnName.add("PatientName");
		columnName.add("NurseID");
		columnName.add("NurseName");
		columnName.add("DateFrom");
		columnName.add("DateTo");
		setColumnName(columnName);
		super.initUI();
		
		generateControlPanel(true);
		
		JButton bDel= new JButton("Unassign Nurse");
		addToControlPanel(bDel);
		bDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int pid = (int) tableModel.getValueAt(row, 0);
					int nid = (int) tableModel.getValueAt(row, 2);
					
					Date from = (Date) tableModel.getValueAt(row, 4);
					Date to = (Date) tableModel.getValueAt(row, 5);
					unassign(pid, nid, from, to);
				}
			}
		});
	}
	
	private void unassign(int pid, int nid, Date from, Date to){
		try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute(String.format("delete from attend_to where nurse_id = %d "
					+ "and patient_id = %d and begin_date = '%s' and end_date = '%s'", nid, pid, from, to));
			
			getDataFromDBAndShowInList();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
