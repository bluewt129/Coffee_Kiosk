package coffee.pj.dto;

public class Member_DTO {
	private int mem_num;
	private int mem_type; //1:관리자 2:회원
	private String mem_name;
	private String mem_phone;
	private int mem_cart;
	private String mem_date; //Date일지 String일지 생각해보기
	
	public Member_DTO(int mem_num, int mem_type, String mem_name, String mem_phone, int mem_cart, String mem_date) {
		super();
		this.mem_num = mem_num;
		this.mem_type = mem_type;
		this.mem_name = mem_name;
		this.mem_phone = mem_phone;
		this.mem_cart = mem_cart;
		this.mem_date = mem_date;
	}
	public Member_DTO() {
	}
	public int getMem_num() {
		return mem_num;
	}
	public int getMem_type() {
		return mem_type;
	}
	public String getMem_name() {
		return mem_name;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public int getMem_cart() {
		return mem_cart;
	}
	public String getMem_date() {
		return mem_date;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public void setMem_type(int mem_type) {
		this.mem_type = mem_type;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public void setMem_cart(int mem_cart) {
		this.mem_cart = mem_cart;
	}
	public void setMem_date(String mem_date) {
		this.mem_date = mem_date;
	}
	@Override
	public String toString() {
		return "Member_DTO [mem_num=" + mem_num + ", mem_type=" + mem_type + ", mem_name=" + mem_name + ", mem_phone="
				+ mem_phone + ", mem_cart=" + mem_cart + ", mem_date=" + mem_date + "]";
	}
	
}
