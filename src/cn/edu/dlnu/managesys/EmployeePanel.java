package cn.edu.dlnu.managesys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
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
import javax.swing.DefaultListSelectionModel;

import cn.edu.dlnu.dao.Conn;

public class EmployeePanel extends JPanel {
    
	private DefaultTableModel model;
	private Object[][] rowDate;
	private Object[] columnName;
	private JTable table;
    private JScrollPane tablePane;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private ButtonPanel buttonPanel;//������
	private QueryPanel queryPanel;
    private DefaultListSelectionModel dlsm;
    private int changeIndex;
    
    public EmployeePanel(){
    	queryPanel = new QueryPanel(table);
		buttonPanel = new ButtonPanel();
		
		setTable();//��ʼ��������
		
		dlsm = new DefaultListSelectionModel();
		dlsm.addListSelectionListener(new TChangeListener());//Ϊ����޸�ѡ��ģ����Ӽ�����
		//dlsm.addListSelectionListener(new TDeleteListener());
		//��ʼ�����������
		tablePane = new JScrollPane();
		tablePane.getViewport().add(table);
		tablePane.setBounds(0, 100, 1000, 300);
		//Ϊ�������ѡ�����ģ��
		table.setSelectionModel(dlsm);
		//�����������������
		
		this.setLayout(new BorderLayout());
		this.add(queryPanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(tablePane,BorderLayout.CENTER);
		
    }
    
	private void setTable(){
		//�������ݿ�
		try{
			con = Conn.getConnect();
			String sql = "select count(*)from Employee";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			//��ʼ��table����
			int n = 0;
			if(rs != null && rs.next()){
				n = rs.getInt(1);//����Ա�����м�¼��
			}
			//�����ģ��
			rowDate = new Object[n][];
			columnName = new Object[]{
					"Ա�����","Ա������","Ա���Ա�","Ա���绰","Ա������","Ա��ְ��"
			};
		
			String sql2 = "select * from Employee";
			rs = stmt.executeQuery(sql2);
		
			//���������
			int i=0; 
			while(rs != null && rs.next()){
				String eno = rs.getString(1);
				String ename = rs.getString(2);
				String esex = rs.getString(3);
				String etelphone = rs.getString(4);
				String dname = rs.getString(5);
				String pname = rs.getString(6);
				Object[] temp = new Object[]{eno,ename,esex,etelphone,dname,pname};
				rowDate[i] = temp;
				i++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		model = new DefaultTableModel(rowDate,columnName);
		table = new JTable(model);
		setAlignment(table);//�����ı�����
	
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//���õ�Ԫ�����ݶ��䷽ʽ
	public void setAlignment(JTable table){
		DefaultTableCellRenderer d = new DefaultTableCellRenderer();
	    //���õ�Ԫ���ˮƽ���뷽ʽΪ����
		d.setHorizontalAlignment(JLabel.CENTER);
		for(int i=0 ; i<table.getColumnCount() ; i++ ){
			TableColumn col = table.getColumn(table.getColumnName(i));
			col.setCellRenderer(d);
		}
	}
	
	//�ڲ���QueryPane
	class QueryPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField queryField;
		private JButton foundButton;
		
		public QueryPanel(JTable table){
			//��ʼ����ѯ�ı���
			queryField = new JTextField(20);
			
			
			//��ʼ��������ť
			foundButton = new JButton("����");
			foundButton.addActionListener(new QueryListener());
			
			this.add(queryField);
			this.add(foundButton);
		}
		
		//��ѯ��ť������
		  class QueryListener implements ActionListener{
                private int queryIndex;
			  	@Override
			  	public void actionPerformed(ActionEvent a) {
			  			// TODO Auto-generated method stub
			  			Object o = a.getSource();
			  			if(o == foundButton){
					
			  					String content = queryField.getText();
			  					//��ȡ���������ı�ƥ����к�
			  					for(int i=0 ; i<table.getRowCount() ; i++){
			  						String value= (String) model.getValueAt(i, 0);
			  						if(content.equals(value)){
			  							queryIndex = i;
			  							//�����н������⻯����
					  					setShow(table);
					  			        table.updateUI();
					  			     
			  						}
			  					}
					
			  					
			  			}
			  	}
			  	
			  	//��ѯ������ʾ����
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
			  		   //����Ⱦ��Ӧ�õ�JTable��
			  			for(int i=0 ; i<table.getColumnCount() ; i++ ){
	  						TableColumn col = table.getColumn(table.getColumnName(i));
	  						col.setCellRenderer(d);
	  					}
			  		
			  	}

			  
		  }
	}
	//�ڲ���buttonPane
	class ButtonPanel extends JPanel{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel enoLabel;//Ա������ı���
		  private JLabel enameLabel;//Ա��������ǩ
		  private JLabel esexLabel;//Ա���Ա��ǩ
		  private JLabel etelphoneLabel;//Ա���绰��ǩ
		  private JLabel edeptLabel;//Ա�����ű�ǩ
		  private JLabel epostLabel;//Ա��ְ���ǩ
		  
		  //�����ı���
		  private JTextField enoField;
		  private JTextField enameField;
		  private JTextField esexField;
		  private JTextField etelphoneField;
		  private JTextField edeptField;
		  private JTextField epostField;
		  
		  //��Ӱ�ť
		  private JButton addButton;
		  private JButton changeButton;
		  private JButton deleteButton;
		  public ButtonPanel(){
			  //���������ʼ��
			  enoLabel = new JLabel("Ա����ţ�");
			  enameLabel = new JLabel("Ա������:");
			  esexLabel = new JLabel("Ա���Ա�");
			  etelphoneLabel = new JLabel("Ա���绰��");
			  edeptLabel = new JLabel("Ա�����ţ�");
			  epostLabel = new JLabel("Ա��ְ��");
			  
			  enoField = new JTextField(3);
			  enameField = new JTextField(7);
			  esexField = new JTextField(2);
			  etelphoneField = new JTextField(8);
			  edeptField = new JTextField(9);
			  epostField = new JTextField(9);
			  //��Ӱ�ť
			  addButton = new JButton("���");
			  addButton.addActionListener(new AddListener());
			  //�޸İ�ť
			  changeButton = new JButton("�޸�");
			  changeButton.addActionListener(new ChangeListener());
			  //ɾ����ť
			  deleteButton = new JButton("ɾ��");
			  deleteButton.addActionListener(new DeleteListener());
			  
			  //�������ӵ����
			  this.add(enoLabel);
			  this.add(enoField);
			  
			  this.add(enameLabel);
			  this.add(enameField);
			  
			  this.add(esexLabel);
			  this.add(esexField);
			  
			  this.add(etelphoneLabel);
			  this.add(etelphoneField);
			  
			  this.add(edeptLabel);
			  this.add(edeptField);
			  
			  this.add(epostLabel);
			  this.add(epostField);
			  
			  this.add(addButton);
			  this.add(changeButton);
			  this.add(deleteButton);
			  
		  }
		  
		  
		  
		//��Ӱ�ť������
		class AddListener implements ActionListener{
	
				public void actionPerformed(ActionEvent a) {
					// TODO Auto-generated method stub
					Object o = a.getSource();
					if(o == addButton){
							String eno = enoField.getText();
							String ename = enameField.getText();
							String esex = esexField.getText();
							String etelphone = etelphoneField.getText();
							String dname = edeptField.getText();
							String pname = epostField.getText();
							Object[] date = {eno,ename,esex,etelphone,dname,pname};
							model.addRow(date);
							addDate(eno,ename,esex,etelphone,dname,pname);
					}
				}
				
		}
		
		//�޸İ�ť������
		class ChangeListener implements ActionListener{
		       public void actionPerformed(ActionEvent a){
		    	     Object o = a.getSource();
		    	     
		    	     if(o == changeButton){
		    	    	    String eno = enoField.getText();
		    	    	 	String ename = enameField.getText();
							String esex = esexField.getText();
							String etelphone = etelphoneField.getText();
							String dname = edeptField.getText();
							String pname = epostField.getText();
							//����JTable�е�ֵ
							changeDate(eno,ename,esex,etelphone,dname,pname);
							model.setValueAt(enameField.getText(), changeIndex, 1);
							model.setValueAt(esexField.getText(), changeIndex, 2);
							model.setValueAt(etelphoneField.getText(), changeIndex, 3);
							model.setValueAt(edeptField.getText(), changeIndex, 4);
							model.setValueAt(epostField.getText(), changeIndex, 5);
						
		    	     }
		       }
		}
		//ɾ�����ݼ�����
		class DeleteListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO Auto-generated method stub
				Object o = a.getSource();
	    	     
	    	     if(o == deleteButton){
	    	    	 int[] n = table.getSelectedRows();
	    	    	 if(n.length == -1){
	    	    		 return ;//û��ѡ��
	    	    	 }
	    	    	 String[] e = new String[n.length];
	    	    	 for(int i=0; i<n.length ; i++){
	    	    	     String temp = (String) model.getValueAt(n[i], 0);
	    	    	     e[i] = temp;//��ѡ����кŵĶ�Ӧ�ı�Ŵ���String����������
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
			String sql = "delete from employee where eno=? ";
			
			try {
				con = Conn.getConnect();
				ps = con.prepareStatement(sql);
				int i; 
				for(i=0; i<e.length ;i++){
					ps.setString(1, e[i]);
					ps.executeUpdate();
				}
				if(i>0){
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//�޸����ݷ���
		public void changeDate(String eno,String ename,String esex,String etelphone,String dname,String pname){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "update employee set ename=?,esex=?,etelphone=?,dname=?,pname=? where eno=?";
			try {
				con = Conn.getConnect();
				ps = con.prepareStatement(sql);
				ps.setString(1, ename);
				ps.setString(2, esex);
				ps.setString(3, etelphone);
				ps.setString(4, dname);
				ps.setString(5, pname);
				ps.setString(6, eno);
				
				if(ps.executeUpdate()>0){
					JOptionPane.showMessageDialog(null, "�޸ĳɹ���", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "�޸�ʧ�ܣ�", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//������ݷ���
		public void addDate(String eno,String ename, String esex,String etelphone, String dname,String pname){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "insert into employee values(?,?,?,?,?,?)";
			try{
				//�����ݿ⽨������
				con = Conn.getConnect();
				//��ȡԤ�������
				ps = con.prepareStatement(sql);
				//Ԥ�����ʺŲ���
				ps.setString(1, eno);
				ps.setString(2, ename);
				ps.setString(3, esex);
				ps.setString(4, etelphone);
				ps.setString(5, dname);
				ps.setString(6, pname);
				if(ps.executeUpdate()>0){
					JOptionPane.showMessageDialog(null, "��ӳɹ���", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "���ʧ�ܣ�", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				ps.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		
		  
	}
	//��������޸�ѡ�����ķ���
	class TChangeListener implements ListSelectionListener{
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				//�����������ʱ�ᴥ���÷���
			int	index = table.getSelectedRow();//��ȡ��ǰѡ�е��к�
				//��ȡÿ����Ԫ�����ݷ����ı���
				String eno = (String)model.getValueAt(index, 0);
				String ename = (String)model.getValueAt(index, 1);
				String esex = (String)model.getValueAt(index, 2);
				String etelphone = (String)model.getValueAt(index, 3);
				String edept = (String)model.getValueAt(index, 4);
				String epost = (String)model.getValueAt(index, 5);
		
				//����Ա���ı����е�ֵ
				buttonPanel.enoField.setText(eno);
				buttonPanel.enameField.setText(ename);
				buttonPanel.esexField.setText(esex);
				buttonPanel.etelphoneField.setText(etelphone);
				buttonPanel.edeptField.setText(edept);
				buttonPanel.epostField.setText(epost);
				
				changeIndex = index;
			}
	}
}
