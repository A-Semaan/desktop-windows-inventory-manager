package dataClasses;

import frames.RootFrame;

public class SaleDetails implements Comparable<SaleDetails> {
	
	private Stock stock;
	private Sale sale;
	private int qty;
	
	public SaleDetails(Stock stock, Sale sale, int qty) {
		this.stock = stock;
		this.sale = sale;
		this.qty = qty;
	}
	
	public Stock getStock() {
		return stock;
	}

	public Sale getSale() {
		return sale;
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
				"<tr><td align=\"right\">"+stock.getSalePrice()+"$  -"+qty+
				"-  <font color=\"red\">"+getTotal()+"$</font></td></tr></table></html>";
	}

	@Override
	public int compareTo(SaleDetails o) {
		
		return stock.compareTo(o.getStock())+sale.compareTo(o.getSale());
	}

}
