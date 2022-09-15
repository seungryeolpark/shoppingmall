package common;

public class Constants {
	// 업로드 경로
	public final static String PROFILE_PATH = "C:\\upload\\profile\\";
	public final static String PRODUCT_PATH = "C:\\upload\\product\\";
	// 업로드 용량
	public final static int MAX_UPLOAD = 2*1024*1024;
	// 이미지 확장자 제한
	public final static String[] PERMIT_EXT = {"jpg","jpeg","png","gif"};
	// 정규식
	public final static String NUMBER_EXP = "^[0-9]+$";
	public final static String ID_PASSWD_EXP = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
	public final static String EMAIL_EXP = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	public final static String TEL_EXP = "\\d{3}\\d{4}\\d{4}";
}
