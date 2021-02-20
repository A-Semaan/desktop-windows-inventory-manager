package misc;

import java.util.HashMap;
import java.util.Map;

import frames.RootFrame;

/**
 *
 * @author Semaan
 */
public enum FrameStrings {
    
	appname("appname","Inventory Management",""),
	appclosing("appclosing", "Are you sure you want to quit?",""),
	loadingpane("loadingpane","Loading ...","\u062c\u0627\u0631 \u0627\u0644\u062a\u062d\u0645\u064a\u0644 \u002e\u002e\u002e"),
	dollarvalueinputdialog("dollarvalueinputdialog","Please enter dollar value: ",""),
	dollaroldvalueinputdialog("dollaroldvalueinputdialog","Old value is: ",""),
	dollarvalueinputdialogerror("dollarvalueinputdialogerror","You entered a wrong value",""),
	
	menulanguagemenu("menulanguagemenu","Language","\u0627\u0644\u0644\u0651\u063a\u0629"),
	menuoptionsmenu("menuoptionsmenu","Options","\u0625\u0639\u062f\u0627\u062f\u0627\u062a"),
	menuoptionsdollar("menuoptionsdollar","Set dollar value","\u062a\u062d\u062f\u064a\u062f \u0642\u064a\u0645\u0629 \u0627\u0644\u062f\u0648\u0644\u0627\u0631"),
	menuoptioncheckcritical("menuoptioncheckcritical","Check critical stocks",""),
	deletemenuitem("deletemenuitem","Delete",""),
	debtclosemenuitem("debtclosemenuitem","Close",""),
	reportshowmoredetails("reportshowmoredetails","Show details",""),
	reportdebtspayementdetails("reportdebtspayementdetails","Show Payement Details",""),
	
	salespaneltabname("salespaneltabname","Sales","\u0627\u0644\u0645\u0628\u064a\u0639\u0627\u062a"),
	purchasespaneltabname("purchasespaneltabname","Purchases","\u0627\u0644\u0645\u0634\u062a\u0631\u064a\u0627\u062a"),
	stockpaneltabname("stockpaneltabname","Stocks","\u0627\u0644\u0628\u0636\u0627\u0639\u0629"),
	supplierpaneltabname("supplierpaneltabname","Suppliers","\u0627\u0644\u0645\u0645\u0648\u0646\u064a\u0646"),
	customerpaneltabname("customerpaneltabname","Customers","\u0627\u0644\u0632\u0628\u0627\u0626\u0646"),
	reportspaneltabname("reportspaneltabname","Reports","\u0627\u0644\u062a\u0642\u0627\u0631\u064a\u0631"),
	debtpaneltabname("debtpaneltabname","Debts","\u0627\u0644\u062f\u064a\u0648\u0646"),
	
