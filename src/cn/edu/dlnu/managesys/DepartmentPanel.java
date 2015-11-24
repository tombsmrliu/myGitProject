package cn.edu.dlnu.managesys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import cn.edu.dlnu.dao.Conn;


public class DepartmentPanel extends JPanel{
	
	/**
	 * 部门管理面板部分
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;
	private Object[][] rowDate;
	private Object[] columnName;
	private JTable table;
    private JScrollPane tablePane;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private ButtonPanel buttonPanel;//面板对象
	private QueryPanel queryPanel;
    private DefaultListSelectionModel dlsm;
    private int changeIndex;
    
    public DepartmentPanel(){
    	queryPanel = new QueryPanel(table);
		buttonPanel = new ButtonPanel();
		
		setTable();//初始化表格对象
		
		dlsm = new DefaultListSelectionModel();
		dlsm.addListSelectionListener(new TChangeListener());//为表格修改选择模型添加监听器
		//dlsm.addListSelectionListener(new TDeleteListener());
		//初始化滚动条面板
		tablePane = new JScrollPane();
		tablePane.getViewport().add(table);
		//为表格设置选择监听模型
		table.setSelectionModel(dlsm);
		//将滚动面板放入面板中
		
		this.setLayout(new BorderLayout());
		this.add(queryPanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(tablePane,BorderLayout.CENTER);
		
    }
    
	private void setTable(){
		//连接数据库
		try{
			con = Conn.getConnect();
			String sql = "select count(*)from department";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			//初始化table数据
			int n = 0;
			if(rs != null && rs.next()){
				n = rs.getInt(1);//返回员工表中记录数
			}
			//定义表模型
			rowDate = new Object[n][];
			columnName = new Object[]{
					"部门编号","部门名称","部门简介"
			};
		
			String sql2 = "select * from department";
			rs = stmt.executeQuery(sql2);
		
			//遍历结果集
			int i=0; 
			while(rs != null && rs.next()){
				String dno = rs.getString(1);
				String dname = rs.getString(2);
				String dabout = rs.getString(3);
				
				Object[] temp = new Object[]{dno,dname,dabout};
				rowDate[i] = temp;
				i++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		model = new DefaultTableModel(rowDate,columnName);
		table = new JTable(model);
		setAlignment(table);//设置文本对齐
	
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//设置单元格内容对其方式
	public void setAlignment(JTable table){
		DefaultTableCellRenderer d = new DefaultTableCellRenderer();
	    //设置单元格的水平对齐方式为居中
		d.setHorizontalAlignment(JLabel.CENTER);
		for(int i=0 ; i<table.getColumnCount() ; i++ ){
			TableColumn col = table.getColumn(table.getColumnName(i));
			col.setCellRenderer(d);
		}
	}
	
	//内部类QueryPane
	class QueryPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField queryField;
		private JButton foundButton;
		
		public QueryPanel(JTable table){
			//初始化查询文本框
			queryField = new JTextField(20);
			
			
			//初始化搜索按钮
			foundButton = new JButton("搜索");
			foundButton.addActionListener(new QueryListener());
			
			this.add(queryField);
			this.add(foundButton);
		}
		
		//查询按钮监听器
		  class QueryListener implements ActionListener{
                private int queryIndex;
			  	@Override
			  	public void actionPerformed(ActionEvent a) {
			  			// TODO Auto-generated method stub
			  			Object o = a.getSource();
			  			if(o == foundButton){
					
			  					String content = queryField.getText();
			  					//获取搜索框中文本匹配的行号
			  					for(int i=0 ; i<table.getRowCount() ; i++){
			  						String value= (String) model.getValueAt(i, 0);
			  						if(content.equals(value)){
			  							queryIndex = i;
			  							//将该行进行特殊化处理
					  					setShow(table);
					  			        table.updateUI();
					  			     
			  						}
			  					}
					
			  					
			  			}
			  	}
			  	
			  	//查询特殊显示处理
			  	public void setShow(JTable table){
			  	
			  		DefaultTableCellRenderer d = new DefaultTableCellRenderer(){
			  		

						public Component getTableCellRendererComponent(JTable table, Object value, 
			  					 									  boolean isSelected, boolean hasFocus,
			  					 									  int row, int column){

			  					if(row == queryIndex){
			  						setBackground(Color.BLUE);
			  					}else{
			  						setBackground(Color.WHITE);
			  					}
			  					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			  			}
			  		};
			  		   //讲渲染器应用到JTable中
			  			for(int i=0 ; i<table.getColumnCount() ; i++ ){
	  						TableColumn col = table.getColumn(table.getColumnName(i));
	  						col.setCellRenderer(d);
	  					}
			  		
			  	}

			  
		  }
	}
	//内部类buttonPane
	class ButtonPanel extends JPanel{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		  private JLabel dnoLabel;//部门编号标签
		  private JLabel dnameLabel;//部门名称标签
		  private JLabel daboutLabel;//部门简介标签
		  
		  //六个文本框
		  private JTextField dnoField;
		  private JTextField dnameField;
		  private JTextField daboutField;
		 
		  
		  //添加按钮
		  private JButton addButton;
		  private JButton changeButton;
		  private JButton deleteButton;
		  public ButtonPanel(){
			  //进行组件初始化
			  dnoLabel = new JLabel("部门编号：");
			  dnameLabel = new JLabel("部门名称:");
			  daboutLabel = new JLabel("部门简介：");
			  
			  dnoField = new JTextField(3);
			  dnameField = new JTextField(7);
			  daboutField = new JTextField(50);
			  
			  //添加按钮
			  addButton = new JButton("添加");
			  addButton.addActionListener(new AddListener());
			  //修改按钮
			  changeButton = new JButton("修改");
			  changeButton.addActionListener(new ChangeListener());
			  //删除按钮
			  deleteButton = new JButton("删除");
			  deleteButton.addActionListener(new DeleteListener());
			  
			  //将组件添加到面板
			  this.add(dnoLabel);
			  this.add(dnoField);
			  
			  this.add(dnameLabel);
			  this.add(dnameField);
			  
			  this.add(daboutLabel);
			  this.add(daboutField);
			  
			  this.add(addButton);
			  this.add(changeButton);
			  this.add(deleteButton);
			  
		  }
		  
		  
		  
		//添加按钮监听器
		class AddListener implements ActionListener{
	
				public void actionPerformed(ActionEvent a) {
					// TODO Auto-generated method stub
					Object o = a.getSource();
					if(o == addButton){
							String dno = dnoField.getText();
							String dname = dnameField.getText();
							String dabout = daboutField.getText();
							Object[] date = {dno,dname,dabout};
							model.addRow(date);
							addDate(dno,dname,dabout);
					}
				}
				
		}
		
		//修改按钮监听器
		class ChangeListener implements ActionListener{
		       public void actionPerformed(ActionEvent a){
		    	     Object o = a.getSource();
		    	     
		    	     if(o == changeButton){
		    	    	    String dno = dnoField.getText();
		    	    	 	String dname = dnameField.getText();
							String dabout = daboutField.getText();
							
							//更改JTable中的值
							changeDate(dno,dname,dabout);
							model.setValueAt(dnameField.getText(), changeIndex, 1);
							model.setValueAt(daboutField.getText(), changeIndex, 2);
							
						
		    	     }
		       }
		}
		//删除数据监听器
		class DeleteListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO Auto-generated method stub
				Object o = a.getSource();
	    	     
	    	     if(o == deleteButton){
	    	    	 int[] n = table.getSelectedRows();
	    	    	 if(n.length == -1){
	    	    		 return ;//没有选中
	    	    	 }
	    	    	 String[] e = new String[n.length];
	    	    	 for(int i=0; i<n.length ; i++){
	    	    	     String temp = (String) model.getValueAt(n[i], 0);
	    	    	     e[i] = temp;//将选择的行号的对应的编号存入String数组做备份
	    	    	 }
	    	    	 
	    	    	 deleteDate(e);
	    	    	 System.out.println(n.length);
	    	    	 for(int i=0; i< n.length ;i++){
	    	    		 System.out.println(n[i]);
	    	    		 model.removeRow(n[i]);
	    	    	 }
	    	     }
			}
			
		}
		
		public void deleteDate(String[] e){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "delete from department where dno=? ";
			
			try {
				con = Conn.getConnect();
				ps = con.prepareStatement(sql);
				int i; 
				for(i=0; i<e.length ;i++){
					ps.setString(1, e[i]);
					ps.executeUpdate();
				}
				if(i>0){
					JOptionPane.showMessageDialog(null, "删除成功！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "删除失败！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//修改数据方法
		public void changeDate(String dno,String dname,String dabout){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "update department set dname=?,dabout=? where dno=?";
			try {
				con = Conn.getConnect();
				ps = con.prepareStatement(sql);
				ps.setString(1, dname);
				ps.setString(2, dabout);
				ps.setString(3, dno);
				
				if(ps.executeUpdate()>0){
					JOptionPane.showMessageDialog(null, "修改成功！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "修改失败！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//添加数据方法
		public void addDate(String dno,String dname, String dabout){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "insert into department values(?,?,?)";
			try{
				//与数据库建立连接
				con = Conn.getConnect();
				//获取预编译对象
				ps = con.prepareStatement(sql);
				//预编译问号部分
				ps.setString(1, dno);
				ps.setString(2, dname);
				ps.setString(3, dabout);
			
				if(ps.executeUpdate()>0){
					JOptionPane.showMessageDialog(null, "添加成功！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "添加失败！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		
		  
	}
	//监听表格修改选择动作的方法
	class TChangeListener implements ListSelectionListener{
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				//当鼠标点击该行时会触发该方法
			int	index = table.getSelectedRow();//获取当前选中的行号
				//获取每个单元格数据放入文本框
				String dno = (String)model.getValueAt(index, 0);
				String dname = (String)model.getValueAt(index, 1);
				String dabout = (String)model.getValueAt(index, 2);
		
				//设置员工文本框中的值
				buttonPanel.dnoField.setText(dno);
				buttonPanel.dnameField.setText(dname);
				buttonPanel.daboutField.setText(dabout);
				
				changeIndex = index;
			}
	}
}
