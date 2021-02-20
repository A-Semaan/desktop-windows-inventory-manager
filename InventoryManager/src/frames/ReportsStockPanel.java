package frames;

import java.awt.Color;
import java.awt.Font;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePickerImpl;

import misc.FrameArrays;
import misc.FrameStrings;

import javax.swing.JLabel;

public class ReportsStockPanel extends JPanel {
	
	private JTextField reportsStockSearchTxt;
	private JComboBox<String> reportsStockCbx;
	private JLabel reportsStockSearchLbl;
	
	/**
	 * Create the panel.
	 */
	public ReportsStockPanel() {
		SpringLayout sl_reportsStockPanel = new SpringLayout();
		setLayout(sl_reportsStockPanel);
		
		reportsStockSearchLbl = new JLabel(FrameStrings.valueOfStringName("reportsstocksearchlbl"));
		reportsStockSearchLbl.setFont(RootFrame.mainFont);
		sl_reportsStockPanel.putConstraint(SpringLayout.NORTH, reportsStockSearchLbl, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.SOUTH, reportsStockSearchLbl, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.WEST, reportsStockSearchLbl, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.EAST, reportsStockSearchLbl, (int)(500*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		add(reportsStockSearchLbl);
		
		reportsStockSearchTxt = new JTextField();
		reportsStockSearchTxt.setFont(RootFrame.mainFont);
		sl_reportsStockPanel.putConstraint(SpringLayout.NORTH, reportsStockSearchTxt, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.SOUTH, reportsStockSearchTxt, (int)(50*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.WEST, reportsStockSearchTxt, (int)(530*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.EAST, reportsStockSearchTxt, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsStockSearchTxt);
		
		reportsStockCbx = new JComboBox<String>(FrameArrays.valueOfArrayName("reportsstockcbx"));
		reportsStockCbx.setFont(RootFrame.mainFont);
		sl_reportsStockPanel.putConstraint(SpringLayout.NORTH, reportsStockCbx, (int)(60*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.SOUTH, reportsStockCbx, (int)(90*RootFrame.sizeMultiplier), SpringLayout.NORTH, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.WEST, reportsStockCbx, (int)(400*RootFrame.sizeMultiplier), SpringLayout.WEST, this);
		sl_reportsStockPanel.putConstraint(SpringLayout.EAST, reportsStockCbx, (int)(-400*RootFrame.sizeMultiplier), SpringLayout.EAST, this);
		this.add(reportsStockCbx);
	}
	public String[] getValues() {
		String[] returnable = new String[2];
		returnable[0]=reportsStockSearchTxt.getText().toString();
		returnable[1]=(String) reportsStockCbx.getSelectedItem();
		return returnable;
	}

}
