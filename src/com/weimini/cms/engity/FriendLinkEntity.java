package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;

public class FriendLinkEntity implements Serializable {
	
	private static final long serialVersionUID = -2577405476596408800L;

	private int id;
	
	private String name;
	
	private String description;
	
	private String addr;
	
	private String typeIds;
	
	private String imgPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	public String getTypeIds() {
		return typeIds;
	}
	
	public List<Integer> getTypeIdList() {
		List<Integer> typeIdList = new ArrayList<Integer>();
		String strTypeId = this.getTypeIds().replaceAll("\\[", "").replaceAll("\\]", "");
		if(StringUtils.isNotBlank(strTypeId)) {
			String[] strTypeIds = strTypeId.split(",");
			for(String typeId : strTypeIds) {
				typeIdList.add(SystemUtils.strToInt(typeId));
			}
		}
		return typeIdList;
	}

	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgUrl() {
		if(StringUtils.isNotEmpty(this.getImgPath())) {
			return CmsConstant.IMAGE_DOMAIN + this.getImgPath();
		} else {
			return CmsConstant.IMAGE_DOMAIN + "/default.jpg";
		}
	}

}
