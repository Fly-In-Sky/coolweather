package model;

public class County {

	private int id;
	private String CountyName;
	private String CountyCode;
	private int CityId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCountyName() {
		return CountyName;
	}
	
	public void setCountyName(String countyName) {
		CountyName = countyName;
	}
	
	public String getCountyCode() {
		return CountyCode;
	}
	
	public void setCountyCode(String countyCode) {
		CountyCode = countyCode;
	}
	
	public int getCityId() {
		return CityId;
	}
	
	public void setCityId(int cityId) {
		CityId = cityId;
	}
	
	
	
}
