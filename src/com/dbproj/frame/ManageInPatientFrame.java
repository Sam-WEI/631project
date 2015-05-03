package com.dbproj.frame;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dbproj.panel.DataListPanel;
import com.dbproj.panel.PatientToBedListPanel;
import com.dbproj.panel.RoomBedListPanel;


public class ManageInPatientFrame extends MyFrame {

	JTabbedPane tabbedPane;  
	JPanel Panel;
	
	public ManageInPatientFrame() {
		super();
	}
	
	@Override
	protected void init() {
		super.init();
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		Panel = new JPanel(new BorderLayout());
		
		tabbedPane.add("Room/Bed", new RoomBedListPanel());
		tabbedPane.add("Assign Patient To Bed", new PatientToBedListPanel());
		
		
	}
	
	@Override
	protected String title() {
		return "In-Patient Management";
	}
}
