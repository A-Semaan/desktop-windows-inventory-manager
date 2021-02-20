package dataClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Purchase implements Comparable<Purchase> {
	
	private static int next;
	private Supplier supplier;
	private int id_purchase;
	private LocalDateTime date;
	private DateTimeFormatter formatter;
	private double total;
	private double discount;
	
	public Purchase(LocalDateTime date,double total,Supplier supplier,double discount) {
		this.id_purchase=next++;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=date;
		this.total=total;
		this.supplier=supplier;
		this.discount=discount;
	}
	
	public Purchase(String date,double total,Supplier supplier, double discount) {
		this.id_purchase=next++;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=LocalDateTime.parse(date, formatter);
		this.total=total;
		this.supplier=supplier;
		this.discount=discount;
	}
	
	public Purchase(double total,Supplier supplier,double discount) {
		
		this(LocalDateTime.now(),total,supplier,discount);
	}
	public Purchase(double total,Supplier supplier) {
		
		this(LocalDateTime.now(),total,supplier,0);
	}
	
	public Purchase(Supplier supplier) {
		this(0,supplier);
	}

	public int getID() {
		return id_purchase;
	}
	
	public void setID(int id) {
		this.id_purchase=id;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}
	
	public String getDate() {
		
		return formatter.format(date);
	}
	public LocalDateTime getLocalDateTime() {
		
		return date;
	}
	
	public void setLocalDateTime(LocalDateTime ldt) {
		this.date=ldt;
	}

	public double getTotal() {
		return total;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	public double getTotalAfterDiscount() {
		return total-discount;
	}
	
	public static void setStatic(int next) {
		Purchase.next = next;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void setSupplier(Supplier s) {
		this.supplier=s;
	}
	
	public String toString() {
		return formatter.format(date)+" "+total+" "+supplier.toString();
	}
	
	@Override
	public int compareTo(Purchase o) {
		return (this.id_purchase-o.getID())+(this.date.compareTo(o.getLocalDateTime()));
	}
	
	
}
