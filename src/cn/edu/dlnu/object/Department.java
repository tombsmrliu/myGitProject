package cn.edu.dlnu.object;

public class Department {
	 private String dpNumber;//部门编号
     private String dpName;//部门名称
     private String dpAbout;//部门简介
     
     //无参构造函数
     public Department(){
    	 
     }
     //有参构造函数
     public Department(String dpNumber, String dpName, String dpAbout){
    	 this.dpNumber = dpNumber;
    	 this.dpName = dpName;
    	 this.dpAbout = dpAbout;
     }
     //获取部门编号
     public String getDpNumber(){
    	 return dpNumber;
     }
     //获取部门名称
     public String getDpName(){
    	 return dpName;
     }
     //获取部门简介
     public String getDpAbout(){
    	 return dpAbout;
     }
     //设置部门编号
     public void setDpNumber(String dpNumber){
    	 this.dpNumber = dpNumber;
     }
     //设置部门名称
     public void setDpName(String dpName){
    	 this.dpName = dpName;
     }
     //设置部门简介
     public void setDpAbout(String dpAbout){
    	 this.dpAbout = dpAbout;
     }
}
