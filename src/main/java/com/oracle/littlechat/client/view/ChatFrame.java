package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatUser;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ChatFrame extends JFrame {
	private ChatUser  friend,my;

	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public ChatFrame(ChatUser friend,ChatUser my) {
		this.friend=friend;
		this.my=my;
		setTitle("和【"+friend.getNickname()+"】聊天中...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);//在聊天窗口上设置该窗口的关闭行为仅仅是隐藏这个窗口，而不推出整个程序
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 203);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 253, 414, 103);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					System.out.println("huiche ");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		scrollPane_1.setViewportView(textArea_1);
		
		JButton button = new JButton("\u53D1\u9001");
		button.setBounds(201, 374, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5173\u95ED");
		button_1.setBounds(319, 374, 93, 23);
		contentPane.add(button_1);
		
		JButton btnFile = new JButton("file");
		btnFile.setBounds(10, 222, 70, 23);
		contentPane.add(btnFile);
		
		JButton btnEmoj = new JButton("emoj");
		btnEmoj.setBounds(90, 222, 70, 23);
		contentPane.add(btnEmoj);
	}

}
