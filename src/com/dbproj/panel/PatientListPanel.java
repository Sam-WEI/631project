package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		controlPanel = new JPanel(new FlowLayout());
		btnView = new JButton("View");
		controlPanel.add(btnView);
		
		add(controlPanel, BorderLayout.SOUTH);
		
		btnView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int idx = (int) table.getModel().getValueAt(row, 0);
					
					JDialog dialog = new JDialog();
					dialog.setTitle("View Patient");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setSize(400, 600);
					PatientViewerPanel viewer = new PatientViewerPanel(false);
					viewer.showPatientWithID(idx);
					dialog.add(new JScrollPane(viewer));
					dialog.setVisible(true);
					
					
				}
				
				
			}
		});
	}
	
}
