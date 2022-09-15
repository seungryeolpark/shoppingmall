package shoppingmall_board;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
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
import shoppingmall_board.dao.Shoppingmall_BoardDAO;
import shoppingmall_board.dto.Shoppingmall_BoardCommentDTO;
import shoppingmall_board.dto.Shoppingmall_BoardDTO;


@WebServlet("/shoppingmall_board_servlet/*")
public class shoppingmall_BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		Shoppingmall_BoardDAO dao = new Shoppingmall_BoardDAO();
		
		if (uri.indexOf("productInsert.do") != -1) {
			File uploadDir = new File(Constants.PRODUCT_PATH);
			if (!uploadDir.exists()) { // 업로드디렉토리가 존재하지 않으면
				uploadDir.mkdir(); // 디렉토리를 만듬
			}
			// 서블릿으로 parameter 값 가져오는 코드
			MultipartRequest multi = new MultipartRequest(request,Constants.PRODUCT_PATH
					,Constants.MAX_UPLOAD,"utf-8",new DefaultFileRenamePolicy());
			
			String subject = multi.getParameter("subject");
			String type = multi.getParameter("type");
			String product_name = multi.getParameter("product_name");
			String price = multi.getParameter("price");
			String isSale = multi.getParameter("isSale");
			String sale_percent = multi.getParameter("sale_percent");
			
			File img_file = multi.getFile("img_file");
			
			// invalid 유효성 검사 코드
			String page;
			String[] inputs = {subject,product_name,price};
			if (isParamEmpty(inputs)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"product_insert.jsp?error="+URLEncoder.encode("빈값이 있습니다","utf-8");;
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.NUMBER_EXP, price)) {
				page = request.getContextPath()+"/shoppingmall/"
				+"product_insert.jsp?error="+URLEncoder.encode("가격은 숫자만 가능합니다","utf-8");;
				response.sendRedirect(page);
				return;
			}
			
			if (img_file != null) {
				String filename = img_file.getName();
				String[] filenameArr = filename.split("\\.");
				String ext = filenameArr[filenameArr.length-1];
				
				if (permitExit(ext) && img_file.canRead()) {
				} else {
					page = request.getContextPath()+"/shoppingmall/"
					+"product_insert.jsp?error="+URLEncoder.encode("정상적인 이미지 파일만 가능합니다\n"
					+ "(가능한 확장자:jpg,jpeg,png,gif)","utf-8");
					response.sendRedirect(page);
					return;
				}
			}
			// 로그인중인지 체크
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			if (id == null) { // 세션이 없을 경우
				page = request.getContextPath()+"/shoppingmall_board_servlet/"
				+"list.do?type=food"
				+"&error="+URLEncoder.encode("아이디가 없습니다","utf-8");
				response.sendRedirect(page);
			}
			
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_BoardDTO dto = new Shoppingmall_BoardDTO();
			dto.setWriter(id);
			dto.setSubject(subject);
			dto.setType(type);
			dto.setProduct_name(product_name);
			dto.setPrice(Integer.parseInt(price));
			// 할인을 적용할 경우
			if (isSale != null) {
				dto.setIsSale(isSale);
				dto.setSale_percent(Integer.parseInt(sale_percent));
			} else { // 할인 적용 안할 경우
				dto.setIsSale("n");
				dto.setSale_percent(0);
			}
			// 이미지 파일이 있을 경우
			if (img_file != null) {
				dto.setImage(img_file.getName());
			} else { // 이미지 파일이 없을 경우
				dto.setImage("noImage.png");
			}
			// DB에서 값을 주고받는 코드
			dao.productInsert(dto);
			
			page = request.getContextPath()
			+"/shoppingmall_board_servlet/list.do?type="+type
			+ "&success="+URLEncoder.encode("상품을 추가합니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("list.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String type = "";
			String page = "";
			if (request.getParameter("type") != null) { type = request.getParameter("type");
			if (type.equals("all")) type = "food";
			} else {
				page = "/shoppingmall/product_list.jsp?type=food&error="+URLEncoder.encode("상품 종류를 선택하세요","utf-8");
				response.sendRedirect(page);
			}
			// page 코드
			int count = dao.productCount(type);
			int curPage = 1;
			if (request.getParameter("curPage") != null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			Pager pager = new Pager(count, curPage);
			int start = pager.getPageBegin();
			int end = pager.getPageEnd();
			// DB에서 값을 주고받는 코드
			List<Shoppingmall_BoardDTO> list = dao.list(start, end, type);
			request.setAttribute("type", type);
			request.setAttribute("list",list);
			request.setAttribute("page", pager);
			
			page = "/shoppingmall/product_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("productDelete.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			String type = request.getParameter("type");
			// DB에서 값을 주고받는 코드
			Shoppingmall_BoardDTO dto = dao.getProduct(board_id);
			String image = dto.getImage();
			// 이미지 삭제
			deleteImage(image);
			// DB에서 값을 주고받는 코드
			dao.productDelete(board_id);
			
			String page = request.getContextPath()
			+"/shoppingmall_board_servlet/list.do?type="+type
			+ "&success="+URLEncoder.encode("상품을 삭제합니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("productEditView.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			// DB에서 값을 주고받는 코드
			Shoppingmall_BoardDTO dto = dao.getProduct(board_id);
			request.setAttribute("dto",dto);
			
			String page = "/shoppingmall/product_edit.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("productEdit.do") != -1) {
			File uploadDir = new File(Constants.PRODUCT_PATH);
			if (!uploadDir.exists()) { // 업로드디렉토리가 존재하지 않으면
				uploadDir.mkdir(); // 디렉토리를 만듬
			}
			// 서블릿으로 parameter 값 가져오는 코드
			MultipartRequest multi = new MultipartRequest(request,Constants.PRODUCT_PATH
					,Constants.MAX_UPLOAD,"utf-8",new DefaultFileRenamePolicy());
			
			String board_id = multi.getParameter("board_id");
			String before_type = multi.getParameter("before_type");
			String subject = multi.getParameter("subject");
			String type = multi.getParameter("type");
			String product_name = multi.getParameter("product_name");
			String price = multi.getParameter("price");
			String isSale = multi.getParameter("isSale");
			String sale_percent = multi.getParameter("sale_percent");
			
			File img_file = multi.getFile("img_file");
			
			// invalid 유효성 검사 코드
			String page;
			String[] inputs = {subject,product_name,price};
			if (isParamEmpty(inputs)) {
				page = request.getContextPath()+"/shoppingmall_board_servlet/"
				+"productEditView.do?board_id="+board_id+"&type="+before_type
				+"&error="+URLEncoder.encode("빈값이 있습니다","utf-8");;
				response.sendRedirect(page);
				return;
			}
			
			if (!isRegexp(Constants.NUMBER_EXP, price)) {
				page = request.getContextPath()+"/shoppingmall_board_servlet/"
				+"productEditView.do?board_id="+board_id+"&type="+before_type
				+"&error="+URLEncoder.encode("가격은 숫자만 가능합니다","utf-8");;
				response.sendRedirect(page);
				return;
			}
			
			if (img_file != null) {
				String filename = img_file.getName();
				String[] filenameArr = filename.split("\\.");
				String ext = filenameArr[filenameArr.length-1];
				
				if (permitExit(ext) && img_file.canRead()) {
				} else {
					page = request.getContextPath()+"/shoppingmall_board_servlet/"
					+"productEditView.do?board_id="+board_id+"&type="+before_type
					+"&error="+URLEncoder.encode("정상적인 이미지 파일만 가능합니다\n"
					+"(가능한 확장자:jpg,jpeg,png,gif)","utf-8");
					response.sendRedirect(page);
					return;
				}
			}
			
			// 로그인중인지 체크
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			if (id == null) { // 세션이 없을 경우
				page = request.getContextPath()+"/shoppingmall_board_servlet/"
				+"list.do?type="+before_type
				+"&error="+URLEncoder.encode("아이디가 없습니다","utf-8");
				response.sendRedirect(page);
			}
			
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_BoardDTO dto = new Shoppingmall_BoardDTO();
			dto.setWriter(id);
			dto.setSubject(subject);
			dto.setType(type);
			dto.setProduct_name(product_name);
			dto.setPrice(Integer.parseInt(price));
			// 할인을 적용할 경우
			if (isSale != null) {
				dto.setIsSale(isSale);
				dto.setSale_percent(Integer.parseInt(sale_percent));
			} else { // 할인 적용 안할 경우
				dto.setIsSale("n");
				dto.setSale_percent(0);
			}
			// 이미지 파일 수정 관련 코드
			String beforeImage = dao.getProduct(board_id).getImage();
			if (img_file != null) { // 이미지 파일을 수정 했을 경우
				deleteImage(beforeImage);
				dto.setImage(img_file.getName());
			} else { // 이미지 파일을 수정 안했을 경우
				dto.setImage(beforeImage);
			}
			// DB에서 값을 주고받는 코드
			dao.productUpdate(dto,board_id);
			
			page = request.getContextPath()
			+"/shoppingmall_board_servlet/list.do?type="+type
			+ "&success="+URLEncoder.encode("상품을 변경합니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("productView.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			int curPage = 1;
			if (request.getParameter("curPage") != null && !request.getParameter("curPage").equals("")) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			// DB에서 값을 주고받는 코드
			Shoppingmall_BoardDTO dto = dao.getProduct(board_id);
			// 조회수 증가
			dao.plusRead(board_id);
			
			// 만약 세일하면 할인된 값 구하기
			int salePrice = 0;
			if (dto.getIsSale().equals("y")) {
				salePrice = (int) (dto.getPrice()-(dto.getPrice()*(dto.getSale_percent()*0.01) ));
			}
			
			request.setAttribute("dto", dto);
			request.setAttribute("curPage", curPage);
			request.setAttribute("salePrice", salePrice);
			
			String page = "/shoppingmall/product_view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("productPlusLike.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			// 추천수 증가
			dao.plusLike(board_id);
			
			String page = request.getContextPath()+"/shoppingmall_board_servlet/"
			+"productView.do?board_id="+board_id;
			response.sendRedirect(page);
			
		} else if (uri.indexOf("commentAdd.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			String content = request.getParameter("content");
			// 로그인중인지 체크
			HttpSession session = request.getSession();
			String writer = (String) session.getAttribute("id");
			String page = "";
			if (writer == null) { // 세션이 없을 경우
				page = request.getContextPath()+"/shoppingmall_board_servlet/"
				+"productView.do?board_id="+board_id
				+"&error="+URLEncoder.encode("아이디가 없습니다","utf-8");
				response.sendRedirect(page);
			}
			String image = (String) session.getAttribute("image");
			String nickname = (String) session.getAttribute("nickname");
			// 값을 변환하거나 객체 생성하는 코드
			Shoppingmall_BoardCommentDTO dto = new Shoppingmall_BoardCommentDTO();
			dto.setBoard_id(Integer.parseInt(board_id));
			dto.setWriter(writer);
			dto.setContent(content);
			dto.setImage(image);
			dto.setNickname(nickname);
			// DB에서 값을 주고받는 코드
			dao.commentAdd(dto);
			
		} else if (uri.indexOf("commentList.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			// page 코드
			int count = dao.productCommentCount(board_id);
			int curPage = 1;
			if (request.getParameter("curPage") != null && !request.getParameter("curPage").equals("")) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			Pager pager = new Pager(count, curPage);
			int start = pager.getPageBegin();
			int end = pager.getPageEnd();
			// DB에서 값을 주고받는 코드
			List<Shoppingmall_BoardCommentDTO> list = dao.commentList(start, end, board_id);
			
			request.setAttribute("list", list);
			request.setAttribute("board_id", board_id);
			request.setAttribute("page", pager);
			
			String page = "/include/fragment/section/product_comment_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (uri.indexOf("commentEdit.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			String comment_id = request.getParameter("comment_id");
			String content = request.getParameter("content");
			// DB에서 값을 주고받는 코드
			dao.commentEdit(comment_id, content);
			
			String page = request.getContextPath()
			+"/shoppingmall_board_servlet/productView.do?board_id="+board_id
			+ "&success="+URLEncoder.encode("댓글을 수정합니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("commentDelete.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String board_id = request.getParameter("board_id");
			String comment_id = request.getParameter("comment_id");
			// DB에서 값을 주고받는 코드
			dao.commentDelete(comment_id);
			
			String page = request.getContextPath()
			+"/shoppingmall_board_servlet/productView.do?board_id="+board_id
			+ "&success="+URLEncoder.encode("댓글을 삭제합니다","utf-8");
			response.sendRedirect(page);
			
		} else if (uri.indexOf("searchList.do") != -1) {
			// 서블릿으로 parameter 값 가져오는 코드
			String search_option = "all";
			String search = "";
			
			if (request.getParameter("search_option") != null) {
				search_option = request.getParameter("search_option");
			}
			
			if (request.getParameter("search") != null) {
				search = request.getParameter("search");
			}
			// page 코드
			int count = dao.productSearchCount(search_option, search);
			int curPage = 1;
			if (request.getParameter("curPage") != null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			Pager pager = new Pager(count, curPage);
			int start = pager.getPageBegin();
			int end = pager.getPageEnd();
			// DB에서 값을 주고받는 코드
			List<Shoppingmall_BoardDTO> list = dao.searchList(start, end, search_option, search);
			request.setAttribute("type", search_option);
			request.setAttribute("search", search);
			request.setAttribute("list", list);
			request.setAttribute("page", pager);
			
			String page = "/shoppingmall/product_search_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean isParamEmpty(String params[]) {
		for (Object param : params) {
			if (param.equals("")) return true;
		}
		return false;
	}
	
	private boolean isRegexp(String exp, String str) {
		if (Pattern.matches(exp,str)) return true;
		return false;
	}
	
	private void deleteImage(String image) {
		if (image.equals("noImage.png")) { // 기본이지면 삭제 x
		} else { // 기본이미지가 아닐경우 삭제
			File deleteFile = new File(Constants.PRODUCT_PATH+"/"+image);
			deleteFile.delete();
		}
	}
	
	private boolean permitExit(String ext) {
		for (String permitExt : Constants.PERMIT_EXT) {
			if (ext.equals(permitExt)) return true;
		}
		return false;
	}
}
