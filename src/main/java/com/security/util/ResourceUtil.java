package com.security.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.security.entity.ResourceEntity;

public class ResourceUtil {

	public static void main(String[] args) {
		List<ResourceEntity> list = new ArrayList<ResourceEntity>();
		ResourceEntity m1 = new ResourceEntity();
		ResourceEntity m2 = new ResourceEntity();
		ResourceEntity m3 = new ResourceEntity();
		m1.setId((long) 1);
		m1.setpId((long)0);
		m2.setId((long)2);
		m2.setpId((long)1);
		m3.setId((long)3);
		m3.setpId((long)2);
		list.add(m1);
		list.add(m2);
		list.add(m3);
		System.out.println(JSONArray.toJSON(format(list)));
	}
	
	public static List<ResourceEntity> format(List<ResourceEntity> menus){
		if(menus == null)
			return null;
		
		List<ResourceEntity> result = new ArrayList<ResourceEntity>();
		List<ResourceEntity> list = new ArrayList<ResourceEntity>();
		for(ResourceEntity m :menus){
			m.setChildren(getChildren(menus,m));
			list.add(m);
		}
		for(ResourceEntity m :list){
			if(m.getpId() == 0){
				result.add(m);
			}
		}
		return result;
	}
	
	public static List<ResourceEntity> getChildren(List<ResourceEntity> menus,ResourceEntity menu){
		List<ResourceEntity> childrens = new ArrayList<ResourceEntity>();
		for(ResourceEntity m :menus){
			if(menu.getId() == m.getpId()){
				childrens.add(m);
			}
		}
		return childrens;
	}
}
