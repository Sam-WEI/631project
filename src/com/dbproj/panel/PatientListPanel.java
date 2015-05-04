package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.dbproj.util.Toolbox;

public class PatientListPanel extends DataListPanel {

	JPanel controlPanel;
	JButton btnView;
	JButton btnRemove;
	
	
	public PatientListPanel(String sql) {
		super(sql);
	}

	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		btnView = new JButton("View");
		addToControlPanel(btnView);
		
		
		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int idx = (int) table.getModel().getValueAt(row, 0);
					
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PatientListPanel.this);
					
					JDialog dialog = new JDialog(topFrame, "View Patient", true);
					dialog.setSize(400, 600);
					dialog.setLocationRelativeTo(topFrame);
					PatientViewerPanel viewer = new PatientViewerPanel(false);
					viewer.showPatientWithID(idx);
					dialog.add(new JScrollPane(viewer));
					dialog.setVisible(true);
					
				}
			}
		});
		
		JButton jSchedule = new JButton("Schedule Consultation");
		addToControlPanel(jSchedule);
		
		jSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					JDialog dialog = Toolbox.createDialog(PatientListPanel.this, "Schedule Consulation", 600, 400);
					dialog.setContentPane(new SchedulePhysicianListPanel((int)tableModel.getValueAt(row, 0)));
					dialog.setVisible(true);
				}

			}
		});
		
	}
	
}
