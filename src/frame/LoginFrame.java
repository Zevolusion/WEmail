package frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import utils.CheckNewMail;
import utils.EditorUtils;
import action.LoginAction;

/**
 * 登录页面
 */
public class LoginFrame extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JComboBox pop3;// 收邮件服务器下拉列表
	private JComboBox smtp;// 发邮件服务器下拉列表
	private JTextField nameTF;
	private JPasswordField passwordTF;
	private JButton loginButton = null;
	private JButton resetButton = null;
	private String username = null;
	private String password = null;
	private String popHost = null;
	private String smtpHost = null;// SMTP服务器
	private ProgressBarFrame progressBar = null;// 进度条实例

	public LoginFrame() {
		super();
		this.setIconImage(EditorUtils.createIcon("email.png").getImage());
		getContentPane().setLayout(null);
		jFrameValidate();
		setTitle("登录邮箱");
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 775, 575);
		backgroundLabel.setText("<html><img width=775 height=575 src='"
				+ this.getClass().getResource("/loginBg.jpg") + "'></html>");
		backgroundLabel.setLayout(null);

		final JLabel smtpLable = new JLabel();
		smtpLable.setText("SMTP 服务器：");
		smtpLable.setBounds(245, 203, 100, 18);
		backgroundLabel.add(smtpLable);

		final JLabel pop3Label = new JLabel();
		pop3Label.setText("POP3 服务器：");
		pop3Label.setBounds(245, 243, 100, 18);
		backgroundLabel.add(pop3Label);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("邮箱名称：");
		nameLabel.setBounds(245, 283, 100, 18);
		backgroundLabel.add(nameLabel);

		final JLabel passwordLable = new JLabel();
		passwordLable.setText("密码：");
		passwordLable.setBounds(245, 323, 100, 18);
		backgroundLabel.add(passwordLable);

		// 发件箱服务器地址列表
		String[] smtpAdd = { "smtp.163.com", "smtp.126.com", "smtp.yeah.net",
				"smtp.qq.com", "smtp.sina.com", "smtp.sohu.com",
				"smtp.139.com", "smtp.mail.yahoo.cn", "smtp.sogou.com",
				"smtp.189.cn", "smtp.live.com","smtp.gmail.com" };
		smtp = new JComboBox(smtpAdd);
		smtp.setSelectedIndex(0);
		smtp.setEditable(true);
		smtp.addItemListener(this);
		smtp.setBounds(380, 203, 150, 22);
		backgroundLabel.add(smtp);

		// 收件箱服务器地址列表
		String[] pop3Add = { "pop.163.com", "pop.126.com", "pop.yeah.net",
				"pop.qq.com", "pop.sina.com", "pop3.sohu.com", "pop.139.com",
				"pop.mail.yahoo.cn", "pop3.sogou.com",
				"pop.189.cn", "pop3.live.com", "pop.gmail.com" };
		pop3 = new JComboBox(pop3Add);
		pop3.setSelectedIndex(0);
		pop3.addItemListener(this);
		pop3.setEditable(true);
		pop3.setBounds(380, 243, 150, 22);
		backgroundLabel.add(pop3);

		nameTF = new JTextField();
		nameTF.setBounds(380, 283, 150, 22);
		backgroundLabel.add(nameTF);

		passwordTF = new JPasswordField();
		passwordTF.setBounds(380, 323, 150, 22);
		backgroundLabel.add(passwordTF);

		loginButton = new JButton("登录");
		loginButton.setBounds(285, 365, 80, 30);
		loginButton.addActionListener(this);
		backgroundLabel.add(loginButton);
		
		resetButton = new JButton("重置");
		resetButton.setBounds(405, 365, 80, 30);
		resetButton.addActionListener(this);
		backgroundLabel.add(resetButton);
		
		getContentPane().add(backgroundLabel);

		progressBar = new ProgressBarFrame(this, "登录", "登录中，请稍后...");
		reset();// 默认初始值
	}

	public void jFrameValidate() {
		Toolkit tk = getToolkit();// 获得屏幕的宽和高
		Dimension dim = tk.getScreenSize();
		this.setResizable(false);
		this.setBounds(dim.width / 2 - 400, dim.height / 2 - 300, 829, 658);
		validate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 登录 和重置事件的处理
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {// 登录
			progressBar.setVisible(true);// 设置进度条可见
			new Thread() {
				public void run() {
					getValues();// 得到界面中的所有项的值
					checkUser();// 登录验证
				}
			}.start();
		} else if (e.getSource() == resetButton) {// 重置
			reset();// 重新设置各项的值
		}
//		this.dispose();// 释放本窗口资源
//		new MainFrame().setVisible(true);
	}

	// 得到界面中的所有项的值
	@SuppressWarnings("deprecation")
	private void getValues() {
		smtpHost = (String) smtp.getSelectedItem();
		popHost = (String) pop3.getSelectedItem();
		username = nameTF.getText().trim();
		password = passwordTF.getText().trim();
	}

	// 重新设置各项的值
	private void reset() {
		smtp.setSelectedIndex(2);
		pop3.setSelectedIndex(2);
		nameTF.setText("");
		passwordTF.setText("");
	}

	// 登录验证
	private void checkUser() {
		LoginAction login = new LoginAction(smtpHost, popHost, username,
				password);
		if (login.isLogin()) {// 登录成功
			progressBar.dispose();
			new CheckNewMail().start();// 开始检测新邮件
			this.dispose();// 释放本窗口资源
			new MainFrame().setVisible(true);
		} else {// 登录失败
			progressBar.setVisible(false);
			JOptionPane.showMessageDialog(this, "<html><h4>"
					+ "登录失败，请检查主机、用户名、密码是否正确！" + "<html><h4>", "警告",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// 下拉列表改变时的事件处理
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == smtp) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& smtp.getSelectedIndex() != -1)
				pop3.setSelectedIndex(smtp.getSelectedIndex());
		} else if (e.getSource() == pop3) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& pop3.getSelectedIndex() != -1)
				smtp.setSelectedIndex(pop3.getSelectedIndex());
		}

	}
}
