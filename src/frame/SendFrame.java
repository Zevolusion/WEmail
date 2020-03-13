package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import mailutil.SendAttachMail;
import utils.EditorPopupMenu;
import utils.EditorUtils;
import utils.SendedMailTable;

/**
 * �����ʼ�����
 */
public class SendFrame extends JInternalFrame implements ActionListener,
		MouseListener, MouseMotionListener, FocusListener {
	private JComboBox fontSizeCB;// �����С�б�
	private JComboBox fontCB;// �����б�
	private JTextPane sendCotent;// �����������
	private JTextField subjectTF;// �ʼ������ı���
	private JTextField copy_to;// ����
	private JTextField to_mail;// �ռ���
	private JList attachmentList = null;// �����б�����������������
	private JScrollPane scrollPane = null;// ���ı༭����
	private JScrollPane jsp = null;// ������ʾ����
	private DefaultListModel listmodel = null;// �����б�ģ��
	private JLabel adjunctL = null;// ������ǩ
	private JLabel to_mailLabel = null;
	private JLabel copy_toLabel = null;
	private JLabel subject_Label = null;
	private JButton sendButton = null;// ���Ͱ�ť
	private JButton resetButton = null;// ����
	private JButton attachmentButton = null;// ���븽����ť
	private JButton selectColorButton = null;// ��ɫѡ��ť
	private Box baseBox = null;
	private Box boxV1 = null;
	private Box boxV2 = null;
	private ArrayList<String> attachArrayList = new ArrayList<String>();// ���ڴ洢����·��������
	private Color color = Color.black;
	// ���Զ���
	private Action boldAction = new StyledEditorKit.BoldAction();// ��ӼӴ�������
	private Action underlineAction = new StyledEditorKit.UnderlineAction(); // ��Ӽ��»���������
	private Action italicAction = new StyledEditorKit.ItalicAction(); // �����б������
	private HTMLDocument document = null;// ����һ����ҳ�ĵ��������
	private SendAttachMail sendMail = null;
	private EditorPopupMenu rightMouseButton = null;
	private ProgressBarFrame progressBar = null;// ������ʵ��

	public SendFrame() {
		super("���ʼ�");
		this.setFrameIcon(EditorUtils.createIcon("newMail.gif"));
		// ��ʼ��������
		getContentPane().setLayout(new BorderLayout());// ���ÿղ���
		setIconifiable(true);// �Ƿ�ʹ JInternalFrame ���һ��ͼ��
		setClosable(true);// �Ƿ�ر�
		setMaximizable(true);// �����������
		setResizable(true);// ���ô��ڿ��Ե�����С
		setBounds(0, 0, 760, 531);// ���ý���Ĵ�С
		setVisible(true);

		// �����ռ��˱�ǩ
		to_mailLabel = new JLabel();
		to_mailLabel.setText("�ռ��ˣ�");
		// ���ͱ�ǩ
		copy_toLabel = new JLabel();
		copy_toLabel.setText("���ͣ�");
		// �����ǩ
		subject_Label = new JLabel();
		subject_Label.setText("���⣺");
		// �ռ����ı���
		to_mail = new JTextField(80);
		to_mail.addFocusListener(this);
		to_mail.setToolTipText("���ռ��˵�ַ�Զ��ŷָ�");
		// �����ı���
		copy_to = new JTextField(80);
		copy_to.addFocusListener(this);
		// �����ı���
		subjectTF = new JTextField(80);
		JPanel setPanel = new JPanel();// �ϰ벿
		setPanel.add(box());
		scrollPane = new JScrollPane();

		sendCotent = new JTextPane();
		sendCotent.setContentType("text/html");
		HTMLEditorKit editorKit = new HTMLEditorKit();
		document = (HTMLDocument) editorKit.createDefaultDocument();// ����Ĭ���ĵ�ָ����ҳ����document
		sendCotent.setEditorKit(editorKit);// ����Ϊhtml��ʽ�ı༭��
		sendCotent.setDocument(document);
		sendCotent.addMouseListener(this);
		scrollPane.setViewportView(sendCotent);
		// ������
		final JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar);
		sendButton = new JButton("����", EditorUtils.createIcon("newsend.gif"));
		sendButton.addActionListener(this);
		toolBar.add(sendButton);
		resetButton = new JButton("��д", EditorUtils.createIcon("rewrite.gif"));
		resetButton.addActionListener(this);
		toolBar.add(resetButton);
		// �����б�
		listmodel = new DefaultListModel();
		adjunctL = new JLabel("������");
		jsp = new JScrollPane();// ������ʾJList
		jsp.setPreferredSize(new Dimension(350, 35));
		attachmentList = new JList(listmodel);
		attachmentList.addMouseListener(this);// Ϊ�ʼ��б��������¼�
		jsp.setViewportView(attachmentList);// ����JScrollPanel����ͼΪJList
		attachmentList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		attachmentList.setVisibleRowCount(1);
		attachmentList.setLayoutOrientation(JList.VERTICAL_WRAP);
		// ���븽����ť
		attachmentButton = new JButton("���븽��",
				EditorUtils.createIcon("attach.png"));
		attachmentButton.addActionListener(this);
		toolBar.add(attachmentButton);
		// б�尴ť
		JButton italicButton = new JButton(italicAction);
		italicButton.setIcon(EditorUtils.createIcon("italic.gif"));
		italicButton.setText("");
		italicButton.setPreferredSize(new Dimension(22, 22));
		// ���尴ť
		JButton blodButton = new JButton(boldAction);
		blodButton.setIcon(EditorUtils.createIcon("blod.gif"));
		blodButton.setText("");
		blodButton.setPreferredSize(new Dimension(22, 22));
		// �»��߰�ť
		JButton underlineButton = new JButton(underlineAction);
		underlineButton.setIcon(EditorUtils.createIcon("underline.gif"));
		underlineButton.setText("");
		underlineButton.setPreferredSize(new Dimension(22, 22));

		// ����
		final JLabel fontLabel = new JLabel("����");
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();// ��ñ��� �������������õ�����
		String font[] = ge.getAvailableFontFamilyNames();
		fontCB = new JComboBox(font);
		fontCB.addActionListener(this);
		// �ֺ��б�
		final JLabel fontSizeLabel = new JLabel("�ֺ�");
		String fontSize[] = { "10", "11", "12", "13", "14", "16", "18", "20",
				"22", "24", "26", "28", "36", "48" };
		fontSizeCB = new JComboBox(fontSize);
		fontSizeCB.addActionListener(this);
		fontSizeCB.setPreferredSize(new Dimension(50, 23));

		// ��ɫ
		final JLabel colorLabel = new JLabel("��ɫ");
		selectColorButton = new JButton("ѡ    ɫ");
		selectColorButton.addActionListener(this);

		JPanel editorToolBarPanel = new JPanel();// �༭��������
		editorToolBarPanel.add(italicButton);
		editorToolBarPanel.add(blodButton);
		editorToolBarPanel.add(underlineButton);
		editorToolBarPanel.add(new JLabel("   "));
		editorToolBarPanel.add(fontLabel);
		editorToolBarPanel.add(fontCB);
		editorToolBarPanel.add(new JLabel("   "));
		editorToolBarPanel.add(fontSizeLabel);
		editorToolBarPanel.add(fontSizeCB);
		editorToolBarPanel.add(new JLabel("   "));
		editorToolBarPanel.add(colorLabel);
		editorToolBarPanel.add(selectColorButton);
		// �༭�����
		JPanel editorPanel = new JPanel(new BorderLayout());// �༭��
		editorPanel.add(editorToolBarPanel, BorderLayout.NORTH);
		editorPanel.add(scrollPane, BorderLayout.CENTER);
		// ���һ���ָ��
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				setPanel, editorPanel);
		splitPane.setOneTouchExpandable(true);// �ڷָ������ṩһ�� UI С����������չ��/�۵��ָ���
		splitPane.setDividerSize(10);// ���÷ָ����Ĵ�С��

		// ��������༭��
		JPanel framePanel = new JPanel(new BorderLayout());// �༭��
		framePanel.add(splitPane, BorderLayout.CENTER);
		this.add(framePanel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.NORTH);
		rightMouseButton = new EditorPopupMenu(sendCotent);
	}

	private Box box() {
		// ������ǩbox
		boxV1 = Box.createVerticalBox();
		boxV1.add(to_mailLabel);
		boxV1.add(Box.createVerticalStrut(16));
		boxV1.add(copy_toLabel);
		boxV1.add(Box.createVerticalStrut(16));
		boxV1.add(subject_Label);

		// �����ı���box
		boxV2 = Box.createVerticalBox();
		boxV2.add(to_mail);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(copy_to);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(subjectTF);

		// ��������box
		baseBox = Box.createHorizontalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createHorizontalStrut(20));
		baseBox.add(boxV2);
		boxV1.add(Box.createVerticalStrut(16));
		boxV2.add(Box.createVerticalStrut(8));
		return baseBox;
	}

	// ��ť�¼��Ĵ���
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectColorButton) {// ѡ����ɫ
			color = JColorChooser.showDialog(this, "��ѡ����ɫ", Color.black);
			Action colorAction = new StyledEditorKit.ForegroundAction(
					"set-foreground-", color);// �����ɫ������
			if (color != null)
				colorAction.actionPerformed(new ActionEvent(color, 0,
						sendCotent.getSelectedText()));
		} else if (e.getSource() == fontCB) {// ��������
			String font = (String) fontCB.getSelectedItem();
			Action fontAction = new StyledEditorKit.FontFamilyAction(font, font);
			fontAction.actionPerformed(new ActionEvent(fontAction, 0,
					sendCotent.getSelectedText()));
		} else if (e.getSource() == fontSizeCB) {// �����С����
			String fontsize = (String) fontSizeCB.getSelectedItem();
			Action fontSizeAction = new StyledEditorKit.FontSizeAction(
					fontsize, Integer.parseInt(fontsize));
			fontSizeAction.actionPerformed(new ActionEvent(fontSizeAction, 0,
					sendCotent.getSelectedText()));
		} else if (e.getSource() == resetButton) {// ���ð�ť�¼�
			reset();
		} else if (e.getSource() == attachmentButton) {// ���븽��
			addAttachment();// ���븽��
		} else if (e.getSource() == sendButton) {// �����ʼ�
			getSendMailInfo();// �����ʼ�
		}
	}

	// ����¼�����
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == attachmentList && e.getButton() == 3) {// ��갴��getButton()��������1��ʾ��������̣�2��ʾ�����м��̣�3��ʾ�����Ҽ���
			deleteAttachment(e);// ɾ������
		} else if (e.getSource() == sendCotent && e.getButton() == 3) {// ��갴��getButton()��������1��ʾ��������̣�2��ʾ�����м��̣�3��ʾ�����Ҽ���
			rightMouseButton.rightMouseButton(e);// ����Ҽ���Ӧ
		}

	}

	// ����϶�
	public void mouseDragged(MouseEvent mouseevent) {

	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	}

	// ��Ӹ���
	private void addAttachment() {
		if (listmodel.getSize() >= 4) {
			JOptionPane.showMessageDialog(this, "ֻ�����4������");
			return;
		}
		File f = new File(".");// �õ���ǰĿ¼
		JFileChooser chooser = new JFileChooser(f);// ����һ����ǰ·�����ļ�ѡ����
		if (chooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION) {// ���ѡ��ȷ����
			File file = chooser.getSelectedFile();
			Icon icon = chooser.getIcon(file);
			attachmentList.setCellRenderer(new CellRender(icon));
			listmodel.addElement(file.getName());// ��������ӵ�JLIST��
			attachArrayList.add(file.getPath());// ��������·����ӵ������б���
		}
		if (listmodel.getSize() <= 1) {
			boxV1.add(adjunctL);
			boxV2.add(jsp);
		}
		validate();
		repaint();
	}

	// ɾ������
	private void deleteAttachment(MouseEvent e) {
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem itemdel = new JMenuItem("ɾ��");
		itemdel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (attachmentList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(SendFrame.this,
							"����ѡ���б�����Ҫɾ���ĸ���");
					return;
				}
				int attachmentIndex = attachmentList.getSelectedIndex();// �õ�ѡ�񸽼���������
				attachArrayList.remove(attachmentIndex);// ������·�������еĶ�Ӧֵɾ��
				listmodel.remove(attachmentIndex);// ���б�ģ���еĸ���ɾ��
			}
		});
		popup.add(itemdel);
		popup.show(e.getComponent(), e.getX(), e.getY());// ��ʾ�����˵�
	}

	// �õ������ʼ���Ϣ
	public void getSendMailInfo() {
		sendMail = SendAttachMail.getSendMailInstantiate();// ��ʼ�������ʼ�����
		String text = sendCotent.getText().trim();// ����
		String sendMan = sendMail.getUser();// ������
		String subject = subjectTF.getText().trim();// ����
		String toMan = to_mail.getText().trim();// �ռ���
		String copy = copy_to.getText().trim();// ���͵�
		SendedMailTable.getSendedMailTable().setSendFrame(this);// ���·����ʼ�ʱ����
		sendMail(toMan, subject, attachArrayList, text, copy, sendMan);// �����ʼ�
	}

	/**
	 * �����ʼ�
	 * 
	 * @param text
	 *            ��������
	 * @param sendMan
	 *            ������
	 * @param subject
	 *            ����
	 * @param toMan
	 *            �ռ���
	 */

	public void sendMail(final String toMan, final String subject,
			ArrayList<String> list, final String text, final String copy,
			final String sendMan) {
		sendMail.setContent(text);// �����ʼ�����
		sendMail.setFilename(list);// �����ʼ���������
		sendMail.setFrom(sendMan);// ���÷�����
		sendMail.setSubject(subject);// �����ʼ�����
		sendMail.setTo(toMan);// �����ռ���
		sendMail.setCopy_to(copy);// ���ó�����

		if (progressBar == null) {
			progressBar = new ProgressBarFrame(MainFrame.MAINFRAME, "�����ʼ�",
					"���ڷ����ʼ������Ժ�...");
		}
		progressBar.setVisible(true);
		new Thread() {// ����һ���µ��̷߳����ʼ�
			public void run() {
				String message = "";
				if ("".equals(message = sendMail.send())) {
					SendedMailTable.getSendedMailTable().setValues(toMan,
							subject, attachArrayList, text, copy, sendMan);// ���ʼ���ӵ��ѷ���
					message = "�ʼ��ѷ��ͳɹ���";
				} else {
					message = "<html><h4>�ʼ�����ʧ�ܣ� ʧ��ԭ��</h4></html>\n" + message;
				}
				progressBar.dispose();
				JOptionPane.showMessageDialog(SendFrame.this, message, "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}.start();
	}

	// ��ո�������ֵ
	private void reset() {
		sendCotent.setText("");
		subjectTF.setText("");
		copy_to.setText("");
		to_mail.setText("");
		attachArrayList.clear();
		listmodel.clear();
	}

	// �����ϵ�˵��ռ���
	public void addLinkman(String linkman) {
		if (focusStatic == 2) {// �жϳ����ı����Ƿ�õ�����
			setJTextFieldString(copy_to, linkman);
			copy_to.requestFocus();// �������ı���õ�����
		} else {
			to_mail.requestFocus();// �ռ����ı���õ�����
			setJTextFieldString(to_mail, linkman);
		}
	}

	// �����ı����е��ַ���
	private void setJTextFieldString(JTextField jt, String linkman) {
		String copy_toString = jt.getText();
		if (!copy_toString.endsWith(";") && !copy_toString.equals(""))
			copy_toString += ";";
		copy_toString += linkman;
		jt.setText(copy_toString);
	}

	private int focusStatic = 1;// 1 �����ռ��˵õ����㣬2�������˵õ�����

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == to_mail)
			focusStatic = 1;
		else
			focusStatic = 2;
	}

	@Override
	public void focusLost(FocusEvent e) {

	}
}
