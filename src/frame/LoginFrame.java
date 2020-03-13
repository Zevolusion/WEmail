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
 * ��¼ҳ��
 */
public class LoginFrame extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JComboBox pop3;// ���ʼ������������б�
	private JComboBox smtp;// ���ʼ������������б�
	private JTextField nameTF;
	private JPasswordField passwordTF;
	private JButton loginButton = null;
	private JButton resetButton = null;
	private String username = null;
	private String password = null;
	private String popHost = null;
	private String smtpHost = null;// SMTP������
	private ProgressBarFrame progressBar = null;// ������ʵ��

	public LoginFrame() {
		super();
		this.setIconImage(EditorUtils.createIcon("email.png").getImage());
		getContentPane().setLayout(null);
		jFrameValidate();
		setTitle("��¼����");
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 775, 575);
		backgroundLabel.setText("<html><img width=775 height=575 src='"
				+ this.getClass().getResource("/loginBg.jpg") + "'></html>");
		backgroundLabel.setLayout(null);

		final JLabel smtpLable = new JLabel();
		smtpLable.setText("SMTP ��������");
		smtpLable.setBounds(245, 203, 100, 18);
		backgroundLabel.add(smtpLable);

		final JLabel pop3Label = new JLabel();
		pop3Label.setText("POP3 ��������");
		pop3Label.setBounds(245, 243, 100, 18);
		backgroundLabel.add(pop3Label);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("�������ƣ�");
		nameLabel.setBounds(245, 283, 100, 18);
		backgroundLabel.add(nameLabel);

		final JLabel passwordLable = new JLabel();
		passwordLable.setText("���룺");
		passwordLable.setBounds(245, 323, 100, 18);
		backgroundLabel.add(passwordLable);

		// �������������ַ�б�
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

		// �ռ����������ַ�б�
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

		loginButton = new JButton("��¼");
		loginButton.setBounds(285, 365, 80, 30);
		loginButton.addActionListener(this);
		backgroundLabel.add(loginButton);
		
		resetButton = new JButton("����");
		resetButton.setBounds(405, 365, 80, 30);
		resetButton.addActionListener(this);
		backgroundLabel.add(resetButton);
		
		getContentPane().add(backgroundLabel);

		progressBar = new ProgressBarFrame(this, "��¼", "��¼�У����Ժ�...");
		reset();// Ĭ�ϳ�ʼֵ
	}

	public void jFrameValidate() {
		Toolkit tk = getToolkit();// �����Ļ�Ŀ�͸�
		Dimension dim = tk.getScreenSize();
		this.setResizable(false);
		this.setBounds(dim.width / 2 - 400, dim.height / 2 - 300, 829, 658);
		validate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ��¼ �������¼��Ĵ���
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {// ��¼
			progressBar.setVisible(true);// ���ý������ɼ�
			new Thread() {
				public void run() {
					getValues();// �õ������е��������ֵ
					checkUser();// ��¼��֤
				}
			}.start();
		} else if (e.getSource() == resetButton) {// ����
			reset();// �������ø����ֵ
		}
//		this.dispose();// �ͷű�������Դ
//		new MainFrame().setVisible(true);
	}

	// �õ������е��������ֵ
	@SuppressWarnings("deprecation")
	private void getValues() {
		smtpHost = (String) smtp.getSelectedItem();
		popHost = (String) pop3.getSelectedItem();
		username = nameTF.getText().trim();
		password = passwordTF.getText().trim();
	}

	// �������ø����ֵ
	private void reset() {
		smtp.setSelectedIndex(2);
		pop3.setSelectedIndex(2);
		nameTF.setText("");
		passwordTF.setText("");
	}

	// ��¼��֤
	private void checkUser() {
		LoginAction login = new LoginAction(smtpHost, popHost, username,
				password);
		if (login.isLogin()) {// ��¼�ɹ�
			progressBar.dispose();
			new CheckNewMail().start();// ��ʼ������ʼ�
			this.dispose();// �ͷű�������Դ
			new MainFrame().setVisible(true);
		} else {// ��¼ʧ��
			progressBar.setVisible(false);
			JOptionPane.showMessageDialog(this, "<html><h4>"
					+ "��¼ʧ�ܣ������������û����������Ƿ���ȷ��" + "<html><h4>", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// �����б�ı�ʱ���¼�����
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
