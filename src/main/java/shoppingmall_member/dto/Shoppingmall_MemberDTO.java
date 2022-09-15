package shoppingmall_member.dto;

import java.util.Date;

public class Shoppingmall_MemberDTO {
	private String id;
	private String passwd;
	private String nickname;
	private String email;
	private String tel;
	private String image;
	private String address;
	private String isAdmin;
	private Date join_date;
	// setter getter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Date getJoin_date() {
		return join_date;
	}
	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}
	// constructor
	public Shoppingmall_MemberDTO() {}
	public Shoppingmall_MemberDTO(String id, String passwd, String nickname, String email, String tel, String image,
			String address, String isAdmin, Date join_date) {
		super();
		this.id = id;
		this.passwd = passwd;
		this.nickname = nickname;
		this.email = email;
		this.tel = tel;
		this.image = image;
		this.address = address;
		this.isAdmin = isAdmin;
		this.join_date = join_date;
	}
	// toString 
	@Override
	public String toString() {
		return "Shoppingmall_MemberDTO [id=" + id + ", passwd=" + passwd + ", nickname=" + nickname + ", email=" + email
				+ ", tel=" + tel + ", image=" + image + ", address=" + address + ", isAdmin=" + isAdmin + ", join_date="
				+ join_date + "]";
	}
}
