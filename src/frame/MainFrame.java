package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import utils.ClassNameTreeCellRenderer;
import utils.EditorUtils;
import utils.FrameFactory;
import utils.ReadLinkmanXMl;
import utils.ReceiveMailTable;

public class MainFrame extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static JDesktopPane desktopPane = null;// ���ڴ������ĵ�������������������
	public static MainFrame MAINFRAME;
	private JTree tree;// ����ͼ
	private JList jl;// ��ϵ���б�
	private JPanel panel, panelframe;// panelframe��벿����
	private JLabel labelbackground;
	private JScrollPane scrollPane;
	private JMenuItem exitMI = null;
	private JMenuItem newMailMI = null;
	private JMenuItem sendedMI = null;
	private JMenuItem receiveMI = null;
	private JMenuItem recycleMI = null;
	private JMenuItem refreshMI = null;
	private JButton addLinkmanButton = null;// �����ϵ�˰�ť
	private JMenu mailMenu = null;
	private ReadLinkmanXMl readLinkman = null;

	// ��ʼ����������
	public void jFrameValidate() {
		Toolkit tk = getToolkit();// �����Ļ�Ŀ�͸�
		Dimension dim = tk.getScreenSize();
		this.setBounds(dim.width / 2 - 490, dim.height / 2 - 300, 996, 658);
		validate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public MainFrame() {
		super("�����ʼ�");
		MAINFRAME = this;
		this.setIconImage(EditorUtils.createIcon("email.png").getImage());
		desktopPane = new JDesktopPane();
		jFrameValidate();// ��ʼ������
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		final JMenu fileMenu = new JMenu("�ļ�(F)");
		mailMenu = new JMenu("�ʼ�(M)");
		menuBar.add(fileMenu);
		menuBar.add(mailMenu);

		exitMI = addMenuItem(fileMenu, "�˳�", "exit.gif");// �˳��˵���ĳ�ʼ��
		newMailMI = addMenuItem(mailMenu, "�½��ʼ�", "newMail.gif");// �½��ʼ��˵���ĳ�ʼ��
		sendedMI = addMenuItem(mailMenu, "�ѷ���", "sended.png");// �ѷ����ʼ��˵���ĳ�ʼ��
		receiveMI = addMenuItem(mailMenu, "�ռ���", "receive.png");// �ռ����ʼ��˵���ĳ�ʼ��
		recycleMI = addMenuItem(mailMenu, "��ɾ��", "deleted.png");// ��ɾ���ʼ��˵���ĳ�ʼ��
		refreshMI = addMenuItem(mailMenu, "ˢ���ռ���", "refresh.jpg");// ��ɾ���ʼ��˵���ĳ�ʼ��

		// �������νڵ�
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("�����ʼ�ϵͳ");
		DefaultMutableTreeNode inbox = new DefaultMutableTreeNode("�ռ���");
		DefaultMutableTreeNode send = new DefaultMutableTreeNode("���ʼ�");
		DefaultMutableTreeNode AlreadySend = new DefaultMutableTreeNode("�ѷ����ʼ�");
		DefaultMutableTreeNode delete = new DefaultMutableTreeNode("��ɾ���ʼ�");
		root.add(send);
		root.add(inbox);
		root.add(AlreadySend);
		root.add(delete);

		tree = new JTree(root);
		tree.addMouseListener(this);// Ϊ���νڵ�ע������¼�
		tree.setPreferredSize(new Dimension(160, 150));
		// ������Ⱦ���νڵ�
		ClassNameTreeCellRenderer render = new ClassNameTreeCellRenderer();
		tree.setCellRenderer(render);
		// ��ϵ�����
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(160, 300));
		// ������벿���
		panelframe = new JPanel();
		panelframe.setLayout(new BorderLayout());
		panelframe.add(panel, BorderLayout.CENTER);
		panelframe.add(tree, BorderLayout.NORTH);

		addLinkmanButton = new JButton();
		addLinkmanButton.setText("��ϵ��(C)");
		addLinkmanButton.setIcon(EditorUtils.createIcon("linkman.png"));
		panel.add(addLinkmanButton, BorderLayout.NORTH);
		addLinkmanButton.addActionListener(this);// ע�������ϵ���¼�
		readLinkman = new ReadLinkmanXMl();
		jl = readLinkman.makeList();// ������ϵ���б�
		jl.addMouseListener(this);// �����ϵ���б�˫���¼�
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(jl);// �ڹ�������������ϵ��
		validate();

		labelbackground = new JLabel();
		labelbackground.setIcon(null); // ���屳��
		desktopPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				Dimension size = e.getComponent().getSize();
				labelbackground.setSize(e.getComponent().getSize());
				labelbackground.setText("<html><img width=" + size.width
						+ " height=" + size.height + " src='"
						+ this.getClass().getResource("/main.jpg")
						+ "'></html>");
			}
		});
		desktopPane.add(labelbackground, new Integer(Integer.MIN_VALUE));

		// ���һ���ָ��
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panelframe, desktopPane);
		splitPane.setOneTouchExpandable(true);// �ڷָ������ṩһ�� UI С����������չ��/�۵��ָ���
		splitPane.setDividerSize(10);// ���÷ָ����Ĵ�С
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}

	// �����½��˵���
	private JMenuItem addMenuItem(JMenu menu, String name, String icon) {
		// �½��ʼ��˵���ĳ�ʼ��
		JMenuItem menuItem = new JMenuItem(name, EditorUtils.createIcon(icon));
		menuItem.addActionListener(this);// �����˳��˵����¼�
		menu.add(menuItem);
		return menuItem;
	}

	// ����Ӵ���ķ���
	public static void addIFame(JInternalFrame iframe) {
		JInternalFrame[] frames = desktopPane.getAllFrames();
		try {
			for (JInternalFrame ifm : frames) {
				if (ifm.getTitle().equals(iframe.getTitle())) {
					desktopPane.selectFrame(true);
					ifm.toFront();
					ifm.setSelected(true);
					return;
				}
			}
			desktopPane.add(iframe);
			iframe.setSelected(true);
			iframe.toFront();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	// action�¼��Ĵ���
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitMI) {
			System.exit(0);// �˳�ϵͳ
		} else if (e.getSource() == addLinkmanButton) {
			addIFame(FrameFactory.getFrameFactory().getAddLinkManFrame());// ��ϵ���б�
		} else if (e.getSource() == newMailMI) {// �½��ʼ�
			addIFame(FrameFactory.getFrameFactory().getSendFrame());// ������
		} else if (e.getSource() == itemPopupOne || e.getSource() == refreshMI) {// �Ҽ�ˢ���ռ��б�
			ReceiveMailTable.getMail2Table().startReceiveMail();// �Ҽ�ˢ���ռ��б�
		} else if (e.getSource() == sendedMI) {// �ѷ���
			addIFame(FrameFactory.getFrameFactory().getSendedFrame());// �ѷ���
		} else if (e.getSource() == receiveMI) {// ���ʼ�
			addIFame(FrameFactory.getFrameFactory().getReceiveFrame());// ���ʼ�
		} else if (e.getSource() == recycleMI) {// ��ɾ��
			addIFame(FrameFactory.getFrameFactory().getRecycleFrame());// ���ʼ�
		}

	}

	private SendFrame sendFrame = null;// �����ʼ�����
	public JMenuItem itemPopupOne = null;// ����Ҽ���һ��ѡ��

	@Override
	public void mouseClicked(MouseEvent e) {
		// ���νڵ��еĵ����¼�
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		if (e.getSource() == tree && e.getButton() != 3 && e.getButton() != 2) {
			if (selectedNode == null)
				return;
			else if (selectedNode.toString().equals("���ʼ�")) {
				sendFrame = FrameFactory.getFrameFactory().getSendFrame();
				addIFame(sendFrame);// ������
			} else if (selectedNode.toString().equals("�ռ���")) {
				addIFame(FrameFactory.getFrameFactory().getReceiveFrame());// �ռ���
			} else if (selectedNode.toString().equals("�ѷ����ʼ�")) {
				addIFame(FrameFactory.getFrameFactory().getSendedFrame());// �ѷ����ʼ�
			} else if (selectedNode.toString().equals("��ɾ���ʼ�")) {
				addIFame(FrameFactory.getFrameFactory().getRecycleFrame());// ��ɾ���ʼ�
			}
		} else if (e.getSource() == jl && e.getClickCount() == 2) {// ˫����ϵ���¼�
			int index = jl.getSelectedIndex();
			if (sendFrame != null && sendFrame.isSelected()) {// ��������ʼ����汻��ʼ�����ұ�����
				sendFrame.addLinkman(readLinkman.findLinkman(index));
			}
		} else if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == tree) {// �ռ����Ҽ�ˢ��
			if (selectedNode == null)
				return;
			else if ("�ռ���".equals(selectedNode.toString())) {
				JPopupMenu popup = new JPopupMenu();
				itemPopupOne = new JMenuItem("ˢ���ռ���",
						EditorUtils.createIcon("refresh.jpg"));
				itemPopupOne.addActionListener(this);
				popup.add(itemPopupOne);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
