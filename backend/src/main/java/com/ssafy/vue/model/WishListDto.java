package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WishListDto : 찜한 아파트 정보", description = "찜한 아파트의 상세 정보를 나타낸다.")
public class WishListDto {

	@ApiModelProperty(value = "찜한 목록 번호(기본키)")
	private int wishlist_no;
	@ApiModelProperty(value = "유저 아이디")
	private String userid;
	@ApiModelProperty(value = "아파트일련번호")
	private int aptCode;
	@ApiModelProperty(value = "동코드")
	private String dongCode;
	@ApiModelProperty(value = "아파트이름")
	private String aptName;
	@ApiModelProperty(value = "아파트이미지")
	private String img;
	@ApiModelProperty(value = "매매가격")
	private String recentPrice;
	
	public int getWishlist_no() {
		return wishlist_no;
	}
	public void setWishlist_no(int wishlist_no) {
		this.wishlist_no = wishlist_no;
	}
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
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRecentPrice() {
		return recentPrice;
	}
	public void setRecentPrice(String recentPrice) {
		this.recentPrice = recentPrice;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	@Override
	public String toString() {
		return "WishListDto [wishlist_no=" + wishlist_no + ", userid=" + userid + ", aptCode=" + aptCode + ", aptName="
				+ aptName + ", img=" + img + ", recentPrice=" + recentPrice + "]";
	}

	
}
