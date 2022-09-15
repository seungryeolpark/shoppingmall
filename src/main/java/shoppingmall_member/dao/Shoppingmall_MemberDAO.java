package shoppingmall_member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import shoppingmall_board.dto.Shoppingmall_BoardCommentDTO;
import shoppingmall_board.dto.Shoppingmall_BoardDTO;
import shoppingmall_member.dto.Shoppingmall_MemberCartDTO;
import shoppingmall_member.dto.Shoppingmall_MemberDTO;
import sqlmap.MybaticsManager;

public class Shoppingmall_MemberDAO {

	public void signup(Shoppingmall_MemberDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.insert("shm_member.signup", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Shoppingmall_MemberDTO login(Shoppingmall_MemberDTO dto) {
		Shoppingmall_MemberDTO result = new Shoppingmall_MemberDTO();
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			result = session.selectOne("shm_member.login", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Shoppingmall_MemberDTO getProfile(String id) {
		Shoppingmall_MemberDTO result = new Shoppingmall_MemberDTO();
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			result = session.selectOne("shm_member.profileView", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void profileEdit(String id, Shoppingmall_MemberDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String,Object> map=new HashMap<>();
			map.put("id", id);
			map.put("dto", dto);
			
			session.update("shm_member.profileEdit", map);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void profileDelete(String id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.delete("shm_member.profileDelete", id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void passwordEdit(Shoppingmall_MemberDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.insert("shm_member.profilePasswordEdit", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cartInsert(Shoppingmall_MemberCartDTO dto) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.insert("shm_member.cartInsert", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int memberCartCount(String member_id) {
		int result = 0;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {;
			result = session.selectOne("shm_member.member_cart_count", member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<Shoppingmall_MemberCartDTO> cartList(int start, int end, String member_id) {
		List<Shoppingmall_MemberCartDTO> list = null;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			map.put("member_id", member_id);
			list = session.selectList("shm_member.memberCartList", map);
			
			for (Shoppingmall_MemberCartDTO dto : list) {
				int price = dto.getPrice();
				int sale_percent = dto.getSale_percent();
				
				price = (int) (price-( price*(sale_percent*0.01) ));
				
				dto.setPrice(price);
				dto.setSum(price*dto.getProduct_count());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void cartDelete(String id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.delete("shm_member.cartDelete", id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getTotalPrice(String member_id) {
		long result = 0;
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {;
			result = session.selectOne("shm_member.getCartTotalPrice", member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void cartAllDelete(String member_id) {
		try (SqlSession session = MybaticsManager.getInstance().openSession()) {
			session.delete("shm_member.cartDelete", member_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
