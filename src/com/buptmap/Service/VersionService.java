package com.buptmap.Service;



import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.buptmap.DAO.VersionDAO;

@Component
public class VersionService {

	private VersionDAO versionDAO;
	
	public JSONArray getVersionList(String unitId){
		
		return this.versionDAO.getVersionList(unitId);
	}
	
	public int updateVersionList(String unitId){
		return this.versionDAO.updateVersionList(unitId);
	}
	
	public int publishVersion(String unitId){
		return this.versionDAO.publishVersion(unitId);
	}
	public JSONArray getLeaseList(){
		
		return this.versionDAO.getLeaseList();
	}
	
	public JSONArray fuzzyQuery(String spotname,String unitId) throws DataAccessException, UnsupportedEncodingException{
		
		return this.versionDAO.fuzzyQuery(spotname,unitId);
	}

	public VersionDAO getVersionDAO() {
		return versionDAO;
	}
	@Resource(name="versionDAO")
	public void setVersionDAO(VersionDAO versionDAO) {
		this.versionDAO = versionDAO;
	}
}
