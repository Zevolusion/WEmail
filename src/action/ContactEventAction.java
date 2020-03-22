package action;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frame.MainFrame;

import utils.ContactListTabelModel;
import utils.SaveContacts2XML;

/**
 * �������ɾ����ϵ���¼�
 */
public class ContactEventAction {
	Vector<Vector<String>> contactVectors = ContactListTabelModel.getVector();
	private JTextField name = null, nickName = null, emailAdress = null;
	private JTable contactList = null;

	public ContactEventAction(JTextField nameTF, JTextField nickNameTF,
			JTextField emailAdressTF, JTable contactList) {
		name = nameTF;// ����
		nickName = nickNameTF;// �ǳ�
		emailAdress = emailAdressTF;// �����ַ
		this.contactList = contactList;
	}

	// �����ϵ��
	public void addContact() {
		if (!checkRepeatEmail(emailAdress.getText().trim()))// ���email��ַ���ظ�
			add();// ���
		else {
			JOptionPane.showMessageDialog(null, "����ӵ������ַ�Ѵ��ڣ������ظ���ӣ�", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// ɾ����ϵ��
	public void deleteContact(int selectRow) {
		if (selectRow < contactVectors.size() && selectRow != -1) {// ѡ��һ��ɾ��
			contactVectors.remove(selectRow);
			contactList.updateUI();
		} else {
			JOptionPane.showMessageDialog(null, "��û��ѡ���κ�һ�в���ɾ����", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// ȷ���޸���ϵ�˲�����ϵ�˱���Ϊxml��ʽ���ĵ�
	public void ok() {
		SaveContacts2XML saveContactsXML = new SaveContacts2XML();
		saveContactsXML.saveContactXml("contact.xml", contactVectors);
		JOptionPane.showMessageDialog(null, "ͨѶ¼�޸ĳɹ����ļ����� contact.xml", "��ʾ",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// �����ϵ��
	private void add() {
		Vector<String> contactVector = new Vector<String>();
		contactVector.add(name.getText().trim());
		contactVector.add(nickName.getText().trim());
		contactVector.add(emailAdress.getText().trim());
		contactVectors.add(contactVector);
		contactList.updateUI();
	}

	// ��������Ƿ��ظ�
	private boolean checkRepeatEmail(String email) {
		boolean isRepeate = true;
		Vector<String> v = null;
		int count = contactVectors.size();// �õ���ϵ�˸���
		if (count > 0) {// �������ϵ�˾ͱȽ�
			for (int i = 0; i < count; i++) {
				v = contactVectors.get(i);// �õ���ϵ�������ַ
				if (v.get(2).equals(email))// �¼ӵĺ����еĵ�ַ�Ƿ��ظ�
					isRepeate = true;
				else
					isRepeate = false;
			}
		} else
			// ���û�о�ֱ�����
			isRepeate = false;
		return isRepeate;
	}
}
