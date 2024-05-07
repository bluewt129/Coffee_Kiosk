package coffee.pj.dto;

public class MemCart_DTO {
	private int mem_cart;
	private String order_num;
	private String menu_name;
	private int order_cost;
	private String order_date;
	
	public MemCart_DTO(int mem_cart, String order_num) {
		super();
		this.mem_cart = mem_cart;
		this.order_num = order_num;
	}
	public MemCart_DTO() {
	}
	public int getMem_cart() {
		return mem_cart;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setMem_cart(int mem_cart) {
		this.mem_cart = mem_cart;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	
	public String getMenu_name() {
		return menu_name;
	}
	public int getOrder_cost() {
		return order_cost;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public void setOrder_cost(int order_cost) {
		this.order_cost = order_cost;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	@Override
	public String toString() {
		return "MemCart_DTO [mem_cart=" + mem_cart + ", order_num=" + order_num + "]";
	}
	
}