	salessearchlbl("salessearchlbl","Search","\u0628\u062d\u062b"),
	salescodelbl("salescodelbl","Code","\u0627\u0644\u0631\u0645\u0632"),
	salesnamelbl("salesnamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	salesdesclbl("salesdesclbl","Description","\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644"),
	salesqtylbl("salesqtylbl","Insert Quantity","\u0623\u062f\u062e\u0644 \u0627\u0644\u0643\u0645\u064a\u0629"),
	salespricelbl("salespricelbl","Price","\u0627\u0644\u0633\u0639\u0631"),
	salesclearbtn("salesclearbtn","Clear","\u0645\u0633\u062d"),
	salescurrentlisttitle("salescurrentlisttitle","Current Sale","\u0627\u0644\u0628\u064a\u0639 \u0627\u0644\u062d\u0627\u0644\u064a"),
	salesitemslisttitle("salesitemslisttitle","Items","\u0627\u0644\u0628\u0636\u0627\u0639\u0629"),
	salestotallbl("salestotallbl","Total","\u0627\u0644\u0645\u062c\u0645\u0648\u0639"),
	salesdiscountlbl("salesdiscountlbl","Discount","\u0627\u0644\u062e\u0635\u0645"),
	salesconfirmbtn("salesconfirmbtn","Confirm","\u0623\u0643\u062f"),
	
	purchasessearchlbl("purchasessearchlbl","Search Item","\u0628\u062d\u062b \u0627\u0644\u0628\u0636\u0627\u0626\u0639"),
	purchasescodelbl("purchasescodelbl","Code","\u0627\u0644\u0631\u0645\u0632"),
	purchasesnamelbl("purchasesnamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	purchasesdesclbl("purchasesdesclbl","Description","\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644"),
	purchasesqtylbl("purchasesqtylbl","Insert Quantity","\u0623\u062f\u062e\u0644 \u0627\u0644\u0643\u0645\u064a\u0629"),
	purchasespricelbl("purchasespricelbl","Buying Price","\u0633\u0639\u0631 \u0634\u0631\u0627\u0621 \u0627\u0644\u0642\u0637\u0639\u0629 \u0627\u0644\u0648\u0627\u062d\u062f\u0629"),
	purchasesclearbtn("purchasesclearbtn","Clear","\u0645\u0633\u062d"),
	purchasescurrentlisttitle("purchasescurrentlisttitle","Current Purchase","\u0627\u0644\u0634\u0631\u0627\u0621 \u0627\u0644\u062d\u0627\u0644\u064a"),
	purchasesitemslisttitle("purchasesitemslisttitle","Items","\u0627\u0644\u0628\u0636\u0627\u0639\u0629"),
	purchasessupplierslisttitle("purchasessupplierslisttitle","Suppliers","\u0627\u0644\u0645\u0645\u0648\u0646\u064a\u0646"),
	purchasestotallbl("purchasestotallbl","Total","\u0627\u0644\u0645\u062c\u0645\u0648\u0639"),
	purchasesdiscountlbl("purchasesdiscountlbl","Discount","\u0627\u0644\u062e\u0635\u0645"),
	purchasesconfirmbtn("purchasesconfirmbtn","Confirm","\u0623\u0643\u062f"),
	purchasessearchsupplierlbl("purchasessearchsupplierlbl","Search Supplier","\u0628\u062d\u062b \u0627\u0644\u0645\u0648\u0631\u062f\u064a\u0646"),
	
	debtssearchlbl("debtssearchlbl","Search Item","\u0628\u062d\u062b \u0627\u0644\u0628\u0636\u0627\u0626\u0639"),
	debtscodelbl("debtscodelbl","Code","\u0627\u0644\u0631\u0645\u0632"),
	debtsnamelbl("debtsnamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	debtsdesclbl("debtsdesclbl","Description","\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644"),
	debtspayementlbl("debtspayementlbl","Payement","\u0627\u0644\u062f\u0641\u0639"),
	debtsqtylbl("debtsqtylbl","Insert Quantity","\u0623\u062f\u062e\u0644 \u0627\u0644\u0643\u0645\u064a\u0629"),
	debtspricelbl("debtspricelbl","Price","\u0627\u0644\u0633\u0639\u0631"),
	debtsclearbtn("debtsclearbtn","Clear","\u0645\u0633\u062d"),
	debtscurrentlisttitle("debtscurrentlisttitle","Current Debts","\u0627\u0644\u062f\u064a\u0646 \u0627\u0644\u062d\u0627\u0644\u064a"),
	debtsitemslisttitle("debtsitemslisttitle","Items","\u0627\u0644\u0628\u0636\u0627\u0639\u0629"),
	debtscustomerslisttitle("debtscustomerslisttitle","Customers","\u0627\u0644\u0632\u0628\u0627\u0626\u0646"),
	debtstotallbl("debtstotallbl","Total","\u0627\u0644\u0645\u062c\u0645\u0648\u0639"),
	debtsdiscountlbl("debtsdiscountlbl","Discount","\u0627\u0644\u062e\u0635\u0645"),
	debtsconfirmbtn("debtsconfirmbtn","Confirm","\u0623\u0643\u062f"),
	debtssearchcustomerlbl("debtssearchcustomerlbl","Search Customer","\u0628\u062d\u062b \u0627\u0644\u0632\u0628\u0627\u0626\u0646"),
	debtsdebtslisttitle("debtsdebtslisttitle","Debts","\u0627\u0644\u062f\u064a\u0648\u0646"),
	debtsremaininglbl("debtsremaininglbl","Remaining: "," :\u0627\u0644\u0645\u062a\u0628\u0642\u064a"),
	debtsclosedlbl("debtsclosedlbl","Closed","\u0645\u062a\u0645\u0651\u0645"),
	debtsstatusclosed("debtsstatusclosed","Closed","\u0645\u062a\u0645\u0651\u0645"),
	debtsstatusopen("debtsstatusopen","Open","\u063a\u064a\u0631 \u0645\u062a\u0645\u0651\u0645"),
	
	stocksearchlbl("stocksearchlbl","Search","\u0628\u062d\u062b"),
	stockcodelbl("stockcodelbl","Code","\u0627\u0644\u0631\u0645\u0632"),
	stocknamelbl("stocknamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	stockdesclbl("stockdesclbl","Description","\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644"),
	stockpricelbl("stockpricelbl","Selling Price","\u0633\u0639\u0631 \u0627\u0644\u0628\u064a\u0639"),
	stockclearbtn("stockclearbtn","Clear","\u0645\u0633\u062d"),
	stockitemslisttitle("stockitemslisttitle","Items","\u0627\u0644\u0628\u0636\u0627\u0639\u0629"),
	stockconfirmbtn("stockconfirmbtn","Confirm","\u0623\u0643\u062f"),
	
	reportstotallbl("reportstotallbl","Total: "," :\u0627\u0644\u0645\u062c\u0645\u0648\u0639"),
	reportspurchasessupplierlbl("reportspurchasessupplierlbl","Suppliers: "," :\u0627\u0644\u0645\u0645\u0648\u0646\u064a\u0646"),
	reportsdebtscustomerlbl("reportsdebtscustomerlbl","Customers: "," :\u0627\u0644\u0632\u0628\u0627\u0626\u0646"),
	reportsstocksearchlbl("reportsstocksearchlbl","Search","\u0628\u062d\u062b"),
	reportsokbtn("reportsokbtn","OK","\u0623\u0643\u062f"),
	
	suppliersearchlbl("suppliersearchlbl","Search","\u0628\u062d\u062b"),
	suppliernamelbl("suppliernamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	supplierphonelbl("supplierphonelbl","Phone (00/000000)","\u0627\u0644\u0647\u0627\u062a\u0641 (00/000000)"),
	supplierclearbtn("supplierclearbtn","Clear","\u0645\u0633\u062d"),
	supplieritemslisttitle("supplieritemslisttitle","Suppliers","\u0627\u0644\u0645\u0645\u0648\u0646\u064a\u0646"),
	supplierconfirmbtn("supplierconfirmbtn","Confirm","\u0623\u0643\u062f"),
	
	customersearchlbl("customersearchlbl","Search","\u0628\u062d\u062b"),
	customernamelbl("customernamelbl","Name","\u0627\u0644\u0627\u0633\u0645"),
	customerphonelbl("customerphonelbl","Phone (00/000000)","\u0627\u0644\u0647\u0627\u062a\u0641 (00/000000)"),
	customerclearbtn("customerclearbtn","Clear","\u0645\u0633\u062d"),
	customeritemslisttitle("customeritemslisttitle","Customers","\u0627\u0644\u0632\u0628\u0627\u0626\u0646"),
	customerconfirmbtn("customerconfirmbtn","Confirm","\u0623\u0643\u062f"),
	
	errorfields("errorfields","Some fields are empty or errored",""),
	optionpaneerrortitle("optionpaneerrortitle","Error",""),
	optionpaneerrorok("optionpaneerrorok","OK","\u0623\u0643\u062f"),
	optionpanesearchnoresult("optionpanesearchnoresult","No results found",""),
	insufficiantstockerror("insufficiantstockerror","You don't have enough stock",""),
	pricestockerror("pricestockerror","This stock has an invalid selling price",""),
	selectsuppliererror("selectsuppliererror","Please select a supplier first",""),
	selectcustomererror("selectcustomererror","Please select a customer first",""),
	purchaseitemzeropriceerror("purchaseitemzeropriceerror","One or more of the items has a zero buying price",""),
	optionpanewarningtitle("optionpanewarningtitle","Warning",""),
	warningstockitem("warningstockitem","The following stock is under the critical quantity:<br>",""),
	nocriticalstocks("nocriticalstocks","No critical stocks found",""),
	criticalstocksfound("criticalstocksfound","You have critical stocks",""),
	newcriticalstocksfound("newcriticalstocksfound","You have new critical stocks",""),
	;
	
    
    private static final Map<String, String> EN_OptionName = new HashMap<>();
    private static final Map<String, String> AR_OptionName = new HashMap<>();
     
    static {
        for (FrameStrings c: values()) {
            EN_OptionName.put(c.stringId, c.stringENValue);
            AR_OptionName.put(c.stringId, c.stringARValue);
        }
    }
 
    public final String stringId;
    public final String stringENValue;
    public final String stringARValue;
 
    private FrameStrings(String stringId, String stringENValue, String stringARValue) {
        this.stringId = stringId;
        this.stringENValue = stringENValue;
        this.stringARValue = stringARValue;
    }
 
    public static String valueOfStringName(String label) {
        switch(RootFrame.lang) {
        	case EN: return EN_OptionName.get(label);
        	case AR: return AR_OptionName.get(label);
        	default:break;
        }
        return null;
    }
}
 
