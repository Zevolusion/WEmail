package utils;

import mailutil.CheckNewMialUtil;
import utils.bickerTray.SystemBickerTray;

/**
 * ������ʼ���
 */
public class CheckNewMail extends Thread {
	private static int MailCount = 0;// ���ʼ�����������
	private SystemBickerTray bickerTray = null;// ϵͳ����ͼ��
	private int num = 0;// �õ����������ʼ��ĸ���
	private CheckNewMialUtil check = null;

	public CheckNewMail() {
		bickerTray = new SystemBickerTray();// ϵͳ����ͼ��
		check = new CheckNewMialUtil();
	}

	public void run() {
		while (true) {
			try {
				num = check.checkNewMail();
				if (num > MailCount) {
					if (!bickerTray.isFlag()) {
						bickerTray.setFlag(true);
					}
					bickerTray.setCount(num - MailCount);// ������ʾ���ʼ�����
				}
				sleep(1500);// ��ͣ1��
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ������ʾ���ʼ�����
	public static void setNewMailCount(int count) {
		MailCount = count;
	}
}
