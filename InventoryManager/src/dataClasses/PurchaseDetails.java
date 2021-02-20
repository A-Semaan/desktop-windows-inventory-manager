package dataClasses;

import frames.RootFrame;

public class PurchaseDetails implements Comparable<PurchaseDetails> {

	private Stock stock;
	private Purchase purchase;
	private int qty;
	
	public PurchaseDetails(Stock stock, Purchase purchase, int qty) {
		this.stock = stock;
		this.purchase = purchase;
		this.qty = qty;
	}

	

	public Stock getStock() {
		return stock;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty=qty;
	}

	public double getTotal() {
		return qty*stock.getBoughtPrice();
	}
	
	public String toString() {		
		return "<html><table width=\""+((int)300*RootFrame.sizeMultiplier)+"px\"><tr width=\"100%\"><td>"+stock.getName()+"</td></tr> "+
				"<tr><td align=\"right\">"+stock.getBoughtPrice()+"$ -"+qty+
				"-  <font color=\"red\">"+getTotal()+"$</font></td></tr></table></html>";
	}

	@Override
	public int compareTo(PurchaseDetails o) {
		return stock.compareTo(o.getStock())+purchase.compareTo(o.getPurchase());
	}

}
