package utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 保存联系人为xml文件
 */
public class SaveContacts2XML {
	private Document document = null;// xml文档对象
	private Element contactsElement = null;// 创建根元素

	// 保存联系人为xml文档
	public boolean saveContactXml(String fileName,
			Vector<Vector<String>> contactVector) {
		boolean isSave = false;
		if (contactVector.size() > 0) {
			initXml();// 初始化xml文档
			Iterator<Vector<String>> iterator = contactVector.iterator();
			while (iterator.hasNext()) {
				Vector<String> vector = iterator.next();
				String name = vector.get(0);// 得到姓名
				String nickname = vector.get(1);// 得到昵称
				String email = vector.get(2);// 得到邮箱地址
				saveContactInfor(name, nickname, email);
			}
			saveXMLFile(fileName);// 保存联系人xml文件
			isSave = true;// 保存成功
		}
		return isSave;

	}

	// 初始化xml文档
	private void initXml() {
		// 使用 DocumentHelper 类创建一个文档实例。 DocumentHelper 是生成 XML 文档节点的 dom4j API
		// 工厂类。
		document = DocumentHelper.createDocument();
		// 使用 addElement() 方法创建根元素 catalog 。addElement() 用于向 XML 文档中增加元素。
		contactsElement = document.addElement("contacts");
		// 在 contacts 元素中使用 addComment() 方法添加注释“An XML catalog”。
		contactsElement.addComment("我的联系人列表！");
	}

	private void saveContactInfor(String name, String nickname,
			String emailadress) {
		// 在 contacts 元素中使用 addElement() 方法增加 contact 元素。
		Element contactElement = contactsElement.addElement("contact");

		// 添加节点contact的子节点name；
		Element nameElement = contactElement.addElement("name");
		nameElement.setText(name);

		// 添加节点contact的子节点nickname
		Element nicknameElement = contactElement.addElement("nickname");
		nicknameElement.setText(nickname);

		// 添加节点contact的子节点emailadress
		Element emailadressElement = contactElement.addElement("emailadress");
		emailadressElement.setText(emailadress);
	}

	// 保存通讯录xml文件
	private void saveXMLFile(String fileName) {
		XMLWriter output;
		try {
			OutputFormat format = new OutputFormat();
			format.setEncoding("gbk");
			String path = System.getProperty("user.dir");
			fileName = path + "/" + fileName;
			output = new XMLWriter(new FileWriter(new File(fileName)), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
