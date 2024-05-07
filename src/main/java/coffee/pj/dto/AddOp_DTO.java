package coffee.pj.dto;

public class AddOp_DTO {
	private int add_num;
	private int opt_num;
	private int menu_num;
	public AddOp_DTO(int add_num, int opt_num, int menu_num) {
		super();
		this.add_num = add_num;
		this.opt_num = opt_num;
		this.menu_num = menu_num;
	}
	public AddOp_DTO() {
	}
	public int getAdd_num() {
		return add_num;
	}
	public int getOpt_num() {
		return opt_num;
	}
	public int getMenu_num() {
		return menu_num;
	}
	public void setAdd_num(int add_num) {
		this.add_num = add_num;
	}
	public void setOpt_num(int opt_num) {
		this.opt_num = opt_num;
	}
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	@Override
	public String toString() {
		return "AddOp_DTO [add_num=" + add_num + ", opt_num=" + opt_num + ", menu_num=" + menu_num + "]";
	}
	
}
