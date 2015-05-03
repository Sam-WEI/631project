package com.dbproj.panel;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.dbproj.util.DBToolbox;

/**
 * pass in an sql and you can get a panel with list populated from that sql
 */
public class DataListPanel extends MyPanel {
	
	int columnCount;
	JTable table;
	Vector<Vector<Object>> dataArrayList = new Vector<>();
	DefaultTableModel tableModel;
	Vector<String> columnName;
	
	String sql;
	
	public DataListPanel(String sql){
		super();
		this.sql = sql;
		initUI();
	}
	
	
	
	void initUI() {
		setLayout(new BorderLayout());
		table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(scrollPane, BorderLayout.CENTER);
		getDataFromDB();
		
		tableModel = new DefaultTableModel(dataArrayList, columnName);
		table.setModel(tableModel);
		
	}
	
	public void getDataFromDB(){
		dataArrayList = new Vector<>();
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			ResultSetMetaData metadata = rs.getMetaData();
			columnCount = metadata.getColumnCount();
			columnName = new Vector<>(columnCount);
			
			for(int i = 1; i <= columnCount; i++){
				columnName.add(metadata.getColumnName(i));
			}
			
			Vector<Object> columns;
			while(rs.next()){
				/*int id = rs.getObject(1, Integer.class);
				int ssn = rs.getObject(2, Integer.class);
				String name = rs.getObject(3, String.class);
				String gender = rs.getObject(4, String.class);
				String type = rs.getObject(5, String.class);
				int phone = rs.getObject(6, Integer.class);
				int salary = rs.getObject(7, Integer.class);
				String address = rs.getObject(8, String.class);
				
				Employee e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSsn(ssn);
				e.setType(type);
				e.setGender(gender);
				e.setPhone(phone);
				e.setSalary(salary);
				e.setAddress(address);
				
				dataArrayList.add(e);*/
				columns = new Vector<>(columnCount);
				for(int i = 1; i <= columnCount; i++){
					columns.add(rs.getObject(i));
				}
				
				dataArrayList.add(columns);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
