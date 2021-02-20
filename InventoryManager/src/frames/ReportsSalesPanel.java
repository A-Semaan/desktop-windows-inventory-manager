package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
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

import misc.FrameArrays;
import misc.FrameStrings;

public class ReportsSalesPanel extends JPanel {
	
	private JDatePickerImpl datePicker;
	private JLabel reportsTotalLbl;
	private JComboBox reportsSalesCbx;
	
	/**
	 * Create the panel.
	 */
	public ReportsSalesPanel() {
		SpringLayout sl_reportsSalesPanel = new SpringLayout();
		setLayout(sl_reportsSalesPanel);
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
		sl_reportsSalesPanel.putConstraint(SpringLayout.NORTH, datePicker, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.SOUTH, datePicker, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePicker.getJFormattedTextField().setFont(RootFrame.mainFont);
		
		sl_reportsSalesPanel.putConstraint(SpringLayout.WEST, datePicker, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.EAST, datePicker, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(datePicker);
		
		reportsTotalLbl = new JLabel(FrameStrings.valueOfStringName("reportstotallbl"));
		sl_reportsSalesPanel.putConstraint(SpringLayout.NORTH, reportsTotalLbl, (int)(-40*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.WEST, reportsTotalLbl, (int)(-300*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.SOUTH, reportsTotalLbl, (int)(-10*RootFrame.sizeMultiplier), SpringLayout.SOUTH, this);
		reportsTotalLbl.setFont(RootFrame.mainFont);
		sl_reportsSalesPanel.putConstraint(SpringLayout.EAST, reportsTotalLbl, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsTotalLbl);
		
		reportsSalesCbx = new JComboBox(FrameArrays.valueOfArrayName("reportssalescbx"));
		reportsSalesCbx.setFont(RootFrame.mainFont);
		sl_reportsSalesPanel.putConstraint(SpringLayout.NORTH, reportsSalesCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.SOUTH, reportsSalesCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.WEST, reportsSalesCbx, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsSalesPanel.putConstraint(SpringLayout.EAST, reportsSalesCbx, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsSalesCbx);
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
	public Object[] getValues() {
		Object[] returnable = new Object[2];
		returnable[0]=((Date) datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		returnable[1]=reportsSalesCbx.getSelectedIndex();
		return returnable;
	}
	
	public void setTotal(double total) {
		reportsTotalLbl.setText(FrameStrings.valueOfStringName("reportstotallbl")+" "+total);
	}

}
