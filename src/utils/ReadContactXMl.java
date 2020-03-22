package utils;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��ȡͨѶ¼�б���Ϣ
 */
public class ReadContactXMl {
	private static Vector<Vector<String>> contacts = ContactListTabelModel
			.getVector();

	// ��ȡ��ϵ����Ϣ
	public void readXMl(String fileName, Vector<Vector<String>> contactVector) {
		try {
			String path = System.getProperty("user.dir");
			fileName = path + "/" + fileName;
			File f = new File(fileName);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);// ��ȡ��ϵ���б�
			Element root = doc.getRootElement();
			Element foo = null;
			for (Iterator i = root.elementIterator("contact"); i.hasNext();) {// ����ÿһ�����
				foo = (Element) i.next();
				Vector<String> vector = new Vector<String>();
				vector.add(foo.elementText("name"));
				vector.add(foo.elementText("nickname"));
				vector.add(foo.elementText("emailadress"));
				contactVector.add(vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static DefaultListModel dl = new DefaultListModel();// ��ϵ���б�ģ��

	// ��ϵ���б� ������ҳ����ʾ
	public JList makeList() {
		JList contactList = null;

		try {
			contacts.removeAllElements();
			readXMl("contact.xml", contacts);
			int contactsCount = contacts.size();
			if (contactsCount != 0) {
				for (int i = 0; i < contactsCount; i++) {
					String name = contacts.get(i).get(0);// �õ���i����ϵ�˵�����
					if (name != null && !"".equals(name)) {
						dl.addElement(name);// ����ϵ��������ӵ��б���ģ����
					} else {
						String email = contacts.get(i).get(2);// �õ���ϵ�˵�email
						dl.addElement(email);
					}
				}
				contactList = new JList(dl);
			} else {
				dl.addElement("��ʱû����ϵ��");
				contactList = new JList(dl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;// ������ϵ���б�
	}

	// ���������������ַ������ϵ��
	public String findContact(int index) {
		String contact = "";
		String name = (String) dl.get(index);// �õ���ϵ��ģ���е���ϵ������
		int contactsCount = contacts.size();
		for (int i = 0; i < contactsCount; i++) {
			String lkmname = contacts.get(i).get(0);// �õ���i����ϵ�˵�����
			if (lkmname.equals(name)) {
				String email = contacts.get(i).get(2);// �õ���ϵ�˵�email
				contact = name + " <" + email + ">;";
				break;
			}
		}
		return contact;
	}
}
