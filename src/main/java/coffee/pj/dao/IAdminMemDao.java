package coffee.pj.dao;

import java.util.List;

import coffee.pj.dto.Member_DTO;

public interface IAdminMemDao {
	
	/**
	 * (관리자 전용)사용자 전체 조회 getAllMem();
	 * @return 사용자 전체 조회 java.util.List<Member_DTO>
	 */
	public List<Member_DTO> getAllMem();
	
	/**
	 * (관리자 전용) 주문 일자를 통해 하루 매출 정보 조회 getOneSales
	 * @param order_date 주문일자 String
	 * @return 해당 주문 일자에 결제된 모든 제품 판매금액 합산
	 */
	public int getOneSales(String order_date);
	
	/**
	 * (관리자 전용) 멤버 추가 setMem (관리자, 손님)
	 * (DB:mem_cart 트리거 처리)
	 * @param mem_type 등록할 멤버 타입 (관리자1, 손님2)
	 * @param mem_name 등록할 멤버 이름 (중복가능)
	 * @param mem_phone 등록할 휴대폰 번호 (중복불가)
	 * @return 성공여부 실패0, 성공1
	 */
	public int setMem(int mem_type, String mem_name, String mem_phone);
	
	/**
	 * (관리자 전용) 멤버 삭제 delAdMem
	 * @param mem_name 삭제할 멤버 이름
	 * @param mem_phone 삭제할 휴대폰 번호
	 * @return 성공여부 실패0, 성공1
	 */
	public int delMem(String mem_name, String mem_phone);

}
