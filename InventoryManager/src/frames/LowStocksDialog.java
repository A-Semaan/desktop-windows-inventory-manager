package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataClasses.Stock;
import misc.FrameArrays;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;

public class LowStocksDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private HashSet<Stock> lowStocks;

	/**
	 * Create the dialog.
	 */
	public LowStocksDialog(HashSet<Stock> ls) {
		lowStocks=ls;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize( (int) (screenSize.width*0.5), (int) (screenSize.height*0.85));
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		scrollPane = new JScrollPane();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, scrollPane, 12, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, scrollPane, 12, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -12, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, scrollPane, -12, SpringLayout.EAST, contentPanel);
		contentPanel.add(scrollPane);
		
		tableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		tableModel.setColumnIdentifiers(FrameArrays.valueOfArrayName("lowstockstabletitles"));
		table = new JTable();
		table.setFont(RootFrame.mainFont);
		table.getTableHeader().setFont(RootFrame.mainFont);
		table.setRowHeight((int)(30*RootFrame.sizeMultiplier));
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			populateTable();
			super.setVisible(b);
		}
		else {
			super.setVisible(b);
		}
		
	}
	
	private void populateTable() {
		tableModel.setRowCount(0);
		Iterator<Stock> it = lowStocks.iterator();
		while(it.hasNext()) {
			Stock s = it.next();
			tableModel.addRow(new String[] {s.getName(),s.getqty()+""});
		}
	}
}
