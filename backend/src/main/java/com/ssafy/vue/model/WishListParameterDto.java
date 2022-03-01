package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WishListParameterDto : 아파트 찜을 위해 필요한 Dto", description = "아파트 찜을 위해 필요한 Dto")
public class WishListParameterDto {

	@ApiModelProperty(value = "유저 아이디")
	private String userid;
	@ApiModelProperty(value = "아파트일련번호")
	private int aptCode;
	@ApiModelProperty(value = "동 코드")
	private int dongCode;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	
	public int getDongCode() {
		return dongCode;
	}
	public void setDongCode(int dongCode) {
		this.dongCode = dongCode;
	}
	@Override
	public String toString() {
		return "WishListParameterDto userid=" + userid + ", aptCode=" + aptCode + "]";
	}
	
	

	
}
