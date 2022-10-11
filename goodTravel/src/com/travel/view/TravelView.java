package com.travel.view;

import java.util.List;
import java.util.Scanner;

import com.travel.comtroller.JoinController;
import com.travel.comtroller.TravelController;
import com.travel.menu.TravelMenu;
import com.travel.vo.JoinVO;
import com.travel.vo.TravelVO;

public class TravelView {
	
	private TravelMenu menu = new TravelMenu();
	private TravelController tc = new TravelController();
	private Scanner sc = new Scanner(System.in);
	
	private JoinController jc = new JoinController();
	
	public void mainMenu() {
		//메인 메뉴
		while(true) {
			System.out.print(menu.getWelcome());
			System.out.print(menu.getManual());
			System.out.print(">>>> ");
			String input = sc.nextLine();
			System.out.println();
			
			switch(input) {
				case "1" :
					//여행지 검색
					this.searchMenu();
					break;
				case "2" :
					//로그인
					this.loginMenu();
					break;
				case "3" :
					//회원가입
					this.join();
					break;
				case "4" :
					//프로그램 종료
					System.out.println("《《 프로그램이 종료되었습니다. 》 》");
					System.exit(0);
				case "5" :	
					//관리자 메뉴
					this.ManagerMenu();
					break;
				default:
					System.out.println("\n" + "【  잘못된 메뉴 번호 입니다. 다시 입력하세요. 】" + "\n");
			}
		}
	}
	
	
	private void joinDelete(JoinVO account) {
		// 회원탈퇴

		System.out.println("\n" + "<<<<<<<<     회원탈퇴    >>>>>>>>");
		System.out.print("▶ password 를 입력해주세요 : ");
		String user_pw = sc.nextLine();
		
		if(jc.delete(account, user_pw)) {
			System.out.println("\n" + "【  계정이 삭제되었습니다 】" + "\n");
		} else {
			System.out.println("\n" + "【  계정 삭제를 실패했습니다. 】" + "\n");
			return;
		}
	}


	private void join() {
		// 회원가입
		
		JoinVO account = new JoinVO();
		
		System.out.println("<<<<<<<<     회원가입    >>>>>>>>");
		System.out.print("▶ id 를 입력하세요 : ");
		String user_id = sc.nextLine();
		account.setUser_id(user_id);
		
		System.out.print("▶ password 를 입력하세요 : ");
		String user_pw = sc.nextLine();
		account.setUser_pw(user_pw);
		
		System.out.print("▶ 이름을 입력하세요 : ");
		String user_name = sc.nextLine();
		account.setUser_name(user_name);
		
		System.out.print("▶ 이메일을 입력하세요 : ");
		String email = sc.nextLine();
		account.setEmail(email);
		
		if(jc.join(account)) {
			System.out.println("\n" + "【  회원가입에 성공했습니다. 】" + "\n");
		} else {
			System.out.println("\n" + "【  회원가입에 실패했습니다.(아이디 중복) 】" + "\n");
		}
	}


	public void loginMenu() {
		// 로그인 메뉴
		System.out.println("<<<<     로그인 하기    >>>>");
		System.out.print("▶ id 를 입력하세요 : ");
		String userid = sc.nextLine();
		
		System.out.print("▶ password 를 입력하세요 : ");
		String userpw = sc.nextLine();
		
		JoinVO account = jc.login(userid, userpw);
		
		if(account != null) {
			System.out.println("\n" + "▶ " + account.getUser_id() + "님이 로그인 하였습니다."+ "◀ \n");
			this.afterLoginMenu(account);
		} else {
			System.out.println("\n" + "【  잘못된 아이디 또는 패스워드 입니다. 】" + "\n");
			return;
		}
		
	}
	
