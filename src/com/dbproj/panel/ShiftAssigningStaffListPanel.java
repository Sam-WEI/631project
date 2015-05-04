package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import com.dbproj.util.Toolbox;

public class ShiftAssigningStaffListPanel extends DataListPanel {

	final static String sql = "select type, name, id, gender from employee order by type, name";
	
	ShiftScheduleListPanel father;
	
	public ShiftAssigningStaffListPanel(ShiftScheduleListPanel father) {
		super(sql);
		this.father = father;
	}
	
	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(false);
		
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JButton ok = new JButton("Assign");
		JButton cancel = new JButton("Cancel");
		
		addToControlPanel(ok);
		addToControlPanel(cancel);
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				if(rows != null){
					int[] emp_ids = new int[rows.length];
					for(int i = 0; i < rows.length; i++){
						emp_ids[i] = (int) table.getModel().getValueAt(rows[i], 2);
					}
					father.insertShift(emp_ids);
					
					Toolbox.closeDialog(ShiftAssigningStaffListPanel.this);
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolbox.closeDialog(ShiftAssigningStaffListPanel.this);
			}
		});
	}

}
