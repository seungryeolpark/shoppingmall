package shoppingmall_board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import shoppingmall_board.dto.Shoppingmall_BoardCommentDTO;
import shoppingmall_board.dto.Shoppingmall_BoardDTO;
import sqlmap.MybaticsManager;

public class Shoppingmall_BoardDAO {

	public void productInsert(Shoppingmall_BoardDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.insert("shm_board.productInsert", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Shoppingmall_BoardDTO> list(int start, int end, String type) {
		List<Shoppingmall_BoardDTO> list = null;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			map.put("type", type);
			//map.put("search", "%"+search+"%");
			//map.put("search_option", search_option);
			list = session.selectList("shm_board.productList", map);
			
			for (Shoppingmall_BoardDTO dto : list) {
				if (dto.getIsSale().equals("y")) {
					int price = dto.getPrice();
					int sale_percent = dto.getSale_percent();
					
					dto.setSale_price((int) (price*(1-(sale_percent*0.01) )));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Shoppingmall_BoardDTO getProduct(String board_id) {
		Shoppingmall_BoardDTO dto = null;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			dto = session.selectOne("shm_board.product", board_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public void productDelete(String board_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.delete("shm_board.productDelete", board_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void productUpdate(Shoppingmall_BoardDTO dto, String board_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String,Object> map=new HashMap<>();
			map.put("board_id", board_id);
			map.put("dto", dto);
			
			session.update("shm_board.productEdit", map);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int productCount(String type) {
		int result = 0;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			result = session.selectOne("shm_board.product_count", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void commentAdd(Shoppingmall_BoardCommentDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.insert("shm_board.productCommentInsert", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Shoppingmall_BoardCommentDTO> commentList(int start, int end, String board_id) {
		List<Shoppingmall_BoardCommentDTO> list = null;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			map.put("board_id", board_id);
			list = session.selectList("shm_board.productCommentList", map);
			
			for (Shoppingmall_BoardCommentDTO dto : list) {
				String content = dto.getContent();
				content = content.replace("  ", "&nbsp;&nbsp;"); // 공백문자처리(스페이스 2칸만처리)
				content = content.replace("<", "&lt;"); // less than ~보다 작다
				content = content.replace(">", "&gt;"); // greater than ~보다 크다
				
				dto.setContent(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void commentEdit(String comment_id, String content) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String,Object> map=new HashMap<>();
			map.put("id", comment_id);
			map.put("content", content);
			
			session.update("shm_board.productCommentEdit", map);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commentDelete(String comment_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.delete("shm_board.productCommentDelete", comment_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void plusRead(String board_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.update("shm_board.ProductPlusRead", board_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void plusLike(String board_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.update("shm_board.ProductPlusLike", board_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int productCommentCount(String board_id) {
		int result = 0;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			result = session.selectOne("shm_board.product_comment_count", board_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int productSearchCount(String search_option, String search) {
		int result = 0;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String,Object> map=new HashMap<>();
			map.put("search_option", search_option);
			map.put("search", "%"+search+"%");
			result = session.selectOne("shm_board.product_search_count", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<Shoppingmall_BoardDTO> searchList(int start, int end, String search_option, String search) {
		List<Shoppingmall_BoardDTO> list = null;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			map.put("search_option", search_option);
			map.put("search", "%"+search+"%");
			list = session.selectList("shm_board.productSearchList", map);
			
			for(Shoppingmall_BoardDTO dto : list) {
				String subject = dto.getSubject();
				subject = subject.replace("  ", "&nbsp;&nbsp;");
				subject = subject.replace("<", "&lt;");
				subject = subject.replace(">", "&gt;"); 
				subject = subject.replace(search, "<font color='red'>"+search+"</font>");
				
				dto.setSubject(subject);
				
				if (dto.getIsSale().equals("y")) {
					int price = dto.getPrice();
					int sale_percent = dto.getSale_percent();
					
					dto.setSale_price((int) (price*(1-(sale_percent*0.01) )));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
