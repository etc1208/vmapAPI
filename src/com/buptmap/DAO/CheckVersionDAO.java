package com.buptmap.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*import org.hibernate.SessionFactory;*/
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;


@Component
public class CheckVersionDAO {
    //private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	
	private JSONArray jsonArray = null;
	private JSONObject jsonObject = null;
	
	public JSONArray checkMonth(int month){
		
		//List里面存储查询出来的所有记录，每条记录存在一个Object数组里，list是以012324....作为索引
		List<Object[]> monthInfo = new ArrayList<Object[]>();
		List<Object[]> username = new ArrayList<Object[]>();
		if(month <10){
			monthInfo = this.hibernateTemplate.find("select company_id,unit_id,rent,state from Lease where last_modify_time like'%-0"+month+"-%'");
		}
		else{
			monthInfo = this.hibernateTemplate.find("select company_id,unit_id,rent,state from Lease where last_modify_time like'%-"+month+"-%'");
		}
		
		try {
			
			jsonArray = new JSONArray();
			
			if(monthInfo != null && monthInfo.size() > 0){
				
				Object[] resultObj = null;
				//Object[] nameObj = null;
				for(int i = 0; i < monthInfo.size(); i++){
					
					resultObj = monthInfo.get(i);//取出list里面的每个object数组，即表中每条记录
					jsonObject = new JSONObject();
					
					username = this.hibernateTemplate.find("select con_per from Company where company_id='"+resultObj[0]+"'");
					
					jsonObject.put("company_id", username.get(0));
					jsonObject.put("unit_id", resultObj[1]);
					jsonObject.put("rent", resultObj[2]==null? "1":resultObj[2]);
					jsonObject.put("state", resultObj[3]);
					jsonArray.add(jsonObject);
				}
			}else{
				System.out.println("this month has no information!!");
			}
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
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
	/*
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public HibernateTemplate getHibernateTemplate() {
//		if(hibernateTemplate == null){
//			hibernateTemplate = new HibernateTemplate(sessionFactory);
//		}
		return hibernateTemplate;
	}

}
