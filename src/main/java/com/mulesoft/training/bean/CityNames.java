package com.mulesoft.training.bean;

public enum CityNames {
	MUMBAI,PUNE,HYDERABAD,SHRINAGAR,BHOPAL;
	
	 public static boolean contains(String s)
	  {
	      for(CityNames choice:values())
	           if (choice.name().equals(s)) 
	              return true;
	      return false;
	  } 

};
