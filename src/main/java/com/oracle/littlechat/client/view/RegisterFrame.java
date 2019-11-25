package com.oracle.littlechat.client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class RegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		setResizable(false);
		setTitle("\u804A\u5929\u6CE8\u518C\u7A97\u53E3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D");
		label.setBounds(55, 57, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBounds(55, 106, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_2.setBounds(55, 154, 54, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u6635\u79F0");
		label_3.setBounds(55, 205, 54, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u6027\u522B");
		label_4.setBounds(55, 248, 54, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u5E74\u9F84");
		label_5.setBounds(55, 303, 54, 15);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u4E2A\u6027\u7B7E\u540D");
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
		
		JRadioButton radioButton = new JRadioButton("\u7537");
		radioButton.setSelected(true);
		radioButton.setBounds(144, 259, 53, 23);
		contentPane.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.setBounds(199, 259, 48, 23);
		contentPane.add(radioButton_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		comboBox.setBounds(144, 300, 103, 21);
		contentPane.add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(146, 353, 101, 55);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("\u6CE8\u518C");
		btnNewButton.setBounds(55, 429, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\u8FD4\u56DE\u767B\u5F55");
		button.setBounds(154, 429, 93, 23);
		contentPane.add(button);
	}
}
