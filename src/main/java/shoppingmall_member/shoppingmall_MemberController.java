package shoppingmall_member;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.Constants;
import crypt.SHA256;
import shoppingmall_board.Pager;
import shoppingmall_member.dao.Shoppingmall_MemberDAO;
import shoppingmall_member.dto.Shoppingmall_MemberCartDTO;
import shoppingmall_member.dto.Shoppingmall_MemberDTO;

@WebServlet("/shoppingmall_member_servlet/*")
public class shoppingmall_MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		Shoppingmall_MemberDAO dao = new Shoppingmall_MemberDAO();
		
		if (uri.indexOf("signup.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			String crf_passwd = request.getParameter("crf_passwd");
			String nickname = request.getParameter("nickname");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String address = request.getParameter("address");
			// invalid 유효성 검사 코드
			String page;
			String[] inputs = {id,passwd,crf_passwd,nickname,email,tel,address};
			if (isParamEmpty(inputs)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("빈값이 있습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!passwd.equals(crf_passwd)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("패스워드가 동일하지 않습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.ID_PASSWD_EXP, id)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("아이디는 숫자와 영문자를 포함해 4~11자여야합니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.ID_PASSWD_EXP, passwd)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("비밀번호는 숫자나 영문자가 4~20자여야합니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.EMAIL_EXP, email)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("이메일을 잘못 적었습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.TEL_EXP, tel)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"signup.jsp?error="+URLEncoder.encode("전화번호를 잘못 적었습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			// 값을 변환하거나 객체 생성하는 코드
			passwd = change_sha256(passwd);
			
			Shoppingmall_MemberDTO dto = new Shoppingmall_MemberDTO();
			dto.setId(id);
			dto.setPasswd(passwd);
			dto.setNickname(nickname);
			dto.setEmail(email);
			dto.setTel(tel);
			dto.setAddress(address);
			dto.setIsAdmin("n");
			dto.setImage("index.png");
			// DB에서 값을 주고받는 코드
			dao.signup(dto);
			
			page = request.getContextPath()+"/shoppingmall/"
			+ "index.jsp?success="+URLEncoder.encode("회원가입 하셨습니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("login.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			// 값을 변환하거나 객체 생성하는 코드
			passwd = change_sha256(passwd);
			
			Shoppingmall_MemberDTO dto = new Shoppingmall_MemberDTO();
			dto.setId(id);
			dto.setPasswd(passwd);
			// DB에서 값을 주고받는 코드
			Shoppingmall_MemberDTO result = dao.login(dto);
			// 로그인 여부 코드
			String path = request.getContextPath()+"/shoppingmall/";
			if (result != null) { // 로그인 성공
				HttpSession session = request.getSession();
				session.setAttribute("id", result.getId());
				session.setAttribute("nickname", result.getNickname());
				session.setAttribute("image", result.getImage());
				session.setAttribute("isAdmin", result.getIsAdmin());
				response.sendRedirect(path+"index.jsp?success="
				+URLEncoder.encode("로그인 성공","utf-8"));
			} else {
				response.sendRedirect(path+"login.jsp?error="
				+URLEncoder.encode("아이디나 비밀번호가 틀렸습니다","utf-8"));
			}
			
		} else if (uri.indexOf("logout.do") != -1) {
			// 세션 객체 생성
			HttpSession session = request.getSession();
			// 세션 초기화
			session.invalidate();
			
			String page = request.getContextPath()
					+"/shoppingmall/index.jsp";
			response.sendRedirect(page);
			
		} else if (uri.indexOf("editView.do") != -1) {
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_MemberDTO dto = new Shoppingmall_MemberDTO();
			dto = dao.getProfile(id);
			request.setAttribute("dto", dto);
			// invalid 유효성 검사 코드
			String error = (String) request.getParameter("error");
			String page = "/shoppingmall/profile_edit.jsp";
			
			if (error != null) {
				if (error.equals("paramEmpty")) {
					page = page+"?error=빈값이 있습니다";
				} else if (error.equals("emailRegexp")) {
					page = page+"?error=이메일을 잘못 적었습니다";
				} else if (error.equals("telRegexp")) {
					page = page+"?error=전화번호를 잘못 적었습니다";
				}
			}
			
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("profileEdit.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			File uploadDir = new File(Constants.PROFILE_PATH);
			if (!uploadDir.exists()) { // 업로드디렉토리가 존재하지 않으면
				uploadDir.mkdir(); // 디렉토리를 만듬
			}
			MultipartRequest multi = new MultipartRequest(request,Constants.PROFILE_PATH
					,Constants.MAX_UPLOAD,"utf-8",new DefaultFileRenamePolicy());
			
			String nickname = multi.getParameter("nickname");
			String email = multi.getParameter("email");
			String tel = multi.getParameter("tel");
			String address = multi.getParameter("address");
			String image = " ";
			
			Enumeration files = multi.getFileNames();
			
			while (files.hasMoreElements()) {
				String file1 = (String) files.nextElement();
				image = multi.getFilesystemName(file1);
			}
			// invalid 유효성 검사 코드
			String page;
			String[] inputs = {nickname,email,tel,address};
			if (isParamEmpty(inputs)) {
				page = request.getContextPath()+"/shoppingmall_member_servlet/"
						+"editView.do?error=paramEmpty";
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.EMAIL_EXP, email)) {
				page = request.getContextPath()+"/shoppingmall_member_servlet/"
						+"editView.do?error=emailRegexp";
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.TEL_EXP, tel)) {
				page = request.getContextPath()+"/shoppingmall_member_servlet/"
						+"editView.do?error=telRegexp";
				response.sendRedirect(page);
				return;
			}
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_MemberDTO dto = new Shoppingmall_MemberDTO();
			dto.setNickname(nickname);
			dto.setEmail(email);
			dto.setTel(tel);
			dto.setAddress(address);
			// 파일 첨부를 하지 않을 경우
			String beforeImage = (String) session.getAttribute("image");
			if (image == null || image.trim().equals("")) {
				image = beforeImage;
			} else { // 파일 첨부 했을 경우 전의 이미지 삭제
				deleteImage(beforeImage);
			}
			dto.setImage(image);
			// DB에서 값을 주고받는 코드
			dao.profileEdit(id, dto);
			
			session.setAttribute("nickname", dto.getNickname());
			session.setAttribute("image", dto.getImage());
			page = request.getContextPath()+"/shoppingmall/"
			+"index.jsp?success="+URLEncoder.encode("계정 수정에 성공하셨습니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("profileDelete.do") != -1) {
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// DB에서 값을 주고받는 코드
			Shoppingmall_MemberDTO dto = dao.getProfile(id);
			String image = dto.getImage();
			// 이미지 삭제
			deleteImage(image);
			// DB에서 값을 주고받는 코드
			dao.profileDelete(id);
			// 세션 초기화
			session.invalidate();
			
			String page = request.getContextPath()+"/shoppingmall/"
			+"index.jsp?success="+URLEncoder.encode("계정 삭제에 성공하셨습니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("profilePasswordEdit.do") != -1) {
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// 서블릿으로 parameter 값 가져오는 코드
			String passwd = request.getParameter("passwd");
			String crf_passwd = request.getParameter("crf_passwd");
			// invalid 유효성 검사 코드
			String page;
			String[] inputs = {passwd,crf_passwd};
			if (isParamEmpty(inputs)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"profile_password_edit.jsp?error="+URLEncoder.encode("빈값이 있습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!passwd.equals(crf_passwd)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"profile_password_edit.jsp?error="+URLEncoder.encode("패스워드가 동일하지 않습니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.ID_PASSWD_EXP, passwd)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"profile_password_edit.jsp?error="+URLEncoder.encode("비밀번호는 숫자나 영문자가 4~20자여야합니다","utf-8");
				response.sendRedirect(page);
				return;
			}
			// 값을 변환하거나 객체 생성하는 코드
			passwd = change_sha256(passwd);
			
			Shoppingmall_MemberDTO dto = new Shoppingmall_MemberDTO();
			dto.setId(id);
			dto.setPasswd(passwd);
			// DB에서 값을 주고받는 코드
			dao.passwordEdit(dto);
			
			page = request.getContextPath()+"/shoppingmall/"
			+"index.jsp?success="+URLEncoder.encode("계정 비밀번호 수정에 성공하셨습니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("cartInsert.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			int num = Integer.parseInt(request.getParameter("num"));
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_MemberCartDTO dto = new Shoppingmall_MemberCartDTO();
			dto.setProduct_id(Integer.parseInt(board_id));
			dto.setProduct_count(num);
			dto.setMember_id(id);
			// DB에서 값을 주고받는 코드
			dao.cartInsert(dto);
			
		} else if (uri.indexOf("cartList.do") != -1) {
			// 세션 parameter 불러오기
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			// page 코드
			int count = dao.memberCartCount(id);
			int curPage = 1;
			if (request.getParameter("curPage") != null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			Pager pager = new Pager(count, curPage);
			int start = pager.getPageBegin();
			int end = pager.getPageEnd();
			// DB에서 값을 주고받는 코드
			List<Shoppingmall_MemberCartDTO> list = dao.cartList(start, end, id);
			long total_price = dao.getTotalPrice(id);
			request.setAttribute("total_price", total_price);
			request.setAttribute("list",list);
			request.setAttribute("page", pager);
			
			String page = "/shoppingmall/member_cart.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("cartDelete.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String id = request.getParameter("id");
			// DB에서 값을 주고받는 코드
			dao.cartDelete(id);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String change_sha256(String passwd) {
		SHA256 sha = SHA256.getInstance();
		try {
			passwd = sha.getSha256(passwd.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwd;
	}
	
	private boolean isParamEmpty(String params[]) {
		for (String param : params) {
			if (param.equals("")) return true;
		}
		return false;
	}
	
	private boolean isRegexp(String exp, String str) {
		if (Pattern.matches(exp,str)) return true;
		return false;
	}
	
	private void deleteImage(String image) {
		if (image.equals("index.png")) { // 기본이지면 삭제 x
		} else { // 기본이미지가 아닐경우 삭제
			File deleteFile = new File(Constants.PROFILE_PATH+"/"+image);
			deleteFile.delete();
		}
	}
}
