package frames;

import java.awt.AWTException;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.IOUtils;

import Asynchronous.DatabaseThread;
import dataClasses.Customer;
import dataClasses.Debt;
import dataClasses.DebtDetails;
import dataClasses.DebtLog;
import dataClasses.Purchase;
import dataClasses.PurchaseDetails;
import dataClasses.Sale;
import dataClasses.SaleDetails;
import dataClasses.Stock;
import dataClasses.Supplier;
import misc.FrameArrays;
import misc.FrameStrings;
import misc.Language;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;

public class RootFrame extends JFrame {
	
	private HashMap<Integer,Stock> stocks;
	private HashSet<Stock> lowStocks;
	private HashMap<Integer,Customer> customers;
	private HashMap<Integer,Supplier> suppliers;
	private HashMap<Integer,Debt> debts;
	
	private HashMap<Integer,SaleDetails> currentSaleDetails;
	private Sale currentSale;
	
	private HashMap<Integer, PurchaseDetails> currentPurchaseDetails;
	private Purchase currentPurchase;
	
	private HashMap<Integer, DebtDetails> currentDebtDetails;
	private Debt currentDebt;
	
	private DebtLog currentDebtLog;
	
	public static Language lang=Language.EN;
	private File languageFile;
	private File dollarFile;
	private FrameRunner parent;
	private static double dollarValue;
	public static double sizeMultiplier= Toolkit.getDefaultToolkit().getScreenSize().getWidth()>1400?1:0.6;
	public static final Font mainFont=new Font("Arial", Font.BOLD, (int)(20*sizeMultiplier));
	public static final int CRITICAL_STOCKVALUE=10;
	
	private JPanel rootPanel;
	private JTabbedPane rootTabbedPane;
	private JPanel salesPanel;
	private JPanel purchasePanel;
	private JPanel debtPanel;
	private JPanel stockPanel;
	private JPanel reportsPanel;
	private JPanel supplierPanel;
	private JPanel customerPanel;
	
	private JMenuBar menuBar;
	private JMenu menuLanguageMenu;
	private JRadioButtonMenuItem menuLanguageAr;
	private JRadioButtonMenuItem menuLanguageEn;
	private ButtonGroup menuLanguageBtnGrp;
	private JMenuItem menuOptionsDollar;
	private JMenuItem menuOptionsCheckCriticalStocks;
	private JMenu menuOptionsMenu;
	
	private JPopupMenu customerDeletePopup;
	private JMenuItem customerDeleteMenuItem;

	private JPopupMenu supplierDeletePopup;
	private JMenuItem supplierDeleteMenuItem;
	
	private JPopupMenu stockDeletePopup;
	private JMenuItem stockDeleteMenuItem;
	
	private JPopupMenu salesCurrentItemDeletePopup;
	private JMenuItem salesCurrentItemDeleteMenuItem;
	
	private JPopupMenu purchasesCurrentItemDeletePopup;
	private JMenuItem purchasesCurrentItemDeleteMenuItem;
	
	private JPopupMenu debtClosePopup;
	private JMenuItem debtCloseMenuItem;
	private JPopupMenu debtsCurrentItemDeletePopup;
	private JMenuItem debtsCurrentItemDeleteMenuItem;
	
	private JPopupMenu reportsShowMoreDetailsPopup;
	private JMenuItem reportsShowMoreDetailsMenuItem;
	
	private DefaultListModel<SaleDetails> salesCurrentListModel;
	private JList<SaleDetails> salesCurrentList;
	private JLabel salesSearchLbl;
	private JLabel salesCodeLbl;
	private JTextField salesCodeTxt;
	private JLabel salesNameLbl;
	private JTextField salesNameTxt;
	private JLabel salesDescLbl;
	private JTextField salesDescTxt;
	private JLabel salesQtyLbl;
	private JTextField salesQtyTxt;
	private JLabel salesPriceLbl;
	private JTextField salesPriceTxt;
	private JButton salesClearBtn;
	private JLabel salesTotalLbl;
	private JTextField salesTotalTxt;
	private JLabel salesDiscountLbl;
	private JTextField salesDiscountTxt;
	private JButton salesConfirmSale;
	private DefaultListModel<Stock> salesItemsListModel;
	private JList<Stock> salesItemsList;
	private JTextField salesSearchTxt;
	private JPanel salesInfoPanel;
	
	private DefaultListModel<PurchaseDetails> purchasesCurrentListModel;
	private JList<PurchaseDetails> purchasesCurrentList;
	private JLabel purchasesSearchLbl;
	private JLabel purchasesCodeLbl;
	private JTextField purchasesCodeTxt;
	private JLabel purchasesNameLbl;
	private JTextField purchasesNameTxt;
	private JLabel purchasesDescLbl;
	private JTextField purchasesDescTxt;
	private JLabel purchasesQtyLbl;
	private JTextField purchasesQtyTxt;
	private JLabel purchasesPriceLbl;
	private JTextField purchasesPriceTxt;
	private JButton purchasesClearBtn;
	private JLabel purchasesTotalLbl;
	private JTextField purchasesTotalTxt;
	private JLabel purchasesDiscountLbl;
	private JTextField purchasesDiscountTxt;
	private JButton purchasesConfirmPurchase;
	private DefaultListModel<Stock> purchasesItemsListModel;
	private JList<Stock> purchasesItemsList;
	private JTextField purchasesSearchTxt;
	private JPanel purchasesInfoPanel;
	private JLabel purchasesSearchSupplierLbl;
	private JTextField purchasesSearchSupplierTxt;
	private DefaultListModel<Supplier> purchasesSupplierListModel;
	private JList<Supplier> purchasesSupplierList;
	
	private DefaultListModel<DebtDetails> debtsCurrentListModel;
	private JList<DebtDetails> debtsCurrentList;
	private JLabel debtsSearchLbl;
	private JLabel debtsCodeLbl;
	private JTextField debtsCodeTxt;
	private JLabel debtsNameLbl;
	private JTextField debtsNameTxt;
	private JLabel debtsDescPayementLbl;
	private JTextField debtsDescPayementTxt;
	private JLabel debtsQtyLbl;
	private JTextField debtsQtyTxt;
	private JLabel debtsPriceLbl;
	private JTextField debtsPriceTxt;
	private JButton debtsClearBtn;
	private JLabel debtsTotalLbl;
	private JTextField debtsTotalTxt;
	private JLabel debtsDiscountLbl;
	private JTextField debtsDiscountTxt;
	private JButton debtsConfirmDebt;
	private DefaultListModel<Stock> debtsItemsListModel;
	private JList<Stock> debtsItemsList;
	private JTextField debtsSearchTxt;
	private JPanel debtsInfoPanel;
	private JLabel debtsSearchCustomerLbl;
	private JTextField debtsSearchCustomerTxt;
	private DefaultListModel<Customer> debtsCustomerListModel;
	private JList<Customer> debtsCustomerList;
	
	private JLabel stockSearchLbl;
	private JLabel stockCodeLbl;
	private JTextField stockCodeTxt;
	private JLabel stockNameLbl;
	private JTextField stockNameTxt;
	private JLabel stockDescLbl;
	private JTextField stockDescTxt;
	private JLabel stockPriceLbl;
	private JTextField stockPriceTxt;
	private JButton stockClearBtn;
	private JButton stockConfirmstock;
	private DefaultListModel<Stock> stockItemsListModel;
	private JList<Stock> stockItemsList;
	private JTextField stockSearchTxt;
	private JPanel stockInfoPanel;
	
	private JLabel supplierSearchLbl;
	private JLabel supplierNameLbl;
	private JTextField supplierNameTxt;
	private JLabel supplierPhoneLbl;
	private JTextField supplierPhoneTxt;
	private JButton supplierClearBtn;
	private JButton supplierConfirmsupplier;
	private DefaultListModel<Supplier> suppliersListModel;
	private JList<Supplier> suppliersList;
	private JTextField supplierSearchTxt;
	private JPanel supplierInfoPanel;
	
	private JLabel customerSearchLbl;
	private JLabel customerNameLbl;
	private JTextField customerNameTxt;
	private JLabel customerPhoneLbl;
	private JTextField customerPhoneTxt;
	private JButton customerClearBtn;
	private JButton customerConfirmcustomer;
	private DefaultListModel<Customer> customerListModel;
	private JList<Customer> customerList;
	private JTextField customerSearchTxt;
	private JPanel customerInfoPanel;
	private JLabel debtsRemainingLbl;
	private JLabel debtsClosedLbl;
	private DefaultListModel<Debt> debtsDebtsListModel;
	private JList<Debt> debtsDebtsList;
	
	private DefaultTableModel reportsTableModel;
	private JTable reportsTable;
	private JScrollPane scrollPane;
	private JComboBox reportsChoicesCbx;
	private ReportsSalesPanel reportsSalesPanel;
	private ReportsPurchasesPanel reportsPurchasesPanel;
	private ReportsDebtsPanel reportsDebtsPanel;
	private ReportsStockPanel reportsStockPanel;
	
	private LoadingDialog loadingPane;
	
	/*
	 * variables
	 */

