package cn.edu.dlnu.managesys;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame implements ActionListener{
    
	/**
	 * 登录界面部分
	 */
	private static final long serialVersionUID = 1L;
    private JFrame logonFrame;
	private JLabel userNameLabel;
    private JLabel passWordLabel;
    private JTextField userNameField;
    private JPasswordField passWordField;
    private JButton commitButton;
    private JPanel loginPanel;
    
	
	public LoginFrame(){
		logonFrame = new JFrame();
		logonFrame.setTitle("登录人力资源管理系统登录界面");
		logonFrame.setSize(400, 300);
    	componentInit();
    	logonFrame.setVisible(true);
    }
	
	public void componentInit(){
		userNameLabel = new JLabel("用户名:");
		passWordLabel = new JLabel("密码:");
		
		userNameField = new JTextField(10);
		passWordField = new JPasswordField(10);
		passWordField.setEchoChar('*');
		
		commitButton = new JButton("登录");
		commitButton.addActionListener(this);
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3,2));
		
		
		loginPanel.add(userNameLabel);
		loginPanel.add(userNameField);
		loginPanel.add(passWordLabel);
		loginPanel.add(passWordField);
		loginPanel.add(commitButton);
		logonFrame.getContentPane().add(loginPanel);
		logonFrame.pack();
	}
	
	public static void main(String[] args){
		new LoginFrame();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 登录的实现
		try{
			String userName = userNameField.getText();
			String passWord = passWordField.getText();
			if((userName.equals("system"))&& (passWord.equals("Oracle123")) ){
				logonFrame.dispose();
				JOptionPane.showMessageDialog(null, "登录成功！欢迎使用本系统", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				new HRMSFrame();
			}else{
				JOptionPane.showMessageDialog(null, "登录失败！用户名或密码错误", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
				userNameLabel.setText("");userNameLabel.setText("");
			}
			
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "登录失败！用户名或密码为空", "温馨提示",JOptionPane.INFORMATION_MESSAGE);	
			userNameLabel.setText("");userNameLabel.setText("");
		}
	}
}
