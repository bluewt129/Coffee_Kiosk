package coffee.pj.dto;

public class Guest_DTO {
	private String guest_code;
	private int order_num;
	public Guest_DTO(String guest_code, int order_num) {
		super();
		this.guest_code = guest_code;
		this.order_num = order_num;
	}
	public Guest_DTO() {
	}
	public String getGuest_code() {
		return guest_code;
	}
	public int getOrder_num() {
		return order_num;
	}
	public void setGuest_code(String guest_code) {
		this.guest_code = guest_code;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	@Override
	public String toString() {
		return "Guest_DTO [guest_code=" + guest_code + ", order_num=" + order_num + "]";
	}
}