	private static DatabaseThread databaseThread;
	private JButton reportsOkBtn;
	private JScrollPane salesCurrentListSP;
	private JScrollPane salesItemsListSP;
	private JScrollPane purchasesCurrentListSP;
	private JScrollPane purchasesItemsListSP;
	private JScrollPane purchasesSupplierListSP;
	private JScrollPane debtsCurrentListSP;
	private JScrollPane debtsItemsListSP;
	private JScrollPane debtsCustomerListSP;
	private JScrollPane debtsDebtsListSP;
	private JScrollPane stockItemsListSP;
	private JScrollPane suppliersListSP;
	private JScrollPane customerListSP;
	
	
	/**
	 * Create the frame.
	 */
	public RootFrame(FrameRunner frameRunner) {
		//***************************************MAIN SHIT***************************************\\
		super(FrameStrings.valueOfStringName("appname"));
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage("./icon.png");
		setIconImage(img);
		
		
		stocks = new HashMap<Integer, Stock>();
		suppliers = new HashMap<Integer, Supplier>();
		customers = new HashMap<Integer, Customer>();
		debts = new HashMap<Integer, Debt>();
		
		currentSaleDetails = new HashMap<Integer, SaleDetails>();
		currentPurchaseDetails = new HashMap<Integer, PurchaseDetails>();
		currentDebtDetails = new HashMap<Integer, DebtDetails>();
		
		parent=frameRunner;
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize( (int) (screenSize.width*0.85), (int) (screenSize.height*0.85));
		setLocationRelativeTo(null);
		setMinimumSize(getSize());
		
		UIDefaults def = UIManager.getLookAndFeelDefaults();
		UIManager.put( "TabbedPane.background", Color.WHITE );
		def.put( "TabbedPane.tabInsets", new Insets(10,10,10,10) );
		
		//***************************************INITS***************************************\\
		
		try {
			checkAndPlaceDatabaseAndLanguage();
			initializeDataClassesValues();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.showConfirmDialog(RootFrame.this, ErrorOptionPane.HTML_OPEN+
						ErrorOptionPane.FONT_OPEN+FrameStrings.valueOfStringName("appclosing")
						+ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE, "", JOptionPane.YES_NO_OPTION)
						==JOptionPane.YES_OPTION) {
					databaseThread.shutdown();
					System.exit(0);
				}
			}
		});
		
		//***************************************START OF DECLARATIONS***************************************\\
		
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		getContentPane().add(rootPanel, BorderLayout.CENTER);
		SpringLayout sl_rootPanel = new SpringLayout();
		rootPanel.setLayout(sl_rootPanel);
		
				//***************************************LOADING PANEL***************************************\\
		
		loadingPane = new LoadingDialog();
		
				//***************************************TABBED PANEL***************************************\\
		
		rootTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		sl_rootPanel.putConstraint(SpringLayout.NORTH, rootTabbedPane, 0, SpringLayout.NORTH, rootPanel);
		sl_rootPanel.putConstraint(SpringLayout.WEST, rootTabbedPane, 0, SpringLayout.WEST, rootPanel);
		sl_rootPanel.putConstraint(SpringLayout.SOUTH, rootTabbedPane, 0, SpringLayout.SOUTH, rootPanel);
		sl_rootPanel.putConstraint(SpringLayout.EAST, rootTabbedPane, 0, SpringLayout.EAST, rootPanel);
		rootTabbedPane.setFont(new Font("Arial", Font.BOLD, (int)(24*sizeMultiplier)));
		rootTabbedPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		
		/*
		rootTabbedPane.setUI(new BasicTabbedPaneUI() {
			
		    @Override
		    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		        return (int) (rootTabbedPane.getSize().height-44)/7;
		    }
		});/**/
		rootPanel.add(rootTabbedPane);
		
				//***************************************SALES PANEL***************************************\\
		
		salesPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("salespaneltabname"), null, salesPanel, null);
		SpringLayout sl_salesPanel = new SpringLayout();
		salesPanel.setLayout(sl_salesPanel);
		
		salesCurrentListModel = new DefaultListModel<SaleDetails>();
		salesCurrentList = new JList<SaleDetails>(salesCurrentListModel);
		salesCurrentList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					salesCurrentList.setSelectedIndex(salesCurrentList.locationToIndex(e.getPoint()));
					if(salesCurrentList.getSelectedIndex()>=0)
						salesCurrentItemDeletePopup.show(salesCurrentList, e.getX(), e.getY());
				}
			}
		});
		salesCurrentList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(salesCurrentList.getSelectedIndex()>=0) {
					SaleDetails sd = salesCurrentList.getSelectedValue();
					
					salesCodeTxt.setText(sd.getStock().getCode());
					salesNameTxt.setText(sd.getStock().getName());
					salesDescTxt.setText(sd.getStock().getDesc());
					salesQtyTxt.setText(sd.getQty()+"");
					salesPriceTxt.setText((sd.getStock().getSalePrice()*dollarValue)+"");
					salesQtyTxt.grabFocus();
					salesCurrentList.repaint();
					salesItemsList.clearSelection();
				}
			}
		});
		salesCurrentListSP = new JScrollPane(salesCurrentList);
		salesCurrentListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("salescurrentlisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)salesCurrentListSP.getBorder()).setTitleFont(mainFont);
		salesCurrentList.setFont(mainFont);
		salesCurrentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesCurrentListSP,(int)( -150*sizeMultiplier), SpringLayout.SOUTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesCurrentListSP, (int)(25*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesCurrentListSP,(int)( 20*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesCurrentListSP, (int)(480*sizeMultiplier), SpringLayout.WEST, salesPanel);
		salesPanel.add(salesCurrentListSP);
		
		salesItemsListModel = new DefaultListModel<Stock>();
		salesItemsList = new JList<Stock>(salesItemsListModel);
		salesItemsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(salesItemsList.getSelectedIndex()>=0) {
					Stock s = salesItemsList.getSelectedValue();
					if(stocks.get(s.getID()).getqty()<1) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("insufficiantstockerror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						salesItemsList.clearSelection();
						return;
					}
					if(stocks.get(s.getID()).getSalePrice()<=0) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("pricestockerror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						salesItemsList.clearSelection();
						return;
					}
					salesCodeTxt.setText(s.getCode());
					salesNameTxt.setText(s.getName());
					salesDescTxt.setText(s.getDesc());
					salesQtyTxt.setText(1+"");
					salesPriceTxt.setText((s.getSalePrice()*dollarValue)+"");
					
					if(currentSaleDetails.containsKey(s.getID())){
						SaleDetails sd = new SaleDetails(s, currentSale, currentSaleDetails.get(s.getID()).getQty());
						
						salesCurrentList.setSelectedValue(sd, true);
						if(salesCurrentList.getSelectedIndex()<0||sd.compareTo(salesCurrentList.getSelectedValue())!=0)
							for (int i = 0;i<salesCurrentListModel.getSize(); i++)
								if (sd.compareTo(salesCurrentListModel.getElementAt(i))==0) {
									salesCurrentList.setSelectedIndex(i);
									salesCurrentList.ensureIndexIsVisible(i);
									break;
								}
					}
					else {
						SaleDetails sd = new SaleDetails(s, currentSale, 1);
						currentSaleDetails.put(s.getID(), sd);
						salesCurrentListModel.addElement(sd);
						double total = calculateSaleTotal();
						salesTotalTxt.setText(total+"");
						currentSale.setTotal(total);
						salesCurrentList.clearSelection();
					}
					salesQtyTxt.grabFocus();
				}
			}
		});
		salesItemsListSP = new JScrollPane(salesItemsList);
		salesItemsListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("salesitemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)salesItemsListSP.getBorder()).setTitleFont(mainFont);
		salesItemsList.setFont(mainFont);
		salesItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesItemsListSP, (int)(120*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesItemsListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesPanel);
		salesPanel.add(salesItemsListSP);
		
		salesSearchTxt = new JTextField();
		salesSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = salesSearchTxt.getText().toString().toLowerCase().trim();
				salesItemsListModel.removeAllElements();
				Iterator<Stock> it = stocks.values().iterator();
				while(it.hasNext()) {
					Stock s = it.next();
					if(s.getCode().toLowerCase().contains(desired)||
							s.getName().toLowerCase().contains(desired)||
							s.getDesc().toLowerCase().contains(desired)) {
						salesItemsListModel.addElement(s);
					}
				}
				if(salesItemsListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		salesSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesSearchTxt, (int)(25*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesSearchTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesPanel);
		salesPanel.add(salesSearchTxt);
		salesSearchTxt.setColumns(10);
		
		salesSearchLbl = new JLabel(FrameStrings.valueOfStringName("salessearchlbl"));
		salesSearchLbl.setFont(mainFont);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, salesSearchTxt);
		salesPanel.add(salesSearchLbl);
		
		salesInfoPanel = new JPanel();
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesInfoPanel, (int)(-510*sizeMultiplier), SpringLayout.EAST, salesItemsListSP);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesInfoPanel, (int)(50*sizeMultiplier), SpringLayout.EAST, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, salesPanel);
		salesPanel.add(salesInfoPanel);
		SpringLayout sl_salesInfoPanel = new SpringLayout();
		salesInfoPanel.setLayout(sl_salesInfoPanel);
		
		salesCodeLbl = new JLabel(FrameStrings.valueOfStringName("salescodelbl"));
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesCodeLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesInfoPanel);
		salesCodeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesCodeLbl, (int)(50*sizeMultiplier), SpringLayout.NORTH, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesCodeLbl.setFont(mainFont);
		salesInfoPanel.add(salesCodeLbl);
		
		salesCodeTxt = new JTextField();
		salesCodeTxt.setEditable(false);
		salesCodeTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesCodeTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesCodeLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesCodeTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesCodeLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesCodeTxt, (int)(10*sizeMultiplier), SpringLayout.NORTH, salesCodeLbl);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesCodeTxt, (int)(-50*sizeMultiplier), SpringLayout.NORTH, salesCodeLbl);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesCodeTxt);
		salesCodeTxt.setColumns(10);
		
		salesNameLbl = new JLabel(FrameStrings.valueOfStringName("salesnamelbl"));
		salesNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		salesNameLbl.setFont(mainFont);
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesCodeTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesNameLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesCodeTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesCodeTxt);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, salesCodeTxt);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesPanel);
		salesInfoPanel.add(salesNameLbl);
		
		salesNameTxt = new JTextField();
		salesNameTxt.setEditable(false);
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesNameTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesNameLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesNameTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesNameLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		salesInfoPanel.add(salesNameTxt);
		salesNameTxt.setColumns(10);
		
		salesDescLbl = new JLabel(FrameStrings.valueOfStringName("salesdesclbl"));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesDescLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesNameTxt);
		salesDescLbl.setHorizontalAlignment(SwingConstants.CENTER);
		salesDescLbl.setFont(mainFont);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesDescLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesDescLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesNameTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesDescLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesDescLbl);
		
		salesDescTxt = new JTextField();
		salesDescTxt.setEditable(false);
		salesDescTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesDescTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesDescLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesDescTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesDescTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesDescLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesDescTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesDescTxt);
		salesDescTxt.setColumns(10);
		
		salesQtyLbl = new JLabel(FrameStrings.valueOfStringName("salesqtylbl"));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesDescTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesQtyLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesDescTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesQtyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		salesQtyLbl.setFont(mainFont);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesDescTxt);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.SOUTH, salesDescTxt);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesQtyLbl);
		
		salesQtyTxt = new JTextField();
		salesQtyTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(salesCurrentList.getSelectedIndex()<0&&salesItemsList.getSelectedIndex()<0)
					return;
				
				int newQty=-1;
				try{
					newQty = Integer.parseInt(salesQtyTxt.getText().toString());
					
				}
				catch(NumberFormatException ex) {
					salesQtyTxt.grabFocus();
					salesQtyTxt.selectAll();
					return;
				}
				if(newQty<=0) {
					salesQtyTxt.grabFocus();
					salesQtyTxt.selectAll();
					return;
				}
				if(salesCurrentList.getSelectedIndex()>=0) {
					SaleDetails sdCurrent = salesCurrentList.getSelectedValue();
					if(newQty>stocks.get(sdCurrent.getStock().getID()).getqty()) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("insufficiantstockerror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						salesQtyTxt.grabFocus();
						salesQtyTxt.selectAll();
						return;
					}
					currentSaleDetails.get(sdCurrent.getStock().getID()).setQty(newQty);
					int position = salesCurrentList.getSelectedIndex();
					salesCurrentListModel.setElementAt(currentSaleDetails.get(sdCurrent.getStock().getID()), position);
					double total = calculateSaleTotal();
					currentSale.setTotal(total);
					salesTotalTxt.setText(total+"");
				}
				
			}
		});
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesQtyTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesQtyLbl);
		salesQtyTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesQtyTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesQtyLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesQtyTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesQtyTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesQtyTxt);
		salesQtyTxt.setColumns(10);
		
		salesPriceLbl = new JLabel(FrameStrings.valueOfStringName("salespricelbl"));
		salesPriceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		salesPriceLbl.setFont(mainFont);
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesPriceLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesQtyTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesPriceLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesPriceLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesQtyTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesPriceLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesPriceLbl);
		
		salesPriceTxt = new JTextField();
		salesPriceTxt.setEditable(false);
		salesPriceTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesPriceTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesPriceLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesPriceTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesPriceTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, salesPriceLbl);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesPriceTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		salesInfoPanel.add(salesPriceTxt);
		salesPriceTxt.setColumns(10);
		
		salesClearBtn = new JButton(FrameStrings.valueOfStringName("salesclearbtn"));
		salesClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salesCodeTxt.setText("");
				salesNameTxt.setText("");
				salesDescTxt.setText("");
				salesPriceTxt.setText("");
				salesQtyTxt.setText("");
				salesTotalTxt.setText("");
				salesDiscountTxt.setText("");
				salesCurrentListModel.removeAllElements();
				salesItemsList.clearSelection();
				currentSaleDetails.clear();
				salesSearchTxt.setText("");
				try {
					initializeStock();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		salesClearBtn.setFont(mainFont);
		sl_salesInfoPanel.putConstraint(SpringLayout.NORTH, salesClearBtn, (int)(80*sizeMultiplier), SpringLayout.NORTH, salesPriceTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.WEST, salesClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesInfoPanel.putConstraint(SpringLayout.SOUTH, salesClearBtn, (int)(120*sizeMultiplier), SpringLayout.NORTH, salesPriceTxt);
		sl_salesInfoPanel.putConstraint(SpringLayout.EAST, salesClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, salesPriceTxt);
		salesInfoPanel.add(salesClearBtn);
		
		salesTotalLbl = new JLabel(FrameStrings.valueOfStringName("salestotallbl"));
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesTotalLbl, (int)(15*sizeMultiplier), SpringLayout.SOUTH, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesTotalLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesTotalLbl, (int)(30*sizeMultiplier), SpringLayout.SOUTH, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesTotalLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, salesPanel);
		salesTotalLbl.setFont(mainFont);
		salesPanel.add(salesTotalLbl);
		
		salesTotalTxt = new JTextField();
		salesTotalTxt.setEditable(false);
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesTotalTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesTotalTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, salesTotalLbl);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesTotalTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesTotalTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, salesTotalLbl);
		salesTotalTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		salesPanel.add(salesTotalTxt);
		salesTotalTxt.setColumns(10);
		
		salesDiscountLbl = new JLabel(FrameStrings.valueOfStringName("salesdiscountlbl"));
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesDiscountLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, salesTotalLbl);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesDiscountLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, salesPanel);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesDiscountLbl, (int)(60*sizeMultiplier), SpringLayout.NORTH, salesTotalLbl);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesDiscountLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, salesPanel);
		salesDiscountLbl.setFont(mainFont);
		salesPanel.add(salesDiscountLbl);
		
		salesDiscountTxt = new JTextField();
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesDiscountTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, salesTotalTxt);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesDiscountTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, salesDiscountLbl);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesDiscountTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, salesTotalTxt);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesDiscountTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, salesDiscountLbl);
		salesDiscountTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		salesPanel.add(salesDiscountTxt);
		salesDiscountTxt.setColumns(10);
		
		salesConfirmSale = new JButton(FrameStrings.valueOfStringName("salesconfirmbtn"));
		salesConfirmSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentSaleDetails.values().size()==0) {
					return;
				}
				currentSale.setLocalDateTime(LocalDateTime.now());
				double discount=-1;
				if(salesDiscountTxt.getText().toString().equals("")||salesDiscountTxt.getText().toString()==null) {
					discount=0;
				}
				else {
					try {
						discount = Double.parseDouble(salesDiscountTxt.getText().toString());
					}
					catch(NumberFormatException ex) {
						salesDiscountTxt.grabFocus();
						salesDiscountTxt.selectAll();
						return;
					}
				}
				currentSale.setTotal(calculateSaleTotal());
				currentSale.setDiscount(discount);
				boolean newCritical=false;
				try {
					databaseThread.update("insert into Sales(id_sale,date,discount,total) values("
							+ currentSale.getID()+" ,\""+currentSale.getDate()+"\", "+currentSale.getDiscount()+" ,"+currentSale.getTotal()+")");
					
					for(SaleDetails sd:currentSaleDetails.values()) {
						databaseThread.update("insert into Sales_Details(id_stock,id_sale,qty,total) values("+sd.getStock().getID()+","
								+ currentSale.getID()+","+sd.getQty()+","+(sd.getTotal()*dollarValue)+")"); 
						int newStockqty = sd.getStock().getqty()-sd.getQty();
						if(newStockqty<=CRITICAL_STOCKVALUE)
							newCritical=true;
						databaseThread.update("update Stock set qty="+newStockqty+" where id_stock="+sd.getStock().getID());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				salesClearBtn.doClick();
				currentSale=new Sale();
				if(newCritical)
					newCriticalValuesound();
			}
		});
		sl_salesPanel.putConstraint(SpringLayout.NORTH, salesConfirmSale, (int)(20*sizeMultiplier), SpringLayout.SOUTH, salesDiscountTxt);
		sl_salesPanel.putConstraint(SpringLayout.WEST, salesConfirmSale, (int)(115*sizeMultiplier), SpringLayout.WEST, salesCurrentListSP);
		sl_salesPanel.putConstraint(SpringLayout.SOUTH, salesConfirmSale, (int)(60*sizeMultiplier), SpringLayout.SOUTH, salesDiscountTxt);
		sl_salesPanel.putConstraint(SpringLayout.EAST, salesConfirmSale, (int)(-115*sizeMultiplier), SpringLayout.EAST, salesCurrentListSP);
		salesConfirmSale.setFont(mainFont);
		salesPanel.add(salesConfirmSale);
		
		
				//***************************************PURCHASES PANEL***************************************\\
		
		purchasePanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("purchasespaneltabname"), null, purchasePanel, null);
		SpringLayout sl_purchasesPanel = new SpringLayout();
		purchasePanel.setLayout(sl_purchasesPanel);
		purchasesCurrentListModel = new DefaultListModel<PurchaseDetails>();
		purchasesCurrentList = new JList<PurchaseDetails>(purchasesCurrentListModel);
		purchasesCurrentList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(purchasesCurrentList.getSelectedIndex()>=0) {
					PurchaseDetails sd = purchasesCurrentList.getSelectedValue();
					
					purchasesCodeTxt.setText(sd.getStock().getCode());
					purchasesNameTxt.setText(sd.getStock().getName());
					purchasesDescTxt.setText(sd.getStock().getDesc());
					purchasesQtyTxt.setText(sd.getQty()+"");
					purchasesPriceTxt.setText(sd.getStock().getBoughtPrice()+"");
					purchasesQtyTxt.grabFocus();
					purchasesCurrentList.repaint();
					purchasesItemsList.clearSelection();
				}
			}
		});
		purchasesCurrentListSP = new JScrollPane(purchasesCurrentList); 
		purchasesCurrentList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					purchasesCurrentList.setSelectedIndex(purchasesCurrentList.locationToIndex(e.getPoint()));
					if(purchasesCurrentList.getSelectedIndex()>=0)
						purchasesCurrentItemDeletePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		purchasesCurrentListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("purchasescurrentlisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)purchasesCurrentListSP.getBorder()).setTitleFont(mainFont);
		purchasesCurrentList.setFont(mainFont);
		purchasesCurrentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesCurrentListSP, (int)(-150*sizeMultiplier), SpringLayout.SOUTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesCurrentListSP, (int)(25*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesCurrentListSP, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesCurrentListSP, (int)(480*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		purchasePanel.add(purchasesCurrentListSP);
		
		purchasesItemsListModel = new DefaultListModel<Stock>();
		purchasesItemsList = new JList<Stock>(purchasesItemsListModel);
		purchasesItemsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(purchasesItemsList.getSelectedIndex()>=0) {
					if(currentPurchase.getSupplier()==null) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("selectsuppliererror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						purchasesItemsList.clearSelection();
						return;
					}
					Stock s = purchasesItemsList.getSelectedValue();
					purchasesCodeTxt.setText(s.getCode());
					purchasesNameTxt.setText(s.getName());
					purchasesDescTxt.setText(s.getDesc());
					purchasesQtyTxt.setText(1+"");
					purchasesPriceTxt.setText(s.getBoughtPrice()+"");
					
					if(currentPurchaseDetails.containsKey(s.getID())){
						PurchaseDetails sd = new PurchaseDetails(s, currentPurchase, currentPurchaseDetails.get(s.getID()).getQty());
						
						purchasesCurrentList.setSelectedValue(sd, true);
						if(purchasesCurrentList.getSelectedIndex()<0||sd.compareTo(purchasesCurrentList.getSelectedValue())!=0)
							for (int i = 0;i<purchasesCurrentListModel.getSize(); i++)
								if (sd.compareTo(purchasesCurrentListModel.getElementAt(i))==0) {
									purchasesCurrentList.setSelectedIndex(i);
									purchasesCurrentList.ensureIndexIsVisible(i);
									break;
								}
						
					}
					else {
						PurchaseDetails sd = new PurchaseDetails(s, currentPurchase, 1);
						currentPurchaseDetails.put(s.getID(), sd);
						purchasesCurrentListModel.addElement(sd);
						double total = calculatePurchaseTotal();
						purchasesTotalTxt.setText(total+"");
						currentPurchase.setTotal(total);
					}
					purchasesQtyTxt.grabFocus();
				}
			}
		});
		purchasesItemsListSP = new JScrollPane(purchasesItemsList);
		purchasesItemsListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("purchasesitemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)purchasesItemsListSP.getBorder()).setTitleFont(mainFont);
		purchasesItemsList.setFont(mainFont);
		purchasesItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesItemsListSP, (int)(100*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesItemsListSP, (int)(-400*sizeMultiplier), SpringLayout.SOUTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesItemsListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasePanel.add(purchasesItemsListSP);
		
		purchasesSupplierListModel = new DefaultListModel<Supplier>();
		purchasesSupplierList = new JList<Supplier>(purchasesSupplierListModel);
		purchasesSupplierList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(purchasesSupplierList.getSelectedIndex()>=0) {
					currentPurchase.setSupplier(purchasesSupplierList.getSelectedValue());
				}
			}
		});
		purchasesSupplierListSP = new JScrollPane(purchasesSupplierList);
		purchasesSupplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchasesSupplierList.setFont(mainFont);
		purchasesSupplierListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("purchasessupplierslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		((TitledBorder)purchasesSupplierListSP.getBorder()).setTitleFont(mainFont);
		
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesSupplierListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesSupplierListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesSupplierListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasePanel.add(purchasesSupplierListSP);
		
		purchasesSearchTxt = new JTextField();
		purchasesSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = purchasesSearchTxt.getText().toString().toLowerCase().trim();
				purchasesItemsListModel.removeAllElements();
				Iterator<Stock> it = stocks.values().iterator();
				while(it.hasNext()) {
					Stock s = it.next();
					if(s.getCode().toLowerCase().contains(desired)||
							s.getName().toLowerCase().contains(desired)||
							s.getDesc().toLowerCase().contains(desired)) {
						purchasesItemsListModel.addElement(s);
					}
				}
				if(purchasesItemsListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesSearchTxt, (int)(35*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesSearchTxt, (int)(75*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		purchasesSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasePanel.add(purchasesSearchTxt);
		purchasesSearchTxt.setColumns(10);
		
		purchasesSearchLbl = new JLabel(FrameStrings.valueOfStringName("purchasessearchlbl"));
		purchasesSearchLbl.setFont(mainFont);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, purchasesSearchTxt);
		purchasePanel.add(purchasesSearchLbl);
		
		purchasesInfoPanel = new JPanel();
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesInfoPanel, (int)(-510*sizeMultiplier), SpringLayout.EAST, purchasesItemsListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesInfoPanel, (int)(50*sizeMultiplier), SpringLayout.EAST, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, purchasePanel);
		purchasePanel.add(purchasesInfoPanel);
		SpringLayout sl_purchasesInfoPanel = new SpringLayout();
		purchasesInfoPanel.setLayout(sl_purchasesInfoPanel);
		
		purchasesCodeLbl = new JLabel(FrameStrings.valueOfStringName("purchasescodelbl"));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesCodeLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesInfoPanel);
		purchasesCodeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesCodeLbl, (int)(50*sizeMultiplier), SpringLayout.NORTH, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesCodeLbl.setFont(mainFont);
		purchasesInfoPanel.add(purchasesCodeLbl);
		
		purchasesCodeTxt = new JTextField();
		purchasesCodeTxt.setEditable(false);
		purchasesCodeTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesCodeTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesCodeLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesCodeTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesCodeLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesCodeTxt, (int)(10*sizeMultiplier), SpringLayout.NORTH, purchasesCodeLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesCodeTxt, (int)(-50*sizeMultiplier), SpringLayout.NORTH, purchasesCodeLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesCodeTxt);
		purchasesCodeTxt.setColumns(10);
		
		purchasesNameLbl = new JLabel(FrameStrings.valueOfStringName("purchasesnamelbl"));
		purchasesNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purchasesNameLbl.setFont(mainFont);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, purchasesCodeTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesNameLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesCodeTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, purchasesCodeTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, purchasesCodeTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasesInfoPanel.add(purchasesNameLbl);
		
		purchasesNameTxt = new JTextField();
		purchasesNameTxt.setEditable(false);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesNameTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesNameLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesNameTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesNameLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		purchasesInfoPanel.add(purchasesNameTxt);
		purchasesNameTxt.setColumns(10);
		
		purchasesDescLbl = new JLabel(FrameStrings.valueOfStringName("purchasesdesclbl"));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesDescLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, purchasesNameTxt);
		purchasesDescLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purchasesDescLbl.setFont(mainFont);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesDescLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesDescLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesNameTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesDescLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesDescLbl);
		
		purchasesDescTxt = new JTextField();
		purchasesDescTxt.setEditable(false);
		purchasesDescTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesDescTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesDescLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesDescTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesDescTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesDescLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesDescTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesDescTxt);
		purchasesDescTxt.setColumns(10);
		
		purchasesQtyLbl = new JLabel(FrameStrings.valueOfStringName("purchasesqtylbl"));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, purchasesDescTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesQtyLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesDescTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesQtyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purchasesQtyLbl.setFont(mainFont);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesDescTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesQtyLbl, (int)(70*sizeMultiplier), SpringLayout.SOUTH, salesDescTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		purchasesInfoPanel.add(purchasesQtyLbl);
		
		purchasesQtyTxt = new JTextField();
		purchasesQtyTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(purchasesCurrentList.getSelectedIndex()<0&&purchasesItemsList.getSelectedIndex()<0)
					return;
				
				int newQty=-1;
				try{
					newQty = Integer.parseInt(purchasesQtyTxt.getText().toString());
					
				}
				catch(NumberFormatException ex) {
					purchasesQtyTxt.grabFocus();
					purchasesQtyTxt.selectAll();
					return;
				}
				if((newQty<=0)) {
					purchasesQtyTxt.grabFocus();
					purchasesQtyTxt.selectAll();
					return;
				}
				if(purchasesCurrentList.getSelectedIndex()>=0) {
					PurchaseDetails pdCurrent = purchasesCurrentList.getSelectedValue();
					
					currentPurchaseDetails.get(pdCurrent.getStock().getID()).setQty(newQty);
					int position = purchasesCurrentList.getSelectedIndex();
					purchasesCurrentListModel.setElementAt(currentPurchaseDetails.get(pdCurrent.getStock().getID()), position);
					double total = calculatePurchaseTotal();
					currentPurchase.setTotal(total);
					purchasesTotalTxt.setText(total+"");
				}
			}
		});
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesQtyTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesQtyLbl);
		purchasesQtyTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesQtyTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesQtyLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesQtyTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesQtyTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesQtyTxt);
		purchasesQtyTxt.setColumns(10);
		
		purchasesPriceLbl = new JLabel(FrameStrings.valueOfStringName("purchasespricelbl"));
		purchasesPriceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purchasesPriceLbl.setFont(mainFont);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesPriceLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, purchasesQtyTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesPriceLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesPriceLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesQtyTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesPriceLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesPriceLbl);
		
		purchasesPriceTxt = new JTextField();
		purchasesPriceTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(purchasesCurrentList.getSelectedIndex()<0&&purchasesItemsList.getSelectedIndex()<0)
					return;
				
				double newBoughtPrice=-1;
				try{
					newBoughtPrice = Double.parseDouble(purchasesPriceTxt.getText().toString());
					if(newBoughtPrice<0) {
						purchasesPriceTxt.grabFocus();
						purchasesPriceTxt.selectAll();
						return;
					}
				}
				catch(NumberFormatException ex) {
					if(!purchasesPriceTxt.getText().toString().matches("\\d+\\.")) {
						purchasesPriceTxt.grabFocus();
						purchasesPriceTxt.selectAll();
						return;
					}
				}
				
				
				
				if(purchasesCurrentList.getSelectedIndex()>=0) {
					PurchaseDetails pdCurrent = purchasesCurrentList.getSelectedValue();
					
					currentPurchaseDetails.get(pdCurrent.getStock().getID()).getStock().setBoughtPrice(newBoughtPrice);
					int position = purchasesCurrentList.getSelectedIndex();
					purchasesCurrentListModel.setElementAt(currentPurchaseDetails.get(pdCurrent.getStock().getID()), position);
					double total = calculatePurchaseTotal();
					currentPurchase.setTotal(total);
					purchasesTotalTxt.setText(total+"");
				}
			}
		});
		purchasesPriceTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesPriceTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesPriceLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesPriceTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesPriceTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, purchasesPriceLbl);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesPriceTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		purchasesInfoPanel.add(purchasesPriceTxt);
		purchasesPriceTxt.setColumns(10);
		
		purchasesClearBtn = new JButton(FrameStrings.valueOfStringName("purchasesclearbtn"));
		purchasesClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				purchasesCodeTxt.setText("");
				purchasesNameTxt.setText("");
				purchasesDescTxt.setText("");
				purchasesPriceTxt.setText("");
				purchasesQtyTxt.setText("");
				purchasesTotalTxt.setText("");
				purchasesDiscountTxt.setText("");
				purchasesCurrentListModel.removeAllElements();
				purchasesItemsList.clearSelection();
				purchasesSupplierList.clearSelection();
				currentPurchaseDetails.clear();
				purchasesSearchTxt.setText("");
				purchasesSearchSupplierTxt.setText("");
				currentPurchase.setSupplier(null);
				try {
					initializeStock();
					initializeSupplier();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		purchasesClearBtn.setFont(mainFont);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.NORTH, purchasesClearBtn, (int)(80*sizeMultiplier), SpringLayout.NORTH, purchasesPriceTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.WEST, purchasesClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.SOUTH, purchasesClearBtn, (int)(120*sizeMultiplier), SpringLayout.NORTH, purchasesPriceTxt);
		sl_purchasesInfoPanel.putConstraint(SpringLayout.EAST, purchasesClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, purchasesInfoPanel);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, purchasesPriceTxt);
		purchasesInfoPanel.add(purchasesClearBtn);
		
		purchasesTotalLbl = new JLabel(FrameStrings.valueOfStringName("purchasestotallbl"));
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesTotalLbl, (int)(15*sizeMultiplier), SpringLayout.SOUTH, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesTotalLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesTotalLbl, (int)(30*sizeMultiplier), SpringLayout.SOUTH, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesTotalLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		purchasesTotalLbl.setFont(mainFont);
		purchasePanel.add(purchasesTotalLbl);
		
		purchasesTotalTxt = new JTextField();
		purchasesTotalTxt.setEditable(false);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesTotalTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesTotalTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, purchasesTotalLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesTotalTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesTotalTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, purchasesTotalLbl);
		purchasesTotalTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		purchasePanel.add(purchasesTotalTxt);
		purchasesTotalTxt.setColumns(10);
		
		purchasesDiscountLbl = new JLabel(FrameStrings.valueOfStringName("purchasesdiscountlbl"));
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesDiscountLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, purchasesTotalLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesDiscountLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesDiscountLbl, (int)(60*sizeMultiplier), SpringLayout.NORTH, purchasesTotalLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesDiscountLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, purchasePanel);
		purchasesDiscountLbl.setFont(mainFont);
		purchasePanel.add(purchasesDiscountLbl);
		
		purchasesDiscountTxt = new JTextField();
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesDiscountTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, purchasesTotalTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesDiscountTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, purchasesDiscountLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesDiscountTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, purchasesTotalTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesDiscountTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, purchasesDiscountLbl);
		purchasesDiscountTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		purchasePanel.add(purchasesDiscountTxt);
		purchasesDiscountTxt.setColumns(10);
		
		purchasesConfirmPurchase = new JButton(FrameStrings.valueOfStringName("purchasesconfirmbtn"));
		purchasesConfirmPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentPurchaseDetails.values().size()==0) {
					return;
				}
				currentPurchase.setLocalDateTime(LocalDateTime.now());
				double discount=-1;
				if(purchasesDiscountTxt.getText().toString().equals("")||purchasesDiscountTxt.getText().toString()==null) {
					discount=0;
				}
				else {
					try {
						discount = Double.parseDouble(purchasesDiscountTxt.getText().toString());
					}
					catch(NumberFormatException ex) {
						purchasesDiscountTxt.grabFocus();
						purchasesDiscountTxt.selectAll();
						return;
					}
				}
				if(!checkPurchaseItems()) {
					JOptionPane.showMessageDialog(RootFrame.this, 
							ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
							FrameStrings.valueOfStringName("purchaseitemzeropriceerror")+
							ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
					return;
				}
				currentPurchase.setTotal(calculatePurchaseTotal());
				currentPurchase.setDiscount(discount);
				try {
					databaseThread.update("insert into Purchases(id_purchase,id_supplier,date,discount,total) values("+currentPurchase.getID()
							+"," +currentPurchase.getSupplier().getID()+",\""+currentPurchase.getDate()+"\","+currentPurchase.getDiscount()+","
									+ currentPurchase.getTotal()+")");
					
					for(PurchaseDetails pd:currentPurchaseDetails.values()) {
						databaseThread.update("insert into Purchases_Details(id_stock,id_purchase,qty,total) values("+pd.getStock().getID()+","
								+ currentPurchase.getID()+","+pd.getQty()+","+pd.getTotal()+")"); 
						int newStockqty = pd.getStock().getqty()+pd.getQty();
						
						databaseThread.update("update Stock set qty="+newStockqty+", bought_price="+pd.getStock().getBoughtPrice()+" where id_stock="+pd.getStock().getID());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				purchasesClearBtn.doClick();
				currentPurchase=new Purchase(null);
			}

			private boolean checkPurchaseItems() {
				Iterator<PurchaseDetails> it = currentPurchaseDetails.values().iterator();
				double total=0;
				while(it.hasNext()) {
					PurchaseDetails pd = it.next();
					if(pd.getStock().getBoughtPrice()<=0)
						return false;
				}
				return true;
				
			}
			
		});
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesConfirmPurchase, (int)(20*sizeMultiplier), SpringLayout.SOUTH, purchasesDiscountTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesConfirmPurchase, (int)(115*sizeMultiplier), SpringLayout.WEST, purchasesCurrentListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesConfirmPurchase, (int)(60*sizeMultiplier), SpringLayout.SOUTH, purchasesDiscountTxt);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesConfirmPurchase, (int)(-115*sizeMultiplier), SpringLayout.EAST, purchasesCurrentListSP);
		purchasesConfirmPurchase.setFont(mainFont);
		purchasePanel.add(purchasesConfirmPurchase);
		
		purchasesSearchSupplierLbl = new JLabel(FrameStrings.valueOfStringName("purchasessearchsupplierlbl"));
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesSearchSupplierLbl, (int)(20*sizeMultiplier), SpringLayout.SOUTH, purchasesItemsListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesSearchSupplierLbl, (int)(-420*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesSearchSupplierLbl, (int)(40*sizeMultiplier), SpringLayout.SOUTH, purchasesItemsListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesSearchSupplierLbl, (int)(-240*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasesSearchSupplierLbl.setFont(mainFont);
		purchasePanel.add(purchasesSearchSupplierLbl);
		
		purchasesSearchSupplierTxt = new JTextField();
		purchasesSearchSupplierTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = purchasesSearchSupplierTxt.getText().toString().toLowerCase().trim();
				purchasesSupplierListModel.removeAllElements();
				Iterator<Supplier> it = suppliers.values().iterator();
				while(it.hasNext()) {
					Supplier s = it.next();
					if(s.getName().toLowerCase().contains(desired)||
							s.getPhone().toLowerCase().contains(desired)) {
						purchasesSupplierListModel.addElement(s);
					}
				}
				if(purchasesSupplierListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesSearchSupplierTxt, (int)(15*sizeMultiplier), SpringLayout.SOUTH, purchasesItemsListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.WEST, purchasesSearchSupplierTxt, (int)(10*sizeMultiplier), SpringLayout.EAST, purchasesSearchSupplierLbl);
		sl_purchasesPanel.putConstraint(SpringLayout.NORTH, purchasesSupplierListSP, (int)(20*sizeMultiplier), SpringLayout.SOUTH, purchasesSearchSupplierTxt);
		purchasesSearchSupplierTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_purchasesPanel.putConstraint(SpringLayout.SOUTH, purchasesSearchSupplierTxt, (int)(45*sizeMultiplier), SpringLayout.SOUTH, purchasesItemsListSP);
		sl_purchasesPanel.putConstraint(SpringLayout.EAST, purchasesSearchSupplierTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, purchasePanel);
		purchasePanel.add(purchasesSearchSupplierTxt);
		purchasesSearchSupplierTxt.setColumns(10);
		
				//***************************************DEBT PANEL***************************************\\
		
		debtPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("debtpaneltabname"), null, debtPanel, null);
		SpringLayout sl_debtsPanel = new SpringLayout();
		debtPanel.setLayout(sl_debtsPanel);
		
		debtsCurrentListModel = new DefaultListModel<DebtDetails>();
		debtsCurrentList = new JList<DebtDetails>(debtsCurrentListModel);
		debtsCurrentList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					debtsCurrentList.setSelectedIndex(debtsCurrentList.locationToIndex(e.getPoint()));
					if(debtsCurrentList.getSelectedIndex()>=0)
						debtsCurrentItemDeletePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		debtsCurrentList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(debtsCurrentList.getSelectedIndex()>=0) {
					DebtDetails dd = debtsCurrentList.getSelectedValue();
					
					debtsCodeTxt.setText(dd.getStock().getCode());
					debtsNameTxt.setText(dd.getStock().getName());
					debtsQtyTxt.setText(dd.getQty()+"");
					debtsPriceTxt.setText((dd.getStock().getSalePrice()*dollarValue)+"");
					debtsQtyTxt.grabFocus();
					debtsCurrentList.repaint();
					debtsItemsList.clearSelection();
				}
			}
		});
		debtsCurrentListSP = new JScrollPane(debtsCurrentList);
		debtsCurrentListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("debtscurrentlisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)debtsCurrentListSP.getBorder()).setTitleFont(mainFont);
		debtsCurrentList.setFont(mainFont);
		debtsCurrentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsCurrentListSP, (int)(-150*sizeMultiplier), SpringLayout.SOUTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsCurrentListSP, (int)(20*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsCurrentListSP, (int)(480*sizeMultiplier), SpringLayout.WEST, debtPanel);
		debtPanel.add(debtsCurrentListSP);
		
		debtsItemsListModel = new DefaultListModel<Stock>();
		debtsItemsList = new JList<Stock>(debtsItemsListModel);
		debtsItemsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(debtsItemsList.getSelectedIndex()>=0) {
					Stock s = debtsItemsList.getSelectedValue();
					if(stocks.get(s.getID()).getqty()<1) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("insufficiantstockerror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						debtsItemsList.clearSelection();
						return;
					}
					if(stocks.get(s.getID()).getSalePrice()<=0) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("pricestockerror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						debtsItemsList.clearSelection();
						return;
					}
					int pos = debtsItemsList.getSelectedIndex();
					setDebtsFields(true);
					if(debtsItemsList.getSelectedIndex()<0)
						debtsItemsList.setSelectedIndex(pos);
					debtsItemsList.ensureIndexIsVisible(pos);
					if(currentDebt.getCustomer()==null) {
						JOptionPane.showMessageDialog(RootFrame.this, 
								ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
								FrameStrings.valueOfStringName("selectcustomererror")+
								ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE);
						debtsItemsList.clearSelection();
						return;
					}
					
					debtsCodeTxt.setText(s.getCode());
					debtsNameTxt.setText(s.getName());
					debtsQtyTxt.setText(1+"");
					debtsPriceTxt.setText(s.getBoughtPrice()+"");
					
					if(currentDebtDetails.containsKey(s.getID())){
						DebtDetails sd = new DebtDetails(s, currentDebt, currentDebtDetails.get(s.getID()).getQty());
						
						debtsCurrentList.setSelectedValue(sd, true);
						if(debtsCurrentList.getSelectedIndex()<0||sd.compareTo(debtsCurrentList.getSelectedValue())!=0)
							for (int i = 0;i<debtsCurrentListModel.getSize(); i++)
								if (sd.compareTo(debtsCurrentListModel.getElementAt(i))==0) {
									debtsCurrentList.setSelectedIndex(i);
									debtsCurrentList.ensureIndexIsVisible(i);
									break;
								}
						
					}
					else {
						DebtDetails sd = new DebtDetails(s, currentDebt, 1);
						currentDebtDetails.put(s.getID(), sd);
						debtsCurrentListModel.addElement(sd);
						double total = calculateDebtTotal();
						debtsTotalTxt.setText(total+"");
						currentDebt.setTotal(total);
					}
					debtsQtyTxt.grabFocus();
				}
			}
		});
		debtsItemsListSP = new JScrollPane(debtsItemsList);
		debtsItemsListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("debtsitemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)debtsItemsListSP.getBorder()).setTitleFont(mainFont);
		debtsItemsList.setFont(mainFont);
		debtsItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsItemsListSP, (int)(100*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsItemsListSP, (int)(-400*sizeMultiplier), SpringLayout.SOUTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsItemsListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtPanel.add(debtsItemsListSP);
		
		debtsCustomerListModel = new DefaultListModel<Customer>();
		debtsCustomerList = new JList<Customer>(debtsCustomerListModel);
		debtsCustomerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(debtsCustomerList.getSelectedIndex()>=0) {
					int pos = debtsCustomerList.getSelectedIndex();
					setDebtsFields(true);
					if(debtsCustomerList.getSelectedIndex()<0) {
						debtsCustomerList.setSelectedIndex(pos);
						debtsCustomerList.ensureIndexIsVisible(pos);
						currentDebt.setCustomer(debtsCustomerList.getSelectedValue());
					}
					else {
						currentDebt.setCustomer(debtsCustomerList.getSelectedValue());
					}
					
				}
			}
		});
		debtsCustomerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		debtsCustomerList.setFont(mainFont);
		debtsCustomerListSP = new JScrollPane(debtsCustomerList);
		debtsCustomerListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("debtscustomerslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		((TitledBorder)debtsCustomerListSP.getBorder()).setTitleFont(mainFont);
		
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsCustomerListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsCustomerListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsCustomerListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtPanel.add(debtsCustomerListSP);
		
		debtsDebtsListModel = new DefaultListModel<Debt>();
		debtsDebtsList = new JList<Debt>(debtsDebtsListModel);
		debtsDebtsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					debtsDebtsList.setSelectedIndex(debtsDebtsList.locationToIndex(e.getPoint()));
					if(debtsDebtsList.getSelectedIndex()>=0)
						debtClosePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		debtsDebtsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(debtsDebtsList.getSelectedIndex()>=0) {
					int pos = debtsDebtsList.getSelectedIndex();
					setDebtsFields(false);
					if(debtsDebtsList.getSelectedIndex()<0) {
						debtsDebtsList.setSelectedIndex(pos);
						debtsDebtsList.ensureIndexIsVisible(pos);
						Debt debt = debtsDebtsList.getSelectedValue();
						
						debtsRemainingLbl.setText(FrameStrings.valueOfStringName("debtsremaininglbl")+debt.getRemaining());
						
						debtsClosedLbl.setText(
								"<html><font color=\"green\">"+
								FrameStrings.valueOfStringName("debtsstatusopen")+
								"</font></html>");
					}
					else {
						Debt debt = debtsDebtsList.getSelectedValue();
						
						debtsRemainingLbl.setText(FrameStrings.valueOfStringName("debtsremaininglbl")+debt.getRemaining());
						
						debtsClosedLbl.setText(
								"<html><font color=\"green\">"+
								FrameStrings.valueOfStringName("debtsstatusopen")+
								"</font></html>");
					}
				}
			}
		});
		debtsDebtsListSP = new JScrollPane(debtsDebtsList);
		debtsDebtsListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("debtsdebtslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		((TitledBorder)debtsDebtsListSP.getBorder()).setTitleFont(mainFont);
		
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsDebtsListSP, (int)(25*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsDebtsListSP, (int)(25*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsDebtsListSP, (int)(-30*sizeMultiplier), SpringLayout.NORTH, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsDebtsListSP, (int)(480*sizeMultiplier), SpringLayout.WEST, debtPanel);
		debtsDebtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		debtsDebtsList.setFont(mainFont);
		debtPanel.add(debtsDebtsListSP);
		
		debtsSearchTxt = new JTextField();
		debtsSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = debtsSearchTxt.getText().toString().toLowerCase().trim();
				setDebtsFields(true);
				debtsSearchTxt.setText(desired);
				debtsItemsListModel.removeAllElements();
				Iterator<Stock> it = stocks.values().iterator();
				while(it.hasNext()) {
					Stock s = it.next();
					if(s.getCode().toLowerCase().contains(desired)||
							s.getName().toLowerCase().contains(desired)||
							s.getDesc().toLowerCase().contains(desired)) {
						debtsItemsListModel.addElement(s);
					}
				}
				if(debtsItemsListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsSearchTxt, (int)(35*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsSearchTxt, (int)(75*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		debtsSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtPanel.add(debtsSearchTxt);
		debtsSearchTxt.setColumns(10);
		
		debtsSearchLbl = new JLabel(FrameStrings.valueOfStringName("debtssearchlbl"));
		debtsSearchLbl.setFont(mainFont);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, debtsSearchTxt);
		debtPanel.add(debtsSearchLbl);
		
		debtsInfoPanel = new JPanel();
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsInfoPanel, (int)(-510*sizeMultiplier), SpringLayout.EAST, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsInfoPanel, (int)(50*sizeMultiplier), SpringLayout.EAST, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, debtPanel);
		debtPanel.add(debtsInfoPanel);
		SpringLayout sl_debtsInfoPanel = new SpringLayout();
		debtsInfoPanel.setLayout(sl_debtsInfoPanel);
		
		debtsCodeLbl = new JLabel(FrameStrings.valueOfStringName("debtscodelbl"));
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsCodeLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsInfoPanel);
		debtsCodeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsCodeLbl, (int)(50*sizeMultiplier), SpringLayout.NORTH, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsCodeLbl.setFont(mainFont);
		debtsInfoPanel.add(debtsCodeLbl);
		
		debtsCodeTxt = new JTextField();
		debtsCodeTxt.setEditable(false);
		debtsCodeTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsCodeTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsCodeLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsCodeTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsCodeLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsCodeTxt, (int)(10*sizeMultiplier), SpringLayout.NORTH, debtsCodeLbl);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsCodeTxt, (int)(-50*sizeMultiplier), SpringLayout.NORTH, debtsCodeLbl);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsCodeTxt);
		debtsCodeTxt.setColumns(10);
		
		debtsNameLbl = new JLabel(FrameStrings.valueOfStringName("debtsnamelbl"));
		debtsNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		debtsNameLbl.setFont(mainFont);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, debtsCodeTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsNameLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsCodeTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, debtsCodeTxt);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, debtsCodeTxt);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtsInfoPanel.add(debtsNameLbl);
		
		debtsNameTxt = new JTextField();
		debtsNameTxt.setEditable(false);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsNameTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsNameLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsNameTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsNameLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		debtsInfoPanel.add(debtsNameTxt);
		debtsNameTxt.setColumns(10);
		
		debtsDescPayementLbl = new JLabel(FrameStrings.valueOfStringName("debtspayementlbl"));
		debtsDescPayementLbl.setEnabled(false);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsDescPayementLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, debtsNameTxt);
		debtsDescPayementLbl.setHorizontalAlignment(SwingConstants.CENTER);
		debtsDescPayementLbl.setFont(mainFont);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsDescPayementLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsDescPayementLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsNameTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsDescPayementLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsDescPayementLbl);
		
		debtsDescPayementTxt = new JTextField();
		debtsDescPayementTxt.setEnabled(false);
		debtsDescPayementTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(debtsDebtsList.getSelectedIndex()<0)
					return;
				Debt debt = debtsDebtsList.getSelectedValue();
				double payement=-1;
				try{
					payement = Double.parseDouble(debtsDescPayementTxt.getText().toString());
				}
				catch(NumberFormatException ez) {
					debtsDescPayementTxt.grabFocus();
					debtsDescPayementTxt.selectAll();
					return;
				}
				if(payement<=0) {
					debtsDescPayementTxt.grabFocus();
					debtsDescPayementTxt.selectAll();
					return;
				}
				DebtLog dl = new DebtLog(debt, payement);
				double remaining = debt.getRemaining()-dl.getAmount();
				if(remaining<0) {
					debtsDescPayementTxt.grabFocus();
					debtsDescPayementTxt.selectAll();
					return;
				}
				try {
					databaseThread.update("insert into Debts_Log(id_debt,amount,date) values("
							+debt.getID()+","+payement+",\""+dl.getDate()+"\""
							+ ")");
					if(remaining!=0)
						databaseThread.update("update Debts set remaining="+remaining+" where id_debt="+debt.getID());
					else {
						databaseThread.update("update Debts set remaining=0, closed=1 where id_debt="+debt.getID());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				setDebtsFields(true);
				
				
			}
		});
		debtsDescPayementTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsDescPayementTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsDescPayementLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsDescPayementTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsDescPayementTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsDescPayementLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsDescPayementTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsDescPayementTxt);
		debtsDescPayementTxt.setColumns(10);
		
		debtsQtyLbl = new JLabel(FrameStrings.valueOfStringName("debtsqtylbl"));
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, debtsDescPayementTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsQtyLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsDescPayementTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsQtyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		debtsQtyLbl.setFont(mainFont);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsQtyLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, salesDescTxt);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsQtyLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, salesInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsQtyLbl, (int)(70*sizeMultiplier), SpringLayout.SOUTH, salesDescTxt);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsQtyLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, salesInfoPanel);
		debtsInfoPanel.add(debtsQtyLbl);
		
		debtsQtyTxt = new JTextField();
		debtsQtyTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(debtsCurrentList.getSelectedIndex()<0&&debtsItemsList.getSelectedIndex()<0)
					return;
				
				int newQty=-1;
				try{
					newQty = Integer.parseInt(debtsQtyTxt.getText().toString());
					
				}
				catch(NumberFormatException ex) {
					debtsQtyTxt.grabFocus();
					debtsQtyTxt.selectAll();
					return;
				}
				if((newQty<=0)) {
					debtsQtyTxt.grabFocus();
					debtsQtyTxt.selectAll();
					return;
				}
				if(debtsCurrentList.getSelectedIndex()>=0) {
					DebtDetails ddCurrent = debtsCurrentList.getSelectedValue();
					
					currentDebtDetails.get(ddCurrent.getStock().getID()).setQty(newQty);
					int position = debtsCurrentList.getSelectedIndex();
					debtsCurrentListModel.setElementAt(currentDebtDetails.get(ddCurrent.getStock().getID()), position);
					double total = calculateDebtTotal();
					currentDebt.setTotal(total);
					debtsTotalTxt.setText(total+"");
				}
			}
		});
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsQtyTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsQtyLbl);
		debtsQtyTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsQtyTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsQtyLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsQtyTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsQtyTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsQtyTxt);
		debtsQtyTxt.setColumns(10);
		
		debtsPriceLbl = new JLabel(FrameStrings.valueOfStringName("debtspricelbl"));
		debtsPriceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		debtsPriceLbl.setFont(mainFont);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsPriceLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, debtsQtyTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsPriceLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsPriceLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsQtyTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsPriceLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsPriceLbl);
		
		debtsPriceTxt = new JTextField();
		debtsPriceTxt.setEditable(false);
		debtsPriceTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsPriceTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsPriceLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsPriceTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsPriceTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, debtsPriceLbl);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsPriceTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		debtsInfoPanel.add(debtsPriceTxt);
		debtsPriceTxt.setColumns(10);
		
		debtsClearBtn = new JButton(FrameStrings.valueOfStringName("debtsclearbtn"));
		debtsClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				debtsCodeTxt.setText("");
				debtsNameTxt.setText("");
				debtsDescPayementTxt.setText("");
				debtsPriceTxt.setText("");
				debtsQtyTxt.setText("");
				debtsTotalTxt.setText("");
				debtsDiscountTxt.setText("");
				debtsCurrentListModel.removeAllElements();
				debtsItemsList.clearSelection();
				debtsDebtsList.clearSelection();
				debtsCustomerList.clearSelection();
				currentDebtDetails.clear();
				debtsSearchTxt.setText("");
				purchasesSearchSupplierTxt.setText("");
				currentDebt.setCustomer(null);
				try {
					initializeDebts();
					initializeCustomer();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				debtsQtyTxt.setEnabled(true);
				debtsQtyLbl.setEnabled(true);
				debtsDescPayementTxt.setText("");
				debtsDescPayementTxt.setEnabled(false);
				debtsDescPayementLbl.setEnabled(false);
				debtsClosedLbl.setText("");
				debtsRemainingLbl.setText("");
				debtsDiscountLbl.setEnabled(true);
				debtsDiscountTxt.setEnabled(true);
				debtsConfirmDebt.setEnabled(true);
			}
		});
		debtsClearBtn.setFont(mainFont);
		sl_debtsInfoPanel.putConstraint(SpringLayout.NORTH, debtsClearBtn, (int)(80*sizeMultiplier), SpringLayout.NORTH, debtsPriceTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.WEST, debtsClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsInfoPanel.putConstraint(SpringLayout.SOUTH, debtsClearBtn, (int)(120*sizeMultiplier), SpringLayout.NORTH, debtsPriceTxt);
		sl_debtsInfoPanel.putConstraint(SpringLayout.EAST, debtsClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, debtsInfoPanel);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, debtsPriceTxt);
		debtsInfoPanel.add(debtsClearBtn);
		
		debtsTotalLbl = new JLabel(FrameStrings.valueOfStringName("debtstotallbl"));
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsTotalLbl, (int)(15*sizeMultiplier), SpringLayout.SOUTH, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsTotalLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsTotalLbl, (int)(30*sizeMultiplier), SpringLayout.SOUTH, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsTotalLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, debtPanel);
		debtsTotalLbl.setFont(mainFont);
		debtPanel.add(debtsTotalLbl);
		
		debtsTotalTxt = new JTextField();
		debtsTotalTxt.setEditable(false);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsTotalTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsTotalTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, debtsTotalLbl);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsTotalTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsTotalTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, debtsTotalLbl);
		debtsTotalTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		debtPanel.add(debtsTotalTxt);
		debtsTotalTxt.setColumns(10);
		
		debtsDiscountLbl = new JLabel(FrameStrings.valueOfStringName("debtsdiscountlbl"));
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsDiscountLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, debtsTotalLbl);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsDiscountLbl, (int)(70*sizeMultiplier), SpringLayout.WEST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsDiscountLbl, (int)(60*sizeMultiplier), SpringLayout.NORTH, debtsTotalLbl);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsDiscountLbl, (int)(170*sizeMultiplier), SpringLayout.WEST, debtPanel);
		debtsDiscountLbl.setFont(mainFont);
		debtPanel.add(debtsDiscountLbl);
		
		debtsDiscountTxt = new JTextField();
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsDiscountTxt, (int)(10*sizeMultiplier), SpringLayout.SOUTH, debtsTotalTxt);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsDiscountTxt, (int)(20*sizeMultiplier), SpringLayout.EAST, debtsDiscountLbl);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsDiscountTxt, (int)(40*sizeMultiplier), SpringLayout.SOUTH, debtsTotalTxt);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsDiscountTxt, (int)(240*sizeMultiplier), SpringLayout.EAST, debtsDiscountLbl);
		debtsDiscountTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		debtPanel.add(debtsDiscountTxt);
		debtsDiscountTxt.setColumns(10);
		
		debtsConfirmDebt = new JButton(FrameStrings.valueOfStringName("debtsconfirmbtn"));
		debtsConfirmDebt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentDebtDetails.values().size()==0) {
					return;
				}
				currentDebt.setLocalDateTime(LocalDateTime.now());
				double discount=-1;
				if(debtsDiscountTxt.getText().toString().equals("")||debtsDiscountTxt.getText().toString()==null) {
					discount=0;
				}
				else {
					try {
						discount = Double.parseDouble(debtsDiscountTxt.getText().toString());
					}
					catch(NumberFormatException ex) {
						debtsDiscountTxt.grabFocus();
						debtsDiscountTxt.selectAll();
						return;
					}
				}
				
				currentDebt.setTotal(calculateDebtTotal());
				currentDebt.setDiscount(discount);
				boolean newCritical=false;
				try {
					databaseThread.update("insert into Debts(id_debt,id_customer,date,total,closed,remaining,discount,dollar_value) values("
							+currentDebt.getID()+","+currentDebt.getCustomer().getID()+",\""+currentDebt.getDate()+"\","+currentDebt.getTotal()+","
							+"0,"+(currentDebt.getTotal()-currentDebt.getDiscount())+","+currentDebt.getDiscount()+","+dollarValue
							+ ")");
					
					
					for(DebtDetails dd:currentDebtDetails.values()) {
						databaseThread.update("insert into Debts_Details(id_stock,id_debt,qty,total) values("
								+dd.getStock().getID()+","+currentDebt.getID()+","+dd.getQty()+","+dd.getTotal()
								+ ")"); 
						int newStockqty = dd.getStock().getqty()-dd.getQty();
						if(newStockqty<=CRITICAL_STOCKVALUE)
							newCritical=true;
						databaseThread.update("update Stock set qty="+newStockqty+" where id_stock="+dd.getStock().getID());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				debtsClearBtn.doClick();
				currentDebt=new Debt(null);
				if(newCritical)
					newCriticalValuesound();
			}
		});
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsConfirmDebt, (int)(20*sizeMultiplier), SpringLayout.SOUTH, debtsDiscountTxt);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsConfirmDebt, (int)(115*sizeMultiplier), SpringLayout.WEST, debtsCurrentListSP);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsConfirmDebt, (int)(60*sizeMultiplier), SpringLayout.SOUTH, debtsDiscountTxt);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsConfirmDebt, (int)(-115*sizeMultiplier), SpringLayout.EAST, debtsCurrentListSP);
		debtsConfirmDebt.setFont(mainFont);
		debtPanel.add(debtsConfirmDebt);
		
		debtsSearchCustomerLbl = new JLabel(FrameStrings.valueOfStringName("debtssearchcustomerlbl"));
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsCurrentListSP, (int)(-20*sizeMultiplier), SpringLayout.NORTH, debtsSearchCustomerLbl);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsSearchCustomerLbl, (int)(20*sizeMultiplier), SpringLayout.SOUTH, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsSearchCustomerLbl, (int)(-420*sizeMultiplier), SpringLayout.EAST, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsSearchCustomerLbl, (int)(40*sizeMultiplier), SpringLayout.SOUTH, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsSearchCustomerLbl, (int)(-240*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtsSearchCustomerLbl.setFont(mainFont);
		debtPanel.add(debtsSearchCustomerLbl);
		
		debtsSearchCustomerTxt = new JTextField();
		debtsSearchCustomerTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = debtsSearchCustomerTxt.getText().toString().toLowerCase().trim();
				setDebtsFields(true);
				debtsSearchCustomerTxt.setText(desired);
				debtsCustomerListModel.removeAllElements();
				Iterator<Customer> it = customers.values().iterator();
				while(it.hasNext()) {
					Customer s = it.next();
					if(s.getName().toLowerCase().contains(desired)||
							s.getPhone().toLowerCase().contains(desired)) {
						debtsCustomerListModel.addElement(s);
					}
				}
				if(debtsCustomerListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsCustomerListSP, (int)(20*sizeMultiplier), SpringLayout.SOUTH, debtsSearchCustomerTxt);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsSearchCustomerTxt, (int)(15*sizeMultiplier), SpringLayout.SOUTH, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsSearchCustomerTxt, (int)(10*sizeMultiplier), SpringLayout.EAST, debtsSearchCustomerLbl);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsSearchCustomerTxt, (int)(45*sizeMultiplier), SpringLayout.SOUTH, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsSearchCustomerTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, debtPanel);
		debtsSearchCustomerTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		debtPanel.add(debtsSearchCustomerTxt);
		debtsSearchCustomerTxt.setColumns(10);
		
		debtsRemainingLbl = new JLabel("");
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsRemainingLbl, (int)(290*sizeMultiplier), SpringLayout.EAST, debtsDebtsListSP);
		debtsRemainingLbl.setFont(mainFont);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsRemainingLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsRemainingLbl, (int)(20*sizeMultiplier), SpringLayout.EAST, debtsDebtsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsRemainingLbl, (int)(120*sizeMultiplier), SpringLayout.NORTH, debtPanel);
		debtPanel.add(debtsRemainingLbl);
		
		debtsClosedLbl = new JLabel("");
		debtsClosedLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_debtsPanel.putConstraint(SpringLayout.NORTH, debtsClosedLbl, (int)(0*sizeMultiplier), SpringLayout.NORTH, debtsRemainingLbl);
		sl_debtsPanel.putConstraint(SpringLayout.WEST, debtsClosedLbl, (int)(-150*sizeMultiplier), SpringLayout.WEST, debtsItemsListSP);
		sl_debtsPanel.putConstraint(SpringLayout.SOUTH, debtsClosedLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, debtsRemainingLbl);
		sl_debtsPanel.putConstraint(SpringLayout.EAST, debtsClosedLbl, (int)(-20*sizeMultiplier), SpringLayout.WEST, debtsItemsListSP);
		debtsClosedLbl.setFont(mainFont);
		debtPanel.add(debtsClosedLbl);
		
				//***************************************STOCK PANEL***************************************\\
		
		stockPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("stockpaneltabname"), null, stockPanel, null);
		SpringLayout sl_stockPanel = new SpringLayout();
		stockPanel.setLayout(sl_stockPanel);
		
		stockItemsListModel = new DefaultListModel<Stock>();
		stockItemsList = new JList<Stock>(stockItemsListModel);
		stockItemsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					stockItemsList.setSelectedIndex(stockItemsList.locationToIndex(e.getPoint()));
					if(stockItemsList.getSelectedIndex()>=0)
						stockDeletePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		stockItemsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(stockItemsList.getSelectedIndex()>=0) {
					Stock s = stockItemsList.getSelectedValue();
					stockCodeTxt.setText(s.getCode());
					stockDescTxt.setText(s.getDesc());
					stockNameTxt.setText(s.getName());
					stockPriceTxt.setText(s.getSalePrice()+"");
				}
			}
		});
		stockItemsListSP = new JScrollPane(stockItemsList);
		stockItemsListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("stockitemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)stockItemsListSP.getBorder()).setTitleFont(mainFont);
		stockItemsList.setFont(mainFont);
		stockItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockItemsListSP, (int)(120*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockItemsListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockItemsListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockPanel);
		stockPanel.add(stockItemsListSP);
		
		stockSearchTxt = new JTextField();
		stockSearchTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				stockCodeTxt.setText("");
				stockDescTxt.setText("");
				stockNameTxt.setText("");
				stockPriceTxt.setText("");
			}
		});
		stockSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = stockSearchTxt.getText().toString().toLowerCase().trim();
				stockItemsListModel.removeAllElements();
				Iterator<Stock> it = stocks.values().iterator();
				while(it.hasNext()) {
					Stock s = it.next();
					if(s.getCode().toLowerCase().contains(desired)||
							s.getName().toLowerCase().contains(desired)||
							s.getDesc().toLowerCase().contains(desired)) {
						stockItemsListModel.addElement(s);
					}
				}
				if(stockItemsListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		stockSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockSearchTxt, (int)(25*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockSearchTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockPanel);
		stockPanel.add(stockSearchTxt);
		stockSearchTxt.setColumns(10);
		
		stockSearchLbl = new JLabel(FrameStrings.valueOfStringName("stocksearchlbl"));
		stockSearchLbl.setFont(mainFont);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, stockSearchTxt);
		stockPanel.add(stockSearchLbl);
		
		stockInfoPanel = new JPanel();
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockInfoPanel, (int)(165*sizeMultiplier), SpringLayout.WEST, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockInfoPanel, (int)(-121*sizeMultiplier), SpringLayout.WEST, stockItemsListSP);
		stockPanel.add(stockInfoPanel);
		SpringLayout sl_stockInfoPanel = new SpringLayout();
		stockInfoPanel.setLayout(sl_stockInfoPanel);
		
		stockCodeLbl = new JLabel(FrameStrings.valueOfStringName("stockcodelbl"));
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockCodeLbl, (int)(40*sizeMultiplier), SpringLayout.NORTH, stockInfoPanel);
		stockCodeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockCodeLbl, (int)(20*sizeMultiplier), SpringLayout.NORTH, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockCodeLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockCodeLbl, (int)(50*sizeMultiplier), SpringLayout.NORTH, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockCodeLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockCodeLbl.setFont(mainFont);
		stockInfoPanel.add(stockCodeLbl);
		
		stockCodeTxt = new JTextField();
		stockCodeTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockConfirmstock.doClick();
			}
		});
		stockCodeTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockCodeTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, stockCodeLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockCodeTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockCodeLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockCodeTxt, (int)(10*sizeMultiplier), SpringLayout.NORTH, stockCodeLbl);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockCodeTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockCodeTxt, (int)(-50*sizeMultiplier), SpringLayout.NORTH, stockCodeLbl);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockCodeTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockCodeTxt);
		stockCodeTxt.setColumns(10);
		
		stockNameLbl = new JLabel(FrameStrings.valueOfStringName("stocknamelbl"));
		stockNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		stockNameLbl.setFont(mainFont);
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, stockCodeTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockNameLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockCodeTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockNameLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, stockCodeTxt);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, stockCodeTxt);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockPanel);
		stockInfoPanel.add(stockNameLbl);
		
		stockNameTxt = new JTextField();
		stockNameTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockConfirmstock.doClick();
			}
		});
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockNameTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, stockNameLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockNameTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockNameLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		stockInfoPanel.add(stockNameTxt);
		stockNameTxt.setColumns(10);
		
		stockDescLbl = new JLabel(FrameStrings.valueOfStringName("stockdesclbl"));
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockDescLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, stockNameTxt);
		stockDescLbl.setHorizontalAlignment(SwingConstants.CENTER);
		stockDescLbl.setFont(mainFont);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockDescLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockDescLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockNameTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockDescLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockDescLbl);
		
		stockDescTxt = new JTextField();
		stockDescTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockConfirmstock.doClick();
			}
		});
		stockDescTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockDescTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, stockDescLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockDescTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockDescTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockDescLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockDescTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockDescTxt);
		stockDescTxt.setColumns(10);
		
		stockPriceLbl = new JLabel(FrameStrings.valueOfStringName("stockpricelbl"));
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockPriceLbl, (int)(30*sizeMultiplier), SpringLayout.SOUTH, stockDescTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockPriceLbl, (int)(50*sizeMultiplier), SpringLayout.SOUTH, stockDescTxt);
		stockPriceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		stockPriceLbl.setFont(mainFont);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockPriceLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockPriceLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockPriceLbl);
		
		stockPriceTxt = new JTextField();
		stockPriceTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockConfirmstock.doClick();
			}
		});
		stockPriceTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockPriceTxt, (int)(40*sizeMultiplier), SpringLayout.NORTH, stockPriceLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockPriceTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockPriceTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, stockPriceLbl);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockPriceTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockPriceTxt);
		stockPriceTxt.setColumns(10);
		
		stockClearBtn = new JButton(FrameStrings.valueOfStringName("stockclearbtn"));
		stockClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockCodeTxt.setText("");
				stockDescTxt.setText("");
				stockNameTxt.setText("");
				stockPriceTxt.setText("");
				stockSearchTxt.setText("");
				
				try {
					initializeStock();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				stockItemsList.clearSelection();
			}
		});
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockClearBtn, (int)(-250*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockClearBtn, (int)(-90*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockClearBtn.setFont(mainFont);
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockClearBtn, (int)(80*sizeMultiplier), SpringLayout.NORTH, stockPriceTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockClearBtn, (int)(120*sizeMultiplier), SpringLayout.NORTH, stockPriceTxt);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockClearBtn, (int)(-70*sizeMultiplier), SpringLayout.EAST, stockPriceTxt);
		stockInfoPanel.add(stockClearBtn);
		
		stockConfirmstock = new JButton(FrameStrings.valueOfStringName("stockconfirmbtn"));
		stockConfirmstock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkFields()) {
					if(stockItemsList.getSelectedIndex()<0) {
						String name = stockNameTxt.getText().toString().trim();
						String code = stockCodeTxt.getText().toString().trim();
						String desc = stockDescTxt.getText().toString().trim();
						String price = stockPriceTxt.getText().toString().trim();
						
						Stock s = new Stock(name, desc, 0, 0, Double.parseDouble(price), code);
						stocks.put(s.getID(), s);
						
						try {
							databaseThread.update("insert into Stock(code,name,desc,qty,bought_price,sale_price) "
									+ "values(\""+s.getCode()+"\",\""+s.getName()+"\",\""+s.getDesc()+"\","+s.getqty()+","+s.getBoughtPrice()+","
											+ s.getSalePrice()+")");
							stockClearBtn.doClick();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						
						Stock s = stockItemsList.getSelectedValue();
						String name = stockNameTxt.getText().toString().trim();
						String code = stockCodeTxt.getText().toString().trim();
						String desc = stockDescTxt.getText().toString().trim();
						String price = stockPriceTxt.getText().toString().trim();
						s.setName(name);
						s.setCode(code);
						s.setDesc(desc);
						s.setSalePrice(Double.parseDouble(price));
						
						stocks.remove(s.getID());
						stocks.put(s.getID(), s);
						
						try {
							databaseThread.update("update Stock set code= "
									+ "\""+s.getCode()+"\",name=\""+s.getName()+"\",desc=\""+s.getDesc()+"\", sale_price="+ s.getSalePrice()+" where id_stock="+s.getID());
							stockClearBtn.doClick();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						checkOtherLists(s);
					}
					
				}
				else {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("errorfields"));
				}
			}

			private void checkOtherLists(Stock s) {
				if(currentSaleDetails.containsKey(s.getID())) {
					SaleDetails sd = currentSaleDetails.get(s.getID());
					for(int i=0;i<salesCurrentListModel.getSize();i++) {
						if(salesCurrentListModel.getElementAt(i).compareTo(sd)==0) {
							salesCurrentListModel.removeElementAt(i);
							break;
						}
					}
					currentSaleDetails.remove(sd.getStock().getID());
					
					double total = calculateSaleTotal();
					salesTotalTxt.setText(total+"");
					currentSale.setTotal(total);
				}
				if(currentPurchaseDetails.containsKey(s.getID())) {
					PurchaseDetails pd = currentPurchaseDetails.get(s.getID());
					for(int i=0;i<purchasesCurrentListModel.getSize();i++) {
						if(purchasesCurrentListModel.getElementAt(i).compareTo(pd)==0) {
							purchasesCurrentListModel.removeElementAt(i);
							break;
						}
					}
					currentPurchaseDetails.remove(pd.getStock().getID());
					purchasesTotalTxt.setText(calculatePurchaseTotal()+"");
				}
				if(currentDebtDetails.containsKey(s.getID())) {
					DebtDetails dd = currentDebtDetails.get(s.getID());
					for(int i=0;i<debtsCurrentListModel.getSize();i++) {
						if(debtsCurrentListModel.getElementAt(i).compareTo(dd)==0) {
							debtsCurrentListModel.removeElementAt(i);
							break;
						}
					}
					currentDebtDetails.remove(dd.getStock().getID());
					debtsTotalTxt.setText(calculateDebtTotal()+"");
				}
				
			}

			private boolean checkFields() {
				
				try {
					return !stockNameTxt.getText().trim().equals("")&&!stockNameTxt.getText().trim().isEmpty()&&
							Double.parseDouble(stockPriceTxt.getText().toString().toLowerCase().trim())>=0;
				}
				catch(NumberFormatException e) {
					return false;
				}
			}
		});
		sl_stockInfoPanel.putConstraint(SpringLayout.NORTH, stockConfirmstock, (int)(80*sizeMultiplier), SpringLayout.NORTH, stockPriceTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.WEST, stockConfirmstock, (int)(90*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockInfoPanel.putConstraint(SpringLayout.SOUTH, stockConfirmstock, (int)(120*sizeMultiplier), SpringLayout.NORTH, stockPriceTxt);
		sl_stockInfoPanel.putConstraint(SpringLayout.EAST, stockConfirmstock, (int)(250*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, stockConfirmstock, (int)(80*sizeMultiplier), SpringLayout.NORTH, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, stockConfirmstock, (int)(70*sizeMultiplier), SpringLayout.WEST, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.SOUTH, stockConfirmstock, (int)(-10*sizeMultiplier), SpringLayout.SOUTH, stockInfoPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, stockConfirmstock, (int)(-350*sizeMultiplier), SpringLayout.EAST, stockInfoPanel);
		stockInfoPanel.add(stockConfirmstock);
		stockConfirmstock.setFont(mainFont);
		
				//***************************************REPORTS PANEL***************************************\\
		
		reportsPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("reportspaneltabname"), null, reportsPanel, null);
		SpringLayout sl_reportsPanel = new SpringLayout();
		reportsPanel.setLayout(sl_reportsPanel);
		
		scrollPane = new JScrollPane();
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, scrollPane, (int)(150*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, scrollPane, (int)(20*sizeMultiplier), SpringLayout.WEST, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, scrollPane, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, scrollPane, (int)(-20*sizeMultiplier), SpringLayout.EAST, reportsPanel);
		reportsPanel.add(scrollPane);
		
		reportsTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		reportsTable = new JTable();
		reportsTable.setModel(reportsTableModel);
		reportsTable.setFont(mainFont);
		reportsTable.getTableHeader().setFont(mainFont);
		scrollPane.setViewportView(reportsTable);
		reportsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reportsTable.setRowHeight((int)(30*sizeMultiplier));
		
		reportsChoicesCbx = new JComboBox(FrameArrays.valueOfArrayName("reportschoicescbx"));
		reportsChoicesCbx.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				turnOnLocalPanel();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				turnOffLocalPanel();
			}
			private void turnOffLocalPanel() {
				reportsTableModel.setRowCount(0);
				switch(reportsChoicesCbx.getSelectedIndex()) {
					case 0:break;
					case 1:reportsSalesPanel.setVisible(false);break;
					case 2:reportsPurchasesPanel.setVisible(false);break;
					case 3:reportsDebtsPanel.setVisible(false);break;
					case 4:reportsStockPanel.setVisible(false);break;
					default:break;
				}
				
			}
			private void turnOnLocalPanel() {
				switch(reportsChoicesCbx.getSelectedIndex()) {
				case 0:break;
				case 1:reportsSalesPanel.setVisible(true);reportsTable.setComponentPopupMenu(reportsShowMoreDetailsPopup);
					((DefaultTableModel)reportsTable.getModel()).setColumnIdentifiers(FrameArrays.valueOfArrayName("reportssalestitles"));
					break;
				case 2:reportsPurchasesPanel.setVisible(true);reportsTable.setComponentPopupMenu(reportsShowMoreDetailsPopup);
					((DefaultTableModel)reportsTable.getModel()).setColumnIdentifiers(FrameArrays.valueOfArrayName("reportspurchasestitles"));
					break;
				case 3:reportsDebtsPanel.setVisible(true);reportsTable.setComponentPopupMenu(reportsShowMoreDetailsPopup);
					((DefaultTableModel)reportsTable.getModel()).setColumnIdentifiers(FrameArrays.valueOfArrayName("reportsdebtstitles"));
					break;
				case 4:reportsStockPanel.setVisible(true);reportsTable.setComponentPopupMenu(null);
					((DefaultTableModel)reportsTable.getModel()).setColumnIdentifiers(FrameArrays.valueOfArrayName("reportsstocktitles"));
					break;
					default:break;
				}
				
			}
		});
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsChoicesCbx, (int)(60*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsChoicesCbx, (int)(150*sizeMultiplier), SpringLayout.WEST, scrollPane);
		reportsChoicesCbx.setFont(mainFont);
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsChoicesCbx, (int)(20*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsChoicesCbx, (int)(0*sizeMultiplier), SpringLayout.WEST, scrollPane);
		reportsPanel.add(reportsChoicesCbx);
		
		reportsSalesPanel = new ReportsSalesPanel();
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsSalesPanel, (int)(20*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsSalesPanel, (int)(30*sizeMultiplier), SpringLayout.EAST, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsSalesPanel, (int)(-20*sizeMultiplier), SpringLayout.NORTH, scrollPane);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsSalesPanel, (int)(-20*sizeMultiplier), SpringLayout.EAST, reportsPanel);
		reportsPanel.add(reportsSalesPanel);
		reportsSalesPanel.setVisible(false);
		
		reportsPurchasesPanel = new ReportsPurchasesPanel();
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsPurchasesPanel, (int)(20*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsPurchasesPanel, (int)(30*sizeMultiplier), SpringLayout.EAST, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsPurchasesPanel, (int)(-20*sizeMultiplier), SpringLayout.NORTH, scrollPane);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsPurchasesPanel, (int)(-20*sizeMultiplier), SpringLayout.EAST, reportsPanel);
		reportsPanel.add(reportsPurchasesPanel);
		reportsPurchasesPanel.setVisible(false);
		
		reportsDebtsPanel = new ReportsDebtsPanel();
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsDebtsPanel, (int)(20*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsDebtsPanel, (int)(30*sizeMultiplier), SpringLayout.EAST, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsDebtsPanel, (int)(-20*sizeMultiplier), SpringLayout.NORTH, scrollPane);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsDebtsPanel, (int)(-20*sizeMultiplier), SpringLayout.EAST, reportsPanel);
		reportsPanel.add(reportsDebtsPanel);
		reportsDebtsPanel.setVisible(false);
		
		reportsStockPanel = new ReportsStockPanel();
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsStockPanel, (int)(20*sizeMultiplier), SpringLayout.NORTH, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsStockPanel, (int)(30*sizeMultiplier), SpringLayout.EAST, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsStockPanel, (int)(-20*sizeMultiplier), SpringLayout.NORTH, scrollPane);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsStockPanel, (int)(-20*sizeMultiplier), SpringLayout.EAST, reportsPanel);
		reportsPanel.add(reportsStockPanel);
		
		reportsOkBtn = new JButton(FrameStrings.valueOfStringName("reportsokbtn"));
		reportsOkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportsTableModel.setRowCount(0);
				try {
					switch(reportsChoicesCbx.getSelectedIndex()) {
						case 0:break;
						case 1:showReportSales();break;
						case 2:showReportPurchases();break;
						case 3:showReportDebts();break;
						case 4:showReportStocks();break;
						default:break;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void showReportSales() throws SQLException {
				
				Object[] values = reportsSalesPanel.getValues();
				LocalDate ld = (LocalDate) values[0];
				int mode = (Integer) values[1];
				DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
				ResultSet rs;
				double total=0;
				switch(mode) {
					case 0:case 1:
						rs = databaseThread.query("select * from Sales where date like \""+
								formatterDay.format(ld)+"%\"");
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_sale")+"",rs.getString("date"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsSalesPanel.setTotal(total);
						break;
					case 2:
						rs = databaseThread.query("select * from Sales where date like \""+
								formatterMonth.format(ld)+"%\"");
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_sale")+"",rs.getString("date"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsSalesPanel.setTotal(total);
						break;
					case 3:
						rs = databaseThread.query("select * from Sales where date like \""+
								formatterYear.format(ld)+"%\"");
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_sale")+"",rs.getString("date"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsSalesPanel.setTotal(total);
						break;
				}
			}
			
			private void showReportPurchases() throws SQLException {
				Object[] values = reportsPurchasesPanel.getValues();
				LocalDate ld = (LocalDate) values[0];
				int mode = (Integer) values[1];
				Object sup = values[2];
				DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
				ResultSet rs;
				double total=0;
				String additional = sup==null?"":" and id_supplier="+((Supplier)sup).getID()+" ";
				switch(mode) {
					case 0:case 1:
						rs = databaseThread.query("select p.*,s.name from Purchases p,Supplier s where p.id_supplier=s.id_supplier and date like \""+
								formatterDay.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_purchase")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsPurchasesPanel.setTotal(total);
						break;
					case 2:
						rs = databaseThread.query("select * from Purchases p,Supplier s where p.id_supplier=s.id_supplier and date like \""+
								formatterMonth.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_purchase")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsPurchasesPanel.setTotal(total);
						break;
					case 3:
						rs = databaseThread.query("select * from Purchases p,Supplier s where p.id_supplier=s.id_supplier and date like \""+
								formatterYear.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_purchase")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+""});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsPurchasesPanel.setTotal(total);
						break;
				}
				
				
			}

			private void showReportDebts() throws SQLException {
				Object[] values = reportsDebtsPanel.getValues();
				LocalDate ld = (LocalDate) values[0];
				int mode = (Integer) values[1];
				Object customer = values[2];
				DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
				ResultSet rs;
				double total=0;
				String additional = customer==null?"":" and id_customer="+((Customer)customer).getID()+" ";
				switch(mode) {
					case 0:case 1:
						rs = databaseThread.query("select d.*,c.name from Debts d,Customer c where d.id_customer=c.id_customer and date like \""+
								formatterDay.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_debt")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+"",
									rs.getString("remaining"),(rs.getBoolean("closed")?
											"<html><font color=\"red\">"+FrameStrings.valueOfStringName("debtsstatusclosed")+"</font></html>":
											"<html><font color=\"green\">"+FrameStrings.valueOfStringName("debtsstatusopen")+"</font></html>")});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsDebtsPanel.setTotal(total);
						break;
					case 2:
						rs = databaseThread.query("select d.*,c.name from Debts d,Customer c where d.id_customer=c.id_customer and date like \""+
								formatterMonth.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_debt")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+"",
									rs.getString("remaining"),(rs.getBoolean("closed")?
											"<html><font color=\"red\">"+FrameStrings.valueOfStringName("debtsstatusclosed")+"</font></html>":
											"<html><font color=\"green\">"+FrameStrings.valueOfStringName("debtsstatusopen")+"</font></html>")});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsDebtsPanel.setTotal(total);
						break;
					case 3:
						rs = databaseThread.query("select d.*,c.name from Debts d,Customer c where d.id_customer=c.id_customer and date like \""+
								formatterYear.format(ld)+"%\""+additional);
						while(rs.next()) {
							reportsTableModel.addRow(new String[] {rs.getInt("id_debt")+"",rs.getString("date"),rs.getString("name"),
									rs.getString("total"),rs.getString("discount"),(rs.getDouble("total")-rs.getDouble("discount"))+"",
									rs.getString("remaining"),(rs.getBoolean("closed")?
											"<html><font color=\"red\">"+FrameStrings.valueOfStringName("debtsstatusclosed")+"</font></html>":
											"<html><font color=\"green\">"+FrameStrings.valueOfStringName("debtsstatusopen")+"</font></html>")});
							total+=(rs.getDouble("total")-rs.getDouble("discount"));
						}
						reportsDebtsPanel.setTotal(total);
						break;
				}
			}

			private void showReportStocks() throws SQLException {
				String[] values = reportsStockPanel.getValues();
				String searchTxt = values[0];
				String letter = values[1];
				ResultSet rs;
				if(searchTxt!=null&&!searchTxt.equals("")) {
					rs = databaseThread.query("select * from Stock where name like \"%"+searchTxt+"%\" or "
							+ " desc like \"%"+searchTxt+"%\" or code like \"%"+searchTxt+"%\"");
					while(rs.next()) {
						reportsTableModel.addRow(new Object[] {rs.getString("id_stock"),rs.getString("code"),rs.getString("name"),
								rs.getString("desc"),rs.getString("qty"),rs.getString("bought_price"),rs.getString("sale_price")});
					}
				}
				else if((searchTxt==null||searchTxt.equals(""))&&!letter.toLowerCase().contains("letter")){
					rs = databaseThread.query("select * from Stock where name like \""+letter+"%\"");
					while(rs.next()) {
						reportsTableModel.addRow(new Object[] {rs.getString("id_stock"),rs.getString("code"),rs.getString("name"),
								rs.getString("desc"),rs.getString("qty"),rs.getString("bought_price"),rs.getString("sale_price")});
					}
				}
				else {
					rs = databaseThread.query("select * from Stock");
					while(rs.next()) {
						reportsTableModel.addRow(new Object[] {rs.getString("id_stock"),rs.getString("code"),rs.getString("name"),
								rs.getString("desc"),rs.getString("qty"),rs.getString("bought_price"),rs.getString("sale_price")});
					}
				}
				
				
			}

		});
		sl_reportsPanel.putConstraint(SpringLayout.NORTH, reportsOkBtn, (int)(30*sizeMultiplier), SpringLayout.SOUTH, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.WEST, reportsOkBtn, (int)(20*sizeMultiplier), SpringLayout.WEST, reportsPanel);
		sl_reportsPanel.putConstraint(SpringLayout.SOUTH, reportsOkBtn, (int)(60*sizeMultiplier), SpringLayout.SOUTH, reportsChoicesCbx);
		sl_reportsPanel.putConstraint(SpringLayout.EAST, reportsOkBtn, (int)(0*sizeMultiplier), SpringLayout.EAST, reportsChoicesCbx);
		reportsOkBtn.setFont(mainFont);
		reportsPanel.add(reportsOkBtn);
		reportsStockPanel.setVisible(false);
		
				//***************************************SUPPLIER PANEL***************************************\\
		
		supplierPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("supplierpaneltabname"), null, supplierPanel, null);
		SpringLayout sl_supplierPanel = new SpringLayout();
		supplierPanel.setLayout(sl_supplierPanel);
		
		suppliersListModel = new DefaultListModel<Supplier>();
		suppliersList = new JList<Supplier>(suppliersListModel);
		suppliersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					suppliersList.setSelectedIndex(suppliersList.locationToIndex(e.getPoint()));
					if(suppliersList.getSelectedIndex()>=0)
						supplierDeletePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		suppliersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(suppliersList.getSelectedIndex()>=0) {
					Supplier c = suppliersList.getSelectedValue();
					supplierNameTxt.setText(c.getName());
					supplierPhoneTxt.setText(c.getPhone());
				}
			}
		});
		suppliersListSP = new JScrollPane(suppliersList);
		suppliersListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("supplieritemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)suppliersListSP.getBorder()).setTitleFont(mainFont);
		suppliersList.setFont(mainFont);
		suppliersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, suppliersListSP, (int)(120*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, suppliersListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, suppliersListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, suppliersListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierPanel);
		supplierPanel.add(suppliersListSP);
		
		supplierSearchTxt = new JTextField();
		supplierSearchTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				supplierNameTxt.setText("");
				supplierPhoneTxt.setText("");
			}
		});
		supplierSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = supplierSearchTxt.getText().toString().trim().toLowerCase();
				suppliersListModel.removeAllElements();
				Iterator<Supplier> it = suppliers.values().iterator();
				while(it.hasNext()) {
					Supplier s = it.next();
					if(s.getName().toLowerCase().contains(desired) ||
							s.getPhone().toLowerCase().contains(desired)) {
						suppliersListModel.addElement(s);
					}
					
				}
				if(suppliersListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		supplierSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierSearchTxt, (int)(25*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierSearchTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierPanel);
		supplierPanel.add(supplierSearchTxt);
		supplierSearchTxt.setColumns(10);
		
		supplierSearchLbl = new JLabel(FrameStrings.valueOfStringName("suppliersearchlbl"));
		supplierSearchLbl.setFont(mainFont);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, supplierSearchTxt);
		supplierPanel.add(supplierSearchLbl);
		
		supplierInfoPanel = new JPanel();
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierInfoPanel, (int)(165*sizeMultiplier), SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierInfoPanel, (int)(-121*sizeMultiplier), SpringLayout.WEST, suppliersListSP);
		supplierPanel.add(supplierInfoPanel);
		SpringLayout sl_supplierInfoPanel = new SpringLayout();
		supplierInfoPanel.setLayout(sl_supplierInfoPanel);
		
		supplierNameLbl = new JLabel(FrameStrings.valueOfStringName("suppliernamelbl"));
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierNameLbl, (int)(120*sizeMultiplier), SpringLayout.NORTH, supplierInfoPanel);
		supplierNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		supplierNameLbl.setFont(mainFont);
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierPanel);
		supplierInfoPanel.add(supplierNameLbl);
		
		supplierNameTxt = new JTextField();
		supplierNameTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierConfirmsupplier.doClick();
			}
		});
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierNameTxt, (int)(20*sizeMultiplier), SpringLayout.SOUTH, supplierNameLbl);
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierNameTxt, (int)(70*sizeMultiplier), SpringLayout.SOUTH, supplierNameLbl);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		supplierNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		supplierInfoPanel.add(supplierNameTxt);
		supplierNameTxt.setColumns(10);
		
		supplierPhoneLbl = new JLabel(FrameStrings.valueOfStringName("supplierphonelbl"));
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierPhoneLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, supplierNameTxt);
		supplierPhoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		supplierPhoneLbl.setFont(mainFont);
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierPhoneLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierPhoneLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, supplierNameTxt);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierPhoneLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		supplierInfoPanel.add(supplierPhoneLbl);
		
		supplierPhoneTxt = new JTextField();
		supplierPhoneTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierConfirmsupplier.doClick();
			}
		});
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierPhoneTxt, (int)(20*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneLbl);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierPhoneTxt, (int)(70*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneLbl);
		supplierPhoneTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierPhoneTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierPhoneTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		supplierInfoPanel.add(supplierPhoneTxt);
		supplierPhoneTxt.setColumns(10);
		
		supplierClearBtn = new JButton(FrameStrings.valueOfStringName("supplierclearbtn"));
		supplierClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierNameTxt.setText("");
				supplierPhoneTxt.setText("");
				supplierSearchTxt.setText("");
				try {
					initializeSupplier();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				suppliersList.clearSelection();
			}
		});
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierClearBtn, (int)(150*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneTxt);
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierClearBtn, (int)(-250*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierClearBtn, (int)(190*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneTxt);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierClearBtn, (int)(-90*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		supplierClearBtn.setFont(mainFont);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		supplierInfoPanel.add(supplierClearBtn);
		
		supplierConfirmsupplier = new JButton(FrameStrings.valueOfStringName("supplierconfirmbtn"));
		supplierConfirmsupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkFields()) {
					if(suppliersList.getSelectedIndex()<0) {
						Supplier s = new Supplier(supplierNameTxt.getText().toString().trim(),supplierPhoneTxt.getText().toString().trim());
						suppliers.put(s.getID(),s);
						try {
							databaseThread.update("insert into Supplier(name,phone) values(\""+s.getName()+"\","
									+ "\""+s.getPhone()+"\")");
							supplierClearBtn.doClick();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						Supplier s = suppliersList.getSelectedValue();
						s.setName(supplierNameTxt.getText().toString().trim());
						s.setPhone(supplierPhoneTxt.getText().toString().trim());
						suppliers.remove(s.getID());
						suppliers.put(s.getID(), s);
						
						try {
							
							databaseThread.update("update Supplier set name=\""+s.getName()+"\", phone=\""+s.getPhone()+"\" where id_supplier="+s.getID()+" ");
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						supplierClearBtn.doClick();
					}
					
				}
				else {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("errorfields"));
				}
			}

			private boolean checkFields() {
				
				return supplierPhoneTxt.getText().toString().matches("\\d{2}\\/\\d{6}") &&
						supplierNameTxt.getText().toString().matches("(.+\\s.+)+");
			}
		});
		sl_supplierInfoPanel.putConstraint(SpringLayout.NORTH, supplierConfirmsupplier, (int)(150*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneTxt);
		sl_supplierInfoPanel.putConstraint(SpringLayout.WEST, supplierConfirmsupplier, (int)(90*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierInfoPanel.putConstraint(SpringLayout.SOUTH, supplierConfirmsupplier, (int)(190*sizeMultiplier), SpringLayout.SOUTH, supplierPhoneTxt);
		sl_supplierInfoPanel.putConstraint(SpringLayout.EAST, supplierConfirmsupplier, (int)(250*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierConfirmsupplier, (int)(80*sizeMultiplier), SpringLayout.NORTH, supplierInfoPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierConfirmsupplier, (int)(70*sizeMultiplier), SpringLayout.WEST, supplierInfoPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierConfirmsupplier, (int)(-10*sizeMultiplier), SpringLayout.SOUTH, supplierInfoPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierConfirmsupplier, (int)(-350*sizeMultiplier), SpringLayout.EAST, supplierInfoPanel);
		supplierInfoPanel.add(supplierConfirmsupplier);
		supplierConfirmsupplier.setFont(mainFont);
		
				//***************************************CUSTOMER PANEL***************************************\\
		
		customerPanel = new JPanel();
		rootTabbedPane.addTab(FrameStrings.valueOfStringName("customerpaneltabname"), null, customerPanel, null);
		SpringLayout sl_customerPanel = new SpringLayout(); 
		customerPanel.setLayout(sl_customerPanel);
		
		customerListModel = new DefaultListModel<Customer>();
		customerList = new JList<Customer>(customerListModel);
		customerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					customerList.setSelectedIndex(customerList.locationToIndex(e.getPoint()));
					if(customerList.getSelectedIndex()>=0)
						customerDeletePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		customerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(customerList.getSelectedIndex()>=0) {
					Customer c = customerList.getSelectedValue();
					customerNameTxt.setText(c.getName());
					customerPhoneTxt.setText(c.getPhone());
				}
			}
		});
		customerListSP = new JScrollPane(customerList);
		customerListSP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), FrameStrings.valueOfStringName("customeritemslisttitle"), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)customerListSP.getBorder()).setTitleFont(mainFont);
		customerList.setFont(mainFont);
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sl_customerPanel.putConstraint(SpringLayout.NORTH, customerListSP, (int)(120*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerListSP, (int)(-480*sizeMultiplier), SpringLayout.EAST, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.SOUTH, customerListSP, (int)(-20*sizeMultiplier), SpringLayout.SOUTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerListSP, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerPanel);
		customerPanel.add(customerListSP);
		
		customerSearchTxt = new JTextField();
		customerSearchTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				customerNameTxt.setText("");
				customerPhoneTxt.setText("");
			}
		});
		customerSearchTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desired = customerSearchTxt.getText().toString().toLowerCase().trim();
				customerListModel.removeAllElements();
				Iterator<Customer> it = customers.values().iterator();
				while(it.hasNext()) {
					Customer c = it.next();
					if(c.getName().toLowerCase().contains(desired) ||
							c.getPhone().toLowerCase().contains(desired)) {
						customerListModel.addElement(c);
					}
					
				}
				if(customerListModel.getSize()==0) {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("optionpanesearchnoresult"));
				}
			}
		});
		sl_customerPanel.putConstraint(SpringLayout.NORTH, customerSearchTxt, (int)(25*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerSearchTxt, (int)(1150*sizeMultiplier), SpringLayout.WEST, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.SOUTH, customerSearchTxt, (int)(90*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerSearchTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerPanel);
		customerSearchTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		customerPanel.add(customerSearchTxt);
		customerSearchTxt.setColumns(10);
		
		customerSearchLbl = new JLabel(FrameStrings.valueOfStringName("customersearchlbl"));
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerSearchLbl, (int)(-10*sizeMultiplier), SpringLayout.EAST, customerSearchTxt);
		customerSearchLbl.setFont(mainFont);
		sl_customerPanel.putConstraint(SpringLayout.NORTH, customerSearchLbl, (int)(25*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerSearchLbl, (int)(1000*sizeMultiplier), SpringLayout.WEST, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.SOUTH, customerSearchLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		customerPanel.add(customerSearchLbl);
		
		customerInfoPanel = new JPanel();
		sl_customerPanel.putConstraint(SpringLayout.NORTH, customerInfoPanel, (int)(130*sizeMultiplier), SpringLayout.NORTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerInfoPanel, (int)(165*sizeMultiplier), SpringLayout.WEST, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.SOUTH, customerInfoPanel, (int)(-25*sizeMultiplier), SpringLayout.SOUTH, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerInfoPanel, (int)(-121*sizeMultiplier), SpringLayout.WEST, customerListSP);
		customerPanel.add(customerInfoPanel);
		SpringLayout sl_customerInfoPanel = new SpringLayout();
		customerInfoPanel.setLayout(sl_customerInfoPanel);
		
		customerNameLbl = new JLabel(FrameStrings.valueOfStringName("customernamelbl"));
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerNameLbl, (int)(100*sizeMultiplier), SpringLayout.NORTH, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerNameLbl, (int)(120*sizeMultiplier), SpringLayout.NORTH, customerInfoPanel);
		customerNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		customerNameLbl.setFont(mainFont);
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerNameLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, customerPanel);
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerNameLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerPanel);
		customerInfoPanel.add(customerNameLbl);
		
		customerNameTxt = new JTextField();
		customerNameTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerConfirmcustomer.doClick();
			}
		});
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerNameTxt, (int)(20*sizeMultiplier), SpringLayout.SOUTH, customerNameLbl);
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerNameTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerNameTxt, (int)(70*sizeMultiplier), SpringLayout.SOUTH, customerNameLbl);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerNameTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		customerNameTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		customerInfoPanel.add(customerNameTxt);
		customerNameTxt.setColumns(10);
		
		customerPhoneLbl = new JLabel(FrameStrings.valueOfStringName("customerphonelbl"));
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerPhoneLbl, (int)(70*sizeMultiplier), SpringLayout.NORTH, customerNameTxt);
		customerPhoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		customerPhoneLbl.setFont(mainFont);
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerPhoneLbl, (int)(20*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerPhoneLbl, (int)(90*sizeMultiplier), SpringLayout.NORTH, customerNameTxt);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerPhoneLbl, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		customerInfoPanel.add(customerPhoneLbl);
		
		customerPhoneTxt = new JTextField();
		customerPhoneTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerConfirmcustomer.doClick();
			}
		});
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerPhoneTxt, (int)(20*sizeMultiplier), SpringLayout.SOUTH, customerPhoneLbl);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerPhoneTxt, (int)(70*sizeMultiplier), SpringLayout.SOUTH, customerPhoneLbl);
		customerPhoneTxt.setFont(new Font("Arial", Font.PLAIN, (int)(20*sizeMultiplier)));
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerPhoneTxt, (int)(20*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerPhoneTxt, (int)(-20*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		customerInfoPanel.add(customerPhoneTxt);
		customerPhoneTxt.setColumns(10);
		
		customerClearBtn = new JButton(FrameStrings.valueOfStringName("customerclearbtn"));
		customerClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerNameTxt.setText("");
				customerPhoneTxt.setText("");
				customerSearchTxt.setText("");
				try {
					initializeCustomer();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				customerList.clearSelection();
			}
		});
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerClearBtn, (int)(150*sizeMultiplier), SpringLayout.SOUTH, customerPhoneTxt);
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerClearBtn, (int)(-250*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerClearBtn, (int)(190*sizeMultiplier), SpringLayout.SOUTH, customerPhoneTxt);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerClearBtn, (int)(-90*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		customerClearBtn.setFont(mainFont);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerClearBtn, (int)(70*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		customerInfoPanel.add(customerClearBtn);
		
		customerConfirmcustomer = new JButton(FrameStrings.valueOfStringName("customerconfirmbtn"));
		customerConfirmcustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkFields()) {
					if(customerList.getSelectedIndex()<0) {
						Customer c = new Customer(customerNameTxt.getText().toString().trim(),customerPhoneTxt.getText().toString().trim());
						customers.put(c.getID(),c);
						try {
							databaseThread.update("insert into Customer(name,phone) values(\""+c.getName()+"\","
									+ "\""+c.getPhone()+"\")");
							customerClearBtn.doClick();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						Customer c = customerList.getSelectedValue();
						c.setName(customerNameTxt.getText().toString().trim());
						c.setPhone(customerPhoneTxt.getText().toString().trim());
						customers.remove(c.getID());
						customers.put(c.getID(), c);
						
						try {
							
							databaseThread.update("update Customer set name=\""+c.getName()+"\", phone=\""+c.getPhone()+"\" where id_customer="+c.getID()+" ");
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						customerClearBtn.doClick();
					}
					
				}
				else {
					new ErrorOptionPane().showMessage(RootFrame.this, FrameStrings.valueOfStringName("errorfields"));
				}
			}

			private boolean checkFields() {
				
				return customerPhoneTxt.getText().toString().matches("\\d{2}\\/\\d{6}") &&
						customerNameTxt.getText().toString().matches("(.+\\s.+)+");
			}
		});
		sl_customerInfoPanel.putConstraint(SpringLayout.NORTH, customerConfirmcustomer, (int)(150*sizeMultiplier), SpringLayout.SOUTH, customerPhoneTxt);
		sl_customerInfoPanel.putConstraint(SpringLayout.WEST, customerConfirmcustomer, (int)(90*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerInfoPanel.putConstraint(SpringLayout.SOUTH, customerConfirmcustomer, (int)(190*sizeMultiplier), SpringLayout.SOUTH, customerPhoneTxt);
		sl_customerInfoPanel.putConstraint(SpringLayout.EAST, customerConfirmcustomer, (int)(250*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerPanel.putConstraint(SpringLayout.NORTH, customerConfirmcustomer, (int)(80*sizeMultiplier), SpringLayout.NORTH, customerInfoPanel);
		sl_customerPanel.putConstraint(SpringLayout.WEST, customerConfirmcustomer, (int)(70*sizeMultiplier), SpringLayout.WEST, customerInfoPanel);
		sl_customerPanel.putConstraint(SpringLayout.SOUTH, customerConfirmcustomer, (int)(-10*sizeMultiplier), SpringLayout.SOUTH, customerInfoPanel);
		sl_customerPanel.putConstraint(SpringLayout.EAST, customerConfirmcustomer, (int)(-350*sizeMultiplier), SpringLayout.EAST, customerInfoPanel);
		customerInfoPanel.add(customerConfirmcustomer);
		customerConfirmcustomer.setFont(mainFont);
		
				//*************************************** MENUS ***************************************\\
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuLanguageMenu = new JMenu(FrameStrings.valueOfStringName("menulanguagemenu"));
		menuLanguageMenu.setFont(mainFont);
		menuLanguageBtnGrp = new ButtonGroup();
		menuLanguageMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menuLanguageMenu);
		
		menuLanguageAr = new JRadioButtonMenuItem("\u0639\u0631\u0628\u064A");
		if(lang==Language.AR)
			menuLanguageAr.setSelected(true);
		menuLanguageAr.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					FileWriter fw;
					try {
						fw = new FileWriter(languageFile);
						fw.write("AR");
				    	fw.flush();
				    	fw.close();
				    	parent.notifyLanguageChanged();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		menuLanguageAr.setFont(mainFont);
		menuLanguageMenu.add(menuLanguageAr);
		
		menuLanguageEn = new JRadioButtonMenuItem("English");
		if(lang==Language.EN)
			menuLanguageEn.setSelected(true);
		menuLanguageEn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					FileWriter fw;
					try {
						fw = new FileWriter(languageFile);
						fw.write("EN");
				    	fw.flush();
				    	fw.close();
				    	parent.notifyLanguageChanged();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		menuLanguageEn.setFont(mainFont);
		menuLanguageMenu.add(menuLanguageEn);
		menuLanguageBtnGrp.add(menuLanguageAr);
		menuLanguageBtnGrp.add(menuLanguageEn);
		
		menuOptionsMenu = new JMenu(FrameStrings.valueOfStringName("menuoptionsmenu"));
		menuOptionsMenu.setFont(mainFont);
		menuBar.add(menuOptionsMenu);
		
		menuOptionsDollar = new JMenuItem(FrameStrings.valueOfStringName("menuoptionsdollar"));
		menuOptionsDollar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					promptForDollarValue();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuOptionsDollar.setFont(mainFont);
		menuOptionsMenu.add(menuOptionsDollar);
		
		menuOptionsCheckCriticalStocks = new JMenuItem(FrameStrings.valueOfStringName("menuoptioncheckcritical"));
		menuOptionsCheckCriticalStocks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkAllStocksForCriticalValues()) {
					JOptionPane.showMessageDialog(RootFrame.this, 
							ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
							FrameStrings.valueOfStringName("nocriticalstocks")+
							ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE, 
							"", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					LowStocksDialog lsd = new LowStocksDialog(lowStocks);
					lsd.setVisible(true);
					lsd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				}
				
			}
		});
		menuOptionsCheckCriticalStocks.setFont(mainFont);
		menuOptionsMenu.add(menuOptionsCheckCriticalStocks);
		
		customerDeletePopup = new JPopupMenu();
		customerDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		customerDeleteMenuItem.setFont(mainFont);
		customerDeletePopup.add(customerDeleteMenuItem);
		customerDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(customerList.getSelectedIndex()>=0) {
					Customer c = customerList.getSelectedValue();
					
					try {
						databaseThread.update("delete from Customer where id_customer="+c.getID());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
					customers.remove(c.getID());
					customerClearBtn.doClick();
				}
			}
		});
		
		supplierDeletePopup = new JPopupMenu();
		supplierDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		supplierDeleteMenuItem.setFont(mainFont);
		supplierDeletePopup.add(supplierDeleteMenuItem);
		supplierDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(suppliersList.getSelectedIndex()>=0) {
					Supplier s = suppliersList.getSelectedValue();
					
					try {
						databaseThread.update("delete from Supplier where id_supplier="+s.getID());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
					suppliers.remove(s.getID());
					supplierClearBtn.doClick();
				}
			}
		});
		
		stockDeletePopup = new JPopupMenu();
		stockDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		stockDeleteMenuItem.setFont(mainFont);
		stockDeletePopup.add(stockDeleteMenuItem);
		stockDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stockItemsList.getSelectedIndex()>=0) {
					Stock s = stockItemsList.getSelectedValue();
					
					try {
						databaseThread.update("delete from Stock where id_stock="+s.getID());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
					stocks.remove(s.getID());
					stockClearBtn.doClick();
				}
			}
		});
		
		salesCurrentItemDeletePopup = new JPopupMenu();
		salesCurrentItemDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		salesCurrentItemDeletePopup.add(salesCurrentItemDeleteMenuItem);
		salesCurrentItemDeleteMenuItem.setFont(mainFont);
		salesCurrentItemDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(salesCurrentList.getSelectedIndex()>=0) {
					SaleDetails sd = salesCurrentList.getSelectedValue();
					salesCurrentListModel.removeElement(sd);
					currentSaleDetails.remove(sd.getStock().getID());
					salesCurrentList.clearSelection();
					double total = calculateSaleTotal();
					salesTotalTxt.setText(total+"");
					currentSale.setTotal(total);
					if(salesCurrentListModel.isEmpty())
						salesClearBtn.doClick();
				}
				
			}
		});
		purchasesCurrentItemDeletePopup = new JPopupMenu();
		purchasesCurrentItemDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		purchasesCurrentItemDeletePopup.add(purchasesCurrentItemDeleteMenuItem);
		purchasesCurrentItemDeleteMenuItem.setFont(mainFont);
		purchasesCurrentItemDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(purchasesCurrentList.getSelectedIndex()>=0) {
					PurchaseDetails sd = purchasesCurrentList.getSelectedValue();
					purchasesCurrentListModel.removeElement(sd);
					currentPurchaseDetails.remove(sd.getStock().getID());
					purchasesCurrentList.clearSelection();
					double total = calculatePurchaseTotal();
					purchasesTotalTxt.setText(total+"");
					currentPurchase.setTotal(total);
					if(purchasesCurrentListModel.isEmpty())
						purchasesClearBtn.doClick();
				}
			}
		});
		
		debtClosePopup = new JPopupMenu();
		debtCloseMenuItem = new JMenuItem(FrameStrings.valueOfStringName("debtclosemenuitem"));
		debtClosePopup.add(debtCloseMenuItem);
		debtCloseMenuItem.setFont(mainFont);
		debtCloseMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(debtsDebtsList.getSelectedIndex()>=0) {
					Debt debt = debtsDebtsList.getSelectedValue();
					try {
						databaseThread.update("update Debts set closed=1 where id_debt="+debt.getID());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setDebtsFields(true);
				}
			}
		});
		
		debtsCurrentItemDeletePopup = new JPopupMenu();
		debtsCurrentItemDeleteMenuItem = new JMenuItem(FrameStrings.valueOfStringName("deletemenuitem"));
		debtsCurrentItemDeletePopup.add(debtsCurrentItemDeleteMenuItem);
		debtsCurrentItemDeleteMenuItem.setFont(mainFont);
		debtsCurrentItemDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(debtsCurrentList.getSelectedIndex()>=0) {
					DebtDetails dd = debtsCurrentList.getSelectedValue();
					debtsCurrentListModel.removeElement(dd);
					currentDebtDetails.remove(dd.getStock().getID());
					debtsCurrentList.clearSelection();
					double total = calculateDebtTotal();
					debtsTotalTxt.setText(total+"");
					currentDebt.setTotal(total);
					if(debtsCurrentListModel.isEmpty())
						debtsClearBtn.doClick();
				}
				
			}
		});
		
		reportsShowMoreDetailsPopup = new JPopupMenu();
		reportsShowMoreDetailsMenuItem = new JMenuItem(FrameStrings.valueOfStringName("reportshowmoredetails"));
		reportsShowMoreDetailsPopup.add(reportsShowMoreDetailsMenuItem);
		reportsShowMoreDetailsMenuItem.setFont(mainFont);
		reportsShowMoreDetailsMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reportsTable.getSelectedRow()<0) {
					return;
				}
				int mode = reportsChoicesCbx.getSelectedIndex();
				int id = Integer.parseInt(reportsTableModel.getValueAt(reportsTable.getSelectedRow(), 0).toString());
				ReportDetails rd = new ReportDetails(mode, id,databaseThread);
				rd.setVisible(true);
				rd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		
		
		reportsShowMoreDetailsPopup.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	int rowAtPoint = reportsTable.rowAtPoint(SwingUtilities.convertPoint(reportsShowMoreDetailsPopup, new Point(0, 0), reportsTable));
                        if (rowAtPoint > -1) {
                        	reportsTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		showLoadingDialog(false);
		
		currentSale=new Sale();
		currentDebt=new Debt(null);
		currentPurchase=new Purchase(null);
		currentDebtLog=new DebtLog();
		
		try {
			initializeStock();
			initializeCustomer();
			initializeSupplier();
			initializeDebts();
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
			//***************************************EXTERNAL METHODS***************************************\\
	
	private void checkAndPlaceDatabaseAndLanguage() throws IOException {
		String myDocuments = null;

	    try {
	        Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
	        p.waitFor();

	        InputStream in = p.getInputStream();
	        byte[] b = new byte[in.available()];
	        in.read(b);
	        in.close();

	        myDocuments = new String(b);
	        myDocuments = myDocuments.split("\\s\\s+")[4];

	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	    File f= new File(myDocuments+"\\InventoryManager");
	    if(!f.exists()) {
	    	f.mkdir();
	    }
	    File cache = new File(f.getAbsolutePath()+"\\cache");
	    languageFile = new File(cache.getAbsolutePath()+"\\language");
	    if(!cache.exists()) {
	    	cache.mkdir();
	    }
	    if(!languageFile.exists()) {
	    	languageFile.createNewFile();
	    	FileWriter fw=new FileWriter(languageFile);
	    	fw.write("EN");
	    	fw.flush();
	    	fw.close();
	    	lang = Language.EN;
	    	
	    }
	    else {
	    	Scanner sc = new Scanner(languageFile);
	    	String s ;
	    	try{
	    		s = sc.nextLine();
	    		if(s.equals("")||s.equals("EN")||s==null) {
		    		lang = Language.EN;
		    	}
		    	else {
		    		lang = Language.AR;
		    	}
	    	}
	    	catch(Exception e) {
	    		lang = Language.EN;
	    	}
	    	sc.close();
	    }
	    dollarFile = new File(cache.getAbsolutePath()+"\\dollar");
	    if(!dollarFile.exists()) {
	    	dollarFile.createNewFile();
	    	dollarValue=0;
	    }
	    else {
	    	Scanner dollarScan = new Scanner(dollarFile);
	    	String dollarString;
	    	try{
	    		dollarString = dollarScan.nextLine();
	    		if(dollarScan==null||dollarScan.equals("")) {
		    		dollarValue=0;
		    	}
		    	else {
			    	dollarValue=Double.parseDouble(dollarString.trim());
		    	}
	    	}
	    	catch(Exception ex){
	    		dollarValue=0;
	    	}
	    	dollarScan.close();
	    }
		File db = new File(f.getAbsolutePath()+File.separator+"inventorymanager.db");
		System.out.println(db.getAbsolutePath());
		if(!db.exists()) {
			db.createNewFile();
			InputStream input = RootFrame.class.getResourceAsStream("/inventorymanager.db");
			FileOutputStream output = new FileOutputStream(db);
			IOUtils.copy(input, output);
		}
		databaseThread = new DatabaseThread();
		databaseThread.setDaemon(true);
		databaseThread.start();
	}
	
	private void initializeDataClassesValues() {
		try {

			ResultSet rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Customer\"");
			rs.next();
			Customer.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Supplier\"");
			rs.next();
			Supplier.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Stock\"");
			rs.next();
			Stock.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Debts_Log\"");
			rs.next();
			DebtLog.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Debts\"");
			rs.next();
			Debt.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Purchases\"");
			rs.next();
			Purchase.setStatic(1+Integer.parseInt(rs.getString(2)));
			
			rs = databaseThread.query("SELECT * FROM sqlite_sequence where name=\"Sales\"");
			rs.next();
			Sale.setStatic(1+Integer.parseInt(rs.getString(2)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void showLoadingDialog(boolean b) {
		
		rootPanel.setVisible(!b);
		loadingPane.setLocationRelativeTo(null);
		loadingPane.setVisible(b);
	}
	
	private synchronized void initializeStock() throws SQLException {
		showLoadingDialog(true);
		salesItemsListModel.removeAllElements();
		purchasesItemsListModel.removeAllElements();
		debtsItemsListModel.removeAllElements();
		stockItemsListModel.removeAllElements();
		stocks.clear();
		ResultSet rs = databaseThread.query("SELECT * FROM Stock");
		
		while(rs.next()) {
			Stock s = new Stock(rs.getInt("id_stock"),rs.getString("name"), rs.getString("desc"), rs.getInt("qty"),
					rs.getDouble("bought_price"), rs.getDouble("sale_price"), rs.getString("code"));
			
			stocks.put(s.getID(), s);
			
			salesItemsListModel.addElement(s);
			purchasesItemsListModel.addElement(s);
			debtsItemsListModel.addElement(s);
			stockItemsListModel.addElement(s);
			
		}
		showLoadingDialog(false);
	}
	
	private synchronized void initializeCustomer() throws SQLException {
		showLoadingDialog(true);
		debtsCustomerListModel.removeAllElements();
		customerListModel.removeAllElements();
		customers.clear();
		ResultSet rs = databaseThread.query("SELECT * FROM Customer");
		while(rs.next()) {
			Customer s = new Customer(rs.getInt("id_customer"), rs.getString("name"), rs.getString("phone"));
			
			customers.put(s.getID(), s);
			
			debtsCustomerListModel.addElement(s);
			customerListModel.addElement(s);
			
		}
		reportsDebtsPanel.setComboBoxValues(customers.values());
		
		showLoadingDialog(false);
	}
	
	private synchronized void initializeSupplier() throws SQLException {
		showLoadingDialog(true);
		purchasesSupplierListModel.removeAllElements();
		suppliersListModel.removeAllElements();
		suppliers.clear();
		ResultSet rs = databaseThread.query("SELECT * FROM Supplier");
		
		while(rs.next()) {
			Supplier s = new Supplier(rs.getInt("id_supplier"), rs.getString("name"), rs.getString("phone"));
			
			suppliers.put(s.getID(), s);
			
			purchasesSupplierListModel.addElement(s);
			suppliersListModel.addElement(s);
			
		}
		reportsPurchasesPanel.setComboBoxValues(suppliers.values());
		
		showLoadingDialog(false);
	}
	
	private synchronized void initializeDebts() throws SQLException {
		showLoadingDialog(true);
		debtsDebtsListModel.removeAllElements();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		ResultSet rs = databaseThread.query("SELECT * FROM Debts where closed=0");
		debts.clear();
		while(rs.next()) {
			Debt d = new Debt(rs.getInt("id_debt"),rs.getString("date"), rs.getDouble("total"), customers.get(rs.getInt("id_customer")),rs.getDouble("discount"));
			d.setID(rs.getInt("id_debt"));
			d.setClosed(rs.getBoolean("closed"));
			d.setRemaining(rs.getDouble("remaining"));
			
			debts.put(d.getID(), d);
			
			debtsDebtsListModel.addElement(d);
			
		}
		
		showLoadingDialog(false);
	}
	
	private void promptForDollarValue() throws IOException {
		String newDollarValue="";
		String toDisplay="";
		boolean reentering=false;
		boolean valueIsRight=false;
		do {
			toDisplay=ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN;
			if(reentering) {
				toDisplay+=FrameStrings.valueOfStringName("dollarvalueinputdialogerror")+"<br>";
			}
			reentering=true;
			toDisplay+=FrameStrings.valueOfStringName("dollarvalueinputdialog");
			if(dollarValue!=0)
				toDisplay+="<br>"+FrameStrings.valueOfStringName("dollaroldvalueinputdialog")+dollarValue;
			toDisplay+=ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE;
			JLabel l = new JLabel(toDisplay);
			JOptionPane jp = new JOptionPane();
			jp.setFont(mainFont);
			newDollarValue = jp.showInputDialog(l);
			
			
			if(newDollarValue==null) {
				continue;
			}
			else if(newDollarValue.equals("")) {
				continue;
			}
			valueIsRight=newDollarValue.matches("\\d+(\\.(\\d)+)?");
			if(!valueIsRight)
				continue;
			FileWriter fw = new FileWriter(dollarFile);
			fw.write(newDollarValue);
			fw.flush();
			fw.close();
			dollarValue=Double.parseDouble(newDollarValue);
		}while(!valueIsRight);
	}
	
	@Override
	public void setVisible(boolean b) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		super.setVisible(b);
		
		/*try {
			promptForDollarValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/**/
		checkAllStocksForCriticalValuesWithDisplay();
	}
	
	private double calculateSaleTotal() {
		Iterator<SaleDetails> it = currentSaleDetails.values().iterator();
		double total=0;
		while(it.hasNext()) {
			SaleDetails sd = it.next();
			total+=sd.getStock().getSalePrice()*sd.getQty();
		}
		total=total*dollarValue;
		return total;
	}
	private double calculatePurchaseTotal() {
		Iterator<PurchaseDetails> it = currentPurchaseDetails.values().iterator();
		double total=0;
		while(it.hasNext()) {
			PurchaseDetails pd = it.next();
			total+=pd.getStock().getBoughtPrice()*pd.getQty();
		}
		return total;
	}
	private double calculateDebtTotal() {
		Iterator<DebtDetails> it = currentDebtDetails.values().iterator();
		double total=0;
		while(it.hasNext()) {
			DebtDetails dd = it.next();
			total+=dd.getStock().getSalePrice()*dd.getQty();
		}
		total=total*dollarValue;
		return total;
	}
	private void setDebtsFields(boolean b) {
		if(b) {
			if(debtsDebtsList.getSelectedIndex()>=0) {
				debtsClearBtn.doClick();
			}
			debtsQtyTxt.setEnabled(true);
			debtsQtyLbl.setEnabled(true);
			debtsDescPayementTxt.setText("");
			debtsDescPayementTxt.setEnabled(false);
			debtsDescPayementLbl.setEnabled(false);
			debtsClosedLbl.setText("");
			debtsRemainingLbl.setText("");
			debtsDiscountLbl.setEnabled(true);
			debtsDiscountTxt.setEnabled(true);
			debtsConfirmDebt.setEnabled(true);
				
			
		}
		else {
			if(debtsCustomerList.getSelectedIndex()>=0) {
				debtsClearBtn.doClick();
			}
			debtsQtyLbl.setEnabled(false);
			debtsQtyTxt.setEnabled(false);
			debtsDescPayementTxt.setText("");
			debtsDescPayementTxt.setEnabled(true);
			debtsDescPayementLbl.setEnabled(true);
			debtsDiscountLbl.setEnabled(false);
			debtsDiscountTxt.setEnabled(false);
			debtsConfirmDebt.setEnabled(false);
		}
	}

	private void promtStockItemCritical(Stock s) {
		WarningOptionPane wop = new WarningOptionPane();
		wop.showMessage(this, FrameStrings.valueOfStringName("warningstockitem")+s.getName());
	}
	
	private boolean checkAllStocksForCriticalValues() {
		
		Iterator<Stock> it = stocks.values().iterator();
		lowStocks = new HashSet<Stock>();
		while(it.hasNext()) {
			Stock s = it.next();
			if(s.getqty()<=CRITICAL_STOCKVALUE)
				lowStocks.add(s);
		}
		
		return !lowStocks.isEmpty();
	}
	private void checkAllStocksForCriticalValuesWithDisplay() {
		if(checkAllStocksForCriticalValues()) {
			JOptionPane.showMessageDialog(RootFrame.this, 
					ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
					FrameStrings.valueOfStringName("criticalstocksfound")+
					ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE, 
					"", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private void newCriticalValuesound() {
		
		JOptionPane.showMessageDialog(RootFrame.this, 
				ErrorOptionPane.HTML_OPEN+ErrorOptionPane.FONT_OPEN+
				FrameStrings.valueOfStringName("newcriticalstocksfound")+
				ErrorOptionPane.FONT_CLOSE+ErrorOptionPane.HTML_CLOSE, 
				"", JOptionPane.PLAIN_MESSAGE);
		
	}
	
}
