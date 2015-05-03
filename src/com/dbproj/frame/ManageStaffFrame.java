package com.dbproj.frame;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dbproj.panel.DataListPanel;
import com.dbproj.panel.StaffListPanel;

public class ManageStaffFrame extends MyFrame {
	
	JTabbedPane tabbedPane;  
	
	JPanel allStaffPanel;
	
	
	@Override
	protected void init() {
		super.init();
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		allStaffPanel = new JPanel(new BorderLayout());
		DataListPanel empListPanel = new DataListPanel("select * from employee");
		allStaffPanel.add(empListPanel, BorderLayout.CENTER);
		
		final ArrayList<StaffListPanel> panelAList = new ArrayList<>();
		panelAList.add(new StaffListPanel("select * from employee"));
		panelAList.add(new StaffListPanel("select * from physician P, employee E where P.emp_id = E.id"));
		panelAList.add(new StaffListPanel("select * from nurse P, employee E where P.emp_id = E.id"));
		panelAList.add(new StaffListPanel("select * from surgeon P, employee E where P.emp_id = E.id"));
		
		tabbedPane.add("Employee List", panelAList.get(0));
		tabbedPane.add("Physician List", panelAList.get(1));
		tabbedPane.add("Nurse List", panelAList.get(2));
		tabbedPane.add("Surgeon List", panelAList.get(3));
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				 int idx =  tabbedPane.getSelectedIndex();
				 panelAList.get(idx).getDataFromDBAndShowInList();
				 
			}
	    });
	}
	
	
	@Override
	protected String title() {
		return "Staff Management";
	}
}
