package coffee.pj.main;

import coffee.pj.comm.CafeController;
import coffee.pj.comm.Scsupport;
import coffee.pj.dto.Member_DTO;
import coffee.pj.dto.Menu_DTO;
import coffee.pj.dto.OrderMenu_DTO;

public class CoffeeMain {

	public static void main(String[] args) {
		Scsupport sc = new Scsupport();
		CafeController ccon = new CafeController();
		Member_DTO mbto = new Member_DTO();
		OrderMenu_DTO omd = new OrderMenu_DTO();

		boolean logined = false;
		String joinname = "";
		String joinphone = "";
		int logintype = 0;
		boolean kiosk = true;
		while (kiosk) {
			while (!logined) {
				System.out.println("샘플계정(관리자/0000001, 김자바/12345678)");
				System.out.println("====================");
				System.out.println("=     CAFE GUDI    =");
				System.out.println("====================");
				System.out.println("");
				System.out.println("CAFE : 환영합니다");
				System.out.println("CAFE : GUDI의 멤버이신가요?");
				System.out.println("CAFE : (1:로그인 / 2:회원가입 / 3:게스트로 시작하기)");
				System.out.print("입력 : ");
				int rsp1 = sc.scInt();
				if (rsp1 == 1) {
					System.out.println("==================");
					System.out.println("=      로그인      =");
					System.out.println("==================");
					System.out.println("CAFE : 이름과 핸드폰 번호를 차례로 입력해주세요");
					System.out.print("이름 : ");
					joinname = sc.scString();

					boolean isphonenum = false;
					while (!isphonenum) {
						try {
							System.out.print("핸드폰(숫자만 입력) : 010-");
							joinphone = sc.scString();

							Long.parseLong(joinphone);
							if (joinphone.length() == 8) {
								isphonenum = true;
							} else {
								System.out.println("CAFE : 숫자 8자리만 가능합니다");
							}
						} catch (NumberFormatException e) {
							System.out.println("CAFE : 숫자만 입력해주세요");
						}
					}
					ccon.loginMem(joinname, joinphone);
					if (ccon.loginMem(joinname, joinphone) > 0) {
						logined = true;
						if (ccon.loginMem(joinname, joinphone) == 9) {
							logintype = 9;
						}
					}
				} else if (rsp1 == 2) {
					System.out.println("==================");
					System.out.println("=     회원 가입     =");
					System.out.println("==================");
					System.out.println("CAFE : 이름과 핸드폰 번호를 차례로 입력해주세요");
					System.out.print("이름 : ");
					joinname = sc.scString();
					joinphone = sc.scString();
					boolean isphonenum2 = false;
					while (!isphonenum2) {
						try {
							System.out.print("핸드폰(숫자만 입력) : 010-");
							joinphone = sc.scString();
							Long.parseLong(joinphone);
							if (joinphone.length() == 8) {
								isphonenum2 = true;
							} else {
								System.out.println("CAFE : 숫자 8자리만 가능합니다");
							}
						} catch (NumberFormatException e) {
							System.out.println("CAFE : 숫자만 입력해주세요");
						}
					}
					ccon.getJoinMem(joinname, joinphone);
					if (ccon.getJoinMem(joinname, joinphone) == 0) {
						logined = true;
					}
					break;
				} else {
					System.out.println("CAFE : 게스트로 시작합니다");
					logintype = ccon.loginGuest();
					logined = true;
					break;
				}
			} // 로그인 while문 끝
			while (logined) {
				if (logintype == 9) {
					System.out.println();
					System.out.println();
					System.out.println("SYSTEM : 관리자 모드를 시작합니다");
					System.out.println("==================");
					System.out.println("=     관리 선택     =");
					System.out.println("==================");
					System.out.println("1.제품 관리");
					System.out.println("2.회원 관리");
					System.out.println("3.로그아웃");
					System.out.println("4.프로그램 종료하기");
					System.out.print("입력 : ");
					int rspa1 = 0;
					rspa1 = sc.scInt();

					System.out.println();
					switch (rspa1) {
					case 1:
						while (true) {
							System.out.println("SYSTEM : 제품 관리");
							System.out.println("1.제품 전체보기");
							System.out.println("2.제품 등록하기");
							System.out.println("3.제품 수정하기");
							System.out.println("4.제품 삭제하기");
							System.out.println("5.일일 매출 확인");
							System.out.println("6.이전 메뉴로 돌아가기");
							System.out.print("입력 : ");
							int rrsp1 = sc.scInt();
							switch (rrsp1) {
							case 1:
								System.out.println("==================");
								System.out.println("=     전체 메뉴     =");
								System.out.println("==================");
								ccon.getAllMenu();
								break;
							case 2:
								System.out.println("==================");
								System.out.println("=     메뉴 등록     =");
								System.out.println("==================");

								Menu_DTO m1 = new Menu_DTO();
								System.out.println("SYSTEM : 등록할 제품의 정보를 입력해주세요");
								System.out.println("제품 타입:(1:음료/2:디저트)");
								System.out.print("입력:");
								int rspw1 = sc.scInt();
								m1.setMenu_type(rspw1);

								System.out.println("제품 이름");
								System.out.print("입력:");
								String str4 = sc.scString();
								m1.setMenu_name(str4);

								System.out.println("제품 가격");
								System.out.print("입력:");
								int rspp = sc.scInt();
								m1.setMenu_price(rspp);

								System.out.println("제품 상세");
								System.out.print("입력:");
								String str5 = sc.scString();
								m1.setMenu_detail(str5);

								ccon.setMenu(m1);
								break;
							case 3:
								System.out.println("==================");
								System.out.println("=     메뉴 수정     =");
								System.out.println("==================");
								System.out.println("SYSTEM : 수정할 제품의 제품코드를 입력해주세요");
								System.out.print("입력:");
								int rrrsp1 = sc.scInt();

								System.out.println("==============================");
								System.out.println("제품 타입 수정 :(1:음료/2:디저트)");
								System.out.print("입력:");
								int rrrsp2 = sc.scInt();

								System.out.println("제품 이름 수정");
								System.out.print("입력:");
								String str1 = sc.scString();

								System.out.println("제품 가격 수정");
								System.out.print("입력:");
								int rrrsp3 = sc.scInt();

								System.out.println("제품 상세 수정");
								System.out.print("입력:");
								String str2 = sc.scString();

								ccon.modifyMenu(rrrsp2, str1, rrrsp3, str2, rrrsp1);
								break;
							case 4:
								System.out.println("==================");
								System.out.println("=     제품 삭제     =");
								System.out.println("==================");
								System.out.println("SYSTEM : 삭제할 제품 번호를 입력해주세요");
								System.out.print("입력 : ");
								int rspt = sc.scInt();
								ccon.delMenu(rspt);
								break;
							case 5:
								System.out.println("==================");
								System.out.println("=   일일 매출 조회   =");
								System.out.println("==================");
								System.out.println("SYSTEM : 조회할 날짜를 지정해주세요");
								System.out.println("SYSTEM : 입력 양식(YYYY-MM-DD)");
								System.out.print("입력 : ");
								String darp = sc.scString();
								int sale = ccon.getOneSales(darp);
								System.out.println(darp + " 일매출 : " + sale);
								break;
							case 6:
								break;
							default:
								System.out.println("잘못된 입력입니다.");
								break;
							}
							if (rrsp1 == 6)
								break;
						}
						break;
					case 2:
						while (true) {
							System.out.println("SYSTEM : 회원 관리");
							System.out.println("1.회원 전체보기");
							System.out.println("2.회원 등록하기");
							System.out.println("3.회원 삭제하기");
							System.out.println("4.이전 메뉴로 돌아가기");
							System.out.print("입력 : ");
							int mrsp = sc.scInt();
							switch (mrsp) {
							case 1:
								System.out.println("==================");
								System.out.println("=   회원 전체 조회   =");
								System.out.println("==================");
								ccon.getAllMem();
								break;
							case 2:
								System.out.println("==================");
								System.out.println("=     회원 등록     =");
								System.out.println("==================");
								System.out.println("SYSTEM : 등록할 회원의 타입을 입력해주세요(1:관리자/2:일반회원)");
								System.out.print("입력:");
								int rrrsp1 = sc.scInt();

								System.out.println("등록할 회원 이름");
								System.out.print("입력:");
								String stru = sc.scString();

								System.out.println("등록할 회원 휴대폰 번호");
								System.out.print("입력(8자리 숫자만):010-");
								String stru2 = sc.scString();

								ccon.setMem(rrrsp1, stru, stru2);
								break;
							case 3:
								System.out.println("==================");
								System.out.println("=     회원 삭제     =");
								System.out.println("==================");
								System.out.println("SYSTEM : 삭제할 회원의 이름을 입력해주세요");
								System.out.print("입력:");
								String duser1 = sc.scString();

								System.out.println("삭제할 회원 휴대폰 번호");
								System.out.print("입력(8자리 숫자만):010-");
								String duser2 = sc.scString();

								ccon.delMem(duser1, duser2);
								break;
							case 4:
								break;
							default:
								System.out.println("잘못된 입력입니다.");
								break;
							}
							if (mrsp == 4)
								break;
						}
						break;
					case 3:
						System.out.println("SYSTEM : 로그아웃 하시겠습니까? (1:예 / 2:아니오)");
						System.out.print("입력 : ");
						int rrsp3 = sc.scInt();
						if (rrsp3 == 1) {
							logined = false;
							break;
						} else {
							logined = true;
							break;
						}
					case 4:
						System.out.println("정말로 종료하시겠습니까?(1예/2아니오)");
						System.out.print("입력 : ");
						int overp = sc.scInt();
						if (overp == 1) {
							logined = false;
							kiosk = false;
							return;
						} else {
							break;
						}
					default:
						System.out.println("잘못된 입력입니다.");
						break;
					}
				} else if (logintype != 9) {
					System.out.println();
					System.out.println();
					System.out.println("SYSTEM : 사용자 모드를 시작합니다");
					System.out.println("==================");
					System.out.println("=    CAFE GUDI   =");
					System.out.println("==================");
					System.out.println("1.음료 보기");
					System.out.println("2.디저트 보기");
					System.out.println("3.내 구매내역 보기");
					System.out.println("4.로그아웃");
					System.out.print("입력 : ");
					int umopen = sc.scInt();
					System.out.println();
					switch (umopen) {
					case 1:
						System.out.println("==================");
						System.out.println("=       음 료      =");
						System.out.println("==================");
						System.out.println("CAFE : 제품 번호를 누르면 상세보기(0:뒤로가기)");
						ccon.getUserMenu(1);
						System.out.print("입력 : ");
						int dlod = sc.scInt();
						if (dlod != 0) {
							ccon.getOneMenu(dlod);
							System.out.println("해당 메뉴 구매하기(1:예/2:뒤로가기)");
							System.out.print("입력 : ");
							int buyd = sc.scInt();
							switch (buyd) {
							case 1:
								System.out.println("옵션을 추가하시겠습니까?(1:예/2:아니오)");
								System.out.print("입력 : ");
								int buyd2 = sc.scInt();
								if (buyd2 == 1) {
									ccon.getAllOpt();
									System.out.println("원하시는 옵션 번호를 선택해주세요");
									System.out.print("입력 : ");
									int buyd3 = sc.scInt();
									if (buyd3 > ccon.getAllOpt().size()) {
										System.out.println("초과된 지정 범위입니다");
									} else {
										ccon.setOption(dlod, buyd3);
										ccon.setOrder(dlod, mbto.getMem_cart());
										ccon.setOptoOrder(omd.getOrder_num());

										break;
									}
								}
								break;
							case 2:
								System.out.println("CAFE : 주문 취소");
								break;
							default:
								System.out.println("CAFE : 잘못된 키 조작입니다");
								break;
							}
						} else {
							break;
						}
						System.out.println();
						break;
					case 2:
						System.out.println("==================");
						System.out.println("=      디저트      =");
						System.out.println("==================");
						System.out.println("CAFE : 제품 번호를 누르면 상세보기(0:뒤로가기)");
						ccon.getUserMenu(2);
						System.out.print("입력 : ");
						int dlos = sc.scInt();
						if (dlos != 0) {
							ccon.getOneMenu(dlos);
							System.out.println("해당 메뉴 구매하기(1:예/2:뒤로가기)");
							System.out.print("입력 : ");
							int buys = sc.scInt();
							switch (buys) {
							case 1:
								ccon.setOrder(buys, mbto.getMem_cart());
								break;
							case 2:
								break;
							}
						} else {
							break;
						}
						break;
					case 3:
						if (logintype == 8) {
							System.out.println("CAFE : 게스트 유저는 이전 구매내역을 볼 수 없습니다");
							break;
						} else {
							ccon.getMemCart(joinphone);
							break;
						}
					case 4:
						System.out.println("SYSTEM : 로그아웃 하시겠습니까? (1:예 / 2:아니오)");
						System.out.print("입력 : ");
						int rrsp31 = sc.scInt();
						if (rrsp31 == 1) {
							logined = false;
							break;
						} else {
							logined = true;
							break;
						}
					default:
						System.out.println("잘못된 입력입니다.");
						break;
					}
					break;
				} // 사용자모드 끝
			} // 로그인 while문 끝
		} // 첫화면 while문 끝
	}// 메인메서드 끝
}
// 메인클래스 끝
