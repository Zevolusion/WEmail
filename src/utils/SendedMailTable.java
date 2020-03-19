package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JTable;

import frame.SendFrame;

/**
 * �ѷ����ʼ����ģ��
 */
public class SendedMailTable {// ���õ���ģʽ��֤����Ψһ
	private static SendedMailTable sendedMail = new SendedMailTable();// �������
	private JTable sendedMailTable = null;// �ѷ����ʼ����
	private MailTableModel tableModel = null;
	private Vector<Vector<String>> sendedMailVector = null;// �ѷ����ʼ��б�ģ��
	private Vector<Vector<Object>> resendMailVector = new Vector<Vector<Object>>();// ���·���
	private LinkedList<String> sendedMailMessageList = new LinkedList<String>();// �ѷ����ʼ������б�

	private SendedMailTable() {
	}

	// �õ��������
	public static SendedMailTable getSendedMailTable() {
		return sendedMail;
	}

	// ���ñ��ģ��
	public MailTableModel getMailTableModel() {
		if (tableModel == null) {
			tableModel = new MailTableModel();
			tableModel.setColumens(new String[] { "�ռ���", "����", "����ʱ��", "����" });
			sendedMailVector = tableModel.getVector();
		}
		return tableModel;
	}

	// ���ñ�����
	public void setSendedMailTable(JTable table) {
		sendedMailTable = table;
		sendedMailTable.updateUI();
	}

	// ����ʼ����ѷ����б���
	public void addSendedMail(Vector<String> vector, String mailMessage) {
		sendedMailVector.add(vector);// ��Ҫɾ�����ʼ���ӵ�����վ�б���
		sendedMailMessageList.add(mailMessage);
		if (sendedMailTable != null)
			sendedMailTable.updateUI();
	}

	// ɾ���ѷ����б��е��ʼ�
	public void deleteMail(int id) {// id ѡ����к�
		sendedMailVector.removeElementAt(id);// ɾ�������ѡ���ʼ���Ӧ����
		resendMailVector.removeElementAt(id);// ɾ�����·����б��е��ʼ���Ϣ
		sendedMailMessageList.remove(id);
		if (sendedMailTable != null)
			sendedMailTable.updateUI();// ��̬�ĸ��±��
	}

	// ��ȡ��ɾ���ʼ�������
	public String readMail(int id) {
		return sendedMailMessageList.get(id);
	}

	// �����ѷ����ʼ��ĸ�������
	public void setValues(String toMan, // �ռ���
			String subject, // �ѷ����ʼ�������
			ArrayList<String> list,// �ʼ��Ƿ��и���
			String text,// �ѷ����ʼ�������
			String copyto,// ���͵�
			String sendMan) {// ������
		getMailTableModel();// ��ʼ���ѷ����ʼ��б�ģ��
		resendMail(toMan, subject, list, text, copyto, sendMan);// ���ѷ����ʼ���ӵ����·����б���
		Vector<String> sendedMail = new Vector<String>();
		sendedMail.add("<html><strong>" + toMan + "</strong></html>");
		sendedMail.add("<html><strong>" + subject + "</strong></html>");
		sendedMail.add("<html><strong>" + getTime() + "</strong></html>");// �õ�ϵͳ��ǰ��ʱ��
		if (!list.isEmpty())
			sendedMail.add("<html><strong>" + "��" + "</strong></html>");
		else
			sendedMail.add("");// û�и���
		addSendedMail(sendedMail, text);// ����ʼ����ѷ����б���
	}

	// �õ�ϵͳ��ǰ��ʱ��
	private String getTime() {
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy��MM��dd��  HH:mm-ss"); // ��ʽ����ǰϵͳ����
		return dateFm.format(new Date());
	}

	// ���ʼ���Ϣ��ӵ����·����б���
	private void resendMail(String toMan, // �ռ���
			String subject, // �ѷ����ʼ�������
			ArrayList<String> list,// �ʼ��Ƿ��и���
			String text,// �ѷ����ʼ�������
			String copyto,// ���͵�
			String sendMan) {// ������
		Vector<Object> sendedMail = new Vector<Object>();
		sendedMail.add(toMan);
		sendedMail.add(subject);
		sendedMail.add(list);
		sendedMail.add(text);
		sendedMail.add(copyto);
		sendedMail.add(sendMan);
		resendMailVector.add(sendedMail);
	}

	// ���·����ʼ�
	public void resend(int id) {
		Vector<Object> sendedMail = resendMailVector.get(id);
		sendFrame.sendMail((String) sendedMail.get(0),
				(String) sendedMail.get(1),
				(ArrayList<String>) sendedMail.get(2),
				(String) sendedMail.get(3), (String) sendedMail.get(4),
				(String) sendedMail.get(5));

	}

	// �����ʼ�����
	private SendFrame sendFrame = null;

	public void setSendFrame(SendFrame send) {
		sendFrame = send;
	}
}
