package coffee.pj.dto;

public class Stock_DTO {
	
	private int menu_num;
	private int stock_cnt;
	
	public Stock_DTO(int menu_num, int stock_cnt) {
		super();
		this.menu_num = menu_num;
		this.stock_cnt = stock_cnt;
	}

	public int getMenu_num() {
		return menu_num;
	}

	public int getStock_cnt() {
		return stock_cnt;
	}

	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}

	public void setStock_cnt(int stock_cnt) {
		this.stock_cnt = stock_cnt;
	}

	@Override
	public String toString() {
		return "Stock_DTO [menu_num=" + menu_num + ", stock_cnt=" + stock_cnt + "]";
	}

	
}
