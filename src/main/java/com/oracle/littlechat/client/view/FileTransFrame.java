package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatMessage;
import com.oracle.littlechat.client.model.ChatMessageType;
import com.oracle.littlechat.client.model.ChatUser;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FileTransFrame extends JFrame {
	private ObjectOutputStream  out;
	private ObjectInputStream  in;
	private ChatUser  friend,my;

	private JPanel contentPane;

	private JLabel label1,percent;
	private File  selectedFile;
	private JProgressBar progressBar;



	/**
	 * Create the frame.
	 */
	public FileTransFrame(ChatUser  friend,ChatUser  my,ObjectOutputStream out,ObjectInputStream in) {
		this.friend=friend;
		this.my=my;
		this.out=out;
		this.in=in;
		setTitle("\u6587\u4EF6\u4F20\u8F93\u7A97\u53E3");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 254, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u9009\u62E9\u6587\u4EF6");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser  fileChooser=new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.showOpenDialog(FileTransFrame.this);
				File  file=fileChooser.getSelectedFile();
				selectedFile=file;
				label1.setText(file.getName());
			}
		});
		button.setBounds(10, 10, 93, 23);
		contentPane.add(button);
		
		JLabel label = new JLabel("\u6587\u4EF6\u4FE1\u606F");
		label.setBounds(10, 49, 54, 15);
		contentPane.add(label);

		 label1 = new JLabel();
		label1.setBounds(74, 49, 200, 15);
		contentPane.add(label1);
		
		 progressBar = new JProgressBar(0,0,100);
		progressBar.setBounds(69, 87, 120, 14);
		contentPane.add(progressBar);

		percent = new JLabel();
		percent.setBounds(195, 87, 40, 14);
		contentPane.add(percent);
		
		JLabel lblNewLabel = new JLabel("\u4F20\u8F93\u8FDB\u5EA6");
		lblNewLabel.setBounds(10, 87, 54, 15);
		contentPane.add(lblNewLabel);
		
		JButton button_1 = new JButton("\u53D1\u9001");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatMessage  fileMessage=new ChatMessage();
				fileMessage.setType(ChatMessageType.FILE);
				fileMessage.setFrom(my);
				fileMessage.setTo(friend);
				fileMessage.setContent(selectedFile.getName()+","+selectedFile.length());

				try {
					out.writeObject(fileMessage);
					out.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});
		button_1.setBounds(20, 115, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\u5173\u95ED");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileTransFrame.this.dispose();
			}
		});
		button_2.setBounds(123, 115, 93, 23);
		contentPane.add(button_2);

	}




	/**
	 * 发送文件数据的业务方法
	 */
	public void sendFileData(){
		this.setVisible(true);
		ChatMessage  sendFile=new ChatMessage();
		sendFile.setFrom(my);
		sendFile.setTo(friend);
		sendFile.setType(ChatMessageType.TRANSFILE);
		sendFile.setTime(selectedFile.length()+"");
		sendFile.setContent(selectedFile.getName());
		try {
			out.writeObject(sendFile);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	new Thread(){
		@Override
		public void run() {
			try {
				long fullsize=selectedFile.length();
				long transedSize=0;
				FileInputStream  in=new FileInputStream(selectedFile);
				byte[] bs=new byte[1024];
				int length=-1;
				while((length=in.read(bs))!=-1){
					transedSize+=length;
//					System.out.println("总大小："+fullsize+"\t\t传输大小:"+transedSize);
					percent.setText(Math.round(transedSize*100/fullsize)+"%");
					progressBar.setValue(Math.round(transedSize*100/fullsize));
					out.write(bs,0,length);
					out.flush();
				}
				System.out.println("文件传输完毕");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}.start();

	}
}
