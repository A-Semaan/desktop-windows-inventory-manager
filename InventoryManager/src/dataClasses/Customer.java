package dataClasses;

public class Customer implements Comparable<Customer>{
	private static int next;
	private int id_customer;
	private String name;
	private String phone;
	
	public Customer(int id,String name,String phone) {
		id_customer=id;
		this.name=name;
		this.phone=phone;
	}
	public Customer(int id,String name) {
		this(id,name,"");
	}
	
	public Customer(String name,String phone) {
		id_customer=next++;
		this.name=name;
		this.phone=phone;
	}
	
	public Customer(String name) {
		this(name,"");
	}
	
	public static void setStatic(int ne) {
		next=ne;
	}

	public int getID() {
		return id_customer;
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
	public int compareTo(Customer o) {
		return (this.id_customer-o.getID())+(this.name.compareTo(o.getName()));
	}
}
