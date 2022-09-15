package shoppingmall_member.dto;

import java.util.Date;

import shoppingmall_board.dto.Shoppingmall_BoardDTO;

public class Shoppingmall_MemberCartDTO {
	private int id;
	private String member_id;
	private int product_id;
	private int product_count;
	private Date reg_date;
	// 테이블 열이 아님
	private String image;
	private String product_name;
	private int price;
	private int sale_percent;
	private int sum;
	
	// getter, setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getProduct_count() {
		return product_count;
	}
	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSale_percent() {
		return sale_percent;
	}
	public void setSale_percent(int sale_percent) {
		this.sale_percent = sale_percent;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	// constructor
	public Shoppingmall_MemberCartDTO() {}
	public Shoppingmall_MemberCartDTO(int id, String member_id, int product_id, int product_count, Date reg_date,
			String image, String product_name, int price, int sale_percent, int sum) {
		super();
		this.id = id;
		this.member_id = member_id;
		this.product_id = product_id;
		this.product_count = product_count;
		this.reg_date = reg_date;
		this.image = image;
		this.product_name = product_name;
		this.price = price;
		this.sale_percent = sale_percent;
		this.sum = sum;
	}
	// toString
	@Override
	public String toString() {
		return "Shoppingmall_MemberCartDTO [id=" + id + ", member_id=" + member_id + ", product_id=" + product_id
				+ ", product_count=" + product_count + ", reg_date=" + reg_date + ", image=" + image + ", product_name="
				+ product_name + ", price=" + price + ", sale_percent=" + sale_percent + ", sum=" + sum + "]";
	}
}
