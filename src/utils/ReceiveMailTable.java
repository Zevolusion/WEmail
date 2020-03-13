package utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import mailutil.GetMail;
import frame.ProgressBarFrame;
import frame.MainFrame;

public class ReceiveMailTable extends Thread {
	private Vector mails = null;// ����һ����������ʹ���ڲ���������Ĵ�СΪ
	// 10�����׼��������Ϊ�㡣
	private static Vector<Vector<String>> mailListVector = null;// ��ȡ�ʼ��б�ģ��
	private static GetMail getmail = null;
	private RecycleMailTable recycleMail = null;
	private static JTable deleteMailTable = null;// ɾ���ʼ����
	private MailTableModel tableModel = null;
	public static LinkedList<String> list = new LinkedList<String>();
	private static ReceiveMailTable mail2Table = null;// ����ģʽ�õ��������
	private static ProgressBarFrame progressBar = new ProgressBarFrame(
			MainFrame.MAINFRAME, "�ռ���", "���ڲ����ʼ������Ժ�...");

	private ReceiveMailTable() {
		getmail = GetMail.getMailInstantiate();
	}

	public static ReceiveMailTable getMail2Table() {// ����ģʽ�õ��������
		if (mail2Table == null) {
			progressBar.setVisible(true);
			mail2Table = new ReceiveMailTable();
			mail2Table.start();
		}
		return mail2Table;
	}

	public static boolean isInitMail2Table() {// �жϱ�������Ƿ��ʼ��
		boolean isInit = false;
		if (mail2Table != null)
			isInit = true;
		return isInit;
	}

	// �����ʼ��б���
	public void setDeleteMailTable(JTable jTable) {
		deleteMailTable = jTable;
	}

	// ˢ���ʼ��б�ʱ�õ�
	public void startReceiveMail() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisible(true);
				MainFrame.addIFame(FrameFactory.getFrameFactory()
						.getReceiveFrame());// ���ռ��������ʾ����������
			}
		});
		new Thread() {// �����µ��߳�ˢ���ʼ��б�
			public void run() {
				mailToTable();
			}
		}.start();
	}

	@Override
	public void run() {
		mailToTable();
	}

	// ���ʼ���Ϣ��ӵ������
	public void mailToTable() {
		try {
			Iterator it = getmail.getMailInfo(getmail.getAllMail()).iterator();
			Map map = null;
			// ���ʼ���Ϣ�б���ʾ�ڱ����
			if (deleteMailTable.getRowCount() > 0) {
				deleteMailTable.removeAll();// �Ƴ�����е���������
				mailListVector.removeAllElements();// �Ƴ�ģ���е���������
				list.clear();// �Ӵ��б����Ƴ�����Ԫ��
			}
			while (it.hasNext()) {
				mails = new Vector<String>();
				map = (Map) it.next();
				mails.add("<html><strong>" + map.get("sender")
						+ "</strong></html>");
				mails.add("<html><strong>" + map.get("subject")
						+ "</strong></html>");
				mails.add("<html><strong>" + map.get("senddate")
						+ "</strong></html>");
				mails.add("<html><strong>" + map.get("hasAttach")
						+ "</strong></html>");
				list.add((String) map.get("ID"));
				mailListVector.add(mails);
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					deleteMailTable.validate();// ��̬�ĸ��±��
					deleteMailTable.repaint();
					progressBar.setVisible(false);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ�ʼ�����
	public static String readMail(LinkedList<String> linkedList, int id) {// id�к�
		String message = null;
		String mailID = (String) linkedList.get(id);// ����ָ���ж�Ӧ��mailID��
		try {
			Map result = getmail.readMail(mailID);
			message = result.get("content").toString();// �õ��ʼ�����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	// ���ʼ��Ƶ�����վ
	public void moveMail2Recycle(int id) {
		recycleMail = RecycleMailTable.getRecycleMail();// ��ʼ������վ
		recycleMail.getMailTableModel();// �õ�����վ�ı��ģ��
		recycleMail.addRecycleMail(mailListVector.get(id), list.get(id));// ���ʼ��Ƶ�����վ
	}

	// ɾ���ʼ�
	public void deleteMail(int id) {// id ѡ����к�
		mailListVector.removeElementAt(id);// ɾ�������ѡ���ʼ���Ӧ����
		list.remove(id);
		deleteMailTable.updateUI();// ��̬�ĸ��±��
		deleteMailTable.validate();// ��̬�ĸ��±��
		deleteMailTable.repaint();
	}

	// �ָ��ʼ�
	public static void recoverMail(Vector<String> vector, String mailId) {
		mailListVector.add(vector);
		list.add(mailId);
		deleteMailTable.updateUI();// ��̬�ĸ��±��
		deleteMailTable.validate();// ��̬�ĸ��±��
		deleteMailTable.repaint();
	}

	// ����ɾ���ʼ�
	public static boolean deleteMailForever(int[] selectMail, int id, List list) {
		boolean isDelete = false;
		String[] mailID = new String[id];
		for (int i = 0; i < id; i++) {
			mailID[i] = (String) list.get(selectMail[i]);// ����ָ���ж�Ӧ��mailID��
		}
		isDelete = getmail.deleteMail(mailID);// ����ɾ���ʼ�
		return isDelete;
	}

	public MailTableModel getMailTableModel() {// �ʼ����ģ��
		if (tableModel == null)
			tableModel = new MailTableModel();
		mailListVector = tableModel.getVector();
		return tableModel;
	}

}
