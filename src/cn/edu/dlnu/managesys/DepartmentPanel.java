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
	 * ���Ź�����岿��
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
	private ButtonPanel buttonPanel;//������
	private QueryPanel queryPanel;
    private DefaultListSelectionModel dlsm;
    private int changeIndex;
    
    public DepartmentPanel(){
    	queryPanel = new QueryPanel(table);
		buttonPanel = new ButtonPanel();
		
		setTable();//��ʼ��������
		
		dlsm = new DefaultListSelectionModel();
		dlsm.addListSelectionListener(new TChangeListener());//Ϊ����޸�ѡ��ģ����Ӽ�����
		//dlsm.addListSelectionListener(new TDeleteListener());
		//��ʼ�����������
		tablePane = new JScrollPane();
		tablePane.getViewport().add(table);
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
			String sql = "select count(*)from department";
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
					"���ű��","��������","���ż��"
			};
		
			String sql2 = "select * from department";
			rs = stmt.executeQuery(sql2);
		
			//���������
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
		  private JLabel dnoLabel;//���ű�ű�ǩ
		  private JLabel dnameLabel;//�������Ʊ�ǩ
		  private JLabel daboutLabel;//���ż���ǩ
		  
		  //�����ı���
		  private JTextField dnoField;
		  private JTextField dnameField;
		  private JTextField daboutField;
		 
		  
		  //��Ӱ�ť
		  private JButton addButton;
		  private JButton changeButton;
		  private JButton deleteButton;
		  public ButtonPanel(){
			  //���������ʼ��
			  dnoLabel = new JLabel("���ű�ţ�");
			  dnameLabel = new JLabel("��������:");
			  daboutLabel = new JLabel("���ż�飺");
			  
			  dnoField = new JTextField(3);
			  dnameField = new JTextField(7);
			  daboutField = new JTextField(50);
			  
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
		  
		  
		  
		//��Ӱ�ť������
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
		
		//�޸İ�ť������
		class ChangeListener implements ActionListener{
		       public void actionPerformed(ActionEvent a){
		    	     Object o = a.getSource();
		    	     
		    	     if(o == changeButton){
		    	    	    String dno = dnoField.getText();
		    	    	 	String dname = dnameField.getText();
							String dabout = daboutField.getText();
							
							//����JTable�е�ֵ
							changeDate(dno,dname,dabout);
							model.setValueAt(dnameField.getText(), changeIndex, 1);
							model.setValueAt(daboutField.getText(), changeIndex, 2);
							
						
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
		public void addDate(String dno,String dname, String dabout){
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "insert into department values(?,?,?)";
			try{
				//�����ݿ⽨������
				con = Conn.getConnect();
				//��ȡԤ�������
				ps = con.prepareStatement(sql);
				//Ԥ�����ʺŲ���
				ps.setString(1, dno);
				ps.setString(2, dname);
				ps.setString(3, dabout);
			
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
				String dno = (String)model.getValueAt(index, 0);
				String dname = (String)model.getValueAt(index, 1);
				String dabout = (String)model.getValueAt(index, 2);
		
				//����Ա���ı����е�ֵ
				buttonPanel.dnoField.setText(dno);
				buttonPanel.dnameField.setText(dname);
				buttonPanel.daboutField.setText(dabout);
				
				changeIndex = index;
			}
	}
}
