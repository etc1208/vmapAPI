package com.buptmap.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.buptmap.Service.CheckVersionService;


@Component
@Scope("prototype")
public class CheckVersionAction {
	
	private int month;
	private CheckVersionService checkVersionService;
	private Map<String,Object> resultObj;
	
	public String checkMonth(){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray versionArray = new JSONArray();//json数组，每个数组元素是一个json格式数据包
		try {
			versionArray = this.checkVersionService.checkMonth(month);//对应这个月的所有记录
			map.put("monthInfo", versionArray);
			map.put("total", versionArray.size());
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
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public CheckVersionService getCheckVersionService() {
		return checkVersionService;
	}

	public void setCheckVersionService(CheckVersionService checkVersionService) {
		this.checkVersionService = checkVersionService;
	}

	public Map<String, Object> getResultObj() {
		return resultObj;
	}

	public void setResultObj(Map<String, Object> resultObj) {
		this.resultObj = resultObj;
	}

}
