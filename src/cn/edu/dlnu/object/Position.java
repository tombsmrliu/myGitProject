package cn.edu.dlnu.object;

public class Position {
	private String pNumber;//ְ����
    private String pName;//ְ������
    private double pMinimumWage;//ְ����͹���
    private double pMaximumWage;//ְ����߹���
    
    //�޲ι��캯��
    public Position(){
   	 
    }
    //�вι��캯��
    public Position(String pNumber, String pName, double pMinimumWage, double pMaximumWage ){
   	this.pNumber = pNumber;
   	this.pName = pName;
   	this.pMinimumWage = pMinimumWage;
   	this.pMaximumWage = pMaximumWage;
    }
    
    //��ȡְ����
    public String getPNumber(){
   	 return pNumber;
    }
    //��ȡְ������
    public String getPName(){
   	 return pName;
    }
    //��ȡְ����͹���
    public double getPMinimumWage(){
   	 return pMinimumWage;
    }
    //��ȡְ����߹���
    public double getPMaximumWage(){
   	 return pMaximumWage;
    }
    //����ְ����
    public void setPNumber(String pNumber){
   	 this.pNumber = pNumber;
    }
    //����ְ������
    public void setPName(String pName){
   	 this.pName = pName;
    }
    //����ְ����͹���
    public void setPMinimumWage(double pMinimumWage){
   	 this.pMinimumWage = pMinimumWage;
    }
    //����ְ����߹���
    public void setPMaximumWage(double pMaximumWage){
   	 this.pMaximumWage = pMaximumWage;
    }
}
