package com.dbproj.frame;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.dbproj.module.StaffType;
import com.dbproj.panel.MyPanel;

public class AddStaffPanel extends MyPanel {
	
	StaffType type;
	
	JTextField tfNo;
	JTextField tfName;
	JTextField tfSSN;
	JTextField tfDOB;
	JTextField tfAddr;
	JTextField tfPhone;
	JTextField tfSalary;
	
	JRadioButton rbGenderF;
	JRadioButton rbGenderM;
	
	JRadioButton rbTypePhy;
	JRadioButton rbTypeSurg;
	JRadioButton rbTypeNurse;
	JRadioButton rbBloodO;
	
	public AddStaffPanel(StaffType type){
		super();
		this.type = type;
		init();
	}
	
	
	private void init() {
		super.initUI();
		setLayout(new GridLayout(20, 1));
		
		tfNo = new JTextField(20);
		tfName = new JTextField(20);
		tfSSN = new JTextField(20);
		tfDOB = new JTextField(20);
		tfAddr = new JTextField(20);
		tfPhone = new JTextField(20);
		tfSalary = new JTextField(20);
		
		
		add(new JLabel("ID:"));
		add(tfNo);
		
		add(new JLabel("Name:"));
		add(tfName);
		add(new JLabel("SSN:"));
		add(tfSSN);
		
		add(new JLabel("Gender:"));
		JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbGenderF = new JRadioButton("Female");
		rbGenderM = new JRadioButton("Male");
		ButtonGroup bGroupGender = new ButtonGroup();
		bGroupGender.add(rbGenderF);
		bGroupGender.add(rbGenderM);
		gender.add(rbGenderF);
		gender.add(rbGenderM);
		add(gender);
		
		add(new JLabel("Phone:"));
		add(tfPhone);
		
		add(new JLabel("Salary:"));
		add(tfSalary);
		
		add(new JLabel("Type:"));
		JPanel type = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbTypePhy = new JRadioButton("Pysician");
		rbTypeSurg = new JRadioButton("Surgeon");
		rbTypeNurse = new JRadioButton("Nurse");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rbTypePhy);
		bGroup.add(rbTypeSurg);
		bGroup.add(rbTypeNurse);
		
		type.add(rbTypePhy);
		type.add(rbTypeSurg);
		type.add(rbTypeNurse);
		
		add(type);
		
		add(new JLabel("Address:"));
		add(tfAddr);
		
	}
}
