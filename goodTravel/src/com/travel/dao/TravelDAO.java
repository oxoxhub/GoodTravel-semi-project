package com.travel.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.conn.db.DBConn;
import com.travel.vo.JoinVO;
import com.travel.vo.TravelVO;

public class TravelDAO {
	
	private DBConn db;
	
	public TravelDAO() {
		try {
			db = new DBConn(new File(System.getProperty("user.home")+ "/oracle_db.conf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TravelVO> get(String query, String input) {
		// 특정 조건 데이터 조회
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, input);
			
			ResultSet rs = db.sendSelectQuery();
			
			List<TravelVO> vList = new ArrayList<TravelVO>();
			
			while(rs.next()) {
				TravelVO data = new TravelVO();
				data.setLocalName(rs.getString("지역명"));
				data.setKeyword(rs.getString("키워드"));
				data.setBrandName(rs.getString("상호명"));
				data.setAddress(rs.getString("주소"));
				data.setPhoneNumber(rs.getString("연락처"));
				data.setNote(rs.getString("비고"));
				vList.add(data);
			}
			
			if(vList.isEmpty() != true) {
				return vList;
			} 
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<TravelVO> get(String query) {
		//단일 데이터 조회
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			ResultSet rs = db.sendSelectQuery();
			
			List<TravelVO> vList = new ArrayList<TravelVO>();
			
			while(rs.next()) {
				TravelVO data = new TravelVO();
				data.setLocalName(rs.getString("지역명"));
				data.setKeyword(rs.getString("키워드"));
				data.setBrandName(rs.getString("상호명"));
				data.setAddress(rs.getString("주소"));
				data.setPhoneNumber(rs.getString("연락처"));
				data.setNote(rs.getString("비고"));
				vList.add(data);
			}
			return vList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean add(TravelVO data) {
		// 여행지 데이터 추가하기
		
		String query = "INSERT INTO travel_recommend VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, data.getLocalName());
			pstat.setString(2, data.getKeyword());
			pstat.setString(3, data.getBrandName());
			pstat.setString(4, data.getAddress());
			pstat.setString(5, data.getPhoneNumber());
			pstat.setString(6, data.getNote());
			
			int result = db.sendInsertQuery();
			
			if(result == 1) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean modify(TravelVO data) {
		// 여행지 데이터 수정하기
		
		String query = ""
				+ "UPDATE travel_recommend" 
				+ "   SET 지역명 = ?"
				+ "     , 키워드 = ?"
				+ "     , 주소 = ?"
				+ "     , 연락처 = ?"
				+ "     , 비고 = ?"
				+ " WHERE 상호명 = ?";
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, data.getLocalName());
			pstat.setString(2, data.getKeyword());
			pstat.setString(3, data.getAddress());
			pstat.setString(4, data.getPhoneNumber());
			pstat.setString(5, data.getNote());
			pstat.setString(6, data.getBrandName());
			
			int result = db.sendUpdateQuery();
			
			if(result == 1) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(String cName, String input) {
		// 여행지 데이터 삭제하기
		String query = "DELETE FROM travel_recommend WHERE " + cName + " = ?";
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, input);
			
			int result = db.sendDeleteQuery();
			
			if(result == 1) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean wishAdd(JoinVO data, String input) {
		// 위시리스트 데이터 추가
		String query = "INSERT INTO travel_wishlist (user_id, 지역명, 키워드, 상호명, 주소, 연락처, 비고)"
		+ " SELECT (SELECT user_id FROM travel_join WHERE user_id = ?) AS user_id"
		+ " , 지역명"
		+ " , 키워드"
		+ " , 상호명"
		+ " , 주소"
		+ " , 연락처"
		+ " , 비고"
		+ " FROM travel_recommend"
		+ " WHERE 상호명 = ?";
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, data.getUser_id());
			pstat.setString(2, input);
			
			int result = db.sendInsertQuery();
			
			if(result == 1) {		
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	

	public TravelVO getwish(JoinVO account, String input) {
		// 위시리스트 조회
		
		String query = "SELECT * FROM travel_wishlist WHERE user_id = ? AND 상호명 = ?";
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, account.getUser_id());
			pstat.setString(2, input);
			
			ResultSet rs = db.sendSelectQuery();
			
			
			while(rs.next()) {
				TravelVO data = new TravelVO();
				data.setLocalName(rs.getString("지역명"));
				data.setKeyword(rs.getString("키워드"));
				data.setBrandName(rs.getString("상호명"));
				data.setAddress(rs.getString("주소"));
				data.setPhoneNumber(rs.getString("연락처"));
				data.setNote(rs.getString("비고"));
				return data;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean wishDelete(JoinVO account, String input) {
		// 위시리스트 삭제
		String query = "DELETE FROM travel_wishlist WHERE user_id = ? AND 상호명 = ?";
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, account.getUser_id());
			pstat.setString(2, input);
			
			int result = db.sendDeleteQuery();
			
			if(result == 1) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
}
