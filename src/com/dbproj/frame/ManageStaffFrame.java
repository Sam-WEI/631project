package com.dbproj.frame;

import javax.swing.JTabbedPane;

import com.dbproj.module.StaffType;

public class ManageStaffFrame extends MyFrame {
	
	JTabbedPane tabbedPane;  
	
	
	
	@Override
	protected void init() {
		super.init();
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		tabbedPane.add("AAA", new AddStaffPanel(StaffType.Nurse));
	}
	
	
	@Override
	protected String title() {
		return "Staff Management";
	}
}
