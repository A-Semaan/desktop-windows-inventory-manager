package dataClasses;

import frames.RootFrame;

public class DebtDetails implements Comparable<DebtDetails> {

	private Debt debt;
	private Stock stock;
	private int qty;
	
	
	
	public DebtDetails(Stock stock, Debt debt, int qty) {
		this.debt = debt;
		this.stock = stock;
		this.qty = qty;
	}

	public Debt getDebt() {
		return debt;
	}

	public Stock getStock() {
		return stock;
	}

	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty=qty;
	}

	public double getTotal() {
		return qty*stock.getSalePrice();
	}
	
	public String toString() {
		return "<html><table width=\""+((int)300*RootFrame.sizeMultiplier)+"px\"><tr width=\"100%\"><td>"+stock.getName()+"</td></tr> "+
				"<tr><td align=\"right\">"+stock.getSalePrice()+"$ -"+qty+
				"-  <font color=\"red\">"+getTotal()+"$</font></td></tr></table></html>";
	}

	@Override
	public int compareTo(DebtDetails o) {
		return stock.compareTo(o.getStock())+debt.compareTo(o.getDebt());
	}

}
