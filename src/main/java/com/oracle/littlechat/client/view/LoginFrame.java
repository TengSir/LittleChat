package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatMessage;
import com.oracle.littlechat.client.model.ChatMessageType;
import com.oracle.littlechat.client.model.ChatUser;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

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
	private JButton btnNewButton,btnNewButton_1;

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
		
		btnNewButton = new JButton("\u767B\u5F55");
		//给登陆按钮添加事件处理代码，

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//1.先获取用户在ui的输入框中输入的数据
				String username=textField.getText().trim();
				String password=new String(passwordField.getPassword());

				//2.做表单验证吧

				//3.将登陆必须的数据封装成一个Messagge对象
				ChatMessage  loginMessage=new ChatMessage();
					//封装一个聊天用户对象，将登陆的用户名和密码封装进去
					ChatUser  user=new ChatUser();
					user.setUsername(Long.parseLong(username));
					user.setPassword(password);

				loginMessage.setFrom(user);//设置消息的 发送人
				loginMessage.setType(ChatMessageType.LOGIN);


				//4.使用登陆界面持有的socket底层的输出流（序列化流），将刚刚封装好的消息对象发送出去
				try {
					out.writeObject(loginMessage);
					out.flush();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(LoginFrame.this,"消息发送失败，请检查网络！","温馨提示",JOptionPane.ERROR_MESSAGE);
				}

				//5.消息发送给出去之后，就使用当前socket的输入流读取服务器给我回发的登陆结果消息
				try {
					ChatMessage  loginResult=(ChatMessage)in.readObject();
					System.out.println(loginResult.getFrom());
					if(loginResult.getFrom()!=null){
						MainFrame  m=new MainFrame();
						m.setVisible(true);
						LoginFrame.this.dispose();//释放当前登陆窗口的ui资源并隐藏当前窗口
					}else{
						JOptionPane.showMessageDialog(LoginFrame.this,"登陆失败，请检查用户名和密码是否正确！","温馨提示",JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(54, 141, 71, 23);
		contentPane.add(btnNewButton);
		
		 btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.setBounds(140, 141, 71, 23);
		contentPane.add(btnNewButton_1);

		//渲染整个窗口上的所有组件，保证所有组件显示成功
		paintComponents(getGraphics());
		paintAll(getGraphics());

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
