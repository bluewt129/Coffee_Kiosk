package coffee.pj.dto;

public class OrderMenu_DTO {
	private String order_num;
	private String order_date;
	private int menu_num;
	private int order_cost;
	private String order_status;
	private int mem_cart;
	
	public OrderMenu_DTO(String order_num, String order_date, int menu_num, int order_cost, String order_status, int mem_cart) {
		super();
		this.order_num = order_num;
		this.order_date = order_date;
		this.menu_num = menu_num;
		this.order_cost = order_cost;
		this.order_status = order_status;
		this.setMem_cart(mem_cart);
	}
	public OrderMenu_DTO() {
	}
	public String getOrder_num() {
		return order_num;
	}
	public String getOrder_date() {
		return order_date;
	}
	public int getMenu_num() {
		return menu_num;
	}
	public int getOrder_cost() {
		return order_cost;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	public void setOrder_cost(int order_cost) {
		this.order_cost = order_cost;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getMem_cart() {
		return mem_cart;
	}
	public void setMem_cart(int mem_cart) {
		this.mem_cart = mem_cart;
	}
	@Override
	public String toString() {
		return "OrderMenu_DTO [order_num=" + order_num + ", order_date=" + order_date + ", menu_num=" + menu_num
				+ ", order_cost=" + order_cost + ", order_status=" + order_status + "]";
	}
}
