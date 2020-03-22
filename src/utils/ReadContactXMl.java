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
 * 读取通讯录列表信息
 */
public class ReadContactXMl {
	private static Vector<Vector<String>> contacts = ContactListTabelModel
			.getVector();

	// 读取联系人信息
	public void readXMl(String fileName, Vector<Vector<String>> contactVector) {
		try {
			String path = System.getProperty("user.dir");
			fileName = path + "/" + fileName;
			File f = new File(fileName);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);// 读取联系人列表
			Element root = doc.getRootElement();
			Element foo = null;
			for (Iterator i = root.elementIterator("contact"); i.hasNext();) {// 遍历每一个结点
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

	static DefaultListModel dl = new DefaultListModel();// 联系人列表模型

	// 联系人列表 用于主页面显示
	public JList makeList() {
		JList contactList = null;

		try {
			contacts.removeAllElements();
			readXMl("contact.xml", contacts);
			int contactsCount = contacts.size();
			if (contactsCount != 0) {
				for (int i = 0; i < contactsCount; i++) {
					String name = contacts.get(i).get(0);// 得到第i个联系人的姓名
					if (name != null && !"".equals(name)) {
						dl.addElement(name);// 将联系人姓名添加到列表名模型中
					} else {
						String email = contacts.get(i).get(2);// 得到联系人的email
						dl.addElement(email);
					}
				}
				contactList = new JList(dl);
			} else {
				dl.addElement("暂时没有联系人");
				contactList = new JList(dl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;// 返回联系人列表
	}

	// 根据姓名或邮箱地址查找联系人
	public String findContact(int index) {
		String contact = "";
		String name = (String) dl.get(index);// 得到联系人模型中的联系人姓名
		int contactsCount = contacts.size();
		for (int i = 0; i < contactsCount; i++) {
			String lkmname = contacts.get(i).get(0);// 得到第i个联系人的姓名
			if (lkmname.equals(name)) {
				String email = contacts.get(i).get(2);// 得到联系人的email
				contact = name + " <" + email + ">;";
				break;
			}
		}
		return contact;
	}
}
