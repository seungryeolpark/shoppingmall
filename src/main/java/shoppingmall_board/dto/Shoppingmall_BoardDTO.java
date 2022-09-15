package shoppingmall_board.dto;

import java.util.Date;

public class Shoppingmall_BoardDTO {
	private int id;
	private String writer;
	private String type;
	private String subject;
	private String image;
	private int price;
	private String isSale;
	private int sale_percent;
	private int read_count;
	private int like_count;
	private Date reg_date;
	private String product_name;
	// 테이블 열이 아님
	private int comment_count;
	private int sale_price;
	
	// getter, setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getIsSale() {
		return isSale;
	}
	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}
	public int getSale_percent() {
		return sale_percent;
	}
	public void setSale_percent(int sale_percent) {
		this.sale_percent = sale_percent;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getSale_price() {
		return sale_price;
	}
	public void setSale_price(int sale_price) {
		this.sale_price = sale_price;
	}
	// constructor
	public Shoppingmall_BoardDTO() {}
	public Shoppingmall_BoardDTO(int id, String writer, String type, String subject, String image, int price,
			String isSale, int sale_percent, int read_count, int like_count, int sale_price, Date reg_date,
			String product_name, int comment_count) {
		super();
		this.id = id;
		this.writer = writer;
		this.type = type;
		this.subject = subject;
		this.image = image;
		this.price = price;
		this.isSale = isSale;
		this.sale_percent = sale_percent;
		this.read_count = read_count;
		this.like_count = like_count;
		this.sale_price = sale_price;
		this.reg_date = reg_date;
		this.product_name = product_name;
		this.comment_count = comment_count;
	}
	// toString
	@Override
	public String toString() {
		return "Shoppingmall_BoardDTO [id=" + id + ", writer=" + writer + ", type=" + type + ", subject=" + subject
				+ ", image=" + image + ", price=" + price + ", isSale=" + isSale + ", sale_percent=" + sale_percent
				+ ", read_count=" + read_count + ", like_count=" + like_count + ", sale_price=" + sale_price
				+ ", reg_date=" + reg_date + ", product_name=" + product_name + ", comment_count=" + comment_count
				+ "]";
	}
}
