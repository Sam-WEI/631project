package com.dbproj.util;

import java.awt.Container;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.util.JDatePickerUtil;

import com.dbproj.panel.PatientListPanel;
import com.dbproj.panel.PatientViewerPanel;

public class Toolbox {

	public static JDatePicker getDatePicker(){
		JDateComponentFactory jdcf = new JDateComponentFactory(); 
		return jdcf.createJDatePicker();
	}
	
	public static Date getDateFromDatePicker(JDatePicker picker) {
		Calendar selectedValue = (Calendar) picker.getModel().getValue();
		return new Date(selectedValue.getTimeInMillis());
	}
	
	public static void setDateToDatePicker(JDatePicker picker, Date date) {
		picker.getModel().setDate(1900 + date.getYear(), date.getMonth(), date.getDate());
	}
	
	public static void closeDialog(Container c){
		while(!((c = c.getParent()) instanceof JDialog));
		if(c != null){
			((JDialog)c).dispose();
		}
	}
	
	public static JDialog createDialog(JComponent c, String title, int width, int height){
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(c);
		
		JDialog dialog = new JDialog(topFrame, title, true);
		dialog.setSize(width, width);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(topFrame);
		
		return dialog;
	}
}
