package com.oracle.littlechat.client.view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("\u804A\u5929\u4E3B\u7A97\u53E3");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 260, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("D:\\workspace\\git\\javaNet2\\JavaNet2\\images\\default.jpg").getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setBounds(10, 10, 80, 80);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("\u6635\u79F0");
		label.setBounds(115, 10, 54, 15);
		contentPane.add(label);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("\u4E2A\u6027\u7B7E\u540D");
		textArea.setBounds(115, 35, 92, 59);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 234, 432);
		contentPane.add(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
	}
}
