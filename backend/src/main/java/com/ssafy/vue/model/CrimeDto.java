package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CrimeDto : 범죄 발생 현황 정보", description = "해당 구의 범죄 발생 현황 정보를 나타낸다.")
public class CrimeDto {
	@ApiModelProperty(value = "구군 코드")
	private String gugunCode;
	@ApiModelProperty(value = "구군 이름")
	private String gugunName;
	@ApiModelProperty(value = "절도")
	private int theft;
	@ApiModelProperty(value = "살인")
	private int murder;
	@ApiModelProperty(value = "강도")
	private int robbery;
	@ApiModelProperty(value = "방화")
	private int fire;
	@ApiModelProperty(value = "폭행")
	private int attack;
	@ApiModelProperty(value = "상해")
	private int injury;
	@ApiModelProperty(value = "주거침입")
	private int housebreak;
	@ApiModelProperty(value = "교통방해")
	private int obstructTraffic;
	@ApiModelProperty(value = "무면허")
	private int noLicense;
	@ApiModelProperty(value = "음주운전")
	private int drunkenRide;
	@ApiModelProperty(value = "교통사고")
	private int roadViolation;
	@ApiModelProperty(value = "성범죄")
	private int rape;
	@ApiModelProperty(value = "총합")
	private int total;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getGugunCode() {
		return gugunCode;
	}
	public void setGugunCode(String gugunCode) {
		this.gugunCode = gugunCode;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public int getTheft() {
		return theft;
	}
	public void setTheft(int theft) {
		this.theft = theft;
	}
	public int getMurder() {
		return murder;
	}
	public void setMurder(int murder) {
		this.murder = murder;
	}
	public int getRobbery() {
		return robbery;
	}
	public void setRobbery(int robbery) {
		this.robbery = robbery;
	}
	public int getFire() {
		return fire;
	}
	public void setFire(int fire) {
		this.fire = fire;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getInjury() {
		return injury;
	}
	public void setInjury(int injury) {
		this.injury = injury;
	}
	public int getHousebreak() {
		return housebreak;
	}
	public void setHousebreak(int housebreak) {
		this.housebreak = housebreak;
	}
	public int getObstructTraffic() {
		return obstructTraffic;
	}
	public void setObstructTraffic(int obstructTraffic) {
		this.obstructTraffic = obstructTraffic;
	}
	public int getNoLicense() {
		return noLicense;
	}
	public void setNoLicense(int noLicense) {
		this.noLicense = noLicense;
	}
	public int getDrunkenRide() {
		return drunkenRide;
	}
	public void setDrunkenRide(int drunkenRide) {
		this.drunkenRide = drunkenRide;
	}
	public int getRoadViolation() {
		return roadViolation;
	}
	public void setRoadViolation(int roadViolation) {
		this.roadViolation = roadViolation;
	}
	public int getRape() {
		return rape;
	}
	public void setRape(int rape) {
		this.rape = rape;
	}
	@Override
	public String toString() {
		return "CrimeDto [gugunCode=" + gugunCode + ", gugunName=" + gugunName + ", theft=" + theft + ", murder="
				+ murder + ", robbery=" + robbery + ", fire=" + fire + ", attack=" + attack + ", injury=" + injury
				+ ", housebreak=" + housebreak + ", obstructTraffic=" + obstructTraffic + ", noLicense=" + noLicense
				+ ", drunkenRide=" + drunkenRide + ", roadViolation=" + roadViolation + ", rape=" + rape + ", total="
				+ total + "]";
	}
	
	
	
	
	
}
