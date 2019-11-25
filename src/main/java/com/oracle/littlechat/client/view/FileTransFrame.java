package com.oracle.littlechat.client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;

public class FileTransFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileTransFrame frame = new FileTransFrame();
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
	public FileTransFrame() {
		setTitle("\u6587\u4EF6\u4F20\u8F93\u7A97\u53E3");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u9009\u62E9\u6587\u4EF6");
		button.setBounds(10, 10, 93, 23);
		contentPane.add(button);
		
		JLabel label = new JLabel("\u6587\u4EF6\u4FE1\u606F");
		label.setBounds(10, 49, 54, 15);
		contentPane.add(label);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(69, 87, 169, 14);
		contentPane.add(progressBar);
		
		JLabel lblNewLabel = new JLabel("\u4F20\u8F93\u8FDB\u5EA6");
		lblNewLabel.setBounds(10, 87, 54, 15);
		contentPane.add(lblNewLabel);
		
		JButton button_1 = new JButton("\u53D1\u9001");
		button_1.setBounds(20, 115, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\u5173\u95ED");
		button_2.setBounds(123, 115, 93, 23);
		contentPane.add(button_2);
	}
}