	public void afterLoginMenu(JoinVO account) {
		//로그인 성공시 메뉴
		while(true) {
			System.out.println(menu.getAfterLoginManual(account.getUser_id()));
			System.out.print(">>>> ");
			String input = sc.nextLine();
			
			switch(input) {
			case "1" : 
				//위시리스트 조회
				List<TravelVO> voList = tc.wishList(account.getUser_id());
				System.out.println("\n"+ "<<<<<<<<<<      나의 위시리스트      >>>>>>>>>>");
				if(voList != null) {
					this.printList(voList);
				} else {
					System.out.println("\n" + "【  위시 리스트가 비어있습니다. 추가해주세요 】" + "\n");
				}
				break;
			case "2" :
				//위시리스트 추가
				System.out.println("\n" + "추가할 여행지의 상호명을 입력하세요.");
				System.out.print(">>>> ");
				input = sc.nextLine();
				if(tc.wishAdd(account, input)) {
					System.out.println("\n" + "【  위시 리스트에 추가 되었습니다. 】" + "\n");
				} else {
					System.out.println("\n" + "【  위시 리스트 추가에 실패 했습니다. 】" + "\n");
				}
				break;
			case "3" :
				//위시리스트 삭제
				System.out.println("\n" + "삭제할 여행지의 상호명을 입력하세요.");
				System.out.print(">>>> ");
				input = sc.nextLine();
				if(tc.wishDelete(account,input)) {
					System.out.println("\n" + "【  " + input + " 위시리스트가 삭제되었습니다.  】" + "\n");
				} else {
					System.out.println("\n" + "【  위시 리스트 삭제에 실패했습니다. 】" + "\n");
				}
				break;
			case "4" :
				//여행지 검색
				this.searchMenu();
				break;
			case "5" :
				//로그아웃
				account = null;
				return;
			case "6" :
				//회원탈퇴
				this.joinDelete(account);
				return;
			}
		}
	}

	public void ManagerMenu() {
		// 여행지 관리자 메뉴
		
		while(true) {
			System.out.print(menu.getManagerManual());
			System.out.print(">>>> ");
			String input = sc.nextLine();
			System.out.println();
			
			switch(input) {
				case "1" :
					//여행지 추가
					this.addTravelMenu();
					break;
				case "2" :
					//여행지 수정
					this.updateTravel();
					break;
				case "3" :	
					//여행지 삭제
					this.deleteTravel();
					break;
				case "4" :
					//메인 메뉴 돌아가기
					return;
				default:
					System.out.println("\n" + "【  잘못된 메뉴 번호 입니다. 다시 입력하세요. 】" + "\n");
			}
		}
		
	}

	private void deleteTravel() {
		// 여행지 삭제
		
		System.out.print("▶ 삭제할 상호명을 입력하세요 : ");
		String input = sc.nextLine();
		if(tc.delete("상호명", input)) {
			System.out.println("\n" + "【  " + input + " 이(가) 삭제되었습니다. 】" + "\n");
		} else {
			System.out.println("\n" + "【 위시 리스트 삭제에 실패했습니다.】" + "\n");
		}
		
	}

	private void updateTravel() {	
		// 여행지 수정 메뉴
		
		System.out.print("▶ 수정할 상호명을 입력하세요(필수) : ");
		String input = sc.nextLine();
		System.out.println();
		List<TravelVO> vList = tc.bnSearch(input);
		
		if(vList != null) {
			this.printList(vList);
			
			System.out.println("<<<  엔터시 기존 정보 유지  >>>");
			
			System.out.print("▶ 수정할 지역명을 입력하세요(선택) : ");
			input = sc.nextLine();
			input = input.isEmpty() ? vList.get(0).getLocalName() : input;
			vList.get(0).setLocalName(input);
			
			System.out.print("▶ 수정할 키워드를 입력하세요(선택) : ");
			input = sc.nextLine();
			input = input.isEmpty() ? vList.get(0).getKeyword() : input;
			vList.get(0).setKeyword(input);
			
			System.out.print("▶ 수정할 주소를 입력하세요(선택) : ");
			input = sc.nextLine();
			input = input.isEmpty() ? vList.get(0).getAddress() : input;
			vList.get(0).setAddress(input);
			
			System.out.print("▶ 수정할 연락처를 입력하세요(선택) : ");
			input = sc.nextLine();
			input = input.isEmpty() ? vList.get(0).getPhoneNumber() : input;
			vList.get(0).setPhoneNumber(input);
			
			System.out.print("▶ 수정할 비고를 입력하세요(선택) : ");
			input = sc.nextLine();
			input = input.isEmpty() ? vList.get(0).getNote() : input;
			vList.get(0).setNote(input);
			
			boolean result = tc.modify(vList.get(0));
			
			if(result) {
				System.out.println("\n" + "【  정보 수정에 성공했습니다. 】" + "\n");
			} else {
				System.out.println("\n" + "【  정보 수정에 실패했습니다. 】" + "\n");
			}
			
		} else {
			System.out.println("\n" + "【  상호명이 존재하지 않습니다. 】" + "\n");
		}
		
	}

