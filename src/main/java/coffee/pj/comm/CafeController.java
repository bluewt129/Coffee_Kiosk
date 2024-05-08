package coffee.pj.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import coffee.pj.dao.IAdminMemDao;
import coffee.pj.dao.IMenuDao;
import coffee.pj.dao.IOrderMenuDao;
import coffee.pj.dao.IUserMemDao;
import coffee.pj.dto.MemCart_DTO;
import coffee.pj.dto.Member_DTO;
import coffee.pj.dto.Menu_DTO;
import coffee.pj.dto.Optional_DTO;

public class CafeController extends DataBase implements IOrderMenuDao, IUserMemDao, IMenuDao, IAdminMemDao {
	Scanner sc = new Scanner(System.in);

	// (관리자 전용) 회원 전체 조회
	@Override
	public List<Member_DTO> getAllMem() {
		List<Member_DTO> memList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT MEM_NUM, MEM_TYPE, MEM_NAME, MEM_PHONE, MEM_CART, MEM_DATE " + "FROM MEMBER "
					+" ORDER BY MEM_NUM ASC ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Member_DTO temp = new Member_DTO();
				temp.setMem_num(rs.getInt(1));
				temp.setMem_type(rs.getInt(2));
				temp.setMem_name(rs.getString(3));
				temp.setMem_phone(rs.getString(4));
				temp.setMem_cart(rs.getInt(5));
				temp.setMem_date(rs.getString(6));
				memList.add(temp);
			}
			for (Member_DTO m : memList) {
				System.out.print("1 회원번호:" + m.getMem_num() + "  ");
				System.out.print("2 회원유형:" + m.getMem_type() + "  ");
				System.out.print("3 회원이름:" + m.getMem_name() + "  ");
				System.out.print("4 핸드폰:" + m.getMem_phone() + "  ");
				System.out.print("5 카트번호:" + m.getMem_cart() + "  ");
				System.out.print("6 가입일자:" + m.getMem_date() + "  ");
				System.out.println();
			}
			System.out.println("=================");

		} catch (SQLException e) {
			System.out.println("SYSTEM : 멤버 전체 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return memList;
	}// (관리자 전용) 회원 전체 조회

	// (관리자 전용) 일일 매출 조회
	@Override
	public int getOneSales(String order_date) {

		int sum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT SUM(ORDER_COST) " + "	FROM ORDERMENU "
				+ "	WHERE TO_CHAR(ORDER_DATE, 'YYYY-MM-DD') = ? ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, order_date);
			rs = stmt.executeQuery();

			if (rs.next()) {
				sum = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("매출 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return sum;
	}// (관리자 전용) 일일 매출 조회

	// (관리자 전용) 회원 등록, (DB:mem_cart 트리거 처리)
	@Override
	public int setMem(int mem_type, String mem_name, String mem_phone) {
		int rowNum = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " INSERT INTO MEMBER m (MEM_NUM, MEM_TYPE, " + " MEM_NAME, MEM_PHONE, MEM_CART, MEM_DATE) VALUES "
				+ " ((SELECT MAX(MEM_NUM) FROM MEMBER m )+1, ?, ?, "
				+ " ?, (SELECT MAX(MEM_CART) FROM MEMBER m)+1, SYSDATE) ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_type);
			stmt.setString(2, mem_name);
			stmt.setString(3, mem_phone);

			rowNum = stmt.executeUpdate();
			System.out.println("SYSTEM : 회원 등록 완료");
			System.out.println(">> 트리거 발동 : " + mem_name + "의 개인 장바구니가 자동 생성 되었습니다");
		} catch (SQLException e) {
			System.out.println("SYSTEM : 회원 등록 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// (관리자 전용) 회원 등록 (DB:mem_cart 트리거 처리)

	// (관리자, 사용자) 회원 삭제(탈퇴)
	@Override
	public int delMem(String mem_name, String mem_phone) {
		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " DELETE FROM MEMBER " + " WHERE MEM_NAME = ? " + " AND MEM_PHONE = ? ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mem_name);
			stmt.setString(2, mem_phone);

			System.out.println("SYSTEM : 정말 삭제하시겠습니까? (1:예/2:아니오)");
			System.out.print("입력:");
			try {
				int rsp = sc.nextInt();
				switch (rsp) {
				case 1:
					rowNum = stmt.executeUpdate();
					System.out.println("SYSTEM : 삭제완료");
					break;
				case 2:
					rowNum = 0;
					System.out.println("SYSTEM : 삭제를 취소합니다");
					break;
				default:
					System.out.println("SYSTEM : 잘못된 입력입니다 삭제를 취소합니다");
					rowNum = 0;
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("SYSTEM : 올바르지 않은 입력입니다");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SYSTEM : 회원 삭제 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// (관리자, 사용자) 회원 삭제(탈퇴)
	
	// 주문서 등록. 해당 메뉴 재고 자동 차감 트리거. 로그인한 회원은 장바구니 자동업데이트
	@Override
	public int setOrder(int menu_num, int mem_cart) {
		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " INSERT INTO ORDERMENU o (ORDER_NUM, ORDER_DATE, MENU_NUM, " + "ORDER_COST, ORDER_STATUS) VALUES "
				+ "((SELECT SUBSTR(TO_CHAR(TO_DATE(SYSDATE, 'YYYY-MM-DD'), 'YYYYMMDD'), 3) || LPAD(ROWNUM, 3, '0') "
				+ " FROM DUAL), SYSDATE, ?, (SELECT MENU_PRICE  " + "	FROM MENU  "
				+ "	WHERE MENU_NUM = ?), '결제완료') ";

		String sql2 = " UPDATE MEMCART SET ORDER_NUM = " + " (SELECT ORDER_NUM FROM ORDERMENU ORDER BY ORDER_NUM DESC "
				+ "FETCH FIRST 1 ROW ONLY) WHERE MEM_CART = ?  ";

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			System.out.println("==================");
			System.out.println("=    주문서 전달중   =");
			System.out.println("==================");
			System.out.println("SYSTEM : 트랜잭션 시작");

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menu_num);
			stmt.setInt(2, menu_num);
			rowNum += stmt.executeUpdate();
			stmt.close();

			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, mem_cart);
			rowNum += stmt.executeUpdate();

			conn.commit();
			System.out.println("SYSTEM : 트랜잭션 완료 >> 커밋");
			System.out.println("SYSTEM : 주문서가 등록되었습니다");
			System.out.println(">> 트리거 발동 : 해당 품목의 재고를 1 차감합니다");
		} catch (SQLException e) {
			System.out.println("SYSTEM : 주문 등록 실패");
			try {
				conn.rollback();
				System.out.println("SYSTEM : 트랜잭션 완료 >> 롤백");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// 주문서 등록. 해당 메뉴 재고 자동 차감 트리거. 로그인한 회원은 장바구니 자동업데이트

	// 회원 휴대폰 번호(Unique)를 통한 회원 주문 내역 조회
	@Override
	public MemCart_DTO getMemCart(String mem_phone) {
		MemCart_DTO dto = new MemCart_DTO();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT o.ORDER_NUM, m3.MENU_NAME, o.ORDER_COST, o.ORDER_DATE  "
				+ "	FROM  MEMBER m JOIN MEMCART m2 " + "	ON m.MEM_CART = m2.MEM_CART " + "	JOIN ORDERMENU o "
				+ "	ON m2.ORDER_NUM = o.ORDER_NUM " + "	JOIN MENU m3 " + "	ON o.MENU_NUM = m3.MENU_NUM "
				+ "	WHERE MEM_PHONE = ? ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mem_phone);
			rs = stmt.executeQuery();

			System.out.println("==================");
			System.out.println("=   나의 주문 내역   =");
			System.out.println("==================");

			while (rs.next()) {
				dto.setOrder_num(rs.getString("ORDER_NUM"));
				dto.setMenu_name(rs.getString("MENU_NAME"));
				dto.setOrder_date(rs.getString("ORDER_DATE"));
				dto.setOrder_cost(rs.getInt("ORDER_COST"));
			}

			System.out.print("1 주문번호:" + dto.getOrder_num() + "  ");
			System.out.print("2 메뉴이름:" + dto.getMenu_name() + "  ");
			System.out.print("3 주문일자:" + dto.getOrder_date() + "  ");
			System.out.print("4 주문가격:" + dto.getOrder_cost() + "  ");
			System.out.println();

		} catch (SQLException e) {
			System.out.println("SYSTEM : 회원 주문 내역 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return dto;
	}// 회원 휴대폰 번호를 통한 회원 주문 내역 조회

	// 메뉴 선택 시 옵션 추가 (제품타입1:음료를 선택했을 때만 사용되는 메서드)
	@Override
	public int setOption(int menu_num, int opt_num) {
		int rowNum = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " INSERT INTO ADDOP(ADD_NUM, MENU_NUM, OPT_NUM) " + "VALUES ((SELECT MAX(ADD_NUM)+1 "
				+ "	FROM ADDOP a), ?, ?)  ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menu_num);
			stmt.setInt(2, opt_num);
			rowNum += stmt.executeUpdate();

			System.out.println("SYSTEM : 옵션 추가 완료");
		} catch (SQLException e) {
			System.out.println("SYSTEM : 옵션 추가 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}
		return rowNum;
	}// 메뉴 선택 시 옵션 추가 (제품타입1:음료를 선택했을 때만 사용되는 메서드)

	// 옵션 등록시 주문서에 업데이트
	@Override
	public int setOptoOrder(String order_num) {

		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " UPDATE ORDERMENU SET ADD_NUM = (SELECT MAX(ADD_NUM) " + " FROM ADDOP a) WHERE ORDER_NUM = ? ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, order_num);
			rowNum = stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SYSTEM : 주문서에 옵션 등록 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// 옵션 등록시 주문서에 업데이트

	// (사용자 전용) 회원 가입, 중복 유효성 검사 트랜잭션 실행
	@Override
	public int getJoinMem(String mem_name, String mem_phone) {
		int rowNum = 0;
		int duplphone = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " INSERT INTO MEMBER m (MEM_NUM, MEM_TYPE, MEM_NAME, " + " MEM_PHONE, MEM_CART, MEM_DATE) "
				+ "VALUES ((SELECT MAX(MEM_NUM) FROM MEMBER m )+1, '2', "
				+ " ?, ?, (SELECT MAX(MEM_CART) FROM MEMBER m)+1, SYSDATE) ";

		String sql2 = " SELECT COUNT(*) CNT FROM MEMBER WHERE MEM_PHONE = ? ";

		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			System.out.println("SYSTEM : 트랜잭션 시작");
			System.out.println();

			// 중복 유효성 검사 수행
			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, mem_phone);
			rs = stmt.executeQuery();
			if (rs.next()) {
				duplphone = rs.getInt("CNT");
			}
			stmt.close();

			if (duplphone > 0) { // 중복이 발생한 경우
				System.out.println("SYSTEM : 중복 유효성 검사 완료");
				System.out.println("CAFE : 이미 등록된 휴대폰 번호입니다. 기존 아이디로 로그인 해주세요");
				conn.rollback();
				System.out.println("SYSTEM : 트랜잭션 완료 >> 롤백");
				rowNum = 0;
			} else { // 중복이 발생하지 않은 경우
				// 회원가입 처리
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, mem_name);
				stmt.setString(2, mem_phone);
				rowNum = stmt.executeUpdate();
				System.out.println("SYSTEM : 회원 가입 완료");
				System.out.println("CAFE : " + mem_name + "님! 환영합니다!");
				conn.commit();
				System.out.println("SYSTEM : 트랜잭션 완료 >> 커밋");
			}
		} catch (SQLException e) {
			System.out.println("SYSTEM : 회원 가입 중 오류가 발생했습니다.");
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
					System.out.println("SYSTEM : 트랜잭션 완료 >> 롤백");
				}
			} catch (SQLException ex) {
				System.out.println("SYSTEM : 롤백 중 오류가 발생했습니다.");
				ex.printStackTrace();
			}
		} finally {
			closed(rs, stmt, conn);
		}
		return rowNum;
	}// (사용자 전용) 회원 가입, 중복 유효성 검사 트랜잭션 실행

	// (관리자, 사용자 전용) 로그인 (이름, 번호 둘다 만족해야 로그인)
	@Override
	public int loginMem(String mem_name, String mem_phone) {

		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT COUNT(*) CNT FROM MEMBER " + " WHERE  MEM_NAME = ? " + " AND MEM_PHONE = ? ";

		try {

			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mem_name);
			stmt.setString(2, mem_phone);
			rs = stmt.executeQuery();

			if (rs.next()) {
				rowNum = rs.getInt("CNT");
			}
			if (rowNum > 0) {
				System.out.println("CAFE : 로그인 되었습니다");
				System.out.println("CAFE : " + mem_name + "님 환영합니다!");
				if (mem_name.contains("관리자")) {
					rowNum = 9;
				} else {
					rowNum = 8;
				}
			} else {
				System.out.println("CAFE : 존재하지 않는 회원입니다");
			}
		} catch (SQLException e) {
			System.out.println("CAFE : 로그인 실패. 다시 시도해주세요.");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// (관리자, 사용자 전용) 로그인 (이름, 번호 둘다 만족해야 로그인)

	// (사용자 전용) 게스트 로그인. 로그인 없이 주문하는 사용자 전용 (자동 생성 트리거, 입력값 없음)
	@Override
	public int loginGuest() {
		int rowNum = 0;
		int logintype = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " INSERT INTO GUEST g (ORDER_NUM) VALUES (NULL) ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			int insert = stmt.executeUpdate();
			System.out.println("==================");
			System.out.println("=    게스트 로그인   =");
			System.out.println("==================");
			if (insert > 0) {
				System.out.println(">> 트리거 발동 : GUEST_CODE가 생성되었습니다");
				System.out.println("CAFE : 게스트로 시작합니다");
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					rowNum = rs.getInt(1);
				}
			}
			logintype = 8;
		} catch (SQLException e) {
			System.out.println("SYSTEM : 게스트 로그인 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}
		
		return logintype;
	}// (사용자 전용) 게스트 로그인. 로그인 없이 주문하는 사용자 전용 (자동 생성, 입력값 없음)

	// 타입별 메뉴 조회 (사용자 모드)
	@Override
	public List<Menu_DTO> getUserMenu(int menu_type) {

		List<Menu_DTO> menuList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT MENU_NUM, MENU_NAME, MENU_PRICE, MENU_TYPE " + " FROM MENU m WHERE MENU_TYPE = ? "
				+ " ORDER BY m.MENU_NUM ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menu_type);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Menu_DTO temp = new Menu_DTO();
				temp.setMenu_num(rs.getInt(1));
				temp.setMenu_name(rs.getString(2));
				temp.setMenu_price(rs.getInt(3));
				temp.setMenu_type(rs.getInt(4));
				menuList.add(temp);
			}
			for (Menu_DTO m : menuList) {
				System.out.print("제품번호:" + m.getMenu_num() + "  ");
				System.out.print("제품이름:" + m.getMenu_name() + "  ");
				System.out.print("제품가격:" + m.getMenu_price() + "  ");
				System.out.println();
			}
			System.out.println("=================");
		} catch (SQLException e) {
			System.out.println("전체 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return menuList;
	} // 타입별 메뉴 조회 (사용자 모드)

	// 모든 메뉴 조회 (관리자 모드)
	@Override
	public List<Menu_DTO> getAllMenu() {

		List<Menu_DTO> menuList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT m.MENU_NUM, MENU_TYPE, MENU_NAME, MENU_PRICE, MENU_DETAIL, s.STOCK_CNT "
				+ "	FROM MENU m JOIN STOCK s " + "	ON m.MENU_NUM = s.MENU_NUM " + " ORDER BY m.MENU_NUM ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Menu_DTO temp = new Menu_DTO();
				temp.setMenu_num(rs.getInt(1));
				temp.setMenu_type(rs.getInt(2));
				temp.setMenu_name(rs.getString(3));
				temp.setMenu_price(rs.getInt(4));
				temp.setMenu_detail(rs.getString(5));
				temp.setStock_cnt(rs.getInt(6));
				menuList.add(temp);
			}
			for (Menu_DTO m : menuList) {
				System.out.print("제품번호:" + m.getMenu_num() + "  ");
				System.out.print("제품이름:" + m.getMenu_name() + "  ");
				System.out.print("제품분류:" + m.getMenu_type() + "  ");
				System.out.print("제품가격:" + m.getMenu_price() + "  ");
				System.out.print("제품상세:" + m.getMenu_detail() + "  ");
				System.out.print("제품재고:" + m.getStock_cnt() + "  ");
				System.out.println();
			}
			System.out.println("=================");
		} catch (SQLException e) {
			System.out.println("전체 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return menuList;
	} // 모든 메뉴 조회 (관리자모드)

	// 모든 옵션 조회
	@Override
	public List<Optional_DTO> getAllOpt() {

		List<Optional_DTO> optList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT OPT_NUM, OPT_NAME, OPT_PRICE, OPT_DETAIL  " + "	FROM OPTIONAL o ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Optional_DTO temp = new Optional_DTO();
				temp.setOpt_num(rs.getInt(1)); 
				temp.setOpt_name(rs.getString(2)); 
				temp.setOpt_price(rs.getInt(3)); 
				temp.setOpt_detail(rs.getString(4)); 
				optList.add(temp);
			}
			for (Optional_DTO m : optList) {
				System.out.println("======== 옵션 목록 ========");
				System.out.print("제품번호:" + m.getOpt_num() + "  ");
				System.out.print("제품이름:" + m.getOpt_name() + "  ");
				System.out.print("제품가격:" + m.getOpt_price() + "  ");
				System.out.print("제품상세:" + m.getOpt_detail() + "  ");
				System.out.println();
			}
			System.out.println("=================");
		} catch (SQLException e) {
			System.out.println("전체 조회 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return optList;
	} // 모든 옵션 조회

	// 메뉴 상세보기 (사용자 모드)
	@Override
	public Menu_DTO getOneMenu(int menu_num) {
		Menu_DTO dto = new Menu_DTO();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " SELECT MENU_NAME, MENU_PRICE, MENU_DETAIL " + "	FROM MENU m " + "	WHERE MENU_TYPE = '1' "
				+ "	AND MENU_NUM = ? " + "	ORDER BY MENU_NUM ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menu_num);
			rs = stmt.executeQuery();

			while (rs.next()) {
				dto.setMenu_name(rs.getString("MENU_NAME"));
				dto.setMenu_price(rs.getInt("MENU_PRICE"));
				dto.setMenu_detail(rs.getString("MENU_DETAIL"));
			}
			System.out.print("1 제품이름:" + dto.getMenu_name() + "  ");
			System.out.print("2 제품가격:" + dto.getMenu_price() + "  ");
			System.out.println();
			System.out.println("3 제품상세:" + dto.getMenu_detail() + "  ");
			System.out.println("=================");

		} catch (SQLException e) {
			System.out.println("SYSTEM : 상세보기 실패");
			e.printStackTrace();
		}

		return dto;
	}// 메뉴 상세보기 (사용자 모드)

	// 메뉴 신규 등록, 재고 등록 (트랜잭션)
	@Override
	public int setMenu(Menu_DTO dto) {
		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// 제품등록 sql
		String sql = " INSERT INTO MENU m (MENU_NUM, MENU_TYPE, MENU_NAME, MENU_PRICE, MENU_DETAIL) "
				+ " VALUES (MENU_NUM_SEQ.NEXTVAL, ?, ?, ?, ? ) ";

		String sql2 = " INSERT INTO STOCK s (MENU_NUM, STOCK_CNT)VALUES (MENU_NUM_SEQ.CURRVAL, ? ) ";

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			System.out.println("SYSTEM : 트랜잭션 시작");
			stmt = conn.prepareStatement(sql);

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, dto.getMenu_type());
			stmt.setString(2, dto.getMenu_name());
			stmt.setInt(3, dto.getMenu_price());
			stmt.setString(4, dto.getMenu_detail());

			rowNum += stmt.executeUpdate();
			stmt.close();

			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, 100);

			rowNum += stmt.executeUpdate();

			conn.commit();
			System.out.println("SYSTEM : 트랜잭션 완료 >> 커밋");
			System.out.println("SYSTEM : " + dto.getMenu_name() + "(이/가) 정상적으로 등록되었습니다");
			System.out.println(">> 트리거 발동 : " + dto.getMenu_name() + "의 재고가 자동 보충되었습니다");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}
		return rowNum;
	}// 메뉴 신규 등록, 재고 등록 (트랜잭션)

	// 메뉴 수정
	@Override
	public int modifyMenu(int menu_type, String menu_name, int menu_price, String menu_detail, int menu_num) {
		int rowNum = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " UPDATE MENU SET MENU_TYPE = ?, MENU_NAME = ?, MENU_PRICE = ?, "
				+ " MENU_DETAIL = ? WHERE MENU_NUM  = ? ";

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menu_type);
			stmt.setString(2, menu_name);
			stmt.setInt(3, menu_price);
			stmt.setString(4, menu_detail);
			stmt.setInt(5, menu_num);

			rowNum = stmt.executeUpdate();

			System.out.println("===================");
			System.out.println("SYSTEM : 성공적으로 수정되었습니다");

		} catch (SQLException e) {
			System.out.println("SYSTEM : 메뉴 수정 실패");
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// 메뉴 수정

	// 메뉴 삭제, 재고도 함께 삭제되도록 트랜잭션 처리
	@Override
	public int delMenu(int menu_num) {
		int rowNum = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = " DELETE MENU WHERE MENU_NUM = ? ";
		String sql2 = " DELETE STOCK WHERE MENU_NUM = ? ";

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			System.out.println("SYSTEM : 트랜잭션 시작");

			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, menu_num);

			System.out.println("SYSTEM : 정말 삭제하시겠습니까? (1:예/2:아니오)");
			try {
				System.out.print("입력 : ");
				int rsp = sc.nextInt();
				switch (rsp) {
				case 1:
					rowNum += stmt.executeUpdate();
					stmt.close();
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, menu_num);
					rowNum += stmt.executeUpdate();
					conn.commit();
					System.out.println("SYSTEM : 삭제 완료");
					System.out.println("SYSTEM : 트랜잭션 완료 >> 커밋");
					break;
				case 2:
					rowNum = 0;
					System.out.println("SYSTEM : 삭제를 취소합니다");
					conn.rollback();
					System.out.println("SYSTEM : 트랜잭션 완료 >> 롤백");
					break;
				default:
					System.out.println("SYSTEM : 올바르지 않은 입력입니다");
					System.out.println("SYSTEM : 삭제를 취소합니다");
					rowNum = 0;
					conn.rollback();
					System.out.println("SYSTEM : 트랜잭션 완료 >> 롤백");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("SYSTEM : 올바르지 않은 입력입니다");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SYSTEM : 삭제 실패");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closed(rs, stmt, conn);
		}

		return rowNum;
	}// 메뉴 삭제, 재고도 함께 삭제되도록 트랜잭션 처리

}
