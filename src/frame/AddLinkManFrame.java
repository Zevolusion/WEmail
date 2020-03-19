package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.EditorUtils;
import utils.LinkmanListTabelModel;
import action.LinkmanEventAction;

public class AddLinkManFrame extends JInternalFrame implements ActionListener,
		DocumentListener {
	private JTextField emailAdressTF = null;
	private JTextField nickNameTF = null;
	private JTextField nameTF = null;
	private JLabel nameLabel = null;
	private JLabel nickNameLabel = null;
	private JLabel emailAdressLabel = null;
	private JButton add = null;
	private JButton delete = null;
	private JButton ok = null;
	private JButton cancel = null;
	private JScrollPane linkmanJSP;
	private JTable linkmanList = null;// ��ϵ���б�
	private JPanel editorJPanel = null;// �༭���
	private JPanel linkManListJPanel = null;// ��ϵ���б����
	private Box baseBox = null;
	private Box boxV1 = null;
	private Box boxV2 = null;
	private LinkmanEventAction linkmanEvent = null;

	public AddLinkManFrame() {
		this.setFrameIcon(EditorUtils.createIcon("addLinkman.png"));
		setClosable(true);
		setIconifiable(true);
		setBounds(0, 0, 593, 531);
		getContentPane().setLayout(new BorderLayout());
		// ����
		nameLabel = new JLabel();
		nameLabel.setText("������");
		getContentPane().add(nameLabel);
		nameTF = new JTextField(50);
		getContentPane().add(nameTF);

		// �ǳ�
		nickNameLabel = new JLabel();
		nickNameLabel.setText("�ǳƣ�");
		getContentPane().add(nickNameLabel);
		nickNameTF = new JTextField(50);
		getContentPane().add(nickNameTF);
		// �����ʼ�
		emailAdressLabel = new JLabel();
		emailAdressLabel.setText("�����ʼ���");
		getContentPane().add(emailAdressLabel);
		emailAdressTF = new JTextField(50);
		emailAdressTF.getDocument().addDocumentListener(this);

		linkmanList = new JTable();// ��ϵ���б�
		linkmanList.setModel(new LinkmanListTabelModel());
		linkmanJSP = new JScrollPane(linkmanList);
		linkmanJSP.setPreferredSize(new Dimension(400, 150));

		add = new JButton("���");
		add.addActionListener(this);
		add.setEnabled(false);
		delete = new JButton("ɾ��");
		delete.addActionListener(this);
		ok = new JButton("ȷ��");
		ok.addActionListener(this);
		cancel = new JButton("ȡ��");
		cancel.addActionListener(this);
		// �����ϵ�����
		editorJPanel = new JPanel();
		editorJPanel.setBorder(BorderFactory.createTitledBorder("�����ϵ�ˣ�"));
		editorJPanel.add(box());
		this.add(editorJPanel, BorderLayout.NORTH);

		// ���ɾ����ϵ�����
		JPanel addAadDeletePanel = new JPanel();
		addAadDeletePanel.setPreferredSize(new Dimension(70, 150));
		addAadDeletePanel.add(add);
		addAadDeletePanel.add(new JLabel("   "));
		addAadDeletePanel.add(delete);
		// ��ϵ���б����
		JPanel linkmanPanel = new JPanel();
		linkmanPanel.add(linkmanJSP);

		linkManListJPanel = new JPanel(new BorderLayout());
		linkManListJPanel.setBorder(BorderFactory.createTitledBorder("��ϵ���б�"));
		linkManListJPanel.add(linkmanPanel, BorderLayout.CENTER);
		linkManListJPanel.add(addAadDeletePanel, BorderLayout.EAST);
		this.add(linkManListJPanel, BorderLayout.CENTER);

		// ȷ��ȡ�����
		JPanel okAndCancelPanel = new JPanel();
		okAndCancelPanel.setPreferredSize(new Dimension(500, 50));
		okAndCancelPanel.add(ok);
		okAndCancelPanel.add(new JLabel("   "));
		okAndCancelPanel.add(cancel);
		this.add(okAndCancelPanel, BorderLayout.SOUTH);
		setVisible(true);
		linkmanEvent = new LinkmanEventAction(nameTF, nickNameTF,
				emailAdressTF, linkmanList);
	}

	private Box box() {
		// ������ǩbox
		boxV1 = Box.createVerticalBox();
		boxV1.add(nameLabel);
		boxV1.add(Box.createVerticalStrut(10));
		boxV1.add(nickNameLabel);
		boxV1.add(Box.createVerticalStrut(14));
		boxV1.add(emailAdressLabel);
		boxV1.add(Box.createVerticalStrut(12));

		// �����ı���box
		boxV2 = Box.createVerticalBox();
		boxV2.add(nameTF);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(nickNameTF);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(emailAdressTF);

		// ��������box
		baseBox = Box.createHorizontalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createHorizontalStrut(20));
		baseBox.add(boxV2);
		boxV2.add(Box.createVerticalStrut(8));
		return baseBox;
	}

	public void changedUpdate(DocumentEvent e) {
		checkInput();// �ı�����ʱ����
	}

	public void insertUpdate(DocumentEvent e) {
		checkInput();// �������
	}

	public void removeUpdate(DocumentEvent e) {
		checkInput();// �Ƴ�����
	}

	// ����������ݵ���Ч��
	private void checkInput() {
		boolean checkEmail = EditorUtils.checkEmailAdress(emailAdressTF
				.getText().trim());
		if (checkEmail) {
			add.setEnabled(true);
		} else {
			add.setEnabled(false);
		}
	}

	// �¼��Ĵ���
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			linkmanEvent.addLinkman();// �����ϵ��
		} else if (e.getSource() == delete) {
			linkmanEvent.deleteLinkman(linkmanList.getSelectedRow());// ɾ����ϵ��
		} else if (e.getSource() == ok) {
			linkmanEvent.ok();
		} else if (e.getSource() == cancel) {
			this.dispose();// �رմ���
		}
	}
}
