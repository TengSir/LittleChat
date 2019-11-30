package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatMessage;
import com.oracle.littlechat.client.model.ChatMessageType;
import com.oracle.littlechat.client.model.ChatUser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class RegisterFrame extends JFrame {
	private ObjectOutputStream  out;
	private ObjectInputStream  in;
	private LoginFrame  loginFrame;

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;
	private  JRadioButton radioButton;
	private  JRadioButton radioButton_1;
	private  JComboBox comboBox,imageComboBox;
	private  JTextArea textArea;
	private  JButton btnNewButton;
	private  JButton button;
	private  JLabel label;
	private  JLabel label_1;
	private  JLabel label_2;
	private  JLabel label_3;
	private  JLabel label_4;
	private  JLabel label_5;
	private  JLabel label_6;
	private  JLabel label_7;
	private  JLayeredPane lay;
	private  int nowPic=1;

	public static void main(String[] args) {
		RegisterFrame g=new RegisterFrame(null,null,null);
		g.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public RegisterFrame(ObjectOutputStream  out,ObjectInputStream  in,LoginFrame  loginFrame) {
		this.loginFrame= loginFrame;
		this.out=out;
		this.in=in;
		setResizable(false);
		setTitle("\u804A\u5929\u6CE8\u518C\u7A97\u53E3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel("\u7528\u6237\u540D");
		label.setBounds(55, 57, 54, 15);
		contentPane.add(label);

		label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBounds(55, 106, 54, 15);
		contentPane.add(label_1);

		label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_2.setBounds(55, 154, 54, 15);
		contentPane.add(label_2);

		label_3 = new JLabel("\u6635\u79F0");
		label_3.setBounds(55, 205, 54, 15);
		contentPane.add(label_3);

		label_4 = new JLabel("\u6027\u522B");
		label_4.setBounds(55, 248, 54, 15);
		contentPane.add(label_4);

		label_5 = new JLabel("\u5E74\u9F84");
		label_5.setBounds(55, 303, 54, 15);
		contentPane.add(label_5);

		label_6 = new JLabel("\u4E2A\u6027\u7B7E\u540D");
		label_6.setBounds(55, 357, 54, 15);
		contentPane.add(label_6);
		
		textField = new JTextField();
		textField.setBounds(144, 54, 103, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(144, 103, 103, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(144, 151, 103, 21);
		contentPane.add(passwordField_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(144, 202, 103, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		radioButton = new JRadioButton("\u7537");
		radioButton.setSelected(true);
		radioButton.setBounds(144, 259, 53, 23);
		contentPane.add(radioButton);

		radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.setBounds(199, 259, 48, 23);
		contentPane.add(radioButton_1);

		comboBox = new JComboBox();
		for(int n=0;n<=100;n++){
			comboBox.addItem(n);
		}
		comboBox.setBounds(144, 300, 103, 21);
		contentPane.add(comboBox);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		textArea.setBounds(146, 353, 101, 150);
		contentPane.add(textArea);

		label_7=new JLabel("用户头像");
		label_7.setBounds(55, 390, 54, 15);
		contentPane.add(label_7);

		lay=new JLayeredPane();
		for(int n=1;n<=15;n++){
			JLabel  image=new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("images/"+n+".gif")).getScaledInstance(70,70,Image.SCALE_DEFAULT)));
			image.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(nowPic);
					nowPic++;
					lay.setLayer((JLabel)e.getSource(),nowPic);
					lay.setPosition((JLabel)e.getSource(),nowPic);
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});
			image.setBounds(5,20,70,70);
			lay.add(image,n);
		}
		lay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red),"点击切换",TitledBorder.CENTER,TitledBorder.CENTER));

		lay.setBounds(40, 405, 80, 100);
		contentPane.add(lay);

		btnNewButton = new JButton("\u6CE8\u518C");
		btnNewButton.addActionListener(e -> {
			String  username=textField.getText().trim();
			String password=new String(passwordField.getPassword());
			String confirmPassword=new String(passwordField_1.getPassword());
			String nicknname=textField_1.getText().trim();
			String sex=radioButton.isSelected()?"m":"f";
			int age=Integer.parseInt(comboBox.getSelectedItem().toString());
			String signature=textArea.getText().trim();
			String image="images/1.gif";
			ChatUser  user=new ChatUser();
			user.setNickname(nicknname);
			user.setUsername(Long.parseLong(username));
			user.setPassword(password);
			user.setSex(sex);
			user.setAge(age);
			user.setSignature(signature);
			user.setImage(image);

			ChatMessage  registerMessage=new ChatMessage();
			registerMessage.setFrom(user);
			registerMessage.setType(ChatMessageType.REGISTER);
			try {
				out.writeObject(registerMessage);
				out.flush();
				out.reset();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			try {
				ChatMessage  result=(ChatMessage )in.readObject();
				if(result.getContent().equals("true")){
					JOptionPane.showMessageDialog(RegisterFrame.this,"注册成功!","温馨提示",JOptionPane.INFORMATION_MESSAGE);
					loginFrame.setVisible(true);
					loginFrame.getTextField().setText(username);
					loginFrame.getPasswordField().setText(password);
					RegisterFrame.this.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(RegisterFrame.this,"注册失败!","温馨提示",JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}


		});
		btnNewButton.setBounds(55, 529, 93, 23);
		contentPane.add(btnNewButton);

		button = new JButton("\u8FD4\u56DE\u767B\u5F55");
		button.addActionListener(e->{
			loginFrame.setVisible(true);
			RegisterFrame.this.setVisible(false);
		});
		button.setBounds(154, 529, 93, 23);
		contentPane.add(button);
	}
}
