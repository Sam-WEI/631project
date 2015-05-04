package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.dbproj.util.Toolbox;

public class AssignSurgeryPanel extends DataListPanel {

	final static String sql = "SELECT p.id, p.name, p.gender, i.description, d.comment FROM diagnosis d"
			+ " LEFT JOIN consultation c ON d.consultation_id = c.id"
			+ " LEFT JOIN patient p ON p.id = c.patient_id"
			+ " LEFT JOIN illness i ON i.code = d.illness_code";
	
	public AssignSurgeryPanel() {
		super(sql);
	}
	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		JButton bAssignSurgery = new JButton("Book Surgery...");
		addToControlPanel(bAssignSurgery);
		bAssignSurgery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int pid = (int) tableModel.getValueAt(row, 0);
					
					JDialog dialog = Toolbox.createDialog(AssignSurgeryPanel.this, "Book Surgery", 500, 400);
					dialog.setContentPane(new AssigningSurgeryDialogPanel(pid));
					dialog.setVisible(true);
					
					
				}
			}
		});
	}
}
