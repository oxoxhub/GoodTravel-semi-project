package com.travel.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.conn.db.DBConn;
import com.travel.vo.JoinVO;

public class JoinDAO {
	
	private DBConn db;
	
	public JoinDAO() {
		try {
			db = new DBConn(new File(System.getProperty("user.home") + "/oracle_db.conf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JoinVO get(String userid) {
		// USER_ID 조회
		
		String query = "SELECT * FROM travel_join WHERE USER_ID = ?";
		
		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, userid);

			ResultSet rs = db.sendSelectQuery();
			
			if(rs.next()) {
				JoinVO data = new JoinVO();
				data.setUser_id(rs.getString("USER_ID"));
				data.setUser_pw(rs.getString("USER_PW"));
				data.setUser_name(rs.getString("USER_NAME"));
				data.setEmail(rs.getString("EMAIL"));
				return data;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean add(JoinVO data) {
		// USER_ID 추가
		
		String query = "INSERT INTO travel_join VALUES(?,?,?,?)";

		try {
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, data.getUser_id());
			pstat.setString(2, data.getUser_pw());
			pstat.setString(3, data.getUser_name());
			pstat.setString(4, data.getEmail());
			
			int result = db.sendInsertQuery();
			
			if(result == 1) {	
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(JoinVO data) {
		// USER_ID 삭제
		try {
			String query = "DELETE FROM travel_wishlist WHERE USER_ID = ?";
			
			PreparedStatement pstat = db.getPstat(query);
			pstat.setString(1, data.getUser_id());
			
			db.sendDeleteQuery();
			
			query = "DELETE FROM travel_join WHERE USER_ID = ?";
			pstat = db.getPstat(query);
			pstat.setString(1, data.getUser_id());
			
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
