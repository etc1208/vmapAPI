package com.buptmap.DAO;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.buptmap.model.Version;

@Component
public class VersionDAO {

	private HibernateTemplate hibernateTemplate;
	
	private JSONArray jsonArray = null;
	private JSONObject jsonObject = null;
	
	public JSONArray getVersionList(String unitId){
		
		List<Object[]> versionList = new ArrayList<Object[]>();
		versionList = this.hibernateTemplate.find("select version_id,unit_id,isAvailable,description,createTime,finishVersionTime from Version where unit_id='"+unitId+"' order by createTime");
		
		try {
			
			jsonArray = new JSONArray();
			
			if(versionList != null && versionList.size() > 0){
				
				Object[] resultObj = null;
				for(int i=0;i<versionList.size();i++){
					
					resultObj = versionList.get(i);
					jsonObject = new JSONObject();
					
					jsonObject.put("version_id", resultObj[0]);
					jsonObject.put("unit_id", resultObj[1]);
					jsonObject.put("isAvailable", resultObj[2]);
					jsonObject.put("description", resultObj[3]);
					jsonObject.put("createTime", resultObj[4].toString());
					jsonObject.put("finishVersionTime", resultObj[5]==null?"":resultObj[5]);
					jsonArray.add(jsonObject);
				}
				
			}else{
				System.out.println("no version information!!");
			}
			return jsonArray;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONArray fuzzyQuery(String spotname, String unitId) throws UnsupportedEncodingException {
		//System.out.println(new String(spotname.getBytes(), "utf-8"));
		spotname = new String(spotname.getBytes("ISO-8859-1"),"utf-8");
		unitId = new String(unitId.getBytes("ISO-8859-1"),"utf-8");
		//System.out.println("mystring是"+mystring);
		List<Object[]> spotList = new ArrayList<Object[]>();
		//System.out.println(spotname);
		spotList = this.hibernateTemplate.find("select show_name,parent_unit_id,parent_name,floor_id,coord_x,coord_y from Spot where (show_name like '%"+spotname+"%' or keyword like '%"+spotname+"%') and (parent_unit_id='"+unitId+"') order by floor_id");
		//System.out.println(URLDecoder.decode(spotname, "GBK"));
		try {
			
			jsonArray = new JSONArray();
			if(spotList != null && spotList.size() > 0){
				
				Object[] resultObj = null;
				for(int i=0;i<spotList.size();i++){
					
					resultObj = spotList.get(i);
					jsonObject = new JSONObject();
					
					jsonObject.put("spotname", resultObj[0]);
					jsonObject.put("unit_id", resultObj[1]);
					jsonObject.put("unit_name", resultObj[2]);
					jsonObject.put("floor_id", resultObj[3]);
					jsonObject.put("x", resultObj[4]);
					jsonObject.put("y", resultObj[5]);
					
					jsonArray.add(jsonObject);
				}
			}else{
				
				System.out.println("no spot information");
			}
			return jsonArray;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateVersionList(String unitId){
		
		String finishTime = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		finishTime = sdf.format(date);
		
		int result = this.hibernateTemplate.bulkUpdate("update Version set isAvailable='0',finishVersionTime='"+finishTime+"' where unit_id='"+unitId+"' and isAvailable='1'");
		return result;
	}
	
	public int publishVersion(String unitId){
		
		Date date = new Date();
		Version version = new Version();

		try {
			version.setUnitVersion("商铺出售图");
			version.setUnit_id(unitId);
			version.setIsAvailable(1);
			version.setDescription("上海新国际博览中心E1馆");
			version.setCreateTime(date);
			version.setLastModifyTime(date);
			this.hibernateTemplate.save(version);
			return 1;
		} catch (Exception e) {
			
			return 0;
		}
		
	}
	
	public JSONArray getLeaseList(){
		
		List<Object[]> leaseList = new ArrayList<Object[]>();
		List<Object[]> username = new ArrayList<Object[]>();
		List<Object[]> unit = new ArrayList<Object[]>();
		leaseList = this.hibernateTemplate.find("select company_id,unit_id,version_id,rent,last_modify_time from Lease where state='1' order by last_modify_time");
		
		try {
			
			jsonArray = new JSONArray();
			
			if(leaseList != null && leaseList.size() > 0){
				
				Object[] resultObj = null;
				for(int i=0;i<leaseList.size();i++){
					
					resultObj = leaseList.get(i);
					jsonObject = new JSONObject();
					username = this.hibernateTemplate.find("select con_per from Company where company_id='"+resultObj[0]+"'");
					unit = this.hibernateTemplate.find("select booth_num from Indoor where unit_id='"+resultObj[1]+"'");
					jsonObject.put("user", username.get(0));
					jsonObject.put("block_num", unit.get(0));
					jsonObject.put("unitId", resultObj[1]);
					jsonObject.put("versionId", resultObj[2]);
					jsonObject.put("rent", resultObj[3].equals("")?"1":resultObj[3]);
					jsonObject.put("date", resultObj[4]);
					
					jsonArray.add(jsonObject);
				}
				
			}else{
				System.out.println("no lease information!!");
			}
			return jsonArray;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
}
