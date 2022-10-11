package com.travel.comtroller;

import com.travel.dao.JoinDAO;
import com.travel.vo.JoinVO;

public class JoinController {
	
	private JoinDAO jdao = new JoinDAO();
	
	public JoinVO login(String user_id, String user_pw) {
		// 로그인시 travel_join 에 해당하는 계정이 있는지 확인
		JoinVO account = jdao.get(user_id);

		if(account != null) {
			if(account.getUser_pw().equals(user_pw)) {	
				return account;		
			}
		} 
		return null;
		
	}
	
	public boolean join(JoinVO data) {
		//가입을 진행하기 전에 아이디 중복 확인 후 추가
		JoinVO account = jdao.get(data.getUser_id());
		
		if(account == null) {
			boolean result = jdao.add(data);
			if(result) {
				return true;
			}
		}
		return false;
	}
	
	public boolean delete(JoinVO data, String password) {
		//계정 삭제
		if(data.getUser_pw().equals(password)) {
			return jdao.delete(data);
		} else {
			return false;
		}
	}

}
