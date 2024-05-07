package coffee.pj.dto;

public class Optional_DTO {
	private int opt_num;
	private String opt_name;
	private int opt_price;
	private String opt_detail;
	
	public Optional_DTO(int opt_num, String opt_name, int opt_price, String opt_detail) {
		super();
		this.opt_num = opt_num;
		this.opt_name = opt_name;
		this.opt_price = opt_price;
		this.opt_detail = opt_detail;
	}

	public Optional_DTO() {
	}

	public int getOpt_num() {
		return opt_num;
	}

	public String getOpt_name() {
		return opt_name;
	}

	public int getOpt_price() {
		return opt_price;
	}

	public String getOpt_detail() {
		return opt_detail;
	}

	public void setOpt_num(int opt_num) {
		this.opt_num = opt_num;
	}

	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
	}

	public void setOpt_price(int opt_price) {
		this.opt_price = opt_price;
	}

	public void setOpt_detail(String opt_detail) {
		this.opt_detail = opt_detail;
	}

	@Override
	public String toString() {
		return "Optional_DTO [opt_num=" + opt_num + ", opt_name=" + opt_name + ", opt_price=" + opt_price
				+ ", opt_detail=" + opt_detail + "]";
	}
	
	
	
}
