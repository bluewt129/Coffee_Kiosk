package coffee.pj.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import coffee.pj.comm.DataBase;
import coffee.pj.dao.AdminMemDaoImpl;
import coffee.pj.dao.IAdminMemDao;
import coffee.pj.dao.IMenuDao;
import coffee.pj.dao.IOrderMenuDao;
import coffee.pj.dao.IUserMemDao;
import coffee.pj.dao.MenuDaoImpl;
import coffee.pj.dao.OrderMenuDaoImpl;
import coffee.pj.dao.UserMemDaoImpl;
import coffee.pj.dto.MemCart_DTO;
import coffee.pj.dto.Member_DTO;
import coffee.pj.dto.Menu_DTO;

public class Coffee_TestJUnit {

	// @Test //DataBase 공통 모듈 테스트
	public void database_Test() {
		DataBase db = new DataBase();
		Connection conn = null;
		try {
			db.getConnection();
			System.out.println("드라이버 로딩 완료");
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}

		db.closed(null, null, conn);
		assertNull(conn);
	}

	// @Test //모든 메뉴 조회 (관리자모드)
	public void getAllMenu_JUnitTest() {
		IMenuDao dao = new MenuDaoImpl();
		List<Menu_DTO> lists = dao.getAllMenu();
		// lists의 사이즈가 0이라면 아무 데이터가 들어오지 않았다는 뜻
		assertNotEquals(0, lists.size());
	}

	// @Test //메뉴상세보기 (고객)
	public void getOneMenu_JUnitTest() {
		IMenuDao dao = new MenuDaoImpl();
		Menu_DTO dto = dao.getOneMenu(1);
		System.out.println(dto);
		assertNotNull(dto.getMenu_name());
	}

	// @Test //메뉴 신규 등록 테스트 (관리자모드)
	public void setMenu_JUnitTest() {
		IMenuDao dao = new MenuDaoImpl();
		Menu_DTO dto = new Menu_DTO();
		dto.setMenu_type(1);
		dto.setMenu_name("참외스무디");
		dto.setMenu_price(7300);
		dto.setMenu_detail("성주 참외를 갈아 만든 스무디");
		dto.setStock_cnt(50);
		int rowNum = dao.setMenu(dto);
		assertEquals(2, rowNum);
	}

	// @Test //메뉴 수정 테스트 (관리자모드)
	public void modifyMenu_JUnitTest() {
		IMenuDao dao = new MenuDaoImpl();
		int rowNum = dao.modifyMenu(1, "아메리카노", 3200, "풍미 좋은 원두 사용", 1);
		assertEquals(2, rowNum);
	}

	// @Test //메뉴 삭제 테스트 (관리자모드)
	public void delMenu_JUnitTest() {
		IMenuDao dao = new MenuDaoImpl();
		int rowNum = dao.delMenu(17);
		assertEquals(2, rowNum);
	}

	// @Test //모든 회원 조회 (관리자모드)
	public void getAllMem_JUnitTest() {
		IAdminMemDao dao = new AdminMemDaoImpl();
		List<Member_DTO> lists = dao.getAllMem();
		// lists의 사이즈가 0이라면 아무 데이터가 들어오지 않았다는 뜻
		assertNotEquals(0, lists.size());
	}

	// @Test //회원 등록테스트 (관리자모드)
	public void setMem_JUnitTest() {
		IAdminMemDao dao = new AdminMemDaoImpl();

		int rowNum = dao.setMem(2, "박이썬", "10101010");
		assertEquals(1, rowNum);
	}

	// @Test //(관리자, 사용자) 회원 삭제(탈퇴)
	public void delMem_JUmitTest() {
		IAdminMemDao dao = new AdminMemDaoImpl();
		int rowNum = dao.delMem("박이썬", "10101010");
		assertEquals(2, rowNum);
	}

	// @Test //매출 상세 조회 테스트 (관리자모드)
	public void getOneSales_JUnitTest() {
		IAdminMemDao dao = new AdminMemDaoImpl();
		int sum = dao.getOneSales("2024-05-04");
		System.out.println(sum);
	}

	// @Test //회원 가입테스트 (사용자자모드)
	public void getJoinMem_JUnitTest() {
		IUserMemDao dao = new UserMemDaoImpl();
		int rowNum = dao.getJoinMem("박이썬", "12451245");
		assertEquals(1, rowNum);
	}

	// @Test //로그인 (이름, 번호 둘다 만족해야 로그인) (관리자, 사용자 전용)
	public void loginMem_JUnitTest() {
		IUserMemDao dao = new UserMemDaoImpl();
		int rowNum = dao.loginMem("김자바", "12345678");
		assertEquals(1, rowNum);
	}

	// @Test // 로그인 (이름, 번호 둘다 만족해야 로그인) (관리자, 사용자 전용)
	public void loginGuest_JUnitTest() {
		IUserMemDao dao = new UserMemDaoImpl();
		int rowNum = dao.loginGuest();
		assertEquals(1, rowNum);
	}

	// @Test //주문서 등록. 해당 메뉴 재고 자동 차감 트리거. 로그인한 회원은 장바구니 자동업데이트
	public void setOrder_JUnitTest() {
		IOrderMenuDao dao = new OrderMenuDaoImpl();
		int rowNum = dao.setOrder(10, 2);
		assertEquals(1, rowNum);
	}

	// @Test // 회원 휴대폰 번호를 통한 회원 주문 내역 조회
	public void getMemCart_JUnitTest() {
		IOrderMenuDao dao = new OrderMenuDaoImpl();
		MemCart_DTO dto = dao.getMemCart("12345678");
		assertNotNull(dto.getMenu_name());
	}

	// @Test //메뉴 선택 시 옵션 추가
	public void setOption_JUnitTest() {
		IOrderMenuDao dao = new OrderMenuDaoImpl();
		int rowNum = dao.setOption(3, 5);
		assertEquals(1, rowNum);
	}

	//@Test //옵션 등록시 주문서에 추가
	public void setOptoOrder_JUnitTest() {
		IOrderMenuDao dao = new OrderMenuDaoImpl();
		int rowNum = dao.setOptoOrder("240505009");
		assertEquals(1, rowNum);
	}

}
