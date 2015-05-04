package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.dbproj.util.DBToolbox;

public class ShiftListPanel extends DataListPanel {

	static String sql = "select * from shift order by date, begin_time, end_time";
	
	public ShiftListPanel() {
		super(sql);
	}

	@Override
	void initUI() {
		super.initUI();
		
		generateControlPanel(true);
		
		JButton bPopulateShift = new JButton("Populate Shift!!");
		addToControlPanel(bPopulateShift);
		
		bPopulateShift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(ShiftListPanel.this, "All current shifts will be deleted! Be careful!");
				
				if(option == 0){
					removeAllShift();
					
					Calendar ca = Calendar.getInstance();
					ca.add(Calendar.DATE, -10);
					Date date;
					Time begin;
					Time end;
					for(int i = 0; i < 70; i++){
						ca.add(Calendar.DATE, 1);
						date = new Date(ca.getTimeInMillis());
						begin = new Time(9, 0, 0);
						end = new Time(18, 0, 0);
						
						insertShift(date, begin, end);
						
					}
				}
			}
		});
		
	}
	
	PreparedStatement ps;
	
	private void removeAllShift(){
		Statement st;
		try {
			st = DBToolbox.connection.createStatement();
			st.execute("DELETE FROM shift");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertShift(Date date, Time begin, Time end) {
		try {
			if (ps == null) {
				ps = DBToolbox.connection.prepareStatement("INSERT INTO shift VALUES (?, ?, ?)");
			}
			ps.setDate(1, date);
			ps.setTime(2, begin);
			ps.setTime(3, end);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
