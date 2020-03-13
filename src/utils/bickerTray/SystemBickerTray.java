package utils.bickerTray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import mailutil.CheckNewMialUtil;
import utils.EditorUtils;
import utils.ReceiveMailTable;

/**
 * ϵͳ����������
 */
public class SystemBickerTray extends Thread {
	private SystemTray sysTray = null;// ��ǰ����ϵͳ�����̶���
	private TrayIcon trayIcon;// ��ǰ���������
	private ImageIcon icon = null;// ����ͼ��
	private ImageIcon nullIcon = null;// �յ�ͼ��ͼ��
	private boolean flag = false; // �Ƿ������ʼ�
	private PopupMenu popupMenu = null;// �����˵�
	private Image nullimage = null;
	private Image iconImage = null;

	public SystemBickerTray() {
		sysTray = SystemTray.getSystemTray();// ��õ�ǰ����ϵͳ�����̶���
		icon = EditorUtils.createIcon("e.png");// ����ͼ��
		nullIcon = new ImageIcon("");// ��ͼƬ����
		nullimage = nullIcon.getImage();
		iconImage = icon.getImage();
		createPopupMenu();
		trayIcon = new TrayIcon(icon.getImage(), "WeMial", popupMenu);
		trayIcon.addMouseListener(new MouseAdapter() {// ϵͳ�����������¼�
			public void mouseClicked(MouseEvent e) {
				mouseAction(e);// ����¼��Ĵ���
			}
		});
		addTrayIcon();// ��������ӵ�����ϵͳ������
	}

	// �������ʼ�����
	public void setCount(int count) {
		trayIcon.setToolTip("���� " + count + " �����ʼ�������գ�");
	}

	/**
	 * ������̵ķ���
	 */
	public void addTrayIcon() {
		try {
			sysTray.add(trayIcon);// ��������ӵ�����ϵͳ������
			this.start();// �����̷߳�����ʾ�º�ͼƬ����
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * ���������˵�
	 */
	private void createPopupMenu() {
		popupMenu = new PopupMenu();// �����˵�
		MenuItem refresh = new MenuItem("ˢ���ռ���");
		MenuItem cancle = new MenuItem("ȡ����˸");
		MenuItem exit = new MenuItem("�˳�ϵͳ");
		popupMenu.add(refresh);
		popupMenu.add(cancle);
		popupMenu.add(exit);
		refresh.addActionListener(new ActionListener() {// ˢ���ռ���
			public void actionPerformed(ActionEvent e) {
				refreshInbox(e);// ˢ���ռ���
			}
		});
		cancle.addActionListener(new ActionListener() {// ȡ����˸
			public void actionPerformed(ActionEvent e) {
				cancleBicker(e);// ȡ����˸
			}
		});
		exit.addActionListener(new ActionListener() {// �˳�����
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// �˳�ϵͳ
			}
		});
	}

	// �����߳�
	public void run() {
		while (true) {
			if (flag) { // ���ʼ�
				try {
					trayIcon.setImage(nullimage);
					sleep(500);
					// ������Ϣ�Ŀհ�ʱ��
					trayIcon.setImage(iconImage);
					sleep(500);
					// ������Ϣ����ʾͼƬ
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (trayIcon.getImage().equals(nullimage))
					trayIcon.setImage(iconImage);
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// ˢ�������¼�����
	private void refreshInbox(ActionEvent e) {
		ReceiveMailTable.getMail2Table().startReceiveMail();// �Ҽ�ˢ���ռ��б�
		setCount(0);// ������ʾ���ʼ�����
		CheckNewMialUtil.isCheck = true;// �������ʼ����
		flag = false;
	}

	// ȡ�� ��˸�¼�����
	private void cancleBicker(ActionEvent e) {
		flag = false;
		CheckNewMialUtil.isCheck = true;// �������ʼ����
	}

	// ����¼��Ĵ���
	private void mouseAction(MouseEvent e) {
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		if (flag)// ��������ʼ� ��������
			new MyAudioPlayer().play();
		this.flag = flag;
	}

	public static void main(String[] args) throws InterruptedException {
		SystemBickerTray bickerTray = new SystemBickerTray();
		for (int i = 0; i < 10; i++) {
			bickerTray.setFlag(true);
			Thread.sleep(3000);

		}
	}
}
