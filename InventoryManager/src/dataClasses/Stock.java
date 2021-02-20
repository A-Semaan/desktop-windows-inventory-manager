package dataClasses;

public class Stock implements Comparable<Stock> {

	private static int next;
	private int id_stock;
	private String code;
	private String name;
	private String desc;
	private int qty;
	private double boughtPrice;
	private double salePrice;
	
	public Stock(int id, String name, String desc, int qty, double boughtPrice, double salePrice, String code) {
		this.id_stock=id;
		this.name = name;
		this.desc = desc;
		this.qty = qty;
		this.boughtPrice = boughtPrice;
		this.salePrice = salePrice;
		this.code=code;
	}
	public Stock(String name, String desc, int qty, double boughtPrice, double salePrice, String code) {
		System.out.println("next is "+next);
		this.id_stock=next++;
		this.name = name;
		this.desc = desc;
		this.qty = qty;
		this.boughtPrice = boughtPrice;
		this.salePrice = salePrice;
		this.code=code;
	}
	
	public Stock(String name, String desc, int qty, double boughtPrice, double salePrice) {
		this(name,desc,qty,boughtPrice,salePrice,"");
	}

	public Stock(String name, int qty, double boughtPrice, double salePrice) {
		this(name,"",qty,boughtPrice,salePrice,"");
	}
	public Stock(String name, int qty, double boughtPrice, double salePrice,String code) {
		this(name,"",qty,boughtPrice,salePrice,code);
	}


	public static int getStatic() {
		return next;
	}
	
	public static void setStatic(int next) {
		Stock.next = next;
	}
	
	public int getID() {
		return id_stock;
	}
	
	public void setID(int id) {
		this.id_stock=id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getqty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getBoughtPrice() {
		return boughtPrice;
	}
	
	public void setBoughtPrice(double boughtPrice) {
		this.boughtPrice = boughtPrice;
	}
	
	public double getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code=code;
	}

	public String toString() {
		return "<html><table><tr><font color=\"red\">"+id_stock+" "+name+"</font>  "+desc+"<br>&emsp; "+qty+" --- "+code+"</tr></table></html>";
	}
	
	@Override
	public int compareTo(Stock o) {
		return (this.id_stock-o.getID())+(this.name.compareTo(o.getName()));
	}

}
