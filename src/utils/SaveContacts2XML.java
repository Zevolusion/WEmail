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
 * ������ϵ��Ϊxml�ļ�
 */
public class SaveContacts2XML {
	private Document document = null;// xml�ĵ�����
	private Element contactsElement = null;// ������Ԫ��

	// ������ϵ��Ϊxml�ĵ�
	public boolean saveContactXml(String fileName,
			Vector<Vector<String>> contactVector) {
		boolean isSave = false;
		if (contactVector.size() > 0) {
			initXml();// ��ʼ��xml�ĵ�
			Iterator<Vector<String>> iterator = contactVector.iterator();
			while (iterator.hasNext()) {
				Vector<String> vector = iterator.next();
				String name = vector.get(0);// �õ�����
				String nickname = vector.get(1);// �õ��ǳ�
				String email = vector.get(2);// �õ������ַ
				saveContactInfor(name, nickname, email);
			}
			saveXMLFile(fileName);// ������ϵ��xml�ļ�
			isSave = true;// ����ɹ�
		}
		return isSave;

	}

	// ��ʼ��xml�ĵ�
	private void initXml() {
		// ʹ�� DocumentHelper �ഴ��һ���ĵ�ʵ���� DocumentHelper ������ XML �ĵ��ڵ�� dom4j API
		// �����ࡣ
		document = DocumentHelper.createDocument();
		// ʹ�� addElement() ����������Ԫ�� catalog ��addElement() ������ XML �ĵ�������Ԫ�ء�
		contactsElement = document.addElement("contacts");
		// �� contacts Ԫ����ʹ�� addComment() �������ע�͡�An XML catalog����
		contactsElement.addComment("�ҵ���ϵ���б�");
	}

	private void saveContactInfor(String name, String nickname,
			String emailadress) {
		// �� contacts Ԫ����ʹ�� addElement() �������� contact Ԫ�ء�
		Element contactElement = contactsElement.addElement("contact");

		// ��ӽڵ�contact���ӽڵ�name��
		Element nameElement = contactElement.addElement("name");
		nameElement.setText(name);

		// ��ӽڵ�contact���ӽڵ�nickname
		Element nicknameElement = contactElement.addElement("nickname");
		nicknameElement.setText(nickname);

		// ��ӽڵ�contact���ӽڵ�emailadress
		Element emailadressElement = contactElement.addElement("emailadress");
		emailadressElement.setText(emailadress);
	}

	// ����ͨѶ¼xml�ļ�
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
