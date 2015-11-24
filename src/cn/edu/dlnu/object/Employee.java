package cn.edu.dlnu.object;

public class Employee {
      private String eId;
      private String eName;
      private String eSex;
      private String eTelPhone;
      private Department eDept;
      private Position ePost;
      
      //无参构造函数
      public Employee(){
    	  
      }
      //有参构造函数
      public Employee(String eId,String eName,String eSex){
    	  this.eId = eId;
    	  this.eName = eName;
    	  this.eSex = eSex;
      }
      
}
