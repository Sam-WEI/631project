package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class SurgeryListPanel extends DataListPanel {
	
	final static String sql = "select s.surgeon_id, e.name, s.patient_id, p.name, s.date, s.room_no"
			+ " from surgery s, employee e, patient p"
			+ " where s.surgeon_id = e.id and s.patient_id = p.id";
	
	JCheckBox cbDate;
	JCheckBox cbRoom;
	JCheckBox cbSurg;
	JCheckBox cbPatient;
	
	JDatePicker datepicker;
	JComboBox<Integer> drRoom;
	JComboBox<String> drSurgeon;
	JComboBox<String> drPatient;
	
	LinkedHashMap<String, Integer> mapSurgeon;
	LinkedHashMap<String, Integer> mapPatient;
	
	public SurgeryListPanel() {
		super(sql);
	}
	@Override
	void initUI() {
		super.initUI();
		
		JPanel pTop = new JPanel(new GridLayout(2, 2));
		JPanel pTop1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pTop2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pTop3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pTop4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pTop.add(pTop1);
		pTop.add(pTop2);
		pTop.add(pTop3);
		pTop.add(pTop4);
		
		add(pTop, BorderLayout.NORTH);
		cbDate = new JCheckBox("Date:");
		cbRoom = new JCheckBox("Room:");
		cbSurg = new JCheckBox("Surgeon:");
		cbPatient = new JCheckBox("Patient:");
		pTop1.add(cbDate);
		datepicker = Toolbox.getDatePicker();
		pTop1.add((JDatePickerImpl)datepicker);
		pTop2.add(cbRoom);
		drRoom = new JComboBox<>();
		pTop2.add(drRoom);
		
		pTop3.add(cbSurg);
		drSurgeon = new JComboBox<>();
		pTop3.add(drSurgeon);
		
		pTop4.add(cbPatient);
		drPatient = new JComboBox<>();
		pTop4.add(drPatient);
		
		generateControlPanel(true);
		
		
		populateSurgeonCombo();
		populateRoomCombo();
		populatePatientCombo();
		
		datepicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbDate.isSelected()){
					refresh();
				}
			}
		});
		
		drRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbRoom.isSelected()){
					refresh();
				}
			}
		});
		
		drPatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbPatient.isSelected()){
					refresh();
				}
			}
		});
		
		drSurgeon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbSurg.isSelected()){
					refresh();
				}
			}
		});
		
		
		ActionListener checkboxLis = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		};
		
		cbDate.addActionListener(checkboxLis);
		cbRoom.addActionListener(checkboxLis);
		cbPatient.addActionListener(checkboxLis);
		cbSurg.addActionListener(checkboxLis);
		
	}
	
	private void refresh(){
		String sql2 = sql;
		
		if(cbDate.isSelected()){
			Date date = Toolbox.getDateFromDatePicker(datepicker);
			sql2 += " and s.date = '" + date + "'";
		}
		
		if(cbRoom.isSelected()){
			sql2 += " and s.room_no = " + (int)drRoom.getSelectedItem();
		}
		
		if(cbSurg.isSelected()){
			int id = mapSurgeon.get(drSurgeon.getSelectedItem());
			sql2 += " and s.surgeon_id = " + id;
		}
		
		if(cbPatient.isSelected()){
			int id = mapPatient.get(drPatient.getSelectedItem());
			sql2 += " and s.patient_id = " + id;
		}
		
		getDataFromDBAndShowInList(sql2);
	}
	
	
	private void populateSurgeonCombo(){
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select e.name, e.id from surgeon s, employee e where s.emp_id = e.id");
			mapSurgeon = new LinkedHashMap<>();
			while(rs.next()){
				mapSurgeon.put(rs.getString(1)+"["+rs.getInt(2)+"]", rs.getInt(2));
			}
			String[] sss = mapSurgeon.keySet().toArray(new String[0]);
			DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>(sss);
			drSurgeon.setModel(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateRoomCombo(){
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select no from op_room");
			Vector<Integer> items = new Vector<>();
			while(rs.next()){
				items.add(rs.getInt(1));
			}
			DefaultComboBoxModel<Integer> m = new DefaultComboBoxModel<>(items);
			drRoom.setModel(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populatePatientCombo(){
		try {
			String sql = "SELECT p.name, p.id FROM diagnosis d"
					+ " LEFT JOIN consultation c ON d.consultation_id = c.id"
					+ " LEFT JOIN patient p ON p.id = c.patient_id"
					+ " LEFT JOIN illness i ON i.code = d.illness_code";
			
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			mapPatient = new LinkedHashMap<>();
			while(rs.next()){
				mapPatient.put(rs.getString(1)+"["+rs.getInt(2)+"]", rs.getInt(2));
			}
			String[] sss = mapPatient.keySet().toArray(new String[0]);
			DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>(sss);
			drPatient.setModel(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
