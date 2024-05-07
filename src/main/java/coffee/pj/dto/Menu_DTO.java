package coffee.pj.dto;

public class Menu_DTO {
	private int menu_num;
	private int menu_type;
	private String menu_name;
	private int menu_price;
	private String menu_detail;
	private int stock_cnt;


	public Menu_DTO(int menu_num, int menu_type, String menu_name, int menu_price, String menu_detail, int stock_cnt) {
		super();
		this.menu_num = menu_num;
		this.menu_type = menu_type;
		this.menu_name = menu_name;
		this.menu_price = menu_price;
		this.menu_detail = menu_detail;
		this.stock_cnt = stock_cnt;
	}

	public Menu_DTO() {
	}

	public Menu_DTO(String string, String string2, String string3, String string4) {
	}

	public int getMenu_num() {
		return menu_num;
	}

	public int getMenu_type() {
		return menu_type;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public int getMenu_price() {
		return menu_price;
	}

	public String getMenu_detail() {
		return menu_detail;
	}

	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}

	public void setMenu_type(int menu_type) {
		this.menu_type = menu_type;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public void setMenu_price(int menu_price) {
		this.menu_price = menu_price;
	}

	public void setMenu_detail(String menu_detail) {
		this.menu_detail = menu_detail;
	}
	
	public int getStock_cnt() {
		return stock_cnt;
	}

	public void setStock_cnt(int stock_cnt) {
		this.stock_cnt = stock_cnt;
	}

	@Override
	public String toString() {
		return "Menu_DTO [menu_num=" + menu_num + ", menu_type=" + menu_type + ", menu_name=" + menu_name
				+ ", menu_price=" + menu_price + ", menu_detail=" + menu_detail + "stck_cnt=" + stock_cnt + "]";
	}
	
	
}
