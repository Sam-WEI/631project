package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class AssigningNurseListPanel extends DataListPanel {

	final static String sql = "select e.id, e.name, e.gender, e.type, n.grade, n.year"
			+ " from employee e, nurse n"
			+ " where e.id = n.emp_id"
			+ " order by e.id";
	
	int pid;
	Date dateIn, dateOut;
	
	public AssigningNurseListPanel(int pid, Date dateIn, Date dateOut) {
		super(sql);
		this.pid = pid;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
	}
	
	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(false);
		
		JButton bOK = new JButton("Assign");
		addToControlPanel(bOK);
		bOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int nid = (int) tableModel.getValueAt(row, 0);
					assign(nid, pid, dateIn, dateOut);
				}
				
			}
		});
		
		
	}
	
	private void assign(int nid, int pid, Date dateIn, Date dateOut){
		try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute(String.format("insert into attend_to values (%d, %d, '%s', '%s')", nid, pid, dateIn, dateOut));
			
			JOptionPane.showMessageDialog(this, "Nurse is assigned!");
			Toolbox.closeDialog(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
