package com.travel.comtroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.travel.dao.TravelDAO;
import com.travel.vo.JoinVO;
import com.travel.vo.TravelVO;

public class TravelController {
	
	private TravelDAO dao = new TravelDAO();
	
	public List<TravelVO> keySearch(String keyword) {
		// 키워드로 검색
		List<String> sList = new ArrayList<String>();
		sList.add("힐링"); sList.add("미식"); sList.add("축제"); sList.add("쇼핑");
		
		for(int i = 0; i < sList.size(); i++) {
			if(keyword.equals(sList.get(i))) {
				String query = "SELECT * FROM travel_recommend WHERE 키워드 = ?";
				List<TravelVO> voList = dao.get(query, keyword);
				
				if(voList != null) {
					return voList;
				} else {
					System.out.println("검색된 결과가 없습니다.");
					return null;
				}
			} 
		}
		System.out.println("잘못된 키워드 입니다.");		
		return null;
	}

	public List<TravelVO> strSearch(String localname) {
		// 지역명으로 검색
		 List<String> sList = new ArrayList<String>();
		 sList.add("서울"); sList.add("부산"); sList.add("대구"); sList.add("인천"); sList.add("광주");
		 sList.add("대전"); sList.add("울산"); sList.add("세종"); sList.add("경기"); sList.add("강원");
		 sList.add("충북"); sList.add("충남"); sList.add("전북"); sList.add("전남"); sList.add("경북");
		 sList.add("경남"); sList.add("제주");
		
		for(int i = 0; i < sList.size(); i++) {
			if(localname.equals(sList.get(i))) {
				String query = "SELECT * FROM travel_recommend WHERE 지역명 = ?";
				List<TravelVO> voList = dao.get(query, localname);

				if(voList != null) {
					return voList;
				} else {
					System.out.println("검색된 결과가 없습니다. 추가해주세요");
					return null;
				}
			} 
		}
		
		System.out.println("존재하지 않는 지역입니다.");		
		return null;
	}
	
	
	public List<TravelVO> bnSearch(String input) {
		//상호명 중복 검색
		String query = "SELECT * FROM travel_recommend WHERE 상호명 = ?";
		List<TravelVO> voList = dao.get(query, input);
		
		if(voList != null) {
			return voList;
		}
		return null;
	}
	
	
	public List<TravelVO> randomRcomd() {
		// 여행지 랜덤으로 추천
		String query = "SELECT * FROM travel_recommend";
		List<TravelVO> voList = dao.get(query);
		
		Random random = new Random();
		int rand = random.nextInt(voList.size());

		List<TravelVO> data = new ArrayList<TravelVO>();
		data.add(voList.get(rand));
		return data;
	}
	

	public boolean addMenu(TravelVO data) {
		// 여행지 추가
		String query = "SELECT * FROM travel_recommend WHERE 상호명 = ?";
		List<TravelVO> voList = dao.get(query, data.getBrandName());
		
		if(voList == null) {
			boolean result = dao.add(data);
			if(result) {
				return true;
			}
		}
		return false;
	}

	public boolean modify(TravelVO data) {
		// 여행지 수정
		return dao.modify(data);
	}

	public boolean delete(String cName, String input) {
		// 여행지 삭제
		if(dao.delete(cName, input)) {
			return true;
		}
		return false;
	}

	public List<TravelVO> wishList(String userid) {
		//위시리스트 검색
		String query = "SELECT * FROM travel_wishlist WHERE user_id = ?";
		List<TravelVO> voList = dao.get(query, userid);
		
		if(voList != null) {
			return voList;
		} 
		return null;
	}

	public boolean wishAdd(JoinVO account, String input) {
		// 위시리스트 추가
		TravelVO jv = dao.getwish(account, input);
		
		if(jv == null) {
			if(dao.wishAdd(account, input)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean wishDelete(JoinVO account, String input) {
		//위시리스트 삭제
		TravelVO jv = dao.getwish(account, input);
		
		if(jv != null) {
			if(dao.wishDelete(account, input)) {
				return true;
			}
		}
		return false;
	}
	
}
