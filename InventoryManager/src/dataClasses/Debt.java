package dataClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import frames.RootFrame;
import misc.FrameStrings;

public class Debt implements Comparable<Debt> {
	private static int next;
	private Customer customer;
	private int id_debt;
	private LocalDateTime date;
	private DateTimeFormatter formatter;
	private double total;
	private boolean closed;
	private double remaining;
	private double discount;
	private double dollarValue;
	
	public Debt(LocalDateTime date,double total,Customer customer,double discount) {
		this.id_debt=next++;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=date;
		this.total=total;
		this.customer=customer;
		closed=false;
		remaining=total;
		this.discount=discount;
	}
	
	public Debt(int id,String date,double total,Customer customer,double discount) {
		this.id_debt=id;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.date=LocalDateTime.parse(date, formatter);
		this.total=total;
		this.customer=customer;
		closed=false;
		remaining=total;
		this.discount=discount;
	}
	
	public Debt(String date,double total,Customer customer,double discount) {
		this(next++,date,total,customer,discount);
	}
	
	public Debt(double total,Customer customer,double discount) {
		
		this(LocalDateTime.now(),total,customer,discount);
	}
	
	public Debt(double total,Customer customer) {
		
		this(LocalDateTime.now(),total,customer,0);
	}
	
	public Debt(Customer customer) {
		this(0,customer);
	}


	public int getID() {
		return id_debt;
	}
	
	public void setID(int id) {
		this.id_debt=id;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer c) {
		this.customer=c;
	}
	
	public String getDate() {
		
		return formatter.format(date);
	}
	public LocalDateTime getLocalDateTime() {
		
		return date;
	}
	
	public void setLocalDateTime(LocalDateTime now) {
		this.date=now;
		
	}

	public double getTotal() {
		return total;
	}
	
	public double getTotalAfterDiscount() {
		return total-discount;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public double getRemaining() {
		return remaining;
	}
	
	public static void setStatic(int next) {
		Debt.next = next;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	public void setClosed(boolean b) {
		this.closed=b;
	}
	
	public void setRemaining(double rem) {
		this.remaining=rem;
	}
	
	public void deductFromRemaining(double amount) {
		this.remaining-=amount;
	}
	
	public void setDollarValueForDebt(double dollar) {
		dollarValue=dollar;
	}
	
	public double getDollarValueForDebt() {
		return dollarValue;
	}
	
	public String toString() {
		return "<html><table width=\""+((int)300*RootFrame.sizeMultiplier)+"px\"><tr width=\"100%\"><td>"+customer.getName()+" -> "+total+"</td></tr> "+
				"<tr><td align=\"right\">"+formatter.format(date)+" "+"<font color=\"red\">"+
				(remaining==0?FrameStrings.valueOfStringName("debtsstatusclosed"):remaining)+"</font>";
	}
	
	@Override
	public int compareTo(Debt o) {
		return (this.id_debt-o.getID())+(this.date.compareTo(o.getLocalDateTime()));
	}

	public void setTotal(double total) {
		this.total=total;
		
	}

}
