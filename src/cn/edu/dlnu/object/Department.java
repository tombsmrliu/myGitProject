package cn.edu.dlnu.object;

public class Department {
	 private String dpNumber;//���ű��
     private String dpName;//��������
     private String dpAbout;//���ż��
     
     //�޲ι��캯��
     public Department(){
    	 
     }
     //�вι��캯��
     public Department(String dpNumber, String dpName, String dpAbout){
    	 this.dpNumber = dpNumber;
    	 this.dpName = dpName;
    	 this.dpAbout = dpAbout;
     }
     //��ȡ���ű��
     public String getDpNumber(){
    	 return dpNumber;
     }
     //��ȡ��������
     public String getDpName(){
    	 return dpName;
     }
     //��ȡ���ż��
     public String getDpAbout(){
    	 return dpAbout;
     }
     //���ò��ű��
     public void setDpNumber(String dpNumber){
    	 this.dpNumber = dpNumber;
     }
     //���ò�������
     public void setDpName(String dpName){
    	 this.dpName = dpName;
     }
     //���ò��ż��
     public void setDpAbout(String dpAbout){
    	 this.dpAbout = dpAbout;
     }
}
