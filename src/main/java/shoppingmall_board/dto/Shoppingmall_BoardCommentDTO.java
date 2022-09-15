package shoppingmall_board.dto;

import java.util.Date;

public class Shoppingmall_BoardCommentDTO {
	private int id;
	private int board_id;
	private String writer;
	private String content;
	private String image;
	private String nickname;
	private Date reg_date;
	// getter, setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	// constructor
	public Shoppingmall_BoardCommentDTO() {}
	public Shoppingmall_BoardCommentDTO(int id, int board_id, String writer, String content, String image,
			String nickname, Date reg_date) {
		super();
		this.id = id;
		this.board_id = board_id;
		this.writer = writer;
		this.content = content;
		this.image = image;
		this.nickname = nickname;
		this.reg_date = reg_date;
	}
	// toString
	@Override
	public String toString() {
		return "Shoppingmall_BoardCommentDTO [id=" + id + ", board_id=" + board_id + ", writer=" + writer + ", content="
				+ content + ", image=" + image + ", nickname=" + nickname + ", reg_date=" + reg_date + "]";
	}
}
