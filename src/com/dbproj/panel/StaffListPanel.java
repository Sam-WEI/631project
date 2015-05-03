package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.dbproj.util.DBToolbox;

public class StaffListPanel extends DataListPanel {

	JPanel controlPanel;
	JButton btnAdd;
	JButton btnRemove;
	
	
	public StaffListPanel(String sql) {
		super(sql);
	}

	@Override
	protected void initUI(){
		super.initUI();
		
		generateControlPanel(true);
		
		btnAdd = new JButton("Add");
		btnRemove = new JButton("Remove");
		addToControlPanel(btnAdd);
		addToControlPanel(btnRemove);
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(StaffListPanel.this);
				JDialog dialog = new JDialog(topFrame, "Add New Staff", true);
				dialog.setSize(400, 600);
				dialog.setLocationRelativeTo(topFrame);
				
				dialog.add(new JScrollPane(new AddStaffPanel(null)));
				dialog.setVisible(true);
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = table.getSelectedRow();
				if(selected >= 0){
					int id = (int) tableModel.getValueAt(selected, 0);
					removeTuple(id);
					tableModel.removeRow(selected);
					
				}
				
			}
		});
		
	}
	
	 private void removeTuple(int id){
		 try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute("delete from employee where id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	 }
	
}
