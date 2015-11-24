package cn.edu.dlnu.managesys;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class HRMSFrame extends JFrame{
    
	private JPanel ePanel,dPanel,pPanel;
	private JTabbedPane tabbedPane;
	private static final String DRIVERCLASS = "oracle.jdbc.driver.OracleDriver";
    public HRMSFrame(){
    	try{
    		Class.forName(DRIVERCLASS);
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
		
		init();
	}
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //���ڳ�ʼ������
	public void init(){
		this.setSize(1100,400);//���ô��ڳߴ�
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//���ô���Ĭ�Ϲرշ�ʽ
		this.setResizable(false);//���ô��ڲ������
		this.setLayout(new BorderLayout());
	    
        tabbedPane = new JTabbedPane();
		ePanel = new EmployeePanel();
		dPanel = new DepartmentPanel();
		pPanel = new PositionPanel();
		tabbedPane.add("Ա����Ϣ", ePanel);
		tabbedPane.addTab("������Ϣ", dPanel);
		tabbedPane.addTab("ְ����Ϣ", pPanel);
		
		this.getContentPane().add(tabbedPane);
		this.setVisible(true);//���ô��ڿɼ�
		this.pack();
	}
	
	
	
  
}

