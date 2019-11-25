package com.oracle.littlechat.client.view;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

	//has-a 优先使用组合，尽量少用继承
	private Socket  client;

	private ObjectOutputStream  out;
	private ObjectInputStream  in;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
					frame.connectServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setResizable(false);
		setTitle("\u804A\u5929\u767B\u5F55\u7A97\u53E3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 268, 247);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u6237");
		label.setBounds(54, 45, 35, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(99, 45, 105, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBounds(54, 98, 35, 15);
		contentPane.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(99, 98, 105, 21);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.setBounds(54, 141, 71, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.setBounds(140, 141, 71, 23);
		contentPane.add(btnNewButton_1);
		connectServer();


	}

	public void connectServer() {
		//登陆界面底层持有的socket对象应该在构造器最后一行初始化（先要渲染界面，然后再建立底层通讯）
		try {
			client=new Socket("localhost",8888);
			//因为为了更好的传递和处理消息，所以，项目中的任何消息都会封装成一个标准的ChatMessage对象
			//所以，底层socket必须提供出序列化流（能将java对象写入通道的流）
			out=new ObjectOutputStream(client.getOutputStream());
			in=new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			//一旦创建socket时出现异常，说明链接服务器失败，这里应该使用swing的ui技术弹出错误提示框
			JOptionPane.showMessageDialog(this,"网络链接失败，请重试！","温馨提示",JOptionPane.ERROR_MESSAGE);
		}
	}
}
