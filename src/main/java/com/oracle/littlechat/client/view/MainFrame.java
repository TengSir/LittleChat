package com.oracle.littlechat.client.view;

import com.oracle.littlechat.client.model.ChatUser;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainFrame extends JFrame {
	private ChatUser user;

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private final JLabel label;
	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	private final JTree tree;

	/**
	 * Create the frame.
	 */
	public MainFrame(ChatUser user) {
		this.user=user;

		setTitle("\u804A\u5929\u4E3B\u7A97\u53E3");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 260, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource(user.getImage())).getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setBounds(10, 10, 80, 80);
		contentPane.add(lblNewLabel);

		label = new JLabel(user.getNickname());
		label.setBounds(115, 10, 100, 15);
		contentPane.add(label);

		textArea = new JTextArea();
		textArea.setText(user.getSignature());
		textArea.setEnabled(false);
		textArea.setBounds(115, 35, 100, 80);
		textArea.setLineWrap(true);
		contentPane.add(textArea);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 234, 432);
		contentPane.add(scrollPane);

		//在主窗口的构造器里，动态读取好友列表，然后将好友列表挂载到jtree上，用来显示所有的好友

		DefaultMutableTreeNode root=new DefaultMutableTreeNode("所有好友");
		for(ChatUser u:user.getFriends()){
			DefaultMutableTreeNode friend=new DefaultMutableTreeNode(u.getUsername()+"("+u.getNickname()+")");
			root.add(friend);
		}

		tree = new JTree(root);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2&&e.getButton()==1){//判断当用户鼠标左键双击时应该执行代码
					DefaultMutableTreeNode  yourChoice=(DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();//通过jtree方法获取当前鼠标双击的是哪一个节点
					if(((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()).isLeaf()) {
						System.out.println("您点击的好友名称是："+yourChoice);
						String username=yourChoice.toString().substring(0,yourChoice.toString().indexOf("("));
						ChatUser friend=null;
						for(ChatUser u:user.getFriends()){
							if(u.getUsername()==Long.parseLong(username)){
								friend=u;
								break;
							}
						}
						System.out.println(friend);
						ChatFrame  c=new ChatFrame(friend,user);
						c.setVisible(true);
					}
				}
			}
		});
		scrollPane.setViewportView(tree);
	}
}
