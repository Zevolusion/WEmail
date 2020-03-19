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
public class ReadLinkmanXMl {
	private static Vector<Vector<String>> linkmans = LinkmanListTabelModel
			.getVector();

	// ��ȡ��ϵ����Ϣ
	public void readXMl(String fileName, Vector<Vector<String>> linkmanVector) {
		try {
			String path = System.getProperty("user.dir");
			fileName = path + "/" + fileName;
			File f = new File(fileName);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);// ��ȡ��ϵ���б�
			Element root = doc.getRootElement();
			Element foo = null;
			for (Iterator i = root.elementIterator("linkman"); i.hasNext();) {// ����ÿһ�����
				foo = (Element) i.next();
				Vector<String> vector = new Vector<String>();
				vector.add(foo.elementText("name"));
				vector.add(foo.elementText("nickname"));
				vector.add(foo.elementText("emailadress"));
				linkmanVector.add(vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static DefaultListModel dl = new DefaultListModel();// ��ϵ���б�ģ��

	// ��ϵ���б� ������ҳ����ʾ
	public JList makeList() {
		JList linkmanList = null;

		try {
			linkmans.removeAllElements();
			readXMl("linkman.xml", linkmans);
			int linkmansCount = linkmans.size();
			if (linkmansCount != 0) {
				for (int i = 0; i < linkmansCount; i++) {
					String name = linkmans.get(i).get(0);// �õ���i����ϵ�˵�����
					if (name != null && !"".equals(name)) {
						dl.addElement(name);// ����ϵ��������ӵ��б���ģ����
					} else {
						String email = linkmans.get(i).get(2);// �õ���ϵ�˵�email
						dl.addElement(email);
					}
				}
				linkmanList = new JList(dl);
			} else {
				dl.addElement("��ʱû����ϵ��");
				linkmanList = new JList(dl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return linkmanList;// ������ϵ���б�
	}

	// ���������������ַ������ϵ��
	public String findLinkman(int index) {
		String linkman = "";
		String name = (String) dl.get(index);// �õ���ϵ��ģ���е���ϵ������
		int linkmansCount = linkmans.size();
		for (int i = 0; i < linkmansCount; i++) {
			String lkmname = linkmans.get(i).get(0);// �õ���i����ϵ�˵�����
			if (lkmname.equals(name)) {
				String email = linkmans.get(i).get(2);// �õ���ϵ�˵�email
				linkman = name + " <" + email + ">;";
				break;
			}
		}
		return linkman;
	}
}
