package dataClasses;

public class Supplier implements Comparable<Supplier>{
	private static int next;
	private int id_supplier;
	private String name;
	private String phone;
	
	public Supplier(int id,String name,String phone) {
		id_supplier=id;
		this.name=name;
		this.phone=phone;
	}
	
	public Supplier(int id,String name) {
		this(id,name,"");
	}
	
	public Supplier(String name,String phone) {
		id_supplier=next++;
		this.name=name;
		this.phone=phone;
	}
	
	public Supplier(String name) {
		this(name,"");
	}
	
	public static void setStatic(int next) {
		Supplier.next=next;
	}

	public int getID() {
		return id_supplier;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return name+" - "+phone;
	}
	
	@Override
	public int compareTo(Supplier o) {
		return (this.id_supplier-o.getID())+(this.name.compareTo(o.getName()));
	}

}
