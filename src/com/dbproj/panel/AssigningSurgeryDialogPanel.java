package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class AssigningSurgeryDialogPanel extends DataListPanel {

	final static String sql = "select e.id, e.name"
			+ " from employee e, surgeon s"
			+ " where e.id = s.emp_id and e.id NOT IN (SELECT surgeon_id FROM surgery WHERE date = '%s')";
	int pid;
	
	JDatePicker datepicker;
	JComboBox<Integer> combobox;
	
	static final Date cur = new Date(System.currentTimeMillis());
	
	public AssigningSurgeryDialogPanel(int pid) {
		super(String.format(sql, cur));
		this.pid = pid;
	}
	
	@Override
	void initUI() {
		Vector<String> colName =  new Vector<>();
		colName.add("SurgeonID");
		colName.add("SurgeonName");
		setColumnName(colName);
		
		super.initUI();
		
		generateControlPanel(false);
		
		JButton bBook = new JButton("Book");
		addToControlPanel(bBook);
		bBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int surid = (int) tableModel.getValueAt(row, 0);
					Date d = Toolbox.getDateFromDatePicker(datepicker);
					int room = (int) combobox.getSelectedItem();
					book(surid, pid, d, room);
				}
			}
		});
		
		
		JPanel pTop = new JPanel(new FlowLayout());
		add(pTop, BorderLayout.NORTH);
		pTop.add(new Label("Date:"));
		datepicker = Toolbox.getDatePicker();
		pTop.add((JDatePickerImpl)datepicker);
		
		datepicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = Toolbox.getDateFromDatePicker(datepicker);
				getDataFromDBAndShowInList(String.format(sql, date));
			}
		});
		
		
		pTop.add(new Label("Room:"));
		combobox = new JComboBox<>();
		pTop.add(combobox);
		
		getOpRooms();
		
	}
	
	private void book(int surgid, int pid, Date date, int roomno){
		try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute(String.format("insert into surgery (surgeon_id, patient_id, date, room_no) values (%d, %d, '%s', %d)", surgid, pid, date, roomno));
			
			JOptionPane.showMessageDialog(this, "Surgery is booked!");
			Toolbox.closeDialog(this);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getOpRooms(){
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select * from op_room");
			Vector<Integer> rooms = new Vector<>();
			while(rs.next()){
				rooms.add(rs.getInt(1));
			}
			combobox.setModel(new DefaultComboBoxModel<>(rooms));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
