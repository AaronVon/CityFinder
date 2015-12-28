package com.pioneer.aaron.cityfinder.finder;

/**
 * City properties entity class
 *
 * @author Aaron
 *
 */
public class CityModel {

	private String CityName; //full city name
	private String NameSort; //first letter of city in Pinyin

	public String getCityName()
	{
		return CityName;
	}

	public void setCityName(String cityName)
	{
		CityName = cityName;
	}

	public String getNameSort()
	{
		return NameSort;
	}

	public void setNameSort(String nameSort)
	{
		NameSort = nameSort;
	}

}
