package utils;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JTable;

/**
 * ��˵�����ָ��ʼ���
 * 
 * @author ����:user
 * @version ����ʱ�䣺2011-2-22 ����06:37:03
 */
public class RecycleMailTable {
	private static RecycleMailTable recycleMail = new RecycleMailTable();;
	private Vector<Vector<String>> recycleMailVector = null;// �ָ��ʼ��б�ģ��
	private JTable recycleMailTable = null;// �ָ��ʼ����
	private MailTableModel tableModel = null;
	public static LinkedList<String> listCopy = new LinkedList<String>();

	private RecycleMailTable() {
	}

	public static RecycleMailTable getRecycleMail() {
		return recycleMail;
	}

	// ���ûָ��ʼ����
	public void setRecycleMailTable(JTable table) {
		recycleMailTable = table;
		recycleMailTable.updateUI();
	}

	// ���ɾ���ʼ���Ϣ���ָ��б�ģ����
	public void addRecycleMail(Vector<String> vector, String mailID) {
		recycleMailVector.addElement(vector);// ��Ҫɾ�����ʼ���ӵ�����վ�б���
		listCopy.add(mailID);
		if (recycleMailTable != null)
			recycleMailTable.updateUI();
	}

	// �ָ��ʼ�
	public void recycleMail(int id) {
		ReceiveMailTable.recoverMail(recycleMailVector.get(id), listCopy.get(id));
	}

	// ɾ���ʼ�
	public void deleteMail(int id) {// id ѡ����к�
		recycleMailVector.removeElementAt(id);// ɾ�������ѡ���ʼ���Ӧ����
		listCopy.remove(id);
		if (recycleMailTable != null)
			recycleMailTable.updateUI();// ��̬�ĸ��±��
	}

	// �ʼ����ģ��
	public MailTableModel getMailTableModel() {// �ʼ����ģ��
		if (tableModel == null)
			tableModel = new MailTableModel();
		recycleMailVector = tableModel.getVector();
		return tableModel;
	}

}
