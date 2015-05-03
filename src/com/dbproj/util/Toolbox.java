package com.dbproj.util;

import java.awt.Container;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JDialog;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.util.JDatePickerUtil;

public class Toolbox {

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
}
