package com.buptmap.Service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.buptmap.DAO.CheckVersionDAO;
import net.sf.json.JSONArray;

@Component
public class CheckVersionService {
	
	private CheckVersionDAO checkVersionDAO;
	public JSONArray checkMonth(int month){
		
		return this.checkVersionDAO.checkMonth(month);
	}
	
	
	public CheckVersionDAO getCheckVersionDAO() {
		return checkVersionDAO;
	}
	@Resource(name="checkVersionDAO")
	public void setCheckVersionDAO(CheckVersionDAO checkVersionDAO) {
		this.checkVersionDAO = checkVersionDAO;
	}
}
