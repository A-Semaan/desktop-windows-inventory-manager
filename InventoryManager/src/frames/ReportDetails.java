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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Asynchronous.DatabaseThread;
import misc.FrameArrays;
import misc.FrameStrings;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReportDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int mode;
	private int id;
	private DatabaseThread dtb;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ReportDetails(int mode,int id, DatabaseThread dtb) {
		this.mode=mode;
		this.id=id;
		this.dtb=dtb;
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
		if(mode==3) {
			JMenuBar debtsMenubar = new JMenuBar();
			JMenu debtsMenu = new JMenu(FrameStrings.valueOfStringName("menuoptionsmenu"));
			JMenuItem debtsDetails = new JMenuItem(FrameStrings.valueOfStringName("reportdebtspayementdetails"));
			debtsDetails.setFont(RootFrame.mainFont);
			debtsMenu.setFont(RootFrame.mainFont);
			debtsMenubar.add(debtsMenu);
			debtsMenu.add(debtsDetails);
			getContentPane().add(debtsMenubar, BorderLayout.NORTH);
			debtsDetails.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ReportDebtDetails rdd = new ReportDebtDetails(id, dtb);
					rdd.setVisible(true);
					
				}
			});
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			
			tableModel.setColumnIdentifiers(FrameArrays.valueOfArrayName("reportdetailstitles"));
			populateTable();
			super.setVisible(b);
		}
		else
			super.setVisible(b);
	}

	private void populateTable() {
		tableModel.setRowCount(0);
		try {
			switch(mode) {
				case 1:populateSales();break;
				case 2:populatePurchases();break;
				case 3:populateDebts();break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void populateSales() throws SQLException {
		ResultSet rs = dtb.query("select st.name,st.desc,s.* from Stock st, Sales_Details s where st.id_stock=s.id_stock and s.id_sale="+" "+id);
		while(rs.next()) {
			tableModel.addRow(new Object[] {rs.getString("name"),rs.getString("desc"),rs.getString("qty"),rs.getString("total")});
		}
		
	}

	private void populatePurchases() throws SQLException {
		ResultSet rs = dtb.query("select st.name,st.desc,p.* from Stock st, Purchases_Details p where st.id_stock=p.id_stock and p.id_purchase="+" "+id);
		while(rs.next()) {
			tableModel.addRow(new Object[] {rs.getString("name"),rs.getString("desc"),rs.getString("qty"),rs.getString("total")});
		}
		
	}

	private void populateDebts() throws SQLException {
		// TODO Auto-generated method stub
		ResultSet rs = dtb.query("select s.name,s.desc,d.* from Stock s, Debts_Details d where s.id_stock=d.id_stock and d.id_debt="+" "+id);
		while(rs.next()) {
			tableModel.addRow(new Object[] {rs.getString("name"),rs.getString("desc"),rs.getString("qty"),rs.getString("total")});
		}
	}
}