	private void addTravelMenu() {
		// 여행지 추가 메뉴
		
		TravelVO data = new TravelVO();
		
		System.out.print("▶ 지역명을 입력하세요(필수) (2자) : ");
		data.setLocalName(sc.nextLine());
		
		System.out.print("▶ 키워드를 입력하세요(필수) (힐링/미식/축제/쇼핑) : ");
		data.setKeyword(sc.nextLine());
		
		System.out.print("▶ 상호명을 입력하세요(필수) : ");
		data.setBrandName(sc.nextLine());
		
		System.out.print("▶ 주소를 입력하세요(필수) : ");
		data.setAddress(sc.nextLine());
		
		System.out.print("▶ 연락처를 입력하세요(생략시 엔터) : ");
		data.setPhoneNumber(sc.nextLine());
		
		System.out.print("▶ 비고를 입력하세요(생략시 엔터) : ");
		data.setNote(sc.nextLine());
		
		boolean result = tc.addMenu(data);
		
		if(result) {
			System.out.println("\n" + "【  여행지 추가가 완료되었습니다. 】" + "\n");
		} else {
			System.out.println("\n" + "【  여행지 추가에 실패하였습니다. 】" + "\n");
		}
		
	}

	private void searchMenu() {
		// 여행지 검색 메뉴
		
		while(true) {
			System.out.print(menu.getSearchManual());
			System.out.print(">>>> ");
			String input = sc.nextLine();
			System.out.println();
			
			switch(input) {
			case "1" :
				//지역명 검색
				
				System.out.println("▶ 검색할 지역명 (서울/부산/제주 등의 두 글자 형식으로 입력) : ");
				System.out.print(">>>> ");
				String localname = sc.nextLine();
				System.out.println();
				
				List<TravelVO> localList = tc.strSearch(localname);
				
				if(localList != null) {
					System.out.println("<<<<<<<<<<      지역명 검색 결과     >>>>>>>>>>");
					this.printList(localList);
				} 
				break;
				
			case "2" :
				//키워드 검색
				
				System.out.println("▶ 검색할 키워드 (힐링/미식/축제/쇼핑) : ");
				System.out.print(">>>> ");
				String keyword = sc.nextLine();
				System.out.println();
				
				List<TravelVO> keywordList = tc.keySearch(keyword);
				
				if(keywordList != null) {
					System.out.println("<<<<<<<<<<      키워드 검색 결과     >>>>>>>>>>");
					this.printList(keywordList);
				}
				break;
				
			case "3" : 
				//랜덤 추천
				List<TravelVO> data = tc.randomRcomd();
				
				if(data != null) {
					System.out.println();
					System.out.println("<<<<<<<<<<     랜덤 추천 결과    >>>>>>>>>>");
					this.printList(data);
				}
				break;
				
			case "4" :
				//초기 화면으로 돌아가기
				return;
			default:
				System.out.println("\n" + "【  잘못된 메뉴 번호 입니다. 다시 입력하세요. 】" + "\n");
			}
		}
	}

	public void printList(List<TravelVO> list) {
		// 여행지 조회 출력문
		for(TravelVO i: list) {
			System.out.println("───────────────────────────────────────────────");
			System.out.println("지역명 : " + i.getLocalName());
			System.out.println("키워드 : " + i.getKeyword());
			System.out.println("상호명 : " + i.getBrandName());
			System.out.println("주소 : " + i.getAddress());
			System.out.println("연락처 : " + i.getPhoneNumber());
			System.out.println("비고 : " + i.getNote());
			System.out.println("───────────────────────────────────────────────\n");
		}
	}


}
