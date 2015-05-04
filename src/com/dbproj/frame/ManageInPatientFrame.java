package com.dbproj.frame;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dbproj.panel.AssignSurgeryPanel;
import com.dbproj.panel.DataListPanel;
import com.dbproj.panel.NurseAssignmentListPanel;
import com.dbproj.panel.PatientToBedListPanel;
import com.dbproj.panel.InPatientAssignmentListPanel;
import com.dbproj.panel.SurgeryListPanel;


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
		
		tabbedPane.add("Bed Assignment", new InPatientAssignmentListPanel());
		tabbedPane.add("Assign Patient To Bed", new PatientToBedListPanel());
		tabbedPane.add("Nurse Assignment", new NurseAssignmentListPanel());
		tabbedPane.add("Book Surgery", new AssignSurgeryPanel());
		tabbedPane.add("Surgery List", new SurgeryListPanel());
		
	}
	
	@Override
	protected String title() {
		return "In-Patient Management";
	}
}
