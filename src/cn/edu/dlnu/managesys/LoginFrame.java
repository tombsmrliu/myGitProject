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
	 * ��¼���沿��
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
		logonFrame.setTitle("��¼������Դ����ϵͳ��¼����");
		logonFrame.setSize(400, 300);
    	componentInit();
    	logonFrame.setVisible(true);
    }
	
	public void componentInit(){
		userNameLabel = new JLabel("�û���:");
		passWordLabel = new JLabel("����:");
		
		userNameField = new JTextField(10);
		passWordField = new JPasswordField(10);
		passWordField.setEchoChar('*');
		
		commitButton = new JButton("��¼");
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
		// ��¼��ʵ��
		try{
			String userName = userNameField.getText();
			String passWord = passWordField.getText();
			if((userName.equals("system"))&& (passWord.equals("Oracle123")) ){
				logonFrame.dispose();
				JOptionPane.showMessageDialog(null, "��¼�ɹ�����ӭʹ�ñ�ϵͳ", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				new HRMSFrame();
			}else{
				JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û������������", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				userNameLabel.setText("");userNameLabel.setText("");
			}
			
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û���������Ϊ��", "��ܰ��ʾ",JOptionPane.INFORMATION_MESSAGE);	
			userNameLabel.setText("");userNameLabel.setText("");
		}
	}
}
