package com.dbproj.frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dbproj.util.DBToolbox;


public class MainFrame extends MyFrame {
	
	
	public MainFrame() throws HeadlessException {
		super();
		
	}
	
	@Override
	protected void init(){
		super.init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBToolbox.closeConnection();
			}
		});
		
		
		setLayout(new GridLayout(2, 1));

		ImageIcon image = new ImageIcon("image\\main_bg.jpg");
		
		add(new JLabel(image));
		
		JPanel panelLogin = new JPanel();
		add(panelLogin);
		panelLogin.setLayout(new GridLayout(1, 3));
		JButton btn = new JButton("Manage Patient");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManagePatientFrame().setVisible(true);
			}
		});
		btn = new JButton("Manage In-Patient");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageInPatientFrame().setVisible(true);
			}
		});
		btn = new JButton("Manage Staff");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageStaffFrame().setVisible(true);
			}
		});
	} 
	
	@Override
	protected int[] getWH() {
		return new int[]{1024, 768};
	}
	
	@Override
	protected String title() {
		return "NewarkMedicalAssociates";
	}
}
