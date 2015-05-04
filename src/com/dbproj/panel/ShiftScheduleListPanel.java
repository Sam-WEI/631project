package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.dbproj.util.DBToolbox;

public class ShiftScheduleListPanel extends DataListPanel {

	static String sql = "SELECT s.date, s.begin_time, s.end_time, e.id, e.name"
			+ " FROM shift s LEFT JOIN shift_schedule ss"
			+ " ON ss.date = s.date AND ss.begin_time = s.begin_time AND ss.end_time = s.end_time"
			+ " LEFT JOIN employee e"
			+ " ON ss.emp_id = e.id ORDER BY s.date, s.begin_time, s.end_time";
	
	
	int[] selectedRows;
	
	public ShiftScheduleListPanel() {
		super(sql);
	}

	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		JButton bAssign = new JButton("Assign Employee");
		addToControlPanel(bAssign);
		
		JButton bRemove = new JButton("Remove Shift");
		addToControlPanel(bRemove);
		
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		bAssign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedRows = table.getSelectedRows();
				if(selectedRows != null){
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ShiftScheduleListPanel.this);
					JDialog dialog = new JDialog(topFrame, "Assign Employee", true);
					dialog.setContentPane(new ShiftAssigningStaffListPanel(ShiftScheduleListPanel.this));
					dialog.setSize(500, 600);
					dialog.setLocationRelativeTo(topFrame);
					dialog.setResizable(false);
					dialog.setVisible(true);
					
				}
			}
		});
		
		bRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				if(rows != null){
					removeShift(rows);
				}
			}
		});
		
	}
	
	
	
	public void insertShift(int[] empID) {
		try {
			PreparedStatement ps = DBToolbox.connection.prepareStatement("INSERT INTO shift_schedule VALUES (?, ?, ?, ?)");
			Date date;
			Time begin;
			Time end;
			for(int row : selectedRows){
				// logic bug exists here. When shifts(date, begin_time, end_time) in selected rows duplicate, inserting duplicates 
				date = (Date) tableModel.getValueAt(row, 0);
				begin = (Time) tableModel.getValueAt(row, 1);
				end = (Time) tableModel.getValueAt(row, 2);
				ps.setDate(1, date);
				ps.setTime(2, begin);
				ps.setTime(3, end);
				for(int id : empID){
					ps.setInt(4, id);
					ps.addBatch();
				}
			}
			ps.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getDataFromDBAndShowInList();
	}
	
	void removeShift(int[] rows){
		try {
			PreparedStatement ps = DBToolbox.connection.prepareStatement("delete from shift_schedule where date = ? and begin_time = ? "
					+ " and end_time = ? and emp_id = ?");
			Date date;
			Time begin;
			Time end;
			int emp_id;
			for(int row : rows){
				date = (Date) tableModel.getValueAt(row, 0);
				begin = (Time) tableModel.getValueAt(row, 1);
				end = (Time) tableModel.getValueAt(row, 2);
				emp_id = (int) tableModel.getValueAt(row, 3);
				ps.setDate(1, date);
				ps.setTime(2, begin);
				ps.setTime(3, end);
				ps.setInt(4, emp_id);
				ps.addBatch();
			}
			ps.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getDataFromDBAndShowInList();
	}
	
}
