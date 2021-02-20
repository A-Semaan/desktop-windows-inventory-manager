package dataClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Sale implements Comparable<Sale> {
	
	private static int next;
	private int id_sale;
	private LocalDateTime date;
	private DateTimeFormatter formatter;
	private double total;
	private double discount;
	
	public Sale(LocalDateTime date,double total,double discount) {
		this.id_sale=next++;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=date;
		this.total=total;
		this.discount=discount;
	}
	
	public Sale(String date,double total,double discount) {
		this.id_sale=next++;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=LocalDateTime.parse(date, formatter);
		this.total=total;
		this.discount=discount;
	}
	
	public Sale(double total,double discount) {
		
		this(LocalDateTime.now(),total,discount);
	}
	
	public Sale(double total) {
		
		this(LocalDateTime.now(),total,0);
	}
	
	public Sale() {
		this(0);
	}

	public int getID() {
		return id_sale;
	}

	public void setID(int id) {
		this.id_sale=id;
	}
	
	public String getDate() {
		
		return formatter.format(date);
	}
	public LocalDateTime getLocalDateTime() {
		
		return date;
	}
	
	public void setLocalDateTime(LocalDateTime datetime) {
		this.date=datetime;
	}

	public double getTotal() {
		return total;
	}

	public double getTotalAfterDiscount() {
		return total-discount;
	}
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	public static void setStatic(int next) {
		Sale.next = next;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public String toString() {
		return formatter.format(date)+" "+getTotalAfterDiscount();
	}
	
	@Override
	public int compareTo(Sale o) {
		return (this.id_sale-o.getID())+(this.date.compareTo(o.getLocalDateTime()));
	}
	
	

}
