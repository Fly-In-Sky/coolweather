package model;

//2017年11月20日 22:20:15
//这个类对应的是数据库表里的实体类

public class Province {
	private int id;
	private String provinceName;
	private String provinceCode;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	
}
