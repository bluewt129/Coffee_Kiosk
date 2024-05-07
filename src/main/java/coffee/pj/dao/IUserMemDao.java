package coffee.pj.dao;

public interface IUserMemDao {
	
	/**
	 * (사용자 전용) 회원 가입, 중복 유효성 검사 트랜잭션 실행 getJoinMem
	 * @param mem_name 등록할 회원 이름 (중복가능)
	 * @param mem_phone 등록할 회원 번호 (중복불가)
	 * @return 성공여부 실패0, 성공1
	 */
	public int getJoinMem(String mem_name, String mem_phone);
	
	/**
	 * (관리자, 사용자 전용) 로그인 (이름, 번호 둘다 만족해야 로그인)
	 * @param mem_name 로그인할 회원 이름
	 * @param mem_phone 로그인할 회원 번호
	 * @return 성공여부 실패0, 성공1
	 */
	public int loginMem(String mem_name, String mem_phone);
	
	/**
	 * (사용자 전용) 게스트 로그인. 로그인 없이 주문하는 사용자 전용 (자동 생성, 입력값 없음)
	 * @return 성공여부 실패0, 성공1
	 */
	public int loginGuest();


}
