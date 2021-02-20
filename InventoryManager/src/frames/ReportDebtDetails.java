package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Asynchronous.DatabaseThread;
import misc.FrameArrays;
import misc.FrameStrings;

public class ReportDebtDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int id;
	private DatabaseThread dtb;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ReportDebtDetails(int id,DatabaseThread dtb) {
		this.id=id;
		this.dtb=dtb;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize( (int) (screenSize.width*0.45), (int) (screenSize.height*0.7));
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		scrollPane = new JScrollPane();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, scrollPane, (int)(20*RootFrame.sizeMultiplier), SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, scrollPane, (int)(20*RootFrame.sizeMultiplier), SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, scrollPane, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, scrollPane, (int)(-20*RootFrame.sizeMultiplier), SpringLayout.EAST, contentPanel);
		contentPanel.add(scrollPane);
		
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(RootFrame.mainFont);
		table.getTableHeader().setFont(RootFrame.mainFont);
		table.setRowHeight((int)(30*RootFrame.sizeMultiplier));
		table.setModel(tableModel);
		
	}
	@Override
	public void setVisible(boolean b) {
		if(b) {
			
			tableModel.setColumnIdentifiers(FrameArrays.valueOfArrayName("reportdetailsdebttitles"));
			populateTable();
			super.setVisible(b);
		}
		else
			super.setVisible(b);
	}
	private void populateTable() {
		tableModel.setRowCount(0);
		try {
			ResultSet rs = dtb.query("select * from Debts_Log where id_debt="+id);
			while(rs.next()) {
				tableModel.addRow(new Object[] {rs.getString("date"),rs.getString("amount")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
