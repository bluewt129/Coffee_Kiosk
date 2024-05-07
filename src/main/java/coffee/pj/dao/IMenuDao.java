package coffee.pj.dao;

import java.util.List;

import coffee.pj.dto.Menu_DTO;

public interface IMenuDao {
	
	/**
	 * (관리자 전용) 모든 메뉴를 조회하는 getAllMenu();
	 * @return 모든 메뉴 조회 java.util.List<Menu_DTO>
	 */
	public List<Menu_DTO> getAllMenu();
	
	
	/**
	 * (사용자 전용) 모든 메뉴를 조회하는 getUserMenu();
	 * @param meu_num 메뉴 번호 int
	 * @return 모든 메뉴 조회 java.util.List<Menu_DTO>
	 */
	public List<Menu_DTO> getUserMenu(int menu_type);
	
	
	/**
	 * 메뉴 번호를 통한 메뉴 상세 정보 조회 getOneMenu
	 * @param meu_num 메뉴 번호 int
	 * @return 메뉴 상세 정보 NAME, MENU_PRICE, MENU_DETAIL 
	 */
	public Menu_DTO getOneMenu(int meu_num);
	
	/**
	 * 메뉴 추가, 재고 등록 (트랜잭션) setMenu 
	 * @param 메뉴 정보 MENU_TYPE, MENU_NAME, MENU_PRICE, MENU_DETAIL
	 * @return 성공여부 실패 0, 성공 1
	 */
	public int setMenu(Menu_DTO dto);
	
	/**
	 * 메뉴 정보 변경
	 * @param menu_type 변경할 메뉴 타입(음료1, 디저트2)
	 * @param menu_name 변경할 메뉴 이름
	 * @param menu_price 변경할 메뉴 가격
	 * @param menu_detail 변경할 메뉴 상세설명
	 * @param menu_num 변경할 메뉴 번호
	 * @return 성공여부 실패 0, 성공 1
	 */
	public int modifyMenu(int menu_type, String menu_name, int menu_price, String menu_detail, int menu_num);
	
	/**
	 * 메뉴 정보 row를 삭제
	 * @param 삭제할 메뉴의 번호
	 * @return 성공 여부 실패 0, 성공 1
	 */
	public int delMenu(int menu_no);

}
