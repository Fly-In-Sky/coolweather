package model;

//2017年11月20日 22:38:49
//建立数据库，表的实例，共三个实例，分别是province，city，county分三个实体类
//这个类是city

public class City {
	
	private int id;
	private String cityName;
	private String cityCode;
	private int provinceId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public int getProvinceId() {
		return provinceId;
	}
	
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	
	
}
