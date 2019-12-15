package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatMessage;
import com.oracle.littlechat.client.model.ChatMessageType;
import com.oracle.littlechat.client.model.ChatUser;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChatFrame extends JFrame {
	public JTextArea getTextArea() {
		return textArea;
	}

	private ChatUser  friend,my;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private JPanel contentPane;//Field字段（属性）
	private  JScrollPane scrollPane;
	private  JTextArea textArea;
	private  JScrollPane scrollPane_1;
	private  JTextArea textArea_1;
	private  JButton button;
	private  JButton button_1;
	private  JButton btnFile;
	private  JButton btnEmoj;

	public FileTransFrame getFileTransFrame() {
		return fileTransFrame;
	}

	public FileTransFrame   fileTransFrame;




	public ChatFrame(ChatUser user,ObjectOutputStream out,ObjectInputStream in){
		this(null,user,out,in);
		setTitle("和所有人群聊中...");
		button.removeActionListener(button.getActionListeners()[0]);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//1.先获取编辑的消息文字
				String editMessage=textArea_1.getText();
				//2.将我要发送的消息显示到上面的聊天框里

				textArea.append(my.getNickname()+"   "+new Date().toLocaleString()+":\r\n"+editMessage+"\r\n\r\n");
				//3.清空消息编辑框（输入框）
				textArea_1.setText("");
				//4.封装一个标准的Message对象
				ChatMessage  message=new ChatMessage();
				ChatUser  mysimple=new ChatUser();
				mysimple.setUsername(my.getUsername());
				mysimple.setNickname(my.getNickname());
				message.setFrom(mysimple);
				message.setType(ChatMessageType.GROUPTEXT);
				message.setContent(editMessage);
				//5.使用底层的socket流将消息对象写入网络另外一端
				try {
					out.writeObject(message);
					out.flush();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(ChatFrame.this,"消息发送失败！","温馨提示",JOptionPane.ERROR_MESSAGE);
				}


			}
		});
	}


	/**
	 * Create the frame.
	 */
	public ChatFrame(ChatUser friend,ChatUser my,ObjectOutputStream  out,ObjectInputStream in) {
		this.out=out;
		this.in=in;
		this.friend=friend;
		this.my=my;
		if(friend!=null)
		setTitle("和【"+friend.getNickname()+"】聊天中...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);//在聊天窗口上设置该窗口的关闭行为仅仅是隐藏这个窗口，而不推出整个程序

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 203);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 253, 414, 103);
		contentPane.add(scrollPane_1);

		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);

		button = new JButton("\u53D1\u9001");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//1.先获取编辑的消息文字
				String editMessage=textArea_1.getText();
				//2.将我要发送的消息显示到上面的聊天框里

				textArea.append(my.getNickname()+"   "+new Date().toLocaleString()+":\r\n"+editMessage+"\r\n\r\n");
				//3.清空消息编辑框（输入框）
				textArea_1.setText("");
				//4.封装一个标准的Message对象
				ChatMessage  message=new ChatMessage();
				ChatUser  mysimple=new ChatUser();
				mysimple.setUsername(my.getUsername());
				mysimple.setNickname(my.getNickname());
				message.setFrom(mysimple);
				message.setTo(friend);
				message.setType(ChatMessageType.TEXT);
				message.setContent(editMessage);
				//5.使用底层的socket流将消息对象写入网络另外一端
				try {
					out.writeObject(message);
					out.flush();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(ChatFrame.this,"消息发送失败！","温馨提示",JOptionPane.ERROR_MESSAGE);
				}


			}
		});
		button.setBounds(201, 374, 93, 23);
		contentPane.add(button);

		button_1 = new JButton("\u5173\u95ED");
		button_1.setBounds(319, 374, 93, 23);
		contentPane.add(button_1);

		btnFile = new JButton("file");
		btnFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				  fileTransFrame=new FileTransFrame(friend,my,out,in);
					fileTransFrame.setLocationRelativeTo(ChatFrame.this);
				  fileTransFrame.setVisible(true);
			}
		});
		btnFile.setBounds(10, 222, 70, 23);
		contentPane.add(btnFile);

		btnEmoj = new JButton("emoj");
		btnEmoj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				textArea.append(my.getNickname()+"   "+new Date().toLocaleString()+":\r\n你给对方发送了一个窗口抖动...\r\n\r\n");
				ChatFrame.this.shakeWindow();
				//4.封装一个标准的Message对象
				ChatMessage  message=new ChatMessage();
				ChatUser  mysimple=new ChatUser();
				mysimple.setUsername(my.getUsername());
				mysimple.setNickname(my.getNickname());
				message.setFrom(mysimple);
				message.setTo(friend);
				message.setType(ChatMessageType.SHAKE);

				//5.使用底层的socket流将消息对象写入网络另外一端
				try {
					out.writeObject(message);
					out.flush();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(ChatFrame.this,"消息发送失败！","温馨提示",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEmoj.setBounds(90, 222, 70, 23);
		contentPane.add(btnEmoj);
	}

	public void shakeWindow(){
		new Thread(){
			@Override
			public void run() {

				int startX=ChatFrame.this.getX();
				int startY=ChatFrame.this.getY();
				int fudu=1;
				int delay=20;
				for(int n=0;n<50;n++){
					ChatFrame.this.setLocation(startX-fudu,startY);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(startX+fudu,startY);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(startX,startY-fudu);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(startX,startY+fudu);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				ChatFrame.this.setLocation(startX,startY);
			}
		}.start();
	}

}
