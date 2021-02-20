package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dataClasses.Customer;
import misc.FrameArrays;
import misc.FrameStrings;

public class ReportsDebtsPanel extends JPanel {

	private JDatePickerImpl datePicker;
	private JLabel reportsTotalLbl;
	private JComboBox reportsDebtsCbx;
	private JComboBox<Customer> reportsDebtsCustomersCbx;
	private JLabel reportsDebtsCustomersLbl;
	
	/**
	 * Create the panel.
	 */
	public ReportsDebtsPanel() {
		SpringLayout sl_reportsDebtsPanel = new SpringLayout();
		setLayout(sl_reportsDebtsPanel);
		UtilDateModel model = new UtilDateModel();
		model.setValue(new Date());
		JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
		setFontForDatePicker(datePanel);
		AbstractFormatter formatter = new AbstractFormatter() {
			
			private String datePattern = "yyyy-MM-dd";
		    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		     
		    @Override
		    public Object stringToValue(String text) throws ParseException {
		        return dateFormatter.parseObject(text);
		    }
		 
		    @Override
		    public String valueToString(Object value) throws ParseException {
		        if (value != null) {
		            Calendar cal = (Calendar) value;
		            return dateFormatter.format(cal.getTime());
		        }
		         
		        return "";
		    }
		};
		datePicker = new JDatePickerImpl( datePanel, formatter);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.NORTH, datePicker, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.SOUTH, datePicker, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePicker.getJFormattedTextField().setFont(RootFrame.mainFont);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.WEST, datePicker, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.EAST, datePicker, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(datePicker);
		
		reportsTotalLbl = new JLabel(FrameStrings.valueOfStringName("reportstotallbl"));
		sl_reportsDebtsPanel.putConstraint(SpringLayout.NORTH, reportsTotalLbl, (int)(-40*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.WEST, reportsTotalLbl, (int)(-300*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.SOUTH, reportsTotalLbl, (int)(-10*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		reportsTotalLbl.setFont(RootFrame.mainFont);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.EAST, reportsTotalLbl, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsTotalLbl);
		
		reportsDebtsCbx = new JComboBox(FrameArrays.valueOfArrayName("reportsdebtscbx"));
		reportsDebtsCbx.setFont(RootFrame.mainFont);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.NORTH, reportsDebtsCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.SOUTH, reportsDebtsCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.WEST, reportsDebtsCbx, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.EAST, reportsDebtsCbx, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsDebtsCbx);
		
		reportsDebtsCustomersCbx = new JComboBox<Customer>();
		reportsDebtsCustomersCbx.setFont(RootFrame.mainFont);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.NORTH, reportsDebtsCustomersCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.WEST, reportsDebtsCustomersCbx, (int)(20*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.SOUTH, reportsDebtsCustomersCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.EAST, reportsDebtsCustomersCbx, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.WEST, reportsDebtsCbx);
		add(reportsDebtsCustomersCbx);
		
		reportsDebtsCustomersLbl = new JLabel(FrameStrings.valueOfStringName("reportsdebtscustomerlbl"));
		reportsDebtsCustomersLbl.setFont(RootFrame.mainFont);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.NORTH, reportsDebtsCustomersLbl, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.SOUTH, reportsDebtsCustomersLbl, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.WEST, reportsDebtsCustomersLbl, (int)(20*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsDebtsPanel.putConstraint(SpringLayout.EAST, reportsDebtsCustomersLbl, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.WEST, reportsDebtsCbx);
		add(reportsDebtsCustomersLbl);
	}
	
	private void setFontForDatePicker(Container container) {
		Component[] cs = container.getComponents();
		for(Component c:cs) {
			if(c instanceof JPanel) {
				c.setSize((int)(((int)(c.getWidth()*2))*RootFrame.sizeMultiplier), (int)(((int)(c.getHeight()*2))*RootFrame.sizeMultiplier));
				c.repaint();
			}
			if(c instanceof Container) {
				setFontForDatePicker((Container)c);
			}
			else {
				c.setFont(new Font("Arial",Font.BOLD,(int)(14*RootFrame.sizeMultiplier)));
			}
		}
		container.setFont(new Font("Arial",Font.BOLD,(int)(14*RootFrame.sizeMultiplier)));
		
	}
	
	public void setComboBoxValues(Collection<Customer> l) {
		reportsDebtsCustomersCbx.removeAllItems();
		reportsDebtsCustomersCbx.addItem(null);
		for(Customer c:l) {
			reportsDebtsCustomersCbx.addItem(c);
		}
		
	}
	public Object[] getValues() {
		Object[] returnable = new Object[3];
		returnable[0]=((Date) datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		returnable[1]=reportsDebtsCbx.getSelectedIndex();
		returnable[2]=reportsDebtsCustomersCbx.getSelectedItem();
		return returnable;
	}
	
	public void setTotal(double total) {
		reportsTotalLbl.setText(FrameStrings.valueOfStringName("reportstotallbl")+" "+total);
	}
}
