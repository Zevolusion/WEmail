package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLEditorKit;

import utils.EditorUtils;
import utils.MailTableModel;
import utils.ReceiveMailTable;

/**
 * �����ʼ����桢����վ���桢�ѷ��ͽ���ĸ���
 */
public class BaseReceiceFrame extends JInternalFrame implements MouseListener,
		ActionListener {
	public JTable table;
	private JScrollPane scrollPane_1;
	private JPanel panel;
	public JTextPane mailContent;// �ʼ����ݵ���ʾ
	public MailTableModel tableModel = null;;
	public ReceiveMailTable mail2Table = null;
	public JMenuItem itemPopupOne = null;
	public JMenuItem itemPopupTwo = null;
	public JMenuItem itemPopupThree = null;
	private String popupOne = "��һ��";
	private String popupTwo = "�ڶ���";
	private String popupThree = "������";
	private String icon1 = null;
	private String icon2 = null;
	private String icon3 = null;
	private ProgressBarFrame progressBar = null;// ������ʵ��
	private boolean isThree = false;

	// �����Ҽ���һ������
	public void setPopupOne(String popupOne, String name) {
		icon1 = name;
		this.popupOne = popupOne;
	}

	// �����Ҽ��ڶ�������
	public void setPopupTwo(String popupTwo, String name) {
		icon2 = name;
		this.popupTwo = popupTwo;
	}

	// �����Ҽ�����������
	public void setPopupThree(String popupThree, String name, boolean isThree) {
		icon3 = name;
		this.isThree = isThree;
		this.popupThree = popupThree;
	}

	public BaseReceiceFrame(String title) {
		this.setLayout(new BorderLayout());
		setTitle(title);
		setClosable(true);// �����Ƿ����ͨ��ĳ���û������رմ�JInternalFrame
		setIconifiable(true);
		setMaximizable(true);// �����������
		setResizable(true);// ���ô��ڿ��Ե�����С
		setBounds(0, 0, 760, 531);// ���ý���Ĵ�С
		final JScrollPane scrollPane = new JScrollPane();// ����һ���յģ����ӿڵ���ͼ��JScrollPane��
		scrollPane.setPreferredSize(new Dimension(520, 155));
		table = new JTable();
		table.addMouseListener(this);
		scrollPane.setViewportView(table);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(520, 200));
		// getContentPane().add(panel, BorderLayout.CENTER);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(520, 190));
		mailContent = new JTextPane();// ��������ͼ�η�ʽ��ʾ����������ǵ��ı����
		HTMLEditorKit kit = new HTMLEditorKit();// �½�HTMLEditorKit
		mailContent.setEditorKit(kit);// ����EditorKitΪHTMLEditorKit
		mailContent.setContentType("text/html");
		scrollPane_1.setViewportView(mailContent);
		panel.add(scrollPane_1, BorderLayout.CENTER);

		// ���һ���ָ��
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scrollPane, panel);
		splitPane.setOneTouchExpandable(true);// �ڷָ������ṩһ�� UI С����������չ��/�۵��ָ���
		splitPane.setDividerSize(10);// ���÷ָ����Ĵ�С��
		getContentPane().add(splitPane, BorderLayout.CENTER);
		setVisible(true);
		// ��ӳ�����֧��
		mailContent.setEditable(false);
		mailContent// HyperlinkListener �����Ӽ�����
				.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
					public void hyperlinkUpdate(
							javax.swing.event.HyperlinkEvent e) {
						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							try {
								String command = "cmd /c start "
										+ e.getDescription();
								Runtime.getRuntime().exec(command);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});

	}

	public void mouseClicked(MouseEvent e) {
		int selectRom = table.getSelectedRow();
		if (e.getClickCount() == 2) {// ˫��ʱ��ʾ�ʼ�
			if (selectRom != -1) {// ���ѡ��һ��
				doubleClick(selectRom);
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (selectRom >= 0 && selectRom < tableModel.getRowCount()) {// �ж�ѡ�����Ƿ���Ч
				JPopupMenu popup = new JPopupMenu();
				itemPopupOne = new JMenuItem(popupOne);
				itemPopupOne.addActionListener(this);
				itemPopupOne.setIcon(EditorUtils.createIcon(icon1));

				itemPopupTwo = new JMenuItem(popupTwo);
				itemPopupTwo.addActionListener(this);
				itemPopupTwo.setIcon(EditorUtils.createIcon(icon2));

				popup.add(itemPopupOne);
				popup.add(itemPopupTwo);
				if (isThree) {// �Ƿ��е�����ѡ��
					itemPopupThree = new JMenuItem(popupThree);
					itemPopupThree.setIcon(EditorUtils.createIcon(icon3));
					itemPopupThree.addActionListener(this);
					popup.addSeparator();// ��ӷָ��
					popup.add(itemPopupThree);// ����������ӵ��˵���
				}
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * ˫���¼�����Ӧ
	 * 
	 * @param selectRom
	 *            ѡ����к�
	 */
	public void doubleClick(int selectRom) {
	}

	// ɾ���ʼ����¼�����
	@Override
	public void actionPerformed(ActionEvent e) {
		actionEvent(e);
	}

	/**
	 * �����¼��Ĵ���
	 * 
	 * @param e
	 */
	public void actionEvent(ActionEvent e) {
		final int[] selectRoms = table.getSelectedRows();// ѡ�ж���
		if (e.getSource() == this.itemPopupOne) {// ɾ���ʼ�
			delete(selectRoms, 1);
		} else if (e.getSource() == this.itemPopupTwo) {// ����ɾ���ʼ�
			new Thread() {// �����µ��߳�ɾ���ʼ�
				public void run() {
					popupTwoisSelected(selectRoms, ReceiveMailTable.list);
				}
			}.start();
		} else if (e.getSource() == this.itemPopupThree) {// ������˵�
			popupThreeisSelected();
		}
	}

	// �Ҽ�������ѡ�ѡ��
	private void popupThreeisSelected() {
		ReceiveMailTable.getMail2Table().startReceiveMail();// �Ҽ�ˢ���ռ��б�
	}

	/**
	 * �Ҽ��ڶ���ѡ�ѡ�У� ����ɾ���ʼ���
	 * 
	 * @param selectRoms
	 */
	public void popupTwoisSelected(int[] selectRoms, List list) {
		String message = "";
		progressBar = new ProgressBarFrame(MainFrame.MAINFRAME, "ɾ���ʼ�",
				"����ɾ���ʼ������Ժ�...");
		progressBar.setVisible(true);
		int i = 0;
		for (i = 0; i < selectRoms.length// ѭ��ɾ��ÿ��
				&& selectRoms[i] < tableModel.getRowCount(); i++)
			;// ���ж�ÿ�е���Ч��
		if (ReceiveMailTable.deleteMailForever(selectRoms, i, list)) {// ����ɾ���ʼ�
			delete(selectRoms, 2);
			message = "�ʼ�ɾ���ɹ���";
		} else {
			message = "�ʼ�ɾ��ʧ�ܣ�";
		}
		progressBar.dispose();
		JOptionPane.showMessageDialog(this, message, "��ʾ",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// ��������ɾ����ɾ������
	public void delete(int[] selectRoms, int mailState) {// mailState �ж��ʼ���ɾ��״̬

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
