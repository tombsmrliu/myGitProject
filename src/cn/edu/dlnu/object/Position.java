package cn.edu.dlnu.object;

public class Position {
	private String pNumber;//职务编号
    private String pName;//职务名称
    private double pMinimumWage;//职务最低工资
    private double pMaximumWage;//职务最高工资
    
    //无参构造函数
    public Position(){
   	 
    }
    //有参构造函数
    public Position(String pNumber, String pName, double pMinimumWage, double pMaximumWage ){
   	this.pNumber = pNumber;
   	this.pName = pName;
   	this.pMinimumWage = pMinimumWage;
   	this.pMaximumWage = pMaximumWage;
    }
    
    //获取职务编号
    public String getPNumber(){
   	 return pNumber;
    }
    //获取职务名称
    public String getPName(){
   	 return pName;
    }
    //获取职务最低工资
    public double getPMinimumWage(){
   	 return pMinimumWage;
    }
    //获取职务最高工资
    public double getPMaximumWage(){
   	 return pMaximumWage;
    }
    //设置职务编号
    public void setPNumber(String pNumber){
   	 this.pNumber = pNumber;
    }
    //设置职务名称
    public void setPName(String pName){
   	 this.pName = pName;
    }
    //设置职务最低工资
    public void setPMinimumWage(double pMinimumWage){
   	 this.pMinimumWage = pMinimumWage;
    }
    //设置职务最高工资
    public void setPMaximumWage(double pMaximumWage){
   	 this.pMaximumWage = pMaximumWage;
    }
}
