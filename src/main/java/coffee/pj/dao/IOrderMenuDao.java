package coffee.pj.dao;

import java.util.List;

import coffee.pj.dto.MemCart_DTO;
import coffee.pj.dto.Optional_DTO;

public interface IOrderMenuDao {
	
	/**
	 * 메뉴 번호를 선택해 주문서에 주문을 업데이트하는 메서드 setOrder
	 * 재고 자동 차감 트리거
	 * 로그인한 회원은 장바구니 자동업데이트 트랜잭션
	 * @param menu_num 메뉴 번호
	 * @return 성공여부 실패0, 성공1
	 */
	public int setOrder(int menu_num, int mem_cart);

	/**
	 * 회원 휴대폰 번호를 통해 회원 장바구니 조회하는 메서드 getMemCart
	 * @param mem_phone 회원 휴대폰 번호 (중복없음)
	 * @return 회원 장바구니 상세 정보 ORDER_NUM, MENU_NAME, ORDER_COST, ORDER_DATE
	 */
	public MemCart_DTO getMemCart(String mem_phone);

	/**
	 * 옵션 추가 (제품타입1:음료를 선택했을 때만 사용되는 메서드) setOption
	 * @param opt_num 옵션 번호 
	 * @return 성공여부 실패0, 성공1
	 */
	public int setOption(int menu_num, int opt_num);
	
	/**
	 * 옵션 추가가 존재할 시 주문서에 옵션 추가 업데이트 setOptoOrder
	 * @param order_num 주문 번호
	 * @return 성공여부 실패0, 성공1
	 */
	public int setOptoOrder(String order_num);
	
	/**
	 * 모든 옵션 조회
	 * @return 모든 메뉴 조회 java.util.List<Optional_DTO>
	 */
	public List<Optional_DTO> getAllOpt();
}
