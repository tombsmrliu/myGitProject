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
    //窗口初始化方法
	public void init(){
		this.setSize(1100,400);//设置窗口尺寸
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//设置窗口默认关闭方式
		this.setResizable(false);//设置窗口不能最大化
		this.setLayout(new BorderLayout());
	    
        tabbedPane = new JTabbedPane();
		ePanel = new EmployeePanel();
		dPanel = new DepartmentPanel();
		pPanel = new PositionPanel();
		tabbedPane.add("员工信息", ePanel);
		tabbedPane.addTab("部门信息", dPanel);
		tabbedPane.addTab("职务信息", pPanel);
		
		this.getContentPane().add(tabbedPane);
		this.setVisible(true);//设置窗口可见
		this.pack();
	}
	
	
	
  
}

