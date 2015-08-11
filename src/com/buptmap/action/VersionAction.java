package com.buptmap.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.buptmap.Service.VersionService;

@Component
@Scope("prototype")
public class VersionAction {

	private String unitId;
	private VersionService versionService;
	private Map<String, Object> resultObj;
	
	private String spotname;
	
	public String getVersionList(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray versionArray = new JSONArray();//json数组，每个数组元素是一个json格式数据包
		try {
			versionArray = this.versionService.getVersionList(unitId);
			map.put("versionList", versionArray);
			map.put("total", versionArray.size());
			map.put("success", true);
			resultObj = JSONObject.fromObject(map);//可直接将map赋给resultObj
			return "success";//SUCCESS;
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.toString());
			resultObj = JSONObject.fromObject(map);
			return "success"; //SUCCESS;
		}
	}
	
	public String updateVersionList(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result;
		try {
			result = this.versionService.updateVersionList(unitId);
			map.put("update", result);
			map.put("success", true);
			resultObj = JSONObject.fromObject(map);//可直接将map赋给resultObj
			return "success";//SUCCESS;
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.toString());
			resultObj = JSONObject.fromObject(map);;
			return "success"; //SUCCESS;
		}
	}
	
	public String publishVersion(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result;
		try {
			result = this.versionService.publishVersion(unitId);
			map.put("publish", result);
			map.put("success", true);
			resultObj = JSONObject.fromObject(map);//可直接将map赋给resultObj
			return "success";//SUCCESS;
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.toString());
			resultObj = JSONObject.fromObject(map);;
			return "success"; //SUCCESS;
		}
	}
	
	public String getLeaseList(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray leaseArray = new JSONArray();//json数组，每个数组元素是一个json格式数据包
		try {
			leaseArray = this.versionService.getLeaseList();
			map.put("leaseList", leaseArray);
			map.put("total", leaseArray.size());
			map.put("success", true);
			resultObj = JSONObject.fromObject(map);//可直接将map赋给resultObj
			return "success";//SUCCESS;
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.toString());
			resultObj = JSONObject.fromObject(map);;
			return "success"; //SUCCESS;
		}
	}
	
	public String fuzzyQuery(){
		
		Map<String , Object> map = new HashMap<String, Object>();
		JSONArray spotArray = new JSONArray();
		try{
			
			spotArray = this.versionService.fuzzyQuery(spotname,unitId);
			map.put("spots", spotArray);
			map.put("total", spotArray.size());
			map.put("success", true);
			resultObj = JSONObject.fromObject(map);
			return "success";
		}catch (Exception e) {
			
			map.put("success", false);
			map.put("message", e.toString());
			resultObj = JSONObject.fromObject(map);
			return "success"; //SUCCESS;
		}
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public VersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public Map<String, Object> getResultObj() {
		return resultObj;
	}

	public void setResultObj(Map<String, Object> resultObj) {
		this.resultObj = resultObj;
	}

	public String getSpotname() {
		return spotname;
	}

	public void setSpotname(String spotname) {
		this.spotname = spotname;
	}
		
		
	
}
