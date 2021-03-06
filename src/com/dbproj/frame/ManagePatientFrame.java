package com.dbproj.frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.dbproj.panel.ConsultationListPanel;
import com.dbproj.panel.RegisterPatientPanel;
import com.dbproj.panel.PatientListPanel;
import com.dbproj.panel.PatientViewerPanel;
import com.dbproj.util.DBToolbox;


public class ManagePatientFrame extends MyFrame {
	
	PatientViewerPanel viewPatientPanel;
	
	@Override
	protected void init() {
		super.init();
		
		setLayout(new BorderLayout());
		
		Border border = BorderFactory.createEtchedBorder();
		JPanel panelLeft = new JPanel(new GridLayout(10, 1));
		panelLeft.setBorder(border);
		add(panelLeft, BorderLayout.WEST);
		
		
		JButton btnReg = new JButton("Register New Patient");
		panelLeft.add(btnReg);
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(border);
		add(tabbedPane, BorderLayout.CENTER);
		
		final JPanel panelViewerTab = new JPanel(new GridLayout());
		tabbedPane.setBorder(border);
		panelViewerTab.setLayout(new BorderLayout());
		
		JPanel panelPNO = new JPanel(new FlowLayout());
		panelPNO.add(new JLabel("Input Patient No: "));
		
		final JTextField tfPNo = new JTextField(20);
		panelPNO.add(tfPNo);
		
		
		JButton btnEnter = new JButton("Search");
		btnEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tmp = tfPNo.getText().trim();
				int id = Integer.parseInt(tmp);
				viewPatientPanel.showPatientWithID(id);
			}
		});
		panelPNO.add(btnEnter);
		
		panelViewerTab.add(panelPNO, BorderLayout.NORTH);
		
		viewPatientPanel = new PatientViewerPanel(false);
		panelViewerTab.add(new JScrollPane(viewPatientPanel), BorderLayout.CENTER);
		
		queryPatientFromDB();		
		
		
		
		final JPanel panelReg = new JPanel(new BorderLayout());
		panelReg.setBorder(border);
		
		panelReg.add(new JScrollPane(new RegisterPatientPanel()), BorderLayout.CENTER);
		
		
		
		tabbedPane.add("Patient List", new PatientListPanel("select * from patient"));
		tabbedPane.add("Search Patient", panelViewerTab);
		tabbedPane.add("Register New Patient", panelReg);
		tabbedPane.add("Consultation List", new ConsultationListPanel());
		
		
		
		btnReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
	}
	
	
	private void queryPatientFromDB(){
		ResultSet rs;
		try {
			Statement statement = DBToolbox.connection.createStatement();
			rs = statement.executeQuery("select * from patient");
			
			while(rs.next()){
				System.out.println(rs.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected String title() {
		return "Patient Management";
	}

	
}
