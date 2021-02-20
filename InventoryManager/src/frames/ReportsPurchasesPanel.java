package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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
import dataClasses.Supplier;
import misc.FrameArrays;
import misc.FrameStrings;

public class ReportsPurchasesPanel extends JPanel {

	private JDatePickerImpl datePicker;
	private JLabel reportsTotalLbl;
	private JComboBox reportsPurchasesCbx;
	private JComboBox<Supplier> reportsPurchasesSuppliersCbx;
	private JLabel reportsPurchasesSupplierLbl;
	
	/**
	 * Create the panel.
	 */
	public ReportsPurchasesPanel() {
		SpringLayout sl_reportsPurchasesPanel = new SpringLayout();
		setLayout(sl_reportsPurchasesPanel);
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
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.NORTH, datePicker, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.SOUTH, datePicker, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.WEST, datePicker, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.EAST, datePicker, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePicker.getJFormattedTextField().setFont(RootFrame.mainFont);
		this.add(datePicker);
		
		reportsTotalLbl = new JLabel(FrameStrings.valueOfStringName("reportstotallbl"));
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.NORTH, reportsTotalLbl, (int)(-40*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.WEST, reportsTotalLbl, (int)(-300*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.SOUTH, reportsTotalLbl, (int)(-10*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.EAST, reportsTotalLbl, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		reportsTotalLbl.setFont(RootFrame.mainFont);
		this.add(reportsTotalLbl);
		
		reportsPurchasesCbx = new JComboBox(FrameArrays.valueOfArrayName("reportspurchasescbx"));
		reportsPurchasesCbx.setFont(RootFrame.mainFont);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.NORTH, reportsPurchasesCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.SOUTH, reportsPurchasesCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.WEST, reportsPurchasesCbx, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.EAST, reportsPurchasesCbx, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsPurchasesCbx);
		
		reportsPurchasesSuppliersCbx = new JComboBox<Supplier>();
		reportsPurchasesSuppliersCbx.setFont(RootFrame.mainFont);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.NORTH, reportsPurchasesSuppliersCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.WEST, reportsPurchasesSuppliersCbx, (int)(20*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.SOUTH, reportsPurchasesSuppliersCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.EAST, reportsPurchasesSuppliersCbx, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.WEST, reportsPurchasesCbx);
		add(reportsPurchasesSuppliersCbx);
		
		reportsPurchasesSupplierLbl = new JLabel(FrameStrings.valueOfStringName("reportspurchasessupplierlbl"));
		reportsPurchasesSupplierLbl.setFont(RootFrame.mainFont);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.NORTH, reportsPurchasesSupplierLbl, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.SOUTH, reportsPurchasesSupplierLbl, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.WEST, reportsPurchasesSupplierLbl, (int)(20*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsPurchasesPanel.putConstraint(SpringLayout.EAST, reportsPurchasesSupplierLbl, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.WEST, reportsPurchasesCbx);
		add(reportsPurchasesSupplierLbl);
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
	
	public void setComboBoxValues(Collection<Supplier> l) {
		reportsPurchasesSuppliersCbx.removeAllItems();
		reportsPurchasesSuppliersCbx.addItem(null);
		for(Supplier c:l) {
			reportsPurchasesSuppliersCbx.addItem(c);
		}
		
	}
	
	public Object[] getValues() {
		Object[] returnable = new Object[3];
		returnable[0]=((Date) datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		returnable[1]=reportsPurchasesCbx.getSelectedIndex();
		returnable[2]=reportsPurchasesSuppliersCbx.getSelectedItem();
		return returnable;
	}
	
	public void setTotal(double total) {
		reportsTotalLbl.setText(FrameStrings.valueOfStringName("reportstotallbl")+" "+total);
	}

}
