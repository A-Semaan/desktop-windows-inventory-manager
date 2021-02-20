package dataClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DebtLog implements Comparable<DebtLog> {

	private static int next;
	private int id_log;
	private double amount;
	private Debt debt;
	private DateTimeFormatter formatter;
	private LocalDateTime date;
	
	
	public DebtLog(int id,Debt debt, double amount) {
		id_log=id;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		this.amount=amount;
		this.debt=debt;
		date = LocalDateTime.now();
		
	}
	
	public DebtLog(Debt debt, double amount) {
		this(next++,debt,amount);
		
	}
	public DebtLog() {
		this(null,0);
	}
	
	public static void setStatic(int next) {
		DebtLog.next=next;
	}
	
	public int getID() {
		return id_log;
	}
	
	public void setID(int id) {
		this.id_log=id;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public Debt getDebt() {
		return debt;
	}
	
	public String toString() {
		return debt.toString()+" -"+amount;
	}
	
	public String getDate() {
		return formatter.format(date);
	}
	
	public LocalDateTime getLocalDateTime() {
		return date;
	}
	
	@Override
	public int compareTo(DebtLog o) {
		return (id_log-o.getID())+(debt.compareTo(o.getDebt()));
	}

}
